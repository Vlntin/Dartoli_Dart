package com.example.dartoli.Cricket


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.Counting.CountingGame
import com.example.dartoli.Counting.CountingGameStatisticsActivity
import com.example.dartoli.R
import com.example.dartoli.activities.MainActivity
import com.example.dartoli.activities.MultiplayerActivity
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.MatchesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCricketBinding
import com.example.dartoli.model.Match


class CricketActivity : AppCompatActivity(),View.OnClickListener {

    private lateinit var binding: ActivityCricketBinding
    private var amount = 1

    private lateinit var singleButton: TextView
    private lateinit var doubleButton: TextView
    private lateinit var tribleButton: TextView

    private lateinit var playerAdapter: PlayerStatusAdapter
    private lateinit var rvPlayerStatus: RecyclerView

    private var playingPlayers = arrayListOf<CricketPlayer>()
    private var game: CricketGame? = null
    private var game_id: Int? = null
    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCricketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        game = intent.getSerializableExtra("game") as CricketGame?
        playingPlayers = game!!.game_players

        var games = GamesDatabaseHandler(this).readAllGames()
        for(game in games){
            if (game.name.equals("Cricket")){
                description = game.description
                game_id = game.id
            }
        }
        singleButton = binding.singleBtn
        doubleButton = binding.doubleBtn
        tribleButton = binding.tripleBtn
        binding.tvActualPlayer.setText("Am Zug: " + game!!.game_players[game!!.actualPlayerNumber].playerName + " (" + game!!.actualDartsLeft.toString() + ")")

        binding.btnRules.setOnClickListener(this)
        binding.singleBtn.setOnClickListener(this)
        doubleButton.setOnClickListener(this)
        tribleButton.setOnClickListener(this)
        binding.fifteenBtn.setOnClickListener(this)
        binding.sixteenBtn.setOnClickListener(this)
        binding.seventeenBtn.setOnClickListener(this)
        binding.eightteenBtn.setOnClickListener(this)
        binding.nineteenBtn.setOnClickListener(this)
        binding.twentyBtn.setOnClickListener(this)
        binding.bullBtn.setOnClickListener(this)
        binding.missBtn.setOnClickListener(this)

        rvPlayerStatus = binding.rvRecycler
        setupPlayerStatusRecyclerView()
    }
    override fun onBackPressed() {
        startActivity(Intent(this@CricketActivity, MultiplayerActivity::class.java))
    }
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.single_btn, R.id.double_btn, R.id.triple_btn -> {
                singleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
                doubleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
                tribleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }
        when (view!!.id) {
            binding.btnRules.id -> {
                val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
                val popupView: View = inflater.inflate(R.layout.game_rules_custom_dialog, null)
                val width = LinearLayout.LayoutParams.WRAP_CONTENT
                val height = LinearLayout.LayoutParams.WRAP_CONTENT
                val focusable = true
                popupView.findViewById<TextView>(R.id.tv_game_description).setText(description)
                val popupWindow = PopupWindow(popupView, width, height, focusable)
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
                popupView.setOnTouchListener { v, event ->
                    popupWindow.dismiss()
                    true
                }
            }
            R.id.single_btn-> {
                singleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
                amount = 1
            }
            R.id.double_btn-> {
                doubleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
                amount = 2
            }
            R.id.triple_btn -> {
                tribleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
                amount = 3
            }
            binding.fifteenBtn.id -> {
                if (game!!.thrown_values(15, amount)) finishGame()
            }
            binding.sixteenBtn.id -> {
                if (game!!.thrown_values(16, amount)) finishGame()
            }
            binding.seventeenBtn.id -> {
                if (game!!.thrown_values(17, amount)) finishGame()
            }
            binding.eightteenBtn.id -> {
                if (game!!.thrown_values(18, amount)) finishGame()
            }
            binding.nineteenBtn.id -> {
                if (game!!.thrown_values(19, amount)) finishGame()
            }
            binding.twentyBtn.id -> {
                if (game!!.thrown_values(20, amount)) finishGame()
            }
            binding.bullBtn.id -> {
                if (game!!.thrown_values(25, amount)) finishGame()
            }
            binding.missBtn.id -> {
                game!!.thrown_values(0, 0)
            }
        }
        when (view!!.id) {
            binding.fifteenBtn.id, binding.sixteenBtn.id, binding.seventeenBtn.id, binding.eightteenBtn.id, binding.nineteenBtn.id, binding.twentyBtn.id,
            binding.bullBtn.id, binding.missBtn.id -> {
                binding.tvRoundNumber.setText("Runde " + game!!.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game!!.game_players[game!!.actualPlayerNumber].playerName + " (" + game!!.actualDartsLeft.toString() + ")")
                setupPlayerStatusRecyclerView()
            }
        }
    }

    private fun finishGame(){
        var customDialog = Dialog(this);
        customDialog.setContentView(R.layout.game_finished_dialog);
        var playerAdapter: GameResultPlayerStatusAdapter
        var rvPlayerStatus: RecyclerView
        rvPlayerStatus = customDialog.findViewById<RecyclerView>(R.id.rv_result_recycler)
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var sortedPlayers = setRankForPlayers(playingPlayers)
        playerAdapter = GameResultPlayerStatusAdapter(sortedPlayers)
        rvPlayerStatus.adapter = playerAdapter

        add_match_to_database(sortedPlayers)

        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.show()

        val btnClose = customDialog.findViewById<TextView>(R.id.btn_close)
        val btnStatistics = customDialog.findViewById<TextView>(R.id.btn_statistics)
        btnStatistics.visibility = View.GONE
        btnClose.setOnClickListener(){
            startActivity(Intent(this@CricketActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun add_match_to_database(sortedPlayers: ArrayList<CricketPlayer>){
        var sorted_player_ids = ArrayList<Int>()
        var won_legs_list = ArrayList<Int>()
        var won_sets_list = ArrayList<Int>()
        val data_player_list = PlayerDatabaseHandler(this).readAllPlayers()
        for (player in sortedPlayers){
            for (data_player in data_player_list){
                if (data_player.playerName.equals(player.playerName)){
                    sorted_player_ids.add(data_player.id)
                    won_legs_list.add(player.overall_won_legs)
                    won_sets_list.add((player.won_sets))
                }
            }
        }
        MatchesDatabaseHandler(this).addMatch(Match(1, game_id!!, "a", sorted_player_ids, won_legs_list, won_sets_list))
    }

    private fun setRankForPlayers(players: ArrayList<CricketPlayer>): ArrayList<CricketPlayer>{
        var sortedList = arrayListOf<CricketPlayer>()
        for (i in game!!.needed_sets downTo 0){
            var rank = sortedList.size + 1
            for (player in players){
                if (player.won_sets == i){
                    player.rank = rank
                    sortedList.add(player)
                }
            }
        }
        return sortedList
    }

    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerAdapter = PlayerStatusAdapter(playingPlayers!!, ContextCompat.getColor(this, R.color.cricket_background_color))
        rvPlayerStatus.adapter = playerAdapter
    }
}
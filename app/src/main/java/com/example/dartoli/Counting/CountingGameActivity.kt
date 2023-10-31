package com.example.dartoli.Counting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.Cricket.CricketActivity
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCountingGameBinding
import com.example.dartoli.R
import com.example.dartoli.activities.MainActivity
import com.example.dartoli.activities.MultiplayerActivity
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.MatchesDatabaseHandler
import com.example.dartoli.model.Match

class CountingGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCountingGameBinding

    private var playingPlayers = arrayListOf<CountingPlayer>()
    private var game: CountingGame? = null
    private var amount = 1
    private var game_id: Int? = null
    private var description: String? = null


    private lateinit var playerAdapter: CountingPlayerStatusAdapter
    private lateinit var rvPlayerStatus: RecyclerView

    private lateinit var customDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        game = intent.getSerializableExtra("game") as CountingGame?
        playingPlayers = game!!.game_players

        binding.tvGameTitle.text = game!!.needed_points.toString()
        binding.tvActualPlayer.setText("Am Zug: " + game!!.game_players[game!!.actualPlayerNumber].playerName + " (" + game!!.actualDartsLeft.toString() + ")")

        binding.singleBtn.setOnClickListener(this)
        binding.doubleBtn.setOnClickListener(this)
        binding.tripleBtn.setOnClickListener(this)
        binding.btnRules.setOnClickListener(this)

        binding.oneBtn.setOnClickListener(this)
        binding.twoBtn.setOnClickListener(this)
        binding.threeBtn.setOnClickListener(this)
        binding.fourBtn.setOnClickListener(this)
        binding.fiveBtn.setOnClickListener(this)
        binding.sixBtn.setOnClickListener(this)
        binding.sevenBtn.setOnClickListener(this)
        binding.eightBtn.setOnClickListener(this)
        binding.nineBtn.setOnClickListener(this)
        binding.tenBtn.setOnClickListener(this)
        binding.elevenBtn.setOnClickListener(this)
        binding.twelveBtn.setOnClickListener(this)
        binding.thirteenBtn.setOnClickListener(this)
        binding.fourteenBtn.setOnClickListener(this)
        binding.fiveteenBtn.setOnClickListener(this)
        binding.sixteenBtn.setOnClickListener(this)
        binding.seventeenBtn.setOnClickListener(this)
        binding.eightteenBtn.setOnClickListener(this)
        binding.nineteenBtn.setOnClickListener(this)
        binding.twentyBtn.setOnClickListener(this)
        binding.bullBtn.setOnClickListener(this)
        binding.missBtn.setOnClickListener(this)



        rvPlayerStatus = binding.rvRecycler
        setupPlayerStatusRecyclerView()

        set_game_informations()

    }

    // needed informations for rules and match database
    fun set_game_informations(){
        var games = GamesDatabaseHandler(this).readAllGames()
        for(local_game in games){
            if (local_game.name.equals(game!!.needed_points.toString())){
                description = local_game.description
                game_id = local_game.id
            }
        }
    }

    fun set_rules_popup_view(){
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.game_rules_custom_dialog, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true
        popupView.findViewById<TextView>(R.id.tv_game_description).setText(description)
        popupView.findViewById<TextView>(R.id.tv_game_title).setText(game!!.needed_points.toString())
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.showAtLocation(View(this), Gravity.CENTER, 0, 0)
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this@CountingGameActivity, MultiplayerActivity::class.java))
    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.btn_rules -> set_rules_popup_view()
            R.id.single_btn -> {
                binding.singleBtn.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.doubleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.tripleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                amount = 1
            }
            R.id.double_btn -> {
                binding.doubleBtn.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.singleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.tripleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                amount = 2
            }
            R.id.triple_btn-> {
                binding.tripleBtn.setTextColor(ContextCompat.getColor(this, R.color.black))
                binding.doubleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                binding.singleBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
                amount = 3
            }
            R.id.one_btn -> game!!.thrown_values(1, amount)
            R.id.two_btn -> game!!.thrown_values(2, amount)
            R.id.three_btn -> game!!.thrown_values(3, amount)
            R.id.four_btn -> game!!.thrown_values(4, amount)
            R.id.five_btn -> game!!.thrown_values(5, amount)
            R.id.six_btn -> game!!.thrown_values(6, amount)
            R.id.seven_btn -> game!!.thrown_values(7, amount)
            R.id.eight_btn -> game!!.thrown_values(8, amount)
            R.id.nine_btn -> game!!.thrown_values(9, amount)
            R.id.ten_btn -> game!!.thrown_values(10, amount)
            R.id.twelve_btn -> game!!.thrown_values(12, amount)
            R.id.eleven_btn -> game!!.thrown_values(11, amount)
            R.id.thirteen_btn -> game!!.thrown_values(13, amount)
            R.id.fourteen_btn -> game!!.thrown_values(14, amount)
            R.id.fiveteen_btn -> game!!.thrown_values(15, amount)
            R.id.sixteen_btn -> game!!.thrown_values(16, amount)
            R.id.seventeen_btn -> game!!.thrown_values(17, amount)
            R.id.eightteen_btn -> game!!.thrown_values(18, amount)
            R.id.nineteen_btn -> game!!.thrown_values(19, amount)
            R.id.twenty_btn -> game!!.thrown_values(20, amount)
            R.id.bull_btn -> if (amount < 3){
                game!!.thrown_values(25, amount)
            } else {
                return
            }
            R.id.miss_btn -> game!!.thrown_values(0, amount)
            R.id.btn_one_hit -> game!!.throws_on_double(1)
            R.id.btn_zero_hit -> game!!.throws_on_double(0)
            R.id.btn_two_hit -> game!!.throws_on_double(2)
            R.id.btn_three_hit -> game!!.throws_on_double(3)
            R.id.btn_close -> {
                startActivity(Intent(this@CountingGameActivity, MainActivity::class.java))
                finish()
            }
            R.id.btn_statistics -> {
                val intent = Intent(this@CountingGameActivity, CountingGameStatisticsActivity::class.java)
                intent.putExtra("a", game)
                startActivity(intent)
                finish()
            }

        }
        when (view!!.id){
            R.id.one_btn, R.id.two_btn, R.id.three_btn, R.id.four_btn, R.id.five_btn, R.id.six_btn, R.id.seven_btn, R.id.eight_btn, R.id.nine_btn, R.id.ten_btn,
            R.id.eleven_btn, R.id.twelve_btn, R.id.thirteen_btn, R.id.fourteen_btn, R.id.fiveteen_btn, R.id.sixteen_btn, R.id.seventeen_btn, R.id.eightteen_btn, R.id.nineteen_btn,
            R.id.twenty_btn, R.id.bull_btn, R.id.miss_btn -> {
                var potential_double_hits = game!!.check_potential_double_hit()
                if (potential_double_hits > 0){
                    dialog_for_double_throws_handling(potential_double_hits)
                }
                else {
                    game!!.finish_player_move()
                    if (game!!.check_game_state()) finishGame()
                    binding.tvRoundNumber.setText("Runde " + game!!.actualRound.toString())
                    binding.tvActualPlayer.setText("Am Zug: " + game!!.game_players[game!!.actualPlayerNumber].playerName + " (" + game!!.actualDartsLeft.toString() + ")")
                    setupPlayerStatusRecyclerView()
                }
            }
            R.id.btn_one_hit, R.id.btn_two_hit, R.id.btn_three_hit, R.id.btn_zero_hit -> {
                customDialog.dismiss()
                game!!.finish_player_move()
                if (game!!.check_game_state()) finishGame()
                binding.tvRoundNumber.setText("Runde " + game!!.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game!!.game_players[game!!.actualPlayerNumber].playerName + " (" + game!!.actualDartsLeft.toString() + ")")
                setupPlayerStatusRecyclerView()
            }
        }
    }

    private fun dialog_for_double_throws_handling(potential_double_hits: Int){
        customDialog = Dialog(this)
        customDialog.setContentView(R.layout.throws_on_double_dialog);
        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.show()

        val btnZero = customDialog.findViewById<TextView>(R.id.btn_zero_hit)
        val btnOne = customDialog.findViewById<TextView>(R.id.btn_one_hit)
        val btnTwo = customDialog.findViewById<TextView>(R.id.btn_two_hit)
        val btnThree = customDialog.findViewById<TextView>(R.id.btn_three_hit)

        btnZero.setOnClickListener(this)
        btnOne.setOnClickListener(this)
        btnTwo.setOnClickListener(this)
        btnThree.setOnClickListener(this)
        if (game!!.has_player_finished()) btnZero.visibility = View.GONE
        if (potential_double_hits < 3) btnThree.visibility = View.GONE
        if (potential_double_hits < 2) btnTwo.visibility = View.GONE
    }
    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        playerAdapter = CountingPlayerStatusAdapter(playingPlayers!!, ContextCompat.getColor(this, R.color.cricket_background_color), game!!.actualPlayerNumber)
        rvPlayerStatus.adapter = playerAdapter
    }

    private fun finishGame(){
        var customDialog = Dialog(this);
        customDialog.setContentView(R.layout.game_finished_dialog);
        var playerAdapter: GameResultCountingPlayerStatusAdapter
        var rvPlayerStatus: RecyclerView
        rvPlayerStatus = customDialog.findViewById<RecyclerView>(R.id.rv_result_recycler)
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        var sortedPlayers = setRankForPlayers(playingPlayers)
        playerAdapter = GameResultCountingPlayerStatusAdapter(sortedPlayers)
        rvPlayerStatus.adapter = playerAdapter
        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.show()
        customDialog.findViewById<TextView>(R.id.btn_close).setOnClickListener(this)
        customDialog.findViewById<TextView>(R.id.btn_statistics).setOnClickListener(this)

        add_match_to_db(sortedPlayers)
    }

    private fun add_match_to_db(sortedPlayers: ArrayList<CountingPlayer>){
        var sorted_player_ids = ArrayList<Int>()
        var won_legs_list = ArrayList<Int>()
        var won_sets_list = ArrayList<Int>()
        val players_db = PlayerDatabaseHandler(this)
        val data_player_list = players_db.readAllPlayers()
        for (player in sortedPlayers){
            for (data_player in data_player_list){
                if (data_player.playerName.equals(player.playerName)){
                    sorted_player_ids.add(data_player.id)
                    won_legs_list.add(player.won_overall_legs)
                    won_sets_list.add((player.won_sets))
                }
            }
        }
        val match = Match(1, game_id!!, "a", sorted_player_ids, won_legs_list, won_sets_list)
        val matches_db = MatchesDatabaseHandler(this)
        matches_db.addMatch(match)
    }

    private fun setRankForPlayers(players: ArrayList<CountingPlayer>): ArrayList<CountingPlayer>{
        var sortedList = arrayListOf<CountingPlayer>()
        var copied_players = arrayListOf<CountingPlayer>()
        var rank = 1
        for (player in players){
            copied_players.add(player)
        }
        while (copied_players.isNotEmpty()){
            var best_player = copied_players[0]
            if (copied_players.size > 1){
                for (i in 1..copied_players.size -1){
                    if (copied_players[i].won_sets > best_player.won_sets ||
                        (copied_players[i].won_sets == best_player.won_sets && copied_players[i].won_overall_legs > best_player.won_overall_legs) ||
                        (copied_players[i].won_sets == best_player.won_sets && copied_players[i].won_overall_legs == best_player.won_overall_legs && copied_players[i].game_average > best_player.game_average)) {
                        best_player = copied_players[i]
                    }
                }
            }
            best_player.rank = rank
            rank++
            sortedList.add(best_player)
            copied_players.remove(best_player)
        }
        return sortedList
    }
}
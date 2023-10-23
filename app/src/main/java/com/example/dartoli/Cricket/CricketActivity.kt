package com.example.dartoli.Cricket


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
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
import com.example.dartoli.R
import com.example.dartoli.activities.MainActivity
import com.example.dartoli.data.GamesDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCricketBinding


class CricketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCricketBinding
    private var amount = 1

    private lateinit var singleButton: TextView
    private lateinit var doubleButton: TextView
    private lateinit var tribleButton: TextView

    private lateinit var playerAdapter: PlayerStatusAdapter
    private lateinit var rvPlayerStatus: RecyclerView


    private var playingPlayers = arrayListOf<CricketPlayer>()

    private lateinit var game: CricketGame

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCricketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //val game = intent.getSerializableExtra("cricket_game_key" )
        var player_id_array = intent.extras?.getIntArray("players")
        var legs = intent.getIntExtra("legs", 0)
        var sets = intent.getIntExtra("sets", 0)
        val myDB = PlayerDatabaseHandler(this)
        val players_dataset = myDB.readAllPlayers()
        for (counter in 0..player_id_array!!.size - 1){
            for (player in players_dataset){
                if(player.id == player_id_array[counter]){
                    playingPlayers.add(CricketPlayer(player.playerName, 0, 0,0,0,0,0,0,0, false, false, false, false, false, false, false, 0, legs, 0, sets, 0))
                }
            }
        }
        game = CricketGame(legs, sets, playingPlayers)


        singleButton = binding.singleBtn
        doubleButton = binding.doubleBtn
        tribleButton = binding.tripleBtn


        singleButton.setOnClickListener(){
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 1
        }

        doubleButton.setOnClickListener(){
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 2
        }

        tribleButton.setOnClickListener(){
            tribleButton.setTextColor(ContextCompat.getColor(this, R.color.black))
            doubleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            singleButton.setTextColor(ContextCompat.getColor(this, R.color.white))
            amount = 3
        }

        binding.fifteenBtn.setOnClickListener(){
            if (game.thrown_values(15, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }
        binding.sixteenBtn.setOnClickListener(){
            if (game.thrown_values(16, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }
        binding.seventeenBtn.setOnClickListener(){
            if (game.thrown_values(17, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }
        binding.eightteenBtn.setOnClickListener(){
            if (game.thrown_values(18, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }
        binding.nineteenBtn.setOnClickListener(){
            if (game.thrown_values(19, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }
        binding.twentyBtn.setOnClickListener(){
            if (game.thrown_values(20, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }

        binding.bullBtn.setOnClickListener(){
            if (game.thrown_values(25, amount)) finishGame()
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }

        binding.missBtn.setOnClickListener(){
            game.thrown_values(0, 0)
            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)
            updateAdapter()
        }

        binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName)


        rvPlayerStatus = binding.rvRecycler
        setupPlayerStatusRecyclerView()

        val myDB2: GamesDatabaseHandler
        myDB2 = GamesDatabaseHandler(this)
        var games = myDB2.readAllGames()
        var description =""
        for(game in games){
            if (game.name.equals("Cricket")) description = game.description
        }
        binding.btnRules.setOnClickListener(){
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


        customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        customDialog.show()

        val btnClose = customDialog.findViewById<TextView>(R.id.btn_close)
        val btnStatistics = customDialog.findViewById<TextView>(R.id.btn_statistics)

        btnClose.setOnClickListener(){
            startActivity(Intent(this@CricketActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setRankForPlayers(players: ArrayList<CricketPlayer>): ArrayList<CricketPlayer>{
        var sortedList = arrayListOf<CricketPlayer>()
        for (player in players){
            if (player.won_sets == player.needed_sets){
                player.place = 1
                sortedList.add(player)
            }
        }
        for (i in game.needed_sets -1 downTo 0){
            var place = sortedList.size + 1
            for (player in players){
                if (player.won_sets == i){
                    player.place = place
                    sortedList.add(player)
                }
            }
        }
        return sortedList
    }

    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerAdapter = PlayerStatusAdapter(playingPlayers!!)
        rvPlayerStatus.adapter = playerAdapter
    }

    private fun updateAdapter() {
        for (i in 0..playingPlayers.size-1){
            playerAdapter.notifyItemChanged(i)
        }
    }

}
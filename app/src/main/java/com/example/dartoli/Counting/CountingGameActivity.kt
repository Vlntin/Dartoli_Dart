package com.example.dartoli.Counting

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCountingGameBinding
import com.example.dartoli.R
import com.example.dartoli.activities.MainActivity
import com.example.dartoli.data.GamesDatabaseHandler

class CountingGameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityCountingGameBinding
    private var playingPlayers = arrayListOf<CountingPlayer>()
    private lateinit var game: CountingGame

    private lateinit var singleButton: TextView
    private lateinit var doubleButton: TextView
    private lateinit var tribleButton: TextView
    private var amount = 1

    private lateinit var playerAdapter: CountingPlayerStatusAdapter
    private lateinit var rvPlayerStatus: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var player_id_array = intent.extras?.getIntArray("players")
        var legs = intent.getIntExtra("legs", 0)
        var sets = intent.getIntExtra("sets", 0)
        val myDB = PlayerDatabaseHandler(this)
        val players_dataset = myDB.readAllPlayers()
        for (counter in 0..player_id_array!!.size - 1){
            for (player in players_dataset){
                if(player.id == player_id_array[counter]){
                    playingPlayers.add(CountingPlayer(player.playerName, 501, 0,0.0,0,legs,0,sets,0, 0, arrayListOf<Int>(), 0, 0, 0.0, arrayListOf<Double>(), arrayListOf<Int>(), 0))
                }
            }
        }
        game = CountingGame(legs, sets, playingPlayers)

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

        binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")


        rvPlayerStatus = binding.rvRecycler
        setupPlayerStatusRecyclerView()

        val myDB2 = GamesDatabaseHandler(this)
        var games = myDB2.readAllGames()
        var description : String
        description = ""
        for(game in games){
            if (game.name.equals("501")) description = game.description
        }
        binding.btnRules.setOnClickListener(){
            val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.game_rules_custom_dialog, null)
            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true
            popupView.findViewById<TextView>(R.id.tv_game_description).setText(description)
            popupView.findViewById<TextView>(R.id.tv_game_title).setText("501")
            val popupWindow = PopupWindow(popupView, width, height, focusable)
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            popupView.setOnTouchListener { v, event ->
                popupWindow.dismiss()
                true
            }
        }
    }

    override fun onClick(view: View?) {
        when (view!!.id){
            R.id.one_btn -> game.thrown_values(1, amount)
            R.id.two_btn -> game.thrown_values(2, amount)
            R.id.three_btn -> game.thrown_values(3, amount)
            R.id.four_btn -> game.thrown_values(4, amount)
            R.id.five_btn -> game.thrown_values(5, amount)
            R.id.six_btn -> game.thrown_values(6, amount)
            R.id.seven_btn -> game.thrown_values(7, amount)
            R.id.eight_btn -> game.thrown_values(8, amount)
            R.id.nine_btn -> game.thrown_values(9, amount)
            R.id.ten_btn -> game.thrown_values(10, amount)
            R.id.twelve_btn -> game.thrown_values(12, amount)
            R.id.eleven_btn -> game.thrown_values(11, amount)
            R.id.thirteen_btn -> game.thrown_values(13, amount)
            R.id.fourteen_btn -> game.thrown_values(14, amount)
            R.id.fiveteen_btn -> game.thrown_values(15, amount)
            R.id.sixteen_btn -> game.thrown_values(16, amount)
            R.id.seventeen_btn -> game.thrown_values(17, amount)
            R.id.eightteen_btn -> game.thrown_values(18, amount)
            R.id.nineteen_btn -> game.thrown_values(19, amount)
            R.id.twenty_btn -> game.thrown_values(20, amount)
            R.id.bull_btn -> if (amount < 3){
                game.thrown_values(25, amount)
            } else {
                return
            }
            R.id.miss_btn -> game.thrown_values(0, amount)
        }
        var potential_double_hits = game.check_potential_double_hit()
        if (potential_double_hits > 0){
            var customDialog = Dialog(this);
            customDialog.setContentView(R.layout.throws_on_double_dialog);
            customDialog.getWindow()?.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            customDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            customDialog.show()

            val btnZero = customDialog.findViewById<TextView>(R.id.btn_zero_hit)
            val btnOne = customDialog.findViewById<TextView>(R.id.btn_one_hit)
            val btnTwo = customDialog.findViewById<TextView>(R.id.btn_two_hit)
            val btnThree = customDialog.findViewById<TextView>(R.id.btn_three_hit)

            btnZero.setOnClickListener(){
                game.throws_on_double(0)
                customDialog.dismiss()
                game.finish_player_move()
                if (game.check_game_state()) finishGame()

                binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")
                updateAdapter()
            }
            btnOne.setOnClickListener(){
                game.throws_on_double(1)
                customDialog.dismiss()
                game.finish_player_move()
                if (game.check_game_state()) finishGame()

                binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")
                updateAdapter()
            }
            btnTwo.setOnClickListener(){
                game.throws_on_double(2)
                customDialog.dismiss()
                game.finish_player_move()
                if (game.check_game_state()) finishGame()

                binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")
                updateAdapter()
            }
            btnThree.setOnClickListener(){
                game.throws_on_double(3)
                customDialog.dismiss()
                game.finish_player_move()
                if (game.check_game_state()) finishGame()

                binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
                binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")
                updateAdapter()
            }
            if (game.has_player_finished()) btnZero.visibility = View.GONE
            if (potential_double_hits < 3) btnThree.visibility = View.GONE
            if (potential_double_hits < 2) btnTwo.visibility = View.GONE
            }
        else {
            game.finish_player_move()
            if (game.check_game_state()) finishGame()

            binding.tvRoundNumber.setText("Runde " + game.actualRound.toString())
            binding.tvActualPlayer.setText("Am Zug: " + game.game_players[game.actualPlayerNumber].playerName + " (" + game.actualDartsLeft.toString() + ")")
            updateAdapter()
        }

    }

    private fun setupPlayerStatusRecyclerView() {
        rvPlayerStatus.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        playerAdapter = CountingPlayerStatusAdapter(playingPlayers!!)
        rvPlayerStatus.adapter = playerAdapter
    }

    private fun updateAdapter() {
        for (i in 0..playingPlayers.size-1){
            playerAdapter.notifyItemChanged(i)
        }
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
        customDialog.show()

        val btnClose = customDialog.findViewById<TextView>(R.id.btn_close)
        val btnStatistics = customDialog.findViewById<TextView>(R.id.btn_statistics)

        btnClose.setOnClickListener(){
            startActivity(Intent(this@CountingGameActivity, MainActivity::class.java))
            finish()
        }
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
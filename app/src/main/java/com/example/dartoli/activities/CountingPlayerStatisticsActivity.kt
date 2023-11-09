package com.example.dartoli.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.Statistics.CountingPlayerStatisticsItem
import com.example.dartoli.Statistics.CountingPlayerStatisticsStatusAdapter
import com.example.dartoli.data.CountingPlayerDatabaseHandler
import com.example.dartoli.data.PlayerDatabaseHandler
import com.example.dartoli.databinding.ActivityCountingPlayerStatisticsBinding
import com.example.dartoli.model.CountingPlayerResults
import com.example.dartoli.model.Player

private lateinit var binding: ActivityCountingPlayerStatisticsBinding

private lateinit var playerStatisticsAdapter: CountingPlayerStatisticsStatusAdapter
private lateinit var rvPlayerStatistic: RecyclerView

private var items= arrayListOf<CountingPlayerStatisticsItem>()

class CountingPlayerStatisticsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingPlayerStatisticsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var player_lists = PlayerDatabaseHandler(this).readAllPlayers()
        var player_results = CountingPlayerDatabaseHandler(this).readAllResults()

        items = create_items(player_lists, player_results)

        rvPlayerStatistic = binding.rvRecycler
        setupPlayerStatisticsRecyclerView(items)

        binding.btnBackToMain.setOnClickListener{
            startActivity(Intent(this@CountingPlayerStatisticsActivity, MainActivity::class.java))
            finish()
        }
        binding.btnDirectCompare.setOnClickListener{
            val player_amount = PlayerDatabaseHandler(this).readAllPlayers().size
            if (player_amount > 1) {
                startActivity(Intent(this@CountingPlayerStatisticsActivity, GameStatisticsActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Es gibt keine 2 Spieler", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun create_items(player_list: ArrayList<Player>, player_results: ArrayList<CountingPlayerResults>): ArrayList<CountingPlayerStatisticsItem>{
        var items = arrayListOf<CountingPlayerStatisticsItem>()
        for (player in player_list){
            var player_id = player.id
            var throws = 0
            var all_darts_finishes = arrayListOf<Int>()
            var all_doubles = arrayListOf<Int>()
            var double_tries = 0
            var double_hits = 0
            var player_name = player.playerName
            var games = 0
            var wins = 0
            var longest_street = 0
            var actual_street = 0
            var average: Double? = null
            var best_average: Double? = null
            var badest_average: Double? = null
            var double_quote = "-"
            var most_double: String? = null
            var highest_finish: Int? = null
            var lowest_finish: Int? = null
            var less_darts: Int? = null
            var average_darts: Double? = null
            var most_darts: Int? = null
            var most_hits = arrayListOf<Int>()
            var intervals = arrayOf(0, 0, 0, 0, 0, 0, 0)

            for (result in player_results){
                if (result.player_id == player_id) {
                    games++
                    throws = throws + result.throw_amount
                    if (average == null){
                        average = result.average
                    } else {
                        average = (average * ( 1.0 - (result.throw_amount.toDouble() / throws.toDouble()))) + (result.average * (result.throw_amount.toDouble() / throws.toDouble()))
                    }
                    for (point in result.throws){
                        most_hits.add(point)
                        if (point < 20) intervals[0]++
                        if (point >= 20 && point < 40) intervals[1]++
                        if (point >= 40 && point < 60) intervals[2]++
                        if (point >= 60 && point < 100) intervals[3]++
                        if (point >= 100 && point < 120) intervals[4]++
                        if (point >= 120 && point < 180) intervals[5]++
                        if (point == 180) intervals[6]++
                    }
                    for (element in result.needed_darts_to_win){
                        all_darts_finishes.add(element)
                    }
                    for (finish in result.finishes){
                        if (highest_finish == null || finish > highest_finish) highest_finish = finish
                        if (lowest_finish == null || finish < lowest_finish) lowest_finish = finish
                    }
                    for (double in result.double_hits){
                        all_doubles.add(double)
                    }
                    double_hits = double_hits + result.double_hits_amount
                    double_tries = double_tries + result.double_tries
                    if (all_darts_finishes.size > 0){
                        less_darts = all_darts_finishes.min()
                        most_darts = all_darts_finishes.max()
                        var darts = 0
                        for (finish in all_darts_finishes){
                            darts = darts + finish
                        }
                        average_darts = darts.toDouble() / all_darts_finishes.size.toDouble()
                    }
                    if (result.rank == 1){
                        wins++
                        if (actual_street > 0) actual_street++  else actual_street == 1
                        if (actual_street > longest_street) longest_street = actual_street
                        if (best_average == null || result.average > best_average!!) best_average = result.average
                        if (badest_average == null || result.average < badest_average!!) badest_average = result.average
                    } else {
                        if (actual_street > 0) actual_street = -1 else actual_street--
                    }
                    if (double_tries == 0){
                        double_quote = "-"
                    } else {
                        var quote = (double_hits.toDouble() / double_tries.toDouble()) * 100.0
                        double_quote = String.format("%.2f", quote) +  "% | $double_hits/$double_tries"
                    }
                    if (all_doubles.size > 0){
                        var copie = arrayListOf<Int>()
                        for (element in all_doubles){
                            copie.add(element)
                        }
                        var string = ""
                        for (rank in 1..3){
                            if (copie.size > 0) {
                                val numbersByElement = copie.groupingBy { it }.eachCount()
                                val most_throw = numbersByElement.maxBy { it.value }?.key
                                val most_throw_amount = numbersByElement.get(most_throw)
                                if (rank == 1)string = string + "$most_throw ($most_throw_amount" + "x)" else string = string + "\n$most_throw ($most_throw_amount" + "x)"
                                var copie2 = arrayListOf<Int>()
                                for (element in copie){
                                    if (element != most_throw) copie2.add(element)
                                }
                                copie.clear()
                                for (element in copie2){
                                    copie.add(element)
                                }
                            }
                        most_double = string
                        }
                    }
                }
            }
            var most_hits_string = "-"
            if (most_hits.size > 0){
                var copie = arrayListOf<Int>()
                for (element in most_hits){
                    copie.add(element)
                }
                var string = ""
                for (rank in 1..10){
                    if (copie.size > 0) {
                        val numbersByElement = copie.groupingBy { it }.eachCount()
                        val most_throw = numbersByElement.maxBy { it.value }?.key
                        val most_throw_amount = numbersByElement.get(most_throw)
                        if (rank == 1) string = string + "$most_throw ($most_throw_amount" + "x)" else string = string + "\n$most_throw ($most_throw_amount" + "x)"
                        var copie2 = arrayListOf<Int>()
                        for (element in copie){
                            if (element != most_throw) copie2.add(element)
                        }
                        copie.clear()
                        for (element in copie2){
                            copie.add(element)
                        }
                    }
                    most_hits_string = string
                }
            }
            items.add(CountingPlayerStatisticsItem(player_name, games, wins, longest_street, actual_street, average, best_average, badest_average, double_quote,
                most_double, highest_finish, lowest_finish, less_darts, average_darts, most_darts, most_hits_string, intervals))
        }
        return items
    }

    private fun setupPlayerStatisticsRecyclerView(items: ArrayList<CountingPlayerStatisticsItem>) {
        rvPlayerStatistic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        playerStatisticsAdapter = CountingPlayerStatisticsStatusAdapter(items, ContextCompat.getColor(this, R.color.cricket_background_color))
        rvPlayerStatistic.adapter = playerStatisticsAdapter
    }

    override fun onBackPressed() {
        startActivity(Intent(this@CountingPlayerStatisticsActivity, MainActivity::class.java))
    }
}
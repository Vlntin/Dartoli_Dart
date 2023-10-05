package com.example.dartoli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.dartoli.R
import com.example.dartoli.adapter.ItemAdapter
import com.example.dartoli.data.Datasource
import com.example.dartoli.databinding.ActivityMultiplayerBinding
import com.example.dartoli.model.Game

class MultiplayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMultiplayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiplayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize data.
        val myDataset = Datasource().loadGames()

        val recyclerView = binding.recView
        recyclerView.adapter = ItemAdapter(this, myDataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }

}
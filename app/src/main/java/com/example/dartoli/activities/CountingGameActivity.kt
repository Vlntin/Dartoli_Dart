package com.example.dartoli.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dartoli.R
import com.example.dartoli.databinding.ActivityCountingGameBinding
import com.example.dartoli.databinding.ActivityCricketBinding

class CountingGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountingGameBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountingGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}
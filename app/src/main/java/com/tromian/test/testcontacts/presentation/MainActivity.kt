package com.tromian.test.testcontacts.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tromian.test.testcontacts.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }
    }
}
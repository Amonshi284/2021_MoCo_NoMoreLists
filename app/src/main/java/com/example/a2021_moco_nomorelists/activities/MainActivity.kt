package com.example.a2021_moco_nomorelists.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021_moco_nomorelists.NMLApplication
import com.example.a2021_moco_nomorelists.R
import com.example.a2021_moco_nomorelists.viewModels.UserListAdapter
import com.example.a2021_moco_nomorelists.viewModels.UserViewModel
import com.example.a2021_moco_nomorelists.viewModels.UserViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as NMLApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        userViewModel.allUsers.observe(this) { users ->
            users?.let { adapter.submitList(it) }
        }

        val fabadd = findViewById<FloatingActionButton>(R.id.fabadd)
        fabadd.setOnClickListener {
            startActivity(Intent(this, InputActivity::class.java))
        }

        val fabmap = findViewById<FloatingActionButton>(R.id.fabmap)
        fabmap.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
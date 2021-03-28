package com.example.a2021_moco_nomorelists.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a2021_moco_nomorelists.NMLApplication
import com.example.a2021_moco_nomorelists.R
import com.example.a2021_moco_nomorelists.models.User
import com.example.a2021_moco_nomorelists.viewModels.UserListAdapter
import com.example.a2021_moco_nomorelists.viewModels.UserViewModel
import com.example.a2021_moco_nomorelists.viewModels.UserViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as NMLApplication).repository)
    }

    val getInput = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            it.data?.getStringArrayExtra(InputActivity.EXTRA_REPLY)?.let {
                val user = User(null, it[0], it[1], it[2], it[3].toInt(), it[4].toLong(), it[5])
                userViewModel.insert(user)
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Nicht alle Felder wurden richtig befüllt!",
                Toast.LENGTH_LONG).show()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        /*userViewModel.allUsers.observe(this) { users ->
            users?.let { adapter.submitList(it) }
        }*/

        val fabadd = findViewById<FloatingActionButton>(R.id.fabadd)
        fabadd.setOnClickListener {
            val intent = Intent(this@MainActivity, InputActivity::class.java)
            startActivity(intent)
        }

        val fabmap = findViewById<FloatingActionButton>(R.id.fabmap)
        fabmap.setOnClickListener {
            val intent = Intent(this@MainActivity, MapsActivity::class.java)
            startActivity(intent)
        }
    }
}
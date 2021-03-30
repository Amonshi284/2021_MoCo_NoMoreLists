package com.example.a2021_moco_nomorelists.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.a2021_moco_nomorelists.NMLApplication
import com.example.a2021_moco_nomorelists.R
import com.example.a2021_moco_nomorelists.models.User
import com.example.a2021_moco_nomorelists.viewModels.UserViewModel
import com.example.a2021_moco_nomorelists.viewModels.UserViewModelFactory

class InputActivity : AppCompatActivity() {

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as NMLApplication).repository)
    }

    private lateinit var editTextName: EditText
    private lateinit var editTextStreet: EditText
    private lateinit var editTextCity: EditText
    private lateinit var editTextZIP: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        editTextName = findViewById(R.id.editTextName)
        editTextStreet = findViewById(R.id.editTextStreet)
        editTextCity = findViewById(R.id.editTextCity)
        editTextZIP = findViewById(R.id.editTextZIP)
        editTextPhone = findViewById(R.id.editTextPhone)
        editTextEmail = findViewById(R.id.editTextEmailAddress)

        val buttonSave = findViewById<Button>(R.id.buttonSave)
        buttonSave.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editTextName.text) || TextUtils.isEmpty(editTextStreet.text) || TextUtils.isEmpty(editTextCity.text) || TextUtils.isEmpty(editTextZIP.text) || TextUtils.isEmpty(editTextPhone.text) || TextUtils.isEmpty(editTextEmail.text)) {
                Toast.makeText(this, "Bitte füllen Sie alle Felder richtig aus.", Toast.LENGTH_LONG).show()
            } else {
                val user = User(null, editTextName.text.toString(), editTextStreet.text.toString(), editTextCity.text.toString(), editTextZIP.text.toString().toInt(), editTextPhone.text.toString().toLong(), editTextEmail.text.toString())
                userViewModel.insert(user)
                setResult(Activity.RESULT_OK, replyIntent)
                finish()
            }
        }
    }
}
package com.example.a2021_moco_nomorelists.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.a2021_moco_nomorelists.R

class InputActivity : AppCompatActivity() {

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
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val user = arrayOf<String>(editTextName.text.toString(), editTextStreet.text.toString(), editTextCity.text.toString(), editTextZIP.text.toString(), editTextPhone.text.toString(), editTextEmail.text.toString())
                replyIntent.putExtra(EXTRA_REPLY, user)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.a2021_moco_nomorelists.REPLY"
    }
}
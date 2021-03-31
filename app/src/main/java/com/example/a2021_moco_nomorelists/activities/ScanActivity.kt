package com.example.a2021_moco_nomorelists.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.budiyev.android.codescanner.*
import com.example.a2021_moco_nomorelists.R

private const val CAMERA_REQUEST_CODE = 101

class ScanActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)


    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback{
                runOnUiThread{
                    tv_textView.text = it.text //Decodiert Information zum Scan

                }
            }

            errorCallback = ErrorCallback{
                runOnUiThread{
                    Log.e("Main", "Kamera konnte nicht initialisiert werden: ${it.message}") //Error Message bei Scanfehler
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    override fun onResume() { //Scannt wieder bei Resume der app
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources() //Hilft gegen Memory leaks
        super.onPause()
    }

    
}
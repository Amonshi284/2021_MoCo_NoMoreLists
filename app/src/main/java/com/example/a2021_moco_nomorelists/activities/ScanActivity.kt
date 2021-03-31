package com.example.a2021_moco_nomorelists.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.a2021_moco_nomorelists.R

private const val CAMERA_REQUEST_CODE = 101

class ScanActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var tv_textView: TextView
    private lateinit var scanner_view: CodeScannerView
    private var cameraPermissionGranted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        tv_textView = findViewById<TextView>(R.id.tv_textView)
        scanner_view = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.CONTINUOUS
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                tv_textView.text = it.text //Decodiert Information zum Scan

            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Log.e(
                    "Main",
                    "Kamera konnte nicht initialisiert werden: ${it.message}"
                ) //Error Message bei Scanfehler
            }
        }


        scanner_view.setOnClickListener {
            if (cameraPermissionGranted) {
                codeScanner.startPreview()
            } else {
                getCameraPermission()
            }
        }
    }

    private fun getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSIONS_REQUEST_ACCESS_CAMERA)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        cameraPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    cameraPermissionGranted = true
                }
            }
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

    companion object {
        private const val PERMISSIONS_REQUEST_ACCESS_CAMERA = 2
    }
}
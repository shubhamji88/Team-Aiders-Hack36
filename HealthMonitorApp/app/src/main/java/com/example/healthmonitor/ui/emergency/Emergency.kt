package com.example.healthmonitor.ui.emergency

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.healthmonitor.R


class Emergency : AppCompatActivity() {
    private val REQUEST_LOCATION_PERMISSION = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emergency_activity)
        supportActionBar?.hide()
        val width= resources.displayMetrics.widthPixels
        val height= resources.displayMetrics.heightPixels*0.3
        window.setLayout(width,height.toInt())
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val param=window.attributes
        param.gravity=Gravity.TOP
        if(!enableMyLocation())
            Toast.makeText(this, "Location Permission not Granted!!", Toast.LENGTH_SHORT).show()

//
        }
    private fun isPermissionGranted() : Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun enableMyLocation(): Boolean {
        if (!isPermissionGranted()) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
        return isPermissionGranted()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

//

}
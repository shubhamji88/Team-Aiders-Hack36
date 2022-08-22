package com.example.healthmonitor.ui.mainScreen

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.healthmonitor.R
import com.example.healthmonitor.ui.emergency.Emergency
//import com.example.healthmonitor.ui.entertainment.EntertainmentFragment
import com.example.healthmonitor.ui.signIn.SignInActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),13)
        val sos=findViewById<LinearLayout>(R.id.sos)
        val bottomNavigation=findViewById<MeowBottomNavigation>(R.id.bottomNavigation)
        sos.setOnClickListener { val emergencyIntent= Intent(this, Emergency::class.java)
            startActivity(emergencyIntent) }
        addFragment(MainScreenFragment.newInstance())
        bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_baseline_logout_24))

    }
    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).commit()
    }
}
package com.example.healthmonitor.ui.splash

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentTransaction
import com.example.healthmonitor.R
import com.example.healthmonitor.ui.signIn.SignInActivity
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage


class StarterActivity : AppCompatActivity() {
    private var sharedPref:SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        supportActionBar?.hide()
        sharedPref  = getPreferences(Context.MODE_PRIVATE) ?: null
        if(sharedPref!=null && !sharedPref!!.getBoolean("first",false)) {
            createOnBoarding()
        }
        else{
            startActivity(Intent(this, SignInActivity::class.java))
            this.finish()
        }
    }


    private fun createOnBoarding() {
        val scr1 = PaperOnboardingPage(
            "Welcome to HealthMonitor",
            "Only solution you need\n" +
                    "Track your health 24x7",
            Color.parseColor("#e5d400"),
            R.drawable.mind,
            R.drawable.ic_baseline_keyboard_arrow_right_24
        )
        val scr2 = PaperOnboardingPage(
            "Have an emergency?",
            "Connect yourself to the nearest doctor through a phone call or text message\n",
            Color.parseColor("#92E3D0"),
            R.drawable.doctor_splash,
            R.drawable.ic_baseline_keyboard_arrow_right_24
        )

        val elements: ArrayList<PaperOnboardingPage> = ArrayList()
        elements.add(scr1)
        elements.add(scr2)
        val onBoardingFragment = PaperOnboardingFragment.newInstance(elements)


        onBoardingFragment.setOnRightOutListener {
            startActivity(Intent(this, SignInActivity::class.java))
            this.finish()
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment)
        fragmentTransaction.commit()
        with(sharedPref!!.edit()) {
            putBoolean("first", true)
            apply()
        }
    }
}
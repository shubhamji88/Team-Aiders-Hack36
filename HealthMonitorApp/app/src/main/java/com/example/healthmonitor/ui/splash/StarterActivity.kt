package com.example.healthmonitor.ui.splash

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.fragment.app.FragmentTransaction
import com.example.healthmonitor.R
import com.example.healthmonitor.ui.signIn.SignInActivity
import com.ramotion.paperonboarding.PaperOnboardingFragment
import com.ramotion.paperonboarding.PaperOnboardingPage


class StarterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        supportActionBar?.hide()
        createOnBoarding()
    }


    private fun createOnBoarding() {
        val scr1 = PaperOnboardingPage(
            "Welcome to HealthMonitor",
            "Need a partner to workout with?\n" +
                    "We'll make your yoga sessions interesting with our special AR feature",
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
        val scr3 = PaperOnboardingPage(
            "Watch your favourite shows",
            "We won't let your watching turn to binging with our time out feature",
            Color.parseColor("#F7F7F7"),
            R.drawable.tv,
            R.drawable.ic_baseline_keyboard_arrow_right_24
        )

        val elements: ArrayList<PaperOnboardingPage> = ArrayList()
        elements.add(scr1)
        elements.add(scr2)
        elements.add(scr3)
        val onBoardingFragment = PaperOnboardingFragment.newInstance(elements)


        onBoardingFragment.setOnRightOutListener {
            startActivity(Intent(this, SignInActivity::class.java))
            this.finish()
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment_container, onBoardingFragment)
        fragmentTransaction.commit()
    }
}
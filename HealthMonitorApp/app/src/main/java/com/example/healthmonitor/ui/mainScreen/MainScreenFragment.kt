package com.example.healthmonitor.ui.mainScreen


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekn.gruzer.gaugelibrary.Range
import com.example.healthmonitor.R
import com.example.healthmonitor.databinding.MainScreenBinding
import com.example.healthmonitor.ui.emergency.Emergency
import com.example.healthmonitor.ui.health.HealthMonitor
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainScreenFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            MainScreenFragment()
    }
    private lateinit var viewModel: MainScreenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding=DataBindingUtil.inflate<MainScreenBinding>(inflater, R.layout.main_screen,container,false)
        viewModel= ViewModelProvider(this).get(MainScreenViewModel::class.java)
        bindUI(binding)
        viewModel.getSensorData()
        return binding.root
    }

    private fun bindUI(binding: MainScreenBinding) {
        val auth= Firebase.auth.currentUser!!
        val name=auth.displayName
        val picUrl=auth.photoUrl.toString()
        binding.patientNam.text=name
        val range = Range()
        range.color = Color.parseColor("#ce0000")
        range.from = 0.0
        range.to = 30.0

        val range2 = Range()
        range2.color = Color.parseColor("#E3E500")
        range2.from = 30.0
        range2.to = 50.0
        val range3 = Range()
        range3.color = Color.parseColor("#00b20b")
        range3.from = 50.0
        range3.to = 110.0
        val range4 = Range()
        range4.color = Color.parseColor("#E3E500")
        range4.from = 110.0
        range4.to = 120.0
        val range5 = Range()
        range5.color = Color.parseColor("#ce0000")
        range5.from = 120.0
        range5.to = 150.0

        val heartGauge=binding.arcGauge
        val pulseGauge=binding.pulseGauge
        heartGauge.addRange(range)
        heartGauge.addRange(range2)
        heartGauge.addRange(range3)
        heartGauge.addRange(range4)
        heartGauge.addRange(range5)
        //add color ranges to gauge
        pulseGauge.addRange(range)
        pulseGauge.addRange(range2)
        pulseGauge.addRange(range3)
        pulseGauge.addRange(range4)
        pulseGauge.addRange(range5)

        //set min max and current value
        pulseGauge.minValue=0.0
        pulseGauge.maxValue=150.0
        pulseGauge.value=0.0
        heartGauge.minValue = 0.0
        heartGauge.maxValue = 150.0
        heartGauge.value = 0.0
        viewModel.response.observe(viewLifecycleOwner,{
            it?.feeds?.get(0)?.let { feed->
                try {
                    heartGauge.value=feed.field2!!.toDouble()
                    pulseGauge.value=feed.field1!!.toDouble()
                    if(feed.field1.toDouble()<60 || feed.field1.toDouble()>120)
                        callEmergency()
                    if(feed.field2.toDouble()<60 || feed.field2.toDouble()>120)
                        callEmergency()

                    Handler().postDelayed({
                        viewModel.getSensorData()
                    }, 3000)
                }
                catch (e:Exception){
                    Handler().postDelayed({
                        viewModel.getSensorData()
                    }, 3000)
                }
            }
        })
        binding.spo2Card.setOnClickListener {
            val intent = Intent(context, HealthMonitor::class.java)
            intent.putExtra("type",2)
            startActivity(intent)
        }
        binding.pRate.setOnClickListener {
            val intent = Intent(context, HealthMonitor::class.java)
            intent.putExtra("type",1)
            startActivity(intent)
        }
        bindImage(binding.patientImage,picUrl)
    }

    private fun callEmergency() {
        Toast.makeText(context, "Emergency!!", Toast.LENGTH_SHORT).show()
        val emergencyIntent= Intent(context, Emergency::class.java)
        startActivity(emergencyIntent)
    }

    private fun bindImage(imgView: ImageView, imgUrl: String?) {
        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .circleCrop()
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.person_profile)
                )
                .into(imgView)
        }
    }
}
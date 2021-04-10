package com.example.healthmonitor.ui.mainScreen


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ekn.gruzer.gaugelibrary.Range
import com.example.healthmonitor.R
import com.example.healthmonitor.databinding.MainScreenBinding
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

        val halfGauge=binding.arcGauge
        val pulseGauge=binding.pulseGauge
        halfGauge.addRange(range)
        halfGauge.addRange(range2)
        halfGauge.addRange(range3)
        halfGauge.addRange(range4)
        halfGauge.addRange(range5)
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
        halfGauge.minValue = 0.0
        halfGauge.maxValue = 150.0
        halfGauge.value = 0.0
        viewModel.response.observe(viewLifecycleOwner,{
            it.feeds?.get(0)?.let { feed->
                try {
                    halfGauge.value=feed.field2!!.toDouble()
                    pulseGauge.value=feed.field2!!.toDouble()
                }
                catch (e:Exception){}
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
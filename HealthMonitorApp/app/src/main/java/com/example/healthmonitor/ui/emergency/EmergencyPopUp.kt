package com.example.healthmonitor.ui.emergency


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthmonitor.R
import com.example.healthmonitor.databinding.EmergencyBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class EmergencyPopUp : Fragment() {
    var latitude=""
    var longitude=""
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding=DataBindingUtil.inflate<EmergencyBinding>(
            inflater,
            R.layout.emergency,
            container,
            false
        )
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
        obtainLocation()
        bindUI(binding)
        return binding.root
    }

    private fun bindUI(binding: EmergencyBinding) {
        val name=Firebase.auth.currentUser.displayName
        binding.callNow.setOnClickListener {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "911")
            startActivity(dialIntent)
        }
        binding.messageNow.setOnClickListener {

            val sendIntent = Intent(Intent.ACTION_VIEW)
            sendIntent.data = Uri.parse("sms:")
            if(latitude.isNotEmpty() && longitude.isNotEmpty())
                sendIntent.putExtra("sms_body", "Hey, this is $name and there is some emergency at latitude:$latitude , longitude:$longitude")
            else
                sendIntent.putExtra("sms_body", "Hey, this is $name and there is some emergency here")
            startActivity(sendIntent)
        }
    }
    private fun obtainLocation(){
        val application = requireNotNull(this.activity).application
        if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "Location Permission not Granted", Toast.LENGTH_SHORT).show()
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                latitude =  location?.latitude.toString()
                longitude = location?.longitude.toString()
            }

    }
}
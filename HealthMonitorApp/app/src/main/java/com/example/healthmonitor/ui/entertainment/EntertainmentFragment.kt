package com.example.healthmonitor.ui.entertainment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.healthmonitor.R
import com.example.healthmonitor.databinding.EntertainmentBinding


class EntertainmentFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() =
            EntertainmentFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<EntertainmentBinding>(
            inflater,
            R.layout.entertainment,
            container,
            false
        )
        bindUI(binding)
        return binding.root
    }

    private fun bindUI(binding: EntertainmentBinding) {
        binding.amazon.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.primevideo.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.netflix.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.netflix.com")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
        binding.youtube.setOnClickListener {
            val uri: Uri = Uri.parse("https://www.youtube.com/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }
}

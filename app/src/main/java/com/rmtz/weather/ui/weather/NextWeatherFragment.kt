package com.rmtz.weather.ui.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rmtz.weather.R
import com.rmtz.weather.databinding.FragmentNextWeatherBinding
import com.rmtz.weather.databinding.FragmentSettingBinding

class NextWeatherFragment : Fragment() {
    private var _binding: FragmentNextWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNextWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }
}
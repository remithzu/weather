package com.rmtz.weather.ui.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.rmtz.weather.MainActivity
import com.rmtz.weather.R
import com.rmtz.weather.commons.Weather
import com.rmtz.weather.commons.Weather.getBackground
import com.rmtz.weather.commons.Weather.toFormatDate
import com.rmtz.weather.commons.Weather.toTimezone
import com.rmtz.weather.data.AppDatabase
import com.rmtz.weather.databinding.FragmentCurrentWeatherBinding
import com.rmtz.weather.databinding.ItemCardBinding
import com.rmtz.weather.models.CurrentWeatherResponse
import com.rmtz.weather.models.ViewModelProviderFactory
import com.rmtz.weather.networks.NetworkState
import com.rmtz.weather.repository.WeatherRepository
import com.rmtz.weather.utils.Utils.setTempConversion


class CurrentWeatherFragment : Fragment() {
    private lateinit var viewModel: WeatherViewModel
    private var _binding: FragmentCurrentWeatherBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase
    private var tempUnit = ""
    private var locationName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val toolbar: Toolbar = binding.materialToolbar
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).finish()
        }
        database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Weather Database"
        ).allowMainThreadQueries().build()
        val user = database.AppDao().getListUser()[0]
        tempUnit = user.tempUnit
        locationName = user.locationName
        setupViewModel()
        initListener()
    }

    private fun initListener() {
        binding.swipeLayout.setOnRefreshListener {
            binding.swipeLayout.isRefreshing = true
            viewModel.getCurrentWeather(locationName)
        }
        binding.icMenu.setOnClickListener {
            (activity as MainActivity).setOnTransactionFragment(3)
        }
        binding.icBuilding.setOnClickListener {
            (activity as MainActivity).setOnTransactionFragment(2)
        }
        binding.tvLocation.text = locationName
    }

    private fun setupViewModel(){
        val repository = WeatherRepository()
        val factory = ViewModelProviderFactory(activity?.application!!, repository)
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
        viewModel.getCurrentWeather(locationName)
        viewModel.currentWeather.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    Log.i("wLog", "Loading")
                }
                is NetworkState.Success -> {
                    binding.swipeLayout.isRefreshing = false
                    if (it.data != null) {
                        fetchData(it.data)
                    }
                }
                else -> {
                    Log.i("wLog", "Error:: ${it.message}")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun fetchData(data: CurrentWeatherResponse) {
        data.let {
            binding.apply {
                Glide.with(requireActivity()).asDrawable().load(Weather.getIcon(data.weather[0].icon)).into(ivIcon)
                clFrame.background = requireContext().getBackground(data.weather[0].icon)
                tvName.text = data.weather[0].main
                tvTemperature.text = data.main.temp.setTempConversion(tempUnit)
                tvFeelsLike.text = data.main.feelsLike.setTempConversion(tempUnit)
                tvMinTemp.text = String.format(getString(R.string.min_1), data.main.tempMin.setTempConversion(tempUnit))
                tvMaxTemp.text = String.format(getString(R.string.max_1), data.main.tempMax.setTempConversion(tempUnit))
                tvPressure.text = "${String.format(getString(R.string.pres_1), data.main.pressure.toString())}hPa"
                tvHumidity.text = "${String.format(getString(R.string.hum_1), data.main.humidity.toString())}%"
                tvSeaLevel.text = "${String.format(getString(R.string.sea_1), data.main.seaLevel.toString())}hPa"
                tvGroundLevel.text = "${String.format(getString(R.string.grnd_1), data.main.grndLevel.toString())}hPa"
                tvSpeed.text = "${String.format(getString(R.string.speed_1), data.wind.speed.toString())}M/s"
                tvDeg.text = "${String.format(getString(R.string.deg_1), data.wind.deg.toString())}°"
                tvGust.text = "${String.format(getString(R.string.gust_1), data.wind.gust.toString())}M/s"

                tvDateTime.text = data.dt.toFormatDate("MMM, dd yyyy")
                tvTimezone.text = "UTC"
                tvSunrise.text = String.format(getString(R.string.sunrise_1), data.sys.sunrise.toFormatDate("HH:mm"))
                tvSunset.text = String.format(getString(R.string.sunset_1), data.sys.sunset.toFormatDate("HH:mm"))
                tvRain.text = data.weather[0].description
            }
        }
    }
}
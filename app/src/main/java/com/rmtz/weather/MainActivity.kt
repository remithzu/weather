package com.rmtz.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.rmtz.weather.data.AppDatabase
import com.rmtz.weather.data.User
import com.rmtz.weather.databinding.ActivityMainBinding
import com.rmtz.weather.ui.weather.CurrentWeatherFragment
import com.rmtz.weather.ui.location.LocationFragment
import com.rmtz.weather.ui.weather.NextWeatherFragment
import com.rmtz.weather.ui.SettingFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: AppDatabase

    override fun onResume() {
        super.onResume()
        setOnTransactionFragment(0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        database = Room.databaseBuilder(
            baseContext,
            AppDatabase::class.java,
            "Weather Database"
        ).allowMainThreadQueries().build()
        val user = database.AppDao().getListUser()
        if (user.isEmpty()) {
            database.AppDao().insertUser(User(
                id = 1,
                tempUnit = "Kelvin",
                locationId = 0,
                locationName = "Tokyo"
            ))
        }
        setOnTransactionFragment(0)
    }

    private fun loadFragmentManager(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.setCustomAnimations(
//            com.koinworks.app.R.anim.pull_in_right,
//            com.koinworks.app.R.anim.push_out_left,
//            com.koinworks.app.R.anim.pull_in_left,
//            com.koinworks.app.R.anim.push_out_right
//        )
        transaction.replace(R.id.pager, fragment, fragment.javaClass.toString())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    @SuppressLint("SetTextI18n", "NewApi", "NotifyDataSetChanged")
    fun setOnTransactionFragment(idxTransaction: Int) {
        val content = getContentMenu().elementAt(idxTransaction)
        loadFragmentManager(content.second)
    }

    private fun getContentMenu(): List<Pair<String, Fragment>> {
        return listOf<Pair<String, Fragment>>(
            Pair("Current Weather", CurrentWeatherFragment()),
            Pair("Next Weather", NextWeatherFragment()),
            Pair("Location", LocationFragment()),
            Pair("Setting", SettingFragment())
        )
    }
}
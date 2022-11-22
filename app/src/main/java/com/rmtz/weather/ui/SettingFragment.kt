package com.rmtz.weather.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.rmtz.weather.MainActivity
import com.rmtz.weather.R
import com.rmtz.weather.data.AppDatabase
import com.rmtz.weather.data.User
import com.rmtz.weather.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: AppDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: Toolbar = binding.materialToolbar
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        toolbar.setNavigationOnClickListener {
            (activity as MainActivity).setOnTransactionFragment(0)
        }
        database = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java,
            "Weather Database"
        ).allowMainThreadQueries().build()
        val user = database.AppDao().getListUser()[0]

        binding.tieTemperatureUnit.setText(user.tempUnit)

        binding.tieTemperatureUnit.setOnClickListener {
            showDialog(
                R.string.temperature_unit,
                R.array.temperature,
                binding.tieTemperatureUnit
            )
        }
        binding.tieTemperatureUnit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.e("wLog", "Unit:: $s")
                database.AppDao().updateUser(User(
                    id = user.id,
                    tempUnit = s.toString(),
                    locationId = user.locationId,
                    locationName = user.locationName
                ))
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.btnSave.setOnClickListener {
            (activity as MainActivity).setOnTransactionFragment(0)
        }
    }

    private fun showDialog(title: Int, arrayData: Int, target: TextView): String {
        val builder = context?.let { it1 -> androidx.appcompat.app.AlertDialog.Builder(it1) }
        builder?.setTitle(title)
        var result = ""

        val array = resources.getStringArray(arrayData)

        builder?.setItems(array) { _, which ->
            result = array[which]
            try {
                target.text = result
            } catch (_: IllegalArgumentException) {}
        }
        val dialog = builder?.create()
        dialog?.show()
        return result
    }
}
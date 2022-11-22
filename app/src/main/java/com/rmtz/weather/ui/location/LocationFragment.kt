package com.rmtz.weather.ui.location

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.rmtz.weather.MainActivity
import com.rmtz.weather.R
import com.rmtz.weather.data.AppDatabase
import com.rmtz.weather.data.Location
import com.rmtz.weather.data.User
import com.rmtz.weather.databinding.FragmentLocationBinding
import com.rmtz.weather.databinding.ItemCardBinding
import com.rmtz.weather.models.LocationResponse
import com.rmtz.weather.models.ViewModelProviderFactory
import com.rmtz.weather.networks.NetworkState
import com.rmtz.weather.repository.WeatherRepository
import com.rmtz.weather.ui.dialog.LocationBottomSheet


class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    private lateinit var viewModel: LocationViewModel
    private lateinit var database: AppDatabase
    private var defaultId: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragmentManager.beginTransaction().remove(this).commit()
    }

    @SuppressLint("NotifyDataSetChanged")
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
        defaultId = user.locationId
        adapter = Adapter()
        adapter.callbackAction = object : Adapter.Action {
            override fun onDelete(location: Location) {
                database.AppDao().deleteLocation(location)
                loadData()
            }

            override fun onAction(id: Long, location: String) {
                database.AppDao().updateUser(User(
                    id = user.id,
                    tempUnit = user.tempUnit,
                    locationId = id,
                    locationName = location
                ))
                defaultId = id
                loadData()
                Log.e("wLog", "onAction:: $id: $location")
            }
        }

        binding.rvLocation.adapter = adapter
        binding.btnSave.setOnClickListener {
            Log.i("wLog", "Location search:: ${binding.tieLocation.text.toString()}")
            viewModel.getGeoLocation(binding.tieLocation.text.toString())
        }
        setupViewModel()
        loadData()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadData() {
        adapter.location = listOf()
        val location = database.AppDao().getListLocation()
        adapter.location = location
        adapter.setD = defaultId
        adapter.notifyDataSetChanged()
        binding.tieLocation.setText("")
    }

    private fun fetchData(data: LocationResponse?) {
        val modalBottomSheet = LocationBottomSheet()
        modalBottomSheet.location = data
        modalBottomSheet.callbackAction = object: LocationBottomSheet.Action,
            LocationBottomSheet.Adapter.Action {
            override fun onAction(location: Location) {
                Log.i("wLog", "Location model Selected:: $location")
                database.AppDao().insertLocation(location)
                loadData()
                modalBottomSheet.dismiss()
            }
        }
        modalBottomSheet.show(childFragmentManager, "LocationBottomSheet")
    }

    private fun setupViewModel(){
        val repository = WeatherRepository()
        val factory = ViewModelProviderFactory(activity?.application!!, repository)
        viewModel = ViewModelProvider(this, factory)[LocationViewModel::class.java]
        viewModel.geoLocation.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkState.Loading -> {
                    Log.i("wLog", "Loading")
                }
                is NetworkState.Success -> {
                    Log.i("wLog", "Success:: ${it.data}")
                    fetchData(it.data)
                }
                else -> {
                    Log.i("wLog", "Error:: ${it.message}")
                }
            }
        }
    }

    class ViewHolder(val itemBind: ItemCardBinding): RecyclerView.ViewHolder(itemBind.root)
    class Adapter: RecyclerView.Adapter<ViewHolder>() {
        var location = listOf<Location>()
        var callbackAction: Action? = null
        var setD: Long = 0

        interface Action{
            fun onDelete(location: Location)
            fun onAction(id: Long, location: String)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemCardBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        @SuppressLint("NewApi")
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val m = location[position]
            if (setD == m.id) {
                holder.itemBind.btnRemove.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_link))
                holder.itemBind.btnRemove.imageTintList = ContextCompat.getColorStateList(holder.itemView.context, R.color.purple_500)
            } else {
                holder.itemBind.btnRemove.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context, R.drawable.ic_unlink))
                holder.itemBind.btnRemove.imageTintList = ContextCompat.getColorStateList(holder.itemView.context, R.color.black_overlay)
            }
            holder.itemBind.tvLocationName.text = m.name
            holder.itemBind.btnRemove.setOnClickListener {
                callbackAction?.onDelete(m)
            }
            holder.itemView.setOnClickListener {
                callbackAction?.onAction(m.id, m.name)
            }
        }

        override fun getItemCount(): Int = location.size
    }
}
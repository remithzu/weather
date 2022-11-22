package com.rmtz.weather.ui.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rmtz.weather.data.Location
import com.rmtz.weather.databinding.DialogListLocationBinding
import com.rmtz.weather.databinding.ItemListBinding
import com.rmtz.weather.models.LocationResponse

class LocationBottomSheet : BottomSheetDialogFragment() {
    private var _binding: DialogListLocationBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: Adapter
    var location: LocationResponse? = null
    var callbackAction: Adapter.Action? = null

    interface Action{
        fun onAction(location: Location)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogListLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Adapter()
        adapter.callbackAction = object: Adapter.Action {
            override fun onAction(location: Location) {
                Log.e("wLog", "location selected: $location")
                callbackAction?.onAction(location)
            }
        }
        binding.rvLocation.adapter = adapter
        if (location!=null && location!!.size>0) {
            binding.clNotFound.visibility = View.GONE
            binding.rvLocation.visibility = View.VISIBLE
            adapter.location = location!!
        } else {
            binding.clNotFound.visibility = View.VISIBLE
            binding.rvLocation.visibility = View.GONE
        }
        adapter.notifyDataSetChanged()
    }

    class ViewHolder(val itemBind: ItemListBinding): RecyclerView.ViewHolder(itemBind.root)
    class Adapter: RecyclerView.Adapter<ViewHolder>() {
        var location = listOf<LocationResponse.LocationResponseItem>()
        var callbackAction: Action? = null

        interface Action{
            fun onAction(location: Location)
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                ItemListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val m = location[position]
            holder.itemBind.tvName.text = m.name
            holder.itemBind.tvState.text = m.state
            holder.itemView.setOnClickListener {
                val loc = Location(
                    0,
                    country = m.country,
                    name = m.name,
                    lat = m.lat,
                    lon = m.lon,
                    state = m.state
                )
                callbackAction?.onAction(loc)
            }
        }

        override fun getItemCount(): Int = location.size
    }
}
package net.aliveplex.witoong623.sleepysalaryman.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener
import kotlinx.android.synthetic.main.location_fragment.*
import net.aliveplex.witoong623.sleepysalaryman.R
import net.aliveplex.witoong623.sleepysalaryman.adapters.LocationAdapter
import net.aliveplex.witoong623.sleepysalaryman.database.Location
import net.aliveplex.witoong623.sleepysalaryman.viewmodels.LocationViewModel

class LocationFragment : Fragment(), ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var viewAdapter: LocationAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var viewModel: LocationViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

        add_location_fab.setOnClickListener {
            view!!.findNavController().navigate(R.id.action_locationFragment_to_addLocationFragment)
        }

        viewAdapter = LocationAdapter(mutableListOf())
        viewManager = LinearLayoutManager(activity)
        val divideritemdecorator = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)

        location_recyclerview.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(divideritemdecorator)
            isItemViewSwipeEnabled = true
        }
        location_recyclerview.setOnItemMoveListener(object : OnItemMoveListener {
            override fun onItemDismiss(srcHolder: RecyclerView.ViewHolder?) {
                val position = srcHolder?.adapterPosition
                viewModel.deleteLocation(viewAdapter.locationsData.removeAt(position!!))
                viewAdapter.notifyItemRemoved(position)
            }

            override fun onItemMove(srcHolder: RecyclerView.ViewHolder?, targetHolder: RecyclerView.ViewHolder?): Boolean {
                return false
            }

        })

        viewModel.locations?.observe(this, Observer {
            viewAdapter.locationsData.clear()
            viewAdapter.locationsData.addAll(it!!)
            viewAdapter.notifyDataSetChanged()
        })
    }

    companion object {
        fun newInstance() = LocationFragment()
    }

}

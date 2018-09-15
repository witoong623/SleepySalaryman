package net.aliveplex.witoong623.sleepysalaryman.adapters

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.recyclerview_single_line.view.*
import net.aliveplex.witoong623.sleepysalaryman.R
import net.aliveplex.witoong623.sleepysalaryman.database.Location

class LocationAdapter(var locationsData: List<Location>): RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val linearLayout = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_single_line, parent, false) as LinearLayout

        return LocationViewHolder(linearLayout)
    }

    override fun getItemCount() = locationsData.count()

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindTo(locationsData.get(position))
    }

    class LocationViewHolder(containerView: View): RecyclerView.ViewHolder(containerView) {
        private val firstLine: TextView = containerView.findViewById(R.id.firstLine) as TextView

        fun bindTo(location: Location) {
            firstLine.text = location.name
        }
    }
}
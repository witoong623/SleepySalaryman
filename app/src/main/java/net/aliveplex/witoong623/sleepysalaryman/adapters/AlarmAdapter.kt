package net.aliveplex.witoong623.sleepysalaryman.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import net.aliveplex.witoong623.sleepysalaryman.R
import net.aliveplex.witoong623.sleepysalaryman.database.Alarm

class AlarmAdapter(val alarmsData: MutableList<Alarm>): RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val containerView = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_alarm_item, parent, false)

        return AlarmViewHolder(containerView)
    }

    override fun getItemCount(): Int {
        return alarmsData.size
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.binTo(alarmsData[position])
    }

    class AlarmViewHolder(containerView: View): RecyclerView.ViewHolder(containerView) {
        val alarmName: TextView = containerView.findViewById(R.id.alarm_name)
        val alarmLabel: TextView = containerView.findViewById(R.id.alarm_label)
        val isEnable: Switch = containerView.findViewById(R.id.alarm_enable_sw)

        init {
            isEnable.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {

                } else {

                }
            }
        }

        fun binTo(alarm: Alarm) {
            alarmName.text = alarm.name
            alarmLabel.text = alarm.label
            isEnable.isChecked = alarm.isEnable
        }
    }
}
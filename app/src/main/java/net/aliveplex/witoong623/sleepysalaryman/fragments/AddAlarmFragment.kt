package net.aliveplex.witoong623.sleepysalaryman.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.aliveplex.witoong623.sleepysalaryman.R

class AddAlarmFragment : Fragment() {

    companion object {
        fun newInstance() = AddAlarmFragment()
    }

    private lateinit var viewModel: AddAlarmViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_alarm_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddAlarmViewModel::class.java)
        // TODO: Use the ViewModel
    }

}

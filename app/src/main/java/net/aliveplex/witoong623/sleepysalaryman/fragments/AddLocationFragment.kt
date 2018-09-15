package net.aliveplex.witoong623.sleepysalaryman.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.add_location_fragment.*
import net.aliveplex.witoong623.sleepysalaryman.LATITUDE_ARGUMENT
import net.aliveplex.witoong623.sleepysalaryman.LONGITUDE_ARGUMENT

import net.aliveplex.witoong623.sleepysalaryman.R
import net.aliveplex.witoong623.sleepysalaryman.database.Location
import net.aliveplex.witoong623.sleepysalaryman.viewmodels.AddLocationResult
import net.aliveplex.witoong623.sleepysalaryman.viewmodels.AddLocationStatus
import net.aliveplex.witoong623.sleepysalaryman.viewmodels.AddLocationViewModel

class AddLocationFragment : Fragment(), OnMapReadyCallback, SeekBar.OnSeekBarChangeListener {
    private val MINIMUM_RADIUS = 100
    private lateinit var viewModel: AddLocationViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var locationLatLng: LatLng
    private var locationRadius: Int = 500

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddLocationViewModel::class.java)

        val supportMapFragment = childFragmentManager.findFragmentById(R.id.confirm_location_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        val lat = arguments!!.getDouble(LATITUDE_ARGUMENT)
        val lng = arguments!!.getDouble(LONGITUDE_ARGUMENT)
        locationLatLng = LatLng(lat, lng)

        radius_sb.setOnSeekBarChangeListener(this)
        radius_display.text = resources.getString(R.string.radius_display, 500)

        location_name_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrBlank()) {
                    location_name_et_layout.error = "Name must be provided"
                } else {
                    location_name_et_layout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        save_location_but.setOnClickListener {
            if (location_name_et.text.isNullOrBlank()) {
                location_name_et_layout.error = "Name must be provided"
                return@setOnClickListener
            }

            val location = Location(location_name_et.text.toString(), locationLatLng.latitude, locationLatLng.longitude, locationRadius)
            viewModel.saveLocation(location)
        }

        viewModel.addLocationResult.observe(this, Observer { result: AddLocationResult? ->
            if (result?.addLocationStatus == AddLocationStatus.Success) {
                NavHostFragment.findNavController(this@AddLocationFragment).popBackStack(R.id.locationFragment, false)
            } else {

            }
        })
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationLatLng))
        googleMap.addMarker(MarkerOptions().position(locationLatLng))

        val uiSettings = googleMap.uiSettings
        uiSettings.isMapToolbarEnabled = false
        uiSettings.isScrollGesturesEnabled = false
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        if (progress < MINIMUM_RADIUS) {
            radius_display.text = resources.getString(R.string.radius_display, MINIMUM_RADIUS)
            locationRadius = MINIMUM_RADIUS
        } else {
            radius_display.text = resources.getString(R.string.radius_display, progress)
            locationRadius = progress
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {

    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {

    }

    companion object {
        fun newInstance() = AddLocationFragment()
    }

}

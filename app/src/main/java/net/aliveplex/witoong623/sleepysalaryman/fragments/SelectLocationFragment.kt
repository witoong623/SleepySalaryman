package net.aliveplex.witoong623.sleepysalaryman.fragments

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.select_location_fragment.*
import net.aliveplex.witoong623.sleepysalaryman.LATITUDE_ARGUMENT
import net.aliveplex.witoong623.sleepysalaryman.LONGITUDE_ARGUMENT
import net.aliveplex.witoong623.sleepysalaryman.PERMISSION_REQUEST_ACCESS_FINE_LOCATON

import net.aliveplex.witoong623.sleepysalaryman.R
import net.aliveplex.witoong623.sleepysalaryman.viewmodels.SelectLocationViewModel

class SelectLocationFragment : Fragment(), OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback,GoogleMap.OnMapClickListener {

    private lateinit var viewModel: SelectLocationViewModel
    private lateinit var googleMap: GoogleMap
    private lateinit var addLocationBotomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var selectedPointMarker: Marker? = null
    private var selectedLocationLatLng: LatLng? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.select_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SelectLocationViewModel::class.java)

        val supportMapFragment = childFragmentManager.findFragmentById(R.id.select_location_map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        addLocationBotomSheetBehavior = BottomSheetBehavior.from(add_location_sheet)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)

        add_location_but.setOnClickListener {
            if (selectedLocationLatLng != null) {
                val bundle = Bundle()
                bundle.putString(LATITUDE_ARGUMENT, selectedLocationLatLng!!.latitude.toString())
                bundle.putString(LONGITUDE_ARGUMENT, selectedLocationLatLng!!.longitude.toString())
                it.findNavController().navigate(R.id.action_selectLocationFragment_to_addLocationFragment, bundle)
            } else {
                Toast.makeText(activity!!.applicationContext, "Please select location before add", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map!!

        if (checkLocationPermission()) {
            googleMap.isMyLocationEnabled = true

            val location = fusedLocationProviderClient.lastLocation
            location.addOnCompleteListener { task: Task<Location> ->
                if (task.isSuccessful) {
                    val currentLocation = task.result as Location
                    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } else {
                    Toast.makeText(activity!!.applicationContext, "can't get current location", Toast.LENGTH_LONG).show()
                }
            }
        }

        googleMap.setOnMapClickListener(this)
        addLocationBotomSheetBehavior.peekHeight = 50
        addLocationBotomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSION_REQUEST_ACCESS_FINE_LOCATON -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        googleMap.isMyLocationEnabled = true
                    }
                }
            }
        }
    }

    override fun onMapClick(point: LatLng?) {
        selectedLocationLatLng = point

        if (selectedPointMarker != null) {
            selectedPointMarker?.remove()
        }
        selectedPointMarker = googleMap.addMarker(MarkerOptions().position(point!!))

        add_location_lat.text = point.latitude.toString()
        add_location_lon.text = point.longitude.toString()
        addLocationBotomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // TODO: improve permission handling
    private fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(activity!!,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        PERMISSION_REQUEST_ACCESS_FINE_LOCATON)
            } else {
                Toast.makeText(activity!!.applicationContext, "Location Access is needed if you want to access your current location", Toast.LENGTH_LONG).show()
            }

            return false
        }
    }

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

}

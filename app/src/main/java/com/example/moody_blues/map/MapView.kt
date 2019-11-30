package com.example.moody_blues.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.example.moody_blues.AppManager
import com.example.moody_blues.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

/**
 * Toolkit-specific logic for the map activity
 */
class MapView : AppCompatActivity(), MapContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    override lateinit var presenter: MapContract.Presenter

    private var mapMode: Int = -1
    private lateinit var mMap: GoogleMap
    private lateinit var here: LatLng
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_view)
        title = "Map"
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mapMode = this.intent.getIntExtra("mode", -1)

        // Pass the view to the presenter
        presenter = MapPresenter(this)

        // Do stuff with the presenter
        // TODO implement edge swipe for going back and implement map and location tapping
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        presenter.getLocation()
    }

    /**
     * Get the user's current location
     */
    override fun getLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 0)
        }
        else {
            getLocationResult()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0)
            getLocationResult()
    }

    /**
     * Create a new mood when location is obtained
     */
    private fun getLocationResult() {
        fusedLocationClient
            .lastLocation
            .addOnSuccessListener { location : Location? ->
                setDefaultLocation(location)
            }
    }

    /**
     * Centers the map to a location
     * @param location The location to center on
     */
    override fun setDefaultLocation(location: Location?) {
        here = LatLng(location!!.latitude, location!!.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 10f))
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // for each mood in user's moods add marker
        for (mood in presenter.fetchMoods(mapMode)) {
            if (mood.location == null) {
                continue
            }

            mMap.addMarker(MarkerOptions()
                .position(mood.location!!)
                .icon(BitmapDescriptorFactory.defaultMarker(mood.getColorWheel()))
                .title(mood.username)
                .snippet(mood.getEmotionString())
                .visible(mood.showLocation))
        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        p0?.showInfoWindow()
        return true
    }

    /**
     * Transition to the mood page
     */
    override fun gotoMood() {
//        val intent = Intent(this, MoodView::class.java)
//        startActivity(intent)
        finish()
    }
}


package com.example.educalink_ev2.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LocationViewModel(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModel() {

    private val _userLocation = MutableStateFlow<Location?>(null)
    val userLocation = _userLocation.asStateFlow()

    private val _distancia = MutableStateFlow(0f)
    val distancia = _distancia.asStateFlow()

    // Ubicación fija de la institución (Ej. Duoc UC: Sede Puerto Montt)
    // Lat: -41.4695, Lon: -72.9455
    private val ubicacionInstitucion = Location("Institucion").apply {
        latitude = -41.4695
        longitude = -72.9455
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val lastLocation = locationResult.lastLocation
            if (lastLocation != null) {
                _userLocation.value = lastLocation
                calcularDistancia(lastLocation)
            }
        }
    }

    @SuppressLint("MissingPermission") // Los permisos se piden en la UI
    fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            TimeUnit.SECONDS.toMillis(5) // Actualiza cada 5 segundos
        ).build()

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun calcularDistancia(userLocation: Location) {
        viewModelScope.launch {
            val distanciaMetros = userLocation.distanceTo(ubicacionInstitucion)
            _distancia.value = distanciaMetros
        }
    }

    fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onCleared() {
        stopLocationUpdates()
        super.onCleared()
    }
}
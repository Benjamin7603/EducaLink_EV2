package com.example.educalink_ev2.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.Sede
import com.example.educalink_ev2.network.RetrofitClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SedeViewModel(private val context: Context) : ViewModel() {

    private val _sedes = MutableStateFlow<List<Sede>>(emptyList())
    val sedes = _sedes.asStateFlow()

    private val _ubicacionUsuario = MutableStateFlow<Location?>(null)

    // Texto que mostrará la distancia
    private val _distanciaTexto = MutableStateFlow("Calculando...")
    val distanciaTexto = _distanciaTexto.asStateFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    init {
        cargarSedes()
    }

    fun cargarSedes() {
        viewModelScope.launch {
            try {
                val listaSedes = RetrofitClient.instance.getSedes()
                _sedes.value = listaSedes

                if (_ubicacionUsuario.value != null) {
                    calcularDistancia()
                } else {
                    _distanciaTexto.value = "Obteniendo GPS..."
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _distanciaTexto.value = "Sin conexión al servidor"
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion() {
        _distanciaTexto.value = "Buscando satélites..."

        val cancellationToken = CancellationTokenSource()

        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cancellationToken.token)
            .addOnSuccessListener { location ->
                if (location != null) {
                    _ubicacionUsuario.value = location
                    calcularDistancia()
                } else {
                    _distanciaTexto.value = "No se pudo obtener ubicación."
                }
            }
            .addOnFailureListener {
                _distanciaTexto.value = "Error GPS: Activa la ubicación."
            }
    }

    private fun calcularDistancia() {
        val userLoc = _ubicacionUsuario.value
        val sedesLista = _sedes.value

        if (userLoc != null && sedesLista.isNotEmpty()) {
            val sedeDestino = sedesLista[0] // Tomamos la primera sede (Puerto Montt)

            val destinoLoc = Location("Sede").apply {
                latitude = sedeDestino.latitud
                longitude = sedeDestino.longitud
            }

            val distanciaMetros = userLoc.distanceTo(destinoLoc)

            // --- CAMBIO PARA QUITAR PUNTOS Y COMAS ---
            if (distanciaMetros < 1000) {
                // Menos de 1km: Mostramos metros enteros (ej: "500 metros")
                val metros = distanciaMetros.toInt()
                _distanciaTexto.value = "¡Estás muy cerca! A $metros metros de ${sedeDestino.nombre}"
            } else {
                // Más de 1km: Dividimos y convertimos a Entero (ej: "12500 km")
                val kms = (distanciaMetros / 1000).toInt()
                _distanciaTexto.value = "Estás a $kms km de ${sedeDestino.nombre}"
            }
            // ----------------------------------------

        } else if (sedesLista.isEmpty()) {
            _distanciaTexto.value = "Esperando datos..."
            cargarSedes()
        }
    }
}

class SedeViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SedeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SedeViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
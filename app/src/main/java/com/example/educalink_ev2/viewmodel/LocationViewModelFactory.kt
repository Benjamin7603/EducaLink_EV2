package com.example.educalink_ev2.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient

class LocationViewModelFactory(
    private val context: Context,
    private val fusedLocationClient: FusedLocationProviderClient
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationViewModel(context, fusedLocationClient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
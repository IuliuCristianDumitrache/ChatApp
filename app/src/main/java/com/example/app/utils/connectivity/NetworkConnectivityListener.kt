package com.example.app.utils.connectivity

interface NetworkConnectivityListener {
    fun onConnected()

    fun onDisconnected()
}
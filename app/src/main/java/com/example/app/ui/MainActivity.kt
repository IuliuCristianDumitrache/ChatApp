package com.example.app.ui

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.app.network.RxBus
import com.example.app.network.rxmessages.ReloadList
import com.example.app.utils.connectivity.NetworkCallback
import com.example.app.utils.connectivity.NetworkConnection
import com.example.app.databinding.ActivityMainBinding
import com.example.app.ui.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val networkConnection = NetworkConnection
    lateinit var views: ActivityMainBinding


    //TODO - splash screen to check if user already loggedin
    private val networkCallback = object : NetworkCallback(networkConnection) {
        override fun onAvailable(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = false
            }
            RxBus.publish(ReloadList(true))
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            runOnUiThread {
                views.viewNoInternet.isVisible = true
            }
            super.onLost(network)
        }
    }

    private fun registerNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            networkCallback
        )
    }

    private fun unregisterNetworkCallback() {
        val connectivityManager = getSystemService(ConnectivityManager::class.java)
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        views = ActivityMainBinding.inflate(layoutInflater)
        setContentView(views.root)
        registerNetworkCallback()
    }

    override fun onDestroy() {
        unregisterNetworkCallback()
        super.onDestroy()
    }
}
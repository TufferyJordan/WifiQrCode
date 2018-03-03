package com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.others

import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.wifiqrcode.jtuffery.wifiqrcode.old.presenter.ScannerPresenter
import com.wifiqrcode.jtuffery.wifiqrcode.old.presenter.ScannerPresenterImpl
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.activities.MainActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler, ScannerView {
    private lateinit var presenter: ScannerPresenter

    private var wifiConfiguration: WifiConfiguration? = null

    private lateinit var scannerView: ZXingScannerView

    private var receiver = object : BroadcastReceiver() {
        @SuppressLint("LogNotTimber")
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                MainActivity.ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED -> {
                    (activity?.applicationContext?.getSystemService(Service.WIFI_SERVICE) as WifiManager).apply {
                        if (!isWifiEnabled) isWifiEnabled = true
                        disconnect()

                        val lastConfiguration = getConfiguration(configuredNetworks, wifiConfiguration?.SSID)

                        if (lastConfiguration == null) {
                            wifiConfiguration?.priority = getMaxNetworkPriority(configuredNetworks) + 1
                            if (addNetwork(wifiConfiguration) == -1) {
                                Log.w("ScannerFragment", "addNetwork returned -1")
                            }
                        } else {
                            wifiConfiguration?.priority = getMaxNetworkPriority(configuredNetworks) + 1
                            wifiConfiguration?.networkId = lastConfiguration.networkId
                            if (updateNetwork(wifiConfiguration) == -1) {
                                Log.w("ScannerFragment", "updateNetwork returned -1")
                            }
                        }

                        enableNetwork(wifiConfiguration?.networkId!!, true)
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(activity)
        presenter = ScannerPresenterImpl()
        scannerView.setResultHandler(this)
        return scannerView
    }

    override fun onStart() {
        super.onStart()
        presenter.view = this

        LocalBroadcastManager.getInstance(context!!).registerReceiver(receiver, IntentFilter(MainActivity.ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED))
        scannerView.startCamera()
    }

    override fun onStop() {
        scannerView.stopCamera()
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(receiver)

        presenter.view = null
        super.onStop()
    }

    override fun handleResult(result: Result?) {
        Log.w("READED :", result?.text)
        presenter.receiveJSON(result?.text)
        scannerView.resumeCameraPreview(this)
    }

    override fun handleWifiConnexionScanned(wifiConfiguration: WifiConfiguration) {
        this.wifiConfiguration = wifiConfiguration
        MainActivity.checkPermissionAndAskIfItIsNeeded(activity!!, Manifest.permission.CHANGE_WIFI_STATE)
    }

    override fun showWifiConnexionSuccess() {
        Toast.makeText(context, "Wifi connexion success !", Toast.LENGTH_LONG).show()
    }

    override fun showWifiConnexionFailed() {
        Toast.makeText(context, "Wifi Failed !", Toast.LENGTH_LONG).show()
    }

    override fun showBadScannedQrCode() {
        Toast.makeText(context, "Bad QrCode!", Toast.LENGTH_LONG).show()
    }

    fun onFlashButton() {
        scannerView.flash = !scannerView.flash
    }

    private fun getConfiguration(configuredNetworks: List<WifiConfiguration>, ssid: String?): WifiConfiguration? {
        configuredNetworks.forEach {
            if (it.SSID == ssid) {
                return it
            }
        }

        return null
    }

    private fun getMaxNetworkPriority(configuredNetworks: List<WifiConfiguration>): Int {
        var maxPriority = 0

        configuredNetworks.forEach {
            if (it.priority > maxPriority) {
                maxPriority = it.priority
            }
        }

        return maxPriority
    }
}

interface ScannerView {
    fun handleWifiConnexionScanned(wifiConfiguration: WifiConfiguration)
    fun showWifiConnexionSuccess()
    fun showWifiConnexionFailed()
    fun showBadScannedQrCode()
}

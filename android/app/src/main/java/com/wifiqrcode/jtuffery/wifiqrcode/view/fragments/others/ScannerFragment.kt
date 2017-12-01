package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others

import android.Manifest
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.ScannerPresenter
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.ScannerPresenterImpl
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.MainActivity
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler, ScannerView {
    private var presenter: ScannerPresenter? = null

    private var scannerView: ZXingScannerView? = null
    private var isFlashing = false

    private var receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                MainActivity.ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED -> {
                    if (presenter?.addWifiNetwork() == ScannerPresenterImpl.WIFI_CONNECTION_ERROR) {
                        showWifiConnexionFailed()
                    } else {
                        showWifiConnexionSuccess()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(activity)
        presenter = ScannerPresenterImpl()
        scannerView?.setResultHandler(this)
        return scannerView
    }

    override fun onResume() {
        super.onResume()
        presenter?.view = this
        presenter?.wifiManager = activity.applicationContext.getSystemService(Service.WIFI_SERVICE) as WifiManager

        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, IntentFilter(MainActivity.ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED))

        scannerView?.startCamera()
    }

    override fun onPause() {
        scannerView?.stopCamera()

        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)

        presenter?.wifiManager = null
        presenter?.view = null
        super.onPause()
    }

    override fun handleResult(result: Result?) {
        presenter?.receiveJSON(result?.text)
        scannerView?.resumeCameraPreview(this)
    }

    override fun showWifiConnexionSuccess() {
        Toast.makeText(context, "Wifi Connected !", Toast.LENGTH_LONG).show()
    }

    override fun showWifiConnexionFailed() {
        Toast.makeText(context, "Wifi Failed !", Toast.LENGTH_LONG).show()
    }

    override fun showBadScannedQrCode() {
        Toast.makeText(context, "Bad QrCode!", Toast.LENGTH_LONG).show()
    }

    override fun checkPermission() {
        MainActivity.checkPermissionAndAskIfItIsNeeded(activity, Manifest.permission.CHANGE_WIFI_STATE)
    }

    fun onFlashButton() {
        isFlashing = !isFlashing
        scannerView?.flash = isFlashing
    }
}

interface ScannerView {
    fun showWifiConnexionSuccess()
    fun showWifiConnexionFailed()
    fun showBadScannedQrCode()
    fun checkPermission()
}

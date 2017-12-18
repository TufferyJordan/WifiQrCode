package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation

import android.Manifest
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.GeneratorPresenter
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.GeneratorPresenterImpl
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.Listener
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_generator.*


class GeneratorFragment : Fragment(), GeneratorView {
    private var listener: Listener? = null

    private var wifiManager: WifiManager? = null

    private var presenter: GeneratorPresenter? = null

    companion object {
        fun newInstance(listener: Listener): GeneratorFragment {
            val fragment = GeneratorFragment()
            fragment.listener = listener
            return fragment
        }
    }

    val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.action) {
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> {
                    presenter?.refreshScanResult(wifiManager?.scanResults)
                }
                MainActivity.ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED -> {
                    wifiManager = activity.applicationContext.getSystemService(Service.WIFI_SERVICE) as WifiManager
                    wifiManager?.startScan()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_generator, container, false)

    override fun onStart() {
        super.onStart()

        context.registerReceiver(receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, IntentFilter(MainActivity.ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED))
        presenter = GeneratorPresenterImpl(this)

        qr_code_generate_button.setOnClickListener({
            presenter?.generateQrCode(
                    ssid_spinner.selectedItem as String,
                    password_edit_text.text.toString(),
                    security_spinner.selectedItem as SecurityType)
        })

        val adapter = ArrayAdapter<SecurityType>(context, android.R.layout.simple_spinner_item, SecurityType.values())
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        security_spinner.adapter = adapter

        MainActivity.checkPermissionAndAskIfItIsNeeded(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        MainActivity.checkPermissionAndAskIfItIsNeeded(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
        MainActivity.checkPermissionAndAskIfItIsNeeded(activity, Manifest.permission.ACCESS_FINE_LOCATION)

        wifiManager = activity.applicationContext.getSystemService(Service.WIFI_SERVICE) as WifiManager
        wifiManager?.startScan()
    }

    override fun onStop() {
        presenter = null
        LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver)
        context.unregisterReceiver(receiver)
        super.onStop()
    }

    override fun showGeneratedQrCode(qrCode: Bitmap) {
        generator_image_view_progress_bar.visibility = View.GONE
        qr_code_generated_image_view.visibility = View.VISIBLE
        qr_code_generated_image_view.setImageBitmap(qrCode)
    }

    override fun showProgressBar() {
        qr_code_generated_image_view.visibility = View.GONE
        generator_image_view_progress_bar.visibility = View.VISIBLE
    }

    override fun notifySsidsChanged(ssids: List<String>) {
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, ssids)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ssid_spinner.adapter = adapter

        if (ssids.isNotEmpty()) ssid_spinner.setSelection(0)
    }

    override fun enableButtons(shouldBeEnabled: Boolean) {
        qr_code_save_button.isEnabled = shouldBeEnabled
        qr_code_generate_button.isEnabled = shouldBeEnabled
    }
}

interface GeneratorView {
    fun showGeneratedQrCode(qrCode: Bitmap)
    fun showProgressBar()
    fun enableButtons(shouldBeEnabled: Boolean)
    fun notifySsidsChanged(ssids: List<String>)
}
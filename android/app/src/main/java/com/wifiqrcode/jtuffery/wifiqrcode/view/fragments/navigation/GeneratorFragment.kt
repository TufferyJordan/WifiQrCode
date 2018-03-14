package com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation

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
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.old.presenter.GeneratorPresenter
import com.wifiqrcode.jtuffery.wifiqrcode.old.presenter.GeneratorPresenterImpl
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_generator.*


class GeneratorFragment : Fragment(), GeneratorView {
    private lateinit var wifiManager: WifiManager
    private var presenter: GeneratorPresenter? = null

    private var ssidsValues = listOf<String>()
    private lateinit var ssidsAdapter: ArrayAdapter<String>
    private val securityTypeValues = arrayListOf(SecurityType.WPA, SecurityType.WPA2, SecurityType.WEP)
    private lateinit var securityAdapter: ArrayAdapter<SecurityType>

    companion object {
        fun newInstance(): GeneratorFragment = GeneratorFragment()
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {
            when (p1?.action) {
                WifiManager.SCAN_RESULTS_AVAILABLE_ACTION -> presenter?.refreshScanResult(wifiManager.scanResults)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        wifiManager = activity?.applicationContext?.getSystemService(Service.WIFI_SERVICE) as WifiManager
        return layoutInflater.inflate(R.layout.fragment_generator, container, false)
    }

    override fun onStart() {
        super.onStart()

        activity?.registerReceiver(receiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        presenter = GeneratorPresenterImpl(this)

        qr_code_generate_button.setOnClickListener {
            presenter?.generateQrCode(
                    ssid_spinner.selectedItem,
                    password_edit_text.text.toString(),
                    security_spinner.selectedItem)
        }

        ssidsAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, ssidsValues)
        ssidsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ssid_spinner.adapter = ssidsAdapter

        securityAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, securityTypeValues)
        securityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        security_spinner.adapter = securityAdapter

        enableButtons(false)
        wifiManager.startScan()
    }

    override fun onStop() {
        presenter = null
        LocalBroadcastManager.getInstance(context!!).unregisterReceiver(receiver)
        context!!.unregisterReceiver(receiver)
        super.onStop()
    }

    override fun showGeneratedQrCode(qrCode: Bitmap?) {
        if(qrCode == null) {
            return
        }
        generator_image_view_progress_bar.visibility = View.GONE
        qr_code_generated_image_view.visibility = View.VISIBLE
        qr_code_generated_image_view.setImageBitmap(qrCode)
    }

    override fun showProgressBar() {
        qr_code_generated_image_view.visibility = View.GONE
        generator_image_view_progress_bar.visibility = View.VISIBLE
    }

    override fun notifySsidsChanged(ssids: List<String>) {
        ssidsValues = ssids
        ssidsAdapter.notifyDataSetChanged()
        if (ssids.isNotEmpty()) ssid_spinner.setSelection(0)
    }

    override fun enableButtons(shouldBeEnabled: Boolean) {
        qr_code_save_button.isEnabled = shouldBeEnabled
        qr_code_generate_button.isEnabled = shouldBeEnabled
    }
}

interface GeneratorView {
    fun showGeneratedQrCode(qrCode: Bitmap?)
    fun showProgressBar()
    fun enableButtons(shouldBeEnabled: Boolean)
    fun notifySsidsChanged(ssids: List<String>)
}
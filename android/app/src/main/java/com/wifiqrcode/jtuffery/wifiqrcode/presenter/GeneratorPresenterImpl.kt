package com.wifiqrcode.jtuffery.wifiqrcode.old.presenter

import android.net.wifi.ScanResult
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.wifiqrcode.jtuffery.wifiqrcode.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation.GeneratorView
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation.GeneratorView

class GeneratorPresenterImpl(val view: GeneratorView) : GeneratorPresenter {

    override fun generateQrCode(ssid: Any?, password: String?, securityType: Any?) {
        view.enableButtons(false)
        view.showProgressBar()
        if(ssid == null || password == null || securityType == null) {
            view.showGeneratedQrCode(null)
        } else {
            val json = WiFiDAO(ssid as String, password, securityType as SecurityType).toJson()
            val encoder = QRGEncoder(json, null, QRGContents.Type.TEXT, 1000)
            view.showGeneratedQrCode(encoder.encodeAsBitmap())
            view.enableButtons(true)
        }
    }

    override fun refreshScanResult(results: List<ScanResult>?) {
        val ssids = ArrayList<String>()
        results?.forEach { ssids.add(it.SSID) }
        view.notifySsidsChanged(ssids.filter { it.isNotEmpty() })
    }
}

interface GeneratorPresenter {
    fun refreshScanResult(results: List<ScanResult>?)
    fun generateQrCode(ssid: Any?, password: String?, securityType: Any?)
}
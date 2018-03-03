package com.wifiqrcode.jtuffery.wifiqrcode.old.presenter

import android.net.wifi.ScanResult
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation.GeneratorView

class GeneratorPresenterImpl(val view: GeneratorView) : GeneratorPresenter {

    override fun generateQrCode(ssid: String, password: String, securityType: SecurityType) {
        view.enableButtons(false)
        view.showProgressBar()
        val json = WiFiDAO(ssid, password, securityType).toJson()
        val encoder = QRGEncoder(json, null, QRGContents.Type.TEXT, 1000)
        view.showGeneratedQrCode(encoder.encodeAsBitmap())
        view.enableButtons(true)
    }

    override fun refreshScanResult(results: List<ScanResult>?) {
        val ssids = ArrayList<String>()
        results?.forEach { ssids.add(it.SSID) }
        view.notifySsidsChanged(ssids.filter { it.isNotEmpty() })
    }
}

interface GeneratorPresenter {
    fun refreshScanResult(results: List<ScanResult>?)
    fun generateQrCode(ssid: String, password: String, securityType: SecurityType)
}
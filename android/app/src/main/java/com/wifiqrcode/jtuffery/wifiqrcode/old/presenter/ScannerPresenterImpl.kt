package com.wifiqrcode.jtuffery.wifiqrcode.old.presenter

import android.net.wifi.WifiConfiguration
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.others.ScannerView

class ScannerPresenterImpl : ScannerPresenter {
    override var view: ScannerView? = null

    override fun receiveJSON(json: String?) {
        val wifi = WiFiDAO.fromJsonToString(json)

        if (wifi == null) {
            view?.showBadScannedQrCode()
            return
        }

        val wifiConfiguration = WifiConfiguration().apply {
            SSID = "\"${wifi.ssid}\""
            when (wifi.securityType) {
                SecurityType.NONE -> allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                SecurityType.WEP -> {
                    wepKeys[0] = "\"${wifi.password}\""
                    wepTxKeyIndex = 0
                    allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                    allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
                }
                else -> preSharedKey = "\"${wifi.password}\""
            }
        }
        view?.handleWifiConnexionScanned(wifiConfiguration)
    }
}

interface ScannerPresenter {
    var view: ScannerView?
    fun receiveJSON(json: String?)
}

package com.wifiqrcode.jtuffery.wifiqrcode.presenter

import android.net.wifi.WifiConfiguration
import com.wifiqrcode.jtuffery.wifiqrcode.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others.ScannerView

class ScannerPresenterImpl : ScannerPresenter {

    override var view: ScannerView? = null

    override fun receiveJSON(json: String?) {
        val wifi = WiFiDAO.fromJsonString(json)
        val wifiConfiguration = WifiConfiguration()
        wifiConfiguration.SSID = "\"${wifi.ssid}\""

        when (wifi.securityType) {
            SecurityType.NONE -> {
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
            }
            SecurityType.WEP -> {
                wifiConfiguration.wepKeys[0] = "\"${wifi.password}\""
                wifiConfiguration.wepTxKeyIndex = 0
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
            }
            else -> {
                wifiConfiguration.preSharedKey = "\"${wifi.password}\""
            }
        }
        view?.handleWifiConnexionScanned(wifiConfiguration)
    }
}

interface ScannerPresenter {
    var view: ScannerView?
    fun receiveJSON(json: String?)
}

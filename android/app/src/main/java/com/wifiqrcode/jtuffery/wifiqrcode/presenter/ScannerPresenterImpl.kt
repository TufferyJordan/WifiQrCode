package com.wifiqrcode.jtuffery.wifiqrcode.presenter

import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import com.wifiqrcode.jtuffery.wifiqrcode.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others.ScannerView

class ScannerPresenterImpl : ScannerPresenter {

    override var view: ScannerView? = null
    override var wifiManager: WifiManager? = null
    private var lastWifiConfiguration: WifiConfiguration? = null

    override fun receiveJSON(json: String?) {
        val wifi = WiFiDAO.fromJsonString(json)
        val wifiConfiguration = WifiConfiguration()
        wifiConfiguration.SSID = wifi.ssid
        wifiConfiguration.status = WifiConfiguration.Status.DISABLED
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN)
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA)
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP)
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP)
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40)
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104)

        when (wifi.securityType) {
            SecurityType.NONE -> {
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                wifiConfiguration.allowedAuthAlgorithms.clear()
            }
            SecurityType.WEP -> {
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                wifiConfiguration.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)

                if (getHexKey(wifi.password)) wifiConfiguration.wepKeys[0] = wifi.password
                else wifiConfiguration.wepKeys[0] = "\"${wifi.password}\""
                wifiConfiguration.wepTxKeyIndex = 0
            }
            else -> {
                wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                wifiConfiguration.preSharedKey = wifi.password
            }
        }
        lastWifiConfiguration = wifiConfiguration
        view?.checkPermission()
    }

    override fun addWifiNetwork(): Int? {
        if(lastWifiConfiguration == null) return WIFI_CONNECTION_ERROR
        if(!wifiManager?.isWifiEnabled!!) wifiManager?.isWifiEnabled = true
        val netId = wifiManager?.addNetwork(lastWifiConfiguration)
        wifiManager?.enableNetwork(netId!!, true)
        return 0
    }

    companion object {
        const val WIFI_CONNECTION_ERROR = -1

        private fun getHexKey(s: String?): Boolean {
            if (s == null) {
                return false
            }
            val len = s.length
            if (len != 10 && len != 26 && len != 58) {
                return false
            }
            for (i in 0 until len) {
                val c = s[i]
                if (c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F') {
                    continue
                }
                return false
            }
            return true
        }
    }
}

interface ScannerPresenter {
    var view: ScannerView?
    var wifiManager: WifiManager?
    fun receiveJSON(json: String?)
    fun addWifiNetwork(): Int?
}

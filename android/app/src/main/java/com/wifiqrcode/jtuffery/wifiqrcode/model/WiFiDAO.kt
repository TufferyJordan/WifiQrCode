package com.wifiqrcode.jtuffery.wifiqrcode.model

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType)

enum class SecurityType {
    WPA,
    WPA2,
    WEP
}
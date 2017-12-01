package com.wifiqrcode.jtuffery.wifiqrcode.model

import com.google.gson.JsonElement
import com.google.gson.JsonParser

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType) {
    // {"ssid":"DXOEXT", "password":"waggery-laser-organdy", "securityType":"wpa"}
    companion object {
        fun fromJsonString(json: String?): WiFiDAO {
            val jsonObject = JsonParser().parse(json).asJsonObject
            return WiFiDAO(jsonObject.get("ssid").asString,
                    jsonObject.get("password").asString,
                    jsonToSecurityElement(jsonObject.get("securityType")))
        }
        private fun jsonToSecurityElement(element: JsonElement): SecurityType {
            return when(element.asString) {
                "wpa" -> SecurityType.WPA
                "wpa2" -> SecurityType.WPA2
                "wep" -> SecurityType.WEP
                else -> SecurityType.BAD_SECURITY_TYPE
            }
        }
    }
}

enum class SecurityType {
    WPA,
    WPA2,
    WEP,
    NONE,
    BAD_SECURITY_TYPE
}
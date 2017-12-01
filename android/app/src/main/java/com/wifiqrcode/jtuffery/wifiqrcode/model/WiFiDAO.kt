package com.wifiqrcode.jtuffery.wifiqrcode.model

import com.google.gson.JsonElement
import com.google.gson.JsonParser

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType) {
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
                else -> throw IllegalArgumentException()
            }
        }
    }
}

enum class SecurityType {
    WPA,
    WPA2,
    WEP
}
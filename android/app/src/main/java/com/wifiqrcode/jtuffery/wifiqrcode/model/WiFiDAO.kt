package com.wifiqrcode.jtuffery.wifiqrcode.model

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType) {
    // {"ssid":"DXOEXT", "password":"waggery-laser-organdy", "securityType":"wpa"}
    // {"ssid":"Bbox-0C86DCF4-5GHz", "password":"3696A224F975235EFC1234D523513F", "securityType":"wpa"}
    companion object {
        fun fromJsonToString(json: String?): WiFiDAO {
            val jsonObject = JsonParser().parse(json).asJsonObject
            return WiFiDAO(jsonObject.get("ssid").asString,
                    jsonObject.get("password").asString,
                    jsonToSecurityElement(jsonObject.get("securityType")))
        }

        private fun jsonToSecurityElement(element: JsonElement): SecurityType {
            return when (element.asString) {
                "wpa" -> SecurityType.WPA
                "wpa2" -> SecurityType.WPA2
                "wep" -> SecurityType.WEP
                else -> SecurityType.BAD_SECURITY_TYPE
            }
        }
    }

    fun toJson() : String = JSONObject().apply {
            put("ssid", ssid)
            put("password", password)
            put("securityType", securityType.friendlyName)
        }.toString()
}

enum class SecurityType(val friendlyName: String) {
    WPA("wpa"),
    WPA2("wpa2"),
    WEP("wep"),
    NONE("none"),
    BAD_SECURITY_TYPE("badSecurityType");

    override fun toString(): String = friendlyName
}
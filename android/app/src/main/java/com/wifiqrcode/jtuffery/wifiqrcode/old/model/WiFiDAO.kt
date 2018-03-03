package com.wifiqrcode.jtuffery.wifiqrcode.old.model

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import org.json.JSONObject

/**
 * {"ssid":"DXOEXT", "password":"waggery-laser-organdy", "securityType":"wpa"}
 * {"ssid":"Bbox-0C86DCF4-5GHz", "password":"3696A224F975235EFC1234D523513F", "securityType":"wpa"}
 */

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType) {
    companion object {
        fun fromJsonToString(json: String?): WiFiDAO? {
            val jsonObject: JsonObject
            try {
                jsonObject = JsonParser().parse(json).asJsonObject
            } catch (e: JsonParseException) {
                return null
            }
            return WiFiDAO(jsonObject.get("ssid").asString,
                    jsonObject.get("password").asString,
                    jsonObject.get("securityType").asString.toSecurityType())
        }
    }

    fun toJson(): String = JSONObject().apply {
        put("ssid", ssid)
        put("password", password)
        put("securityType", securityType.friendlyName)
    }.toString()
}

fun String.toSecurityType(): SecurityType = when (this) {
    "wpa" -> SecurityType.WPA
    "wpa2" -> SecurityType.WPA2
    "wep" -> SecurityType.WEP
    "none" -> SecurityType.NONE
    else -> SecurityType.BAD_SECURITY_TYPE
}

enum class SecurityType(val friendlyName: String) {
    WPA("wpa"),
    WPA2("wpa2"),
    WEP("wep"),
    NONE("none"),
    BAD_SECURITY_TYPE("badSecurityType");

    override fun toString(): String = friendlyName
}
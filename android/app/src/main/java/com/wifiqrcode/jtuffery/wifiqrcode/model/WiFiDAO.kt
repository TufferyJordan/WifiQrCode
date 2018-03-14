package com.wifiqrcode.jtuffery.wifiqrcode.model

import com.google.gson.JsonObject
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import org.json.JSONObject

data class WiFiDAO(var ssid: String, var password: String, var securityType: SecurityType) {
    companion object {
        fun fromJsonToString(json: String?): WiFiDAO? {
            val jsonObject: JsonObject
            if(json == null) return null
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
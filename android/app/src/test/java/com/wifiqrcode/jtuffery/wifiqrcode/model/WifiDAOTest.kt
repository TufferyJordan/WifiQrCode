package com.wifiqrcode.jtuffery.wifiqrcode.model

import com.wifiqrcode.jtuffery.wifiqrcode.old.model.SecurityType
import com.wifiqrcode.jtuffery.wifiqrcode.old.model.WiFiDAO
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WifiDAOTest {
    @Test
    fun testIfJSONParsingIsGood() {
        val daoToTest = WiFiDAO.fromJsonToString("{\"ssid\":\"Bbox-5GHz\", \"password\":\"1234\", \"securityType\":\"wpa\"}")
        Assert.assertNotNull(daoToTest)
        Assert.assertEquals(daoToTest?.ssid, "Bbox-5GHz")
        Assert.assertEquals(daoToTest?.password, "1234")
        Assert.assertEquals(daoToTest?.securityType, SecurityType.WPA)
    }

    @Test
    fun testIfJSONParsingIsntGood() {
        var daoToTest = WiFiDAO.fromJsonToString(null)
        Assert.assertNull(daoToTest)
        daoToTest = WiFiDAO.fromJsonToString("AHAHAHAHA")
        Assert.assertNull(daoToTest)
        daoToTest = WiFiDAO.fromJsonToString("{\"ssid\":\"Bbox-5GHz\", \"password\":\"1234\"}")
        Assert.assertNull(daoToTest)
    }
}

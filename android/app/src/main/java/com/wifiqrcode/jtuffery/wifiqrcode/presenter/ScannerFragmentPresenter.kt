package com.wifiqrcode.jtuffery.wifiqrcode.presenter

import com.wifiqrcode.jtuffery.wifiqrcode.model.WiFiDAO
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others.ScannerFragmentView

class ScannerFragmentPresenter(var view: ScannerFragmentView): ScannerFragmentAction {
    override fun receiveJSON(json: String?) {
        var dao = WiFiDAO.fromJsonString(json)
        view.showWifiConnexionSuccess()
    }
}

interface ScannerFragmentAction {
    fun receiveJSON(json: String?)
}

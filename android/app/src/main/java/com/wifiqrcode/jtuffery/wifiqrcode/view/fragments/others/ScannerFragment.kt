package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.zxing.Result
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.ScannerFragmentAction
import com.wifiqrcode.jtuffery.wifiqrcode.presenter.ScannerFragmentPresenter
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler, ScannerFragmentView {
    private var presenter: ScannerFragmentAction? = null

    private var scannerView: ZXingScannerView? = null
    private var isFlashing = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(activity)
        presenter = ScannerFragmentPresenter(this)
        scannerView?.setResultHandler(this)
        return scannerView
    }

    override fun onResume() {
        super.onResume()
        scannerView?.startCamera()
    }

    override fun onPause() {
        scannerView?.startCamera()
        super.onPause()
    }

    override fun handleResult(result: Result?) {
        presenter?.receiveJSON(result?.text)
        scannerView?.resumeCameraPreview(this)
    }

    override fun showWifiConnexionSuccess() {
        Toast.makeText(context, "Wifi Connected !", Toast.LENGTH_LONG).show()
    }

    override fun showWifiConnexionFailed() {
        Toast.makeText(context, "Wifi Failed !", Toast.LENGTH_LONG).show()
    }

    override fun showBadScannedQrCode() {
        Toast.makeText(context, "Bad QrCode!", Toast.LENGTH_LONG).show()
    }

    fun onFlashButton() {
        isFlashing = !isFlashing
        scannerView?.flash = isFlashing
    }
}

interface ScannerFragmentView {
    fun showWifiConnexionSuccess()
    fun showWifiConnexionFailed()
    fun showBadScannedQrCode()
}

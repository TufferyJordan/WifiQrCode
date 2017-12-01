package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerFragment : Fragment(), ZXingScannerView.ResultHandler {
    private var scannerView: ZXingScannerView? = null
    private var isFlashing = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZXingScannerView(activity)
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
        Log.e("SCANNERFRAGMENT", result?.text)
        scannerView?.resumeCameraPreview(this)
    }

    fun onFlashButton() {
        isFlashing = !isFlashing
        scannerView?.flash = isFlashing
    }
}

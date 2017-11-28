package com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.samples.vision.barcodereader.BarcodeGraphic
import com.google.android.gms.vision.barcode.Barcode
import com.wifiqrcode.jtuffery.wifiqrcode.R
import xyz.belvi.mobilevisionbarcodescanner.BarcodeRetriever
import com.google.android.gms.samples.vision.barcodereader.BarcodeCapture


class ReaderFragment : Fragment(), BarcodeRetriever {

    companion object {
        fun newInstance(): ReaderFragment = ReaderFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        val rootView = layoutInflater.inflate(R.layout.fragment_reader, container, false)
        val barcodeCapture = childFragmentManager.findFragmentById(R.id.barcode) as BarcodeCapture
        barcodeCapture.setRetrieval(this)
        return rootView
    }

    override fun onRetrievedMultiple(closetToClick: Barcode?, barcode: MutableList<BarcodeGraphic>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionRequestDenied() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrievedFailed(reason: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRetrieved(barcode: Barcode?) {
        Log.d("ReaderFragment", "Barcode read: " + barcode?.displayValue)
    }

}
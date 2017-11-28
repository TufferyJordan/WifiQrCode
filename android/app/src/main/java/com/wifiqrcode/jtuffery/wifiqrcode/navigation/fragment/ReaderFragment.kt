package com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment

import android.hardware.Camera
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.integration.android.IntentIntegrator
import com.wifiqrcode.jtuffery.wifiqrcode.R

class ReaderFragment : Fragment() {
    companion object {
        fun newInstance(): ReaderFragment = ReaderFragment()
    }

    private var integrator: IntentIntegrator? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = layoutInflater.inflate(R.layout.fragment_reader, container, false)
        return rootView
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
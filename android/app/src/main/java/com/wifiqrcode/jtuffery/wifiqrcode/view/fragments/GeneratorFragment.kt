package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R

class GeneratorFragment : Fragment() {
    companion object {
        fun newInstance(): GeneratorFragment = GeneratorFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_generator, container, false)

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
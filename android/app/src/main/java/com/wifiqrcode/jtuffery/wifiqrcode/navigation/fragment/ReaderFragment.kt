package com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R
import kotlinx.android.synthetic.main.fragment_reader.*


class ReaderFragment : Fragment() {
    companion object {
        fun newInstance(): ReaderFragment = ReaderFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_reader, container, false)
}
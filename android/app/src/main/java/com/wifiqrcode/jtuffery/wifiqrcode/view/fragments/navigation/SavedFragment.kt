package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.Listener

class SavedFragment : Fragment() {
    private var listener: Listener? = activity as Listener

    companion object {
        fun newInstance(): SavedFragment = SavedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_saved, container, false)
}
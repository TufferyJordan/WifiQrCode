package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.Listener

class SavedFragment : Fragment() {
    private var listener: Listener? = null
    companion object {
        fun newInstance(listener: Listener): SavedFragment {
            val fragment = SavedFragment()
            fragment.listener = listener
            return fragment
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_saved, container, false)

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
package com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : Fragment() {

    companion object {
        fun newInstance(): SavedFragment = SavedFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_saved, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        force_crash_button.setOnClickListener(::onForceCrash)
    }
    fun onForceCrash(view: View) {
        throw RuntimeException("This is a crash")
    }

}
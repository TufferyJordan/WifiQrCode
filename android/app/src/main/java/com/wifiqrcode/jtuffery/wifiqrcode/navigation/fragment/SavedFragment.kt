package com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R

class SavedFragment : Fragment() {
    companion object {
        fun newInstance(): SavedFragment = SavedFragment()
    }

    override fun onInflate(context: Context?, attrs: AttributeSet?, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_saved, container, false)

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }
}
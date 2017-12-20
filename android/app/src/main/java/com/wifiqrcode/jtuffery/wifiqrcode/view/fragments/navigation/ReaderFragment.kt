package com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.Listener
import com.wifiqrcode.jtuffery.wifiqrcode.view.activities.MainActivity
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.others.ScannerFragment
import kotlinx.android.synthetic.main.fragment_reader.*


class ReaderFragment : Fragment() {
    private val listener: Listener = activity as Listener
    private var fragment: ScannerFragment = childFragmentManager.findFragmentById(R.id.scanner_fragment) as ScannerFragment

    companion object {
        fun newInstance(): ReaderFragment {
            val fragment = ReaderFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? = layoutInflater.inflate(R.layout.fragment_reader, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reader_flash_button.setOnClickListener({ fragment.onFlashButton() })
        MainActivity.checkPermissionAndAskIfItIsNeeded(activity, Manifest.permission.CAMERA)
    }
}
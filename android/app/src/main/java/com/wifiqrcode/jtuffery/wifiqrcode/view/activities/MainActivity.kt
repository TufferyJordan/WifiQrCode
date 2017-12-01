package com.wifiqrcode.jtuffery.wifiqrcode.view.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation.GeneratorFragment
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation.ReaderFragment
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.navigation.SavedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Listener {
    private var savedFragment: SavedFragment? = SavedFragment.newInstance(this)
    private var readerFragment: ReaderFragment? = ReaderFragment.newInstance(this)
    private var generatorFragment: GeneratorFragment? = GeneratorFragment.newInstance(this)

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_saved_qr_codes -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, savedFragment).commit()
                true
            }
            R.id.navigation_qr_code_reader -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, readerFragment).commit()
                true
            }
            R.id.navigation_qr_code_generator -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, generatorFragment).commit()
                true
            }
            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_qr_code_reader
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MainActivity.REQUEST_PERMISSION_CAMERA) {
            if (!grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                goToGeneratorFragment()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun goToSavedFragment() {
        navigation.selectedItemId = R.id.navigation_saved_qr_codes
    }

    override fun goToReaderFragment() {
        navigation.selectedItemId = R.id.navigation_qr_code_reader
    }

    override fun goToGeneratorFragment() {
        navigation.selectedItemId = R.id.navigation_qr_code_generator
    }

    companion object {
        val REQUEST_PERMISSION_CAMERA = 0

        fun checkPermissionAndAskIfItIsNeeded(activity: FragmentActivity, permission: String) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(permission), getRightRequestCode(permission))
            }
        }

        private fun getRightRequestCode(permission: String): Int {
            return when (permission) {
                Manifest.permission.CAMERA -> REQUEST_PERMISSION_CAMERA
                else -> throw IllegalArgumentException("It's not the right permission !")
            }
        }
    }
}

interface Listener {
    fun goToSavedFragment()
    fun goToReaderFragment()
    fun goToGeneratorFragment()
}

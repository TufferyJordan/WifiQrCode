package com.wifiqrcode.jtuffery.wifiqrcode.old.view.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation.GeneratorFragment
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation.ReaderFragment
import com.wifiqrcode.jtuffery.wifiqrcode.old.view.fragments.navigation.SavedFragment
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), Listener {
    private var savedFragment: SavedFragment? = SavedFragment.newInstance()
    private var readerFragment: ReaderFragment? = ReaderFragment.newInstance()
    private var generatorFragment: GeneratorFragment? = GeneratorFragment.newInstance()

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
        Fabric.with(this, Crashlytics())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_qr_code_reader
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MainActivity.REQUEST_PERMISSION_CAMERA -> if (!grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                goToGeneratorFragment()
            }
            MainActivity.REQUEST_PERMISSION_CHANGE_WIFI_STATE -> LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(MainActivity.ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED))
            MainActivity.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE -> LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(MainActivity.ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED))
            MainActivity.REQUEST_PERMISSION_ACCESS_COARSE_LOCATION -> LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(MainActivity.ACTION_REQUEST_PERMISSION_ACCESS_COARSE_LOCATION))
            MainActivity.REQUEST_PERMISSION_ACCESS_FINE_LOCATION -> LocalBroadcastManager.getInstance(this).sendBroadcast(Intent(MainActivity.ACTION_REQUEST_PERMISSION_ACCESS_FINE_LOCATION))
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
        const val ACTION_REQUEST_PERMISSION_CAMERA = "ACTION_REQUEST_PERMISSION_CAMERA"
        const val ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED = "ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED"
        const val ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED = "ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED"
        const val ACTION_REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = "ACTION_REQUEST_PERMISSION_ACCESS_COARSE_LOCATION"
        const val ACTION_REQUEST_PERMISSION_ACCESS_FINE_LOCATION = "ACTION_REQUEST_PERMISSION_ACCESS_FINE_LOCATION"

        const val REQUEST_PERMISSION_CAMERA = 0
        const val REQUEST_PERMISSION_CHANGE_WIFI_STATE = 1
        const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2
        const val REQUEST_PERMISSION_ACCESS_COARSE_LOCATION = 3
        const val REQUEST_PERMISSION_ACCESS_FINE_LOCATION = 4

        fun checkPermissionAndAskIfItIsNeeded(activity: FragmentActivity, permission: String) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity,
                        arrayOf(permission), getRightRequestCode(permission))
            } else {
                LocalBroadcastManager.getInstance(activity).sendBroadcast(Intent(getRightActionRequest(permission)))
            }
        }

        private fun getRightRequestCode(permission: String): Int = when (permission) {
            Manifest.permission.CAMERA -> REQUEST_PERMISSION_CAMERA
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE
            Manifest.permission.CHANGE_WIFI_STATE -> REQUEST_PERMISSION_CHANGE_WIFI_STATE
            Manifest.permission.ACCESS_COARSE_LOCATION -> REQUEST_PERMISSION_ACCESS_COARSE_LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION -> REQUEST_PERMISSION_ACCESS_FINE_LOCATION
            else -> throw IllegalArgumentException("It's not the right permission !")
        }

        private fun getRightActionRequest(permission: String): String = when (permission) {
            Manifest.permission.CAMERA -> ACTION_REQUEST_PERMISSION_CAMERA
            Manifest.permission.CHANGE_WIFI_STATE -> ACTION_REQUEST_PERMISSION_CHANGE_WIFI_STATE_GRANTED
            Manifest.permission.WRITE_EXTERNAL_STORAGE -> ACTION_REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE_GRANTED
            Manifest.permission.ACCESS_COARSE_LOCATION -> ACTION_REQUEST_PERMISSION_ACCESS_COARSE_LOCATION
            Manifest.permission.ACCESS_FINE_LOCATION -> ACTION_REQUEST_PERMISSION_ACCESS_FINE_LOCATION
            else -> throw IllegalArgumentException("It's not the right permission !")
        }
    }
}

interface Listener {
    fun goToSavedFragment()
    fun goToReaderFragment()
    fun goToGeneratorFragment()
}

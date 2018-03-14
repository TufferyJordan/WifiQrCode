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

    override fun goToSavedFragment() {
        navigation.selectedItemId = R.id.navigation_saved_qr_codes
    }

    override fun goToReaderFragment() {
        navigation.selectedItemId = R.id.navigation_qr_code_reader
    }

    override fun goToGeneratorFragment() {
        navigation.selectedItemId = R.id.navigation_qr_code_generator
    }
}

interface Listener {
    fun goToSavedFragment()
    fun goToReaderFragment()
    fun goToGeneratorFragment()
}

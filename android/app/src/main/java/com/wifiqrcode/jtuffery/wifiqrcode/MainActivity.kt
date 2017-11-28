package com.wifiqrcode.jtuffery.wifiqrcode

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment.GeneratorFragment
import com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment.ReaderFragment
import com.wifiqrcode.jtuffery.wifiqrcode.navigation.fragment.SavedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_saved_qr_codes -> {
                supportFragmentManager.beginTransaction().add(R.id.main_fragment, SavedFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code_reader -> {
                supportFragmentManager.beginTransaction().add(R.id.main_fragment, ReaderFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code_generator -> {
                supportFragmentManager.beginTransaction().add(R.id.main_fragment, GeneratorFragment.newInstance()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}

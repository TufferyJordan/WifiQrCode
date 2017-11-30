package com.wifiqrcode.jtuffery.wifiqrcode.view.activities

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.wifiqrcode.jtuffery.wifiqrcode.R
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.GeneratorFragment
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.ReaderFragment
import com.wifiqrcode.jtuffery.wifiqrcode.view.fragments.SavedFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var savedFragment: SavedFragment? = null
    get(){
        if(field == null)
            field = SavedFragment.newInstance()
        return field
    }
    private var readerFragment: ReaderFragment? = null
        get(){
            if(field == null)
                field = ReaderFragment.newInstance()
            return field
        }
    private var generatorFragment: GeneratorFragment? = null
        get(){
            if(field == null)
                field = GeneratorFragment.newInstance()
            return field
        }


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_saved_qr_codes -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, savedFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code_reader -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, readerFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_qr_code_generator -> {
                supportFragmentManager.beginTransaction().replace(R.id.main_fragment, generatorFragment).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.selectedItemId = R.id.navigation_qr_code_reader
    }
}

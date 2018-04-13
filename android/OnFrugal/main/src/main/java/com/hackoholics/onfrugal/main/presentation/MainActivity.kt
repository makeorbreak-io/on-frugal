package com.hackoholics.onfrugal.main.presentation

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hackoholics.onfrugal.R.id.message
import com.hackoholics.onfrugal.main.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Toast.makeText(this,R.string.title_home, Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_offers-> {
                Toast.makeText(this,R.string.title_my_offers, Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile-> {
                Toast.makeText(this,R.string.title_profile, Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)


             // starting Location Service Manager for receiving location data
//        var intent = Intent(MainActivity.this, LocationService.class);
        startService(intent);

        val fragmentManager = getSupportFragmentManager();

        // define your fragments here
//        val findOffers = MainMapFragment();
//        val search = new SearchFragment();
//        val account = new AccountFragment();

//        BottomNavigationView bottomNavigationView = (BottomNavigationView)
//                    findViewById(R.id.bottom_navigation);


        var fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.flContainer, map).commit();
    }
}

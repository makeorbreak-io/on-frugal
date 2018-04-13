package com.hackoholics.onfrugal.main.presentation

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hackoholics.onfrugal.main.R
import com.hackoholics.onfrugal.main.domain.services.LocationService
import com.hackoholics.onfrugal.main.presentation.FindOfferMainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val findOffers= FindOfferMainFragment()
    val myOffers= MyOffersFragment()
    lateinit var fragmentManager: FragmentManager

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flContainer, findOffers).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_offers-> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.flContainer, myOffers).addToBackStack(null).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_account-> {
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
        val intent = Intent(this@MainActivity, LocationService::class.java)
        startService(intent)

        fragmentManager = getSupportFragmentManager()

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.flContainer, findOffers).commit()
    }
}

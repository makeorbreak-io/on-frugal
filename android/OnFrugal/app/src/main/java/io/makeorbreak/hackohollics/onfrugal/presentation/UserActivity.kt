package io.makeorbreak.hackohollics.onfrugal.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.User
import kotlinx.android.synthetic.main.activity_user.*
import kotlinx.android.synthetic.main.content_scrolling_user.*

class UserActivity : AppCompatActivity() {

    lateinit var user:User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        user = intent.extras["USER"] as User
        updateContactInformation(user)

        phone_container.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL)
            callIntent.data = Uri.parse("tel:${user.phoneNumber}")
            startActivity(callIntent)
        }

        email_container.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.setData(Uri.parse("mailto:${user.email}"))
            startActivity(Intent.createChooser(intent,"Send mail..."))
        }
    }

    fun updateContactInformation(user: User){
        title = user.name

        tvNumber1.text = user.phoneNumber
        tvNumber3.text = user.email

    }

    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return true
    }
}

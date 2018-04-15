package io.makeorbreak.hackohollics.onfrugal

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import io.makeorbreak.hackohollics.onfrugal.presentation.LoginActivity
import io.makeorbreak.hackohollics.onfrugal.presentation.MainActivity

class OnFrugal : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        val token = FirebaseInstanceId.getInstance().getToken()
        Log.d(OnFrugal::class.java.simpleName,"token: " + token)

//        String ip = NetworkUtil.getDeviceIPAddress(true);
//        Log.d("IP Address", ip);


        if (auth.getCurrentUser() != null) {
            startActivity(Intent(this@OnFrugal, MainActivity::class.java))
            finish()
        }else {
            startActivity(Intent(this@OnFrugal, LoginActivity::class.java))
            finish()
        }
    }
}

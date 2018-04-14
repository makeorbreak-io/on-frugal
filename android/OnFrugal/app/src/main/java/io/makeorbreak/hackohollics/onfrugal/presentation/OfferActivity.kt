package io.makeorbreak.hackohollics.onfrugal.presentation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer

import kotlinx.android.synthetic.main.activity_offer.*
import kotlinx.android.synthetic.main.content_offer.*

class OfferActivity : AppCompatActivity() {

    lateinit var offer: Offer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        offer = intent.extras["OFFER"] as Offer
        supportActionBar!!.title = offer.name

        offerDescription.setText(offer.description)
        offerAddress.text = offer.location
        offerEndDate.text = offer.endDate.toString()
        textHostName.text = offer.host.name
        userRatingBar.rating = 3F

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                swipeContainer.isRefreshing = false
            }
        })
    }



}

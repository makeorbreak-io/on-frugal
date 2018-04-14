package io.makeorbreak.hackohollics.onfrugal.presentation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.RatingBar
import android.widget.TextView
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer

import kotlinx.android.synthetic.main.activity_offer.*
import kotlinx.android.synthetic.main.content_offer.*

class OfferActivity : AppCompatActivity() {

    lateinit var offer: Offer

    lateinit var mAdapter: RecyclerView.Adapter<UserAdapter.ViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

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
        host_box.findViewById<TextView>(R.id.textUserName).text = getString(R.string.placeholder_host) + offer.host.name
        host_box.findViewById<RatingBar>(R.id.userRatingBar).rating = 3F

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



        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        user_recycler_view.setHasFixedSize(true)
        user_recycler_view.setFocusable(false)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(this)
        user_recycler_view.setLayoutManager(mLayoutManager)
        //Add line between each item
        user_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        mAdapter = UserAdapter(offer.candidates.toTypedArray())
        user_recycler_view.adapter = mAdapter
    }



}

package io.makeorbreak.hackohollics.onfrugal.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.RatingBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer
import io.makeorbreak.hackohollics.onfrugal.domain.model.User

import kotlinx.android.synthetic.main.activity_offer.*
import kotlinx.android.synthetic.main.content_offer.*

@SuppressLint("SetTextI18n")
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

        updateOfferDetails()

        fab_candidate.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        fab_host.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(object: SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                swipeContainer.isRefreshing = false
            }
        })

        accepted_recycler_view.setHasFixedSize(true)
        accepted_recycler_view.setFocusable(false)
        candidates_recycler_view.setHasFixedSize(true)
        candidates_recycler_view.setFocusable(false)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(this)
        candidates_recycler_view.setLayoutManager(mLayoutManager)
        //Add line between each item
        candidates_recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        if(FirebaseAuth.getInstance().uid == offer.host.uid)
            showHostSection()
            updateCandidates(offer.candidates)
            updateAccepted(offer.accepted)
    }

    private fun updateOfferDetails() {
        offerDescription.setText(offer.description)
        offerAddress.text = offer.location
        offerEndDate.text = offer.endDate.toString()
        host_box.findViewById<TextView>(R.id.textUserName).text = getString(R.string.placeholder_host) + " " + offer.host.name
        host_box.findViewById<RatingBar>(R.id.userRatingBar).rating = 3F

        offerSpots.text = (offer.spots - offer.getNumberAccepted()).toString()
        offerAccepted.text =offer.getNumberAccepted().toString()
        offerCandidates.text =offer.getNumberCandidates().toString()

    }

    private fun showHostSection() {
        host_section.visibility=VISIBLE
        fab_candidate.visibility = GONE
        fab_host.visibility = VISIBLE
    }


    fun updateCandidates(candidates: List<User>){
        label_candidates.text = getString(R.string.candidates) + "(${candidates.size})"
        mAdapter = UserAdapter(candidates.toTypedArray())
        candidates_recycler_view.adapter = mAdapter
    }

    fun updateAccepted(accepted: List<User>){
        label_accepted.text = getString(R.string.accepted) + "(${accepted.size})"
        val adapter= UserAdapter(accepted.toTypedArray())
        accepted_recycler_view.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return true
    }

}

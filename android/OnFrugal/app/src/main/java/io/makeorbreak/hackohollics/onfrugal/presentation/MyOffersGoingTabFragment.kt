package io.makeorbreak.hackohollics.onfrugal.presentation

import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.data.local.UserRepository
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer
import io.makeorbreak.hackohollics.onfrugal.domain.model.User
import kotlinx.android.synthetic.main.fragment_tab_my_offers.*
import java.util.*

class MyOffersGoingTabFragment : Fragment() {

    lateinit var offerOngoingRecyclerView: RecyclerView
    lateinit var offerPastRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tab_my_offers, container, false)
        val layoutManager1 = LinearLayoutManager(context)

        offerOngoingRecyclerView = v.findViewById(R.id.offers_ongoing)
        offerOngoingRecyclerView.setHasFixedSize(true)
        offerOngoingRecyclerView.setLayoutManager(layoutManager1)
        offerOngoingRecyclerView.setFocusable(false)
        offerOngoingRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        val layoutManager2 = LinearLayoutManager(context)
        offerPastRecyclerView = v.findViewById(R.id.offers_past)
        offerPastRecyclerView.setHasFixedSize(true)
        offerPastRecyclerView.setLayoutManager(layoutManager2)
        offerPastRecyclerView.setFocusable(false)
        offerPastRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        startUpdate()
        return v
    }

    private fun startUpdate() {
        AsyncTask.execute(object: Runnable {
            override fun run() {
                //TODO GET list
//                val list = RoomDatabase.instance.getDatabase(applicationContext).benchmarkDao().all
//                updateOffers(list)

                val pastList = ArrayList<Offer>()
                val ongoingList = ArrayList<Offer>()

                val userRepository = UserRepository.getInstance()

                val host = User("0",
                        "John Doe", "johndoe@gmail.com", "919000000")

                val candidatesList = ArrayList<User>()

                for(i in 0..20){
                    candidatesList.add(User(i.toString(),
                            "John Doe"+i,
                            "johndoe@gmail.com",
                            "919000000"
                            ))
                }

                for (i in 0..20){

                    var date: Date

                    if(i > 10){
                        date = Date(2018,11,i)

                        @Suppress("DEPRECATION")
                        val offer = Offer(i.toString(),
                                "Offer "+i,
                                host,
                                getString(R.string.large_text),
                                date,
                                "Avenida dos Aliados",5,9.30F)
                        offer.candidates = candidatesList
                        ongoingList.add(offer)
                    }else{
                        date = Date(2018,1,i)

                        @Suppress("DEPRECATION")
                        val offer = Offer(i.toString(),
                                "Offer "+i,
                                host,
                                getString(R.string.large_text),
                                date,
                                "Avenida dos Aliados",5,9.30F)
                        offer.candidates = candidatesList
                        pastList.add(offer)
                    }
                }

                updateOffers(ongoingList, pastList)
            }
        })
    }

    fun updateOffers(ongoingOffers: List<Offer>,pastOffers :List<Offer>){

        activity!!.runOnUiThread(object: Runnable {
            override fun run() {
                val adapter1 = OfferAdapter(ongoingOffers.toTypedArray())
                offerOngoingRecyclerView.adapter = adapter1

                val adapter2 = OfferAdapter(ongoingOffers.toTypedArray())
                offerPastRecyclerView.adapter = adapter2
            }

        })
    }
}
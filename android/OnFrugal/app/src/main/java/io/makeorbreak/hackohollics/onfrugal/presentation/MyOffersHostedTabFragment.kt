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
import android.widget.Toast
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.data.local.UserRepository
import io.makeorbreak.hackohollics.onfrugal.data.remote.impl.MyHostingOffersRepository
import io.makeorbreak.hackohollics.onfrugal.domain.executor.impl.ThreadExecutor
import io.makeorbreak.hackohollics.onfrugal.domain.interactors.MyOffersInteractorImpl
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer
import io.makeorbreak.hackohollics.onfrugal.domain.model.User
import io.makeorbreak.hackohollics.onfrugal.threading.MainThreadImpl
import kotlinx.android.synthetic.main.fragment_tab_my_offers.*
import java.util.*

class MyOffersHostedTabFragment : Fragment() {


    private lateinit var offerOngoingRecyclerView: RecyclerView

    private lateinit var offerPastRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tab_my_offers, container, false)

        // use a linear layout manager
        var layoutManager1 = LinearLayoutManager(context)

        offerOngoingRecyclerView = v.findViewById(R.id.offers_ongoing)
        offerOngoingRecyclerView.setHasFixedSize(true)
        offerOngoingRecyclerView.setFocusable(false)
        offerOngoingRecyclerView.setLayoutManager(layoutManager1)
        offerOngoingRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        var layoutManager2 = LinearLayoutManager(context)
        offerPastRecyclerView = v.findViewById(R.id.offers_past)
        offerPastRecyclerView.setHasFixedSize(true)
        offerPastRecyclerView.setLayoutManager(layoutManager2)
        offerPastRecyclerView.setFocusable(false)
        offerPastRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        startUpdate()
        return v
    }

    private fun startUpdate() {

        val callback = object: MyOffersInteractorImpl.Callback {
            override fun onSuccess(pastOffers: List<Offer>, ongoingOffers: List<Offer>) {
                updateOffers(ongoingOffers, pastOffers)
            }

            override fun onError(error: String?) {
                Toast.makeText(getContext(),getString(R.string.error_connection),Toast.LENGTH_SHORT).show();
            }

        }

        val interactor = MyOffersInteractorImpl(ThreadExecutor.getInstance(),MainThreadImpl.getInstance(),
                callback,MyHostingOffersRepository(context))

        interactor.execute()

        AsyncTask.execute(object: Runnable {
            override fun run() {
                //TODO GET list
//                val list = RoomDatabase.instance.getDatabase(applicationContext).benchmarkDao().all
//                updateOffers(list)

                val pastList = ArrayList<Offer>()
                val ongoingList = ArrayList<Offer>()

                val userRepository = UserRepository.getInstance()

                val host = User(userRepository.getUID(),
                        userRepository.getDisplayName(), userRepository.getEmail(), "919000000")

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

                val adapter2 = OfferAdapter(pastOffers.toTypedArray())
                offerPastRecyclerView.adapter = adapter2
            }

        })
    }
}
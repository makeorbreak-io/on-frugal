package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer

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
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer
import io.makeorbreak.hackohollics.onfrugal.domain.model.User
import io.makeorbreak.hackohollics.onfrugal.presentation.OfferAdapter
import java.util.*

class FindOfferListTabFragment: Fragment() {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<OfferAdapter.ViewHolder>
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_tab_list_find_offer, container, false)
        mRecyclerView = v.findViewById(R.id.offer_recycler_view)

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true)

        // use a linear layout manager
        mLayoutManager = LinearLayoutManager(context)
        mRecyclerView.setLayoutManager(mLayoutManager)
        //Add line between each item
        mRecyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        startUpdate()
        return v
    }

    private fun startUpdate() {
        AsyncTask.execute(object: Runnable {
            override fun run() {
                //TODO GET list
//                val list = RoomDatabase.instance.getDatabase(applicationContext).benchmarkDao().all
//                updateOffers(list)


                val list = ArrayList<Offer>()
                val user = User("0","John Doe", "johndoe@gmail.com", "919000000")

                for (i in 0..20){

                    @Suppress("DEPRECATION")
                    list.add(Offer(i.toString(),
                            "Offer "+i,
                            user,
                            getString(R.string.large_text),
                            Date(2018,11,i),
                            "Avenida dos Aliados",5,9.30F))
                }

                updateOffers(list)
            }
        })
    }

    fun updateOffers(benchmarks :List<Offer>){

        activity!!.runOnUiThread(object: Runnable {
            override fun run() {
                mAdapter = OfferAdapter(benchmarks.asReversed().toTypedArray())
                mRecyclerView.adapter = mAdapter
            }

        })
    }
}
package io.makeorbreak.hackohollics.onfrugal.presentation

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.presentation.findoffer.FindOfferListTabFragment

class MyOffersFragment: Fragment() {
    lateinit var mRootView: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(R.layout.fragment_my_offers, container, false)

        val tabLayout: TabLayout = mRootView.findViewById(R.id.tab_layout)
        tabLayout.addTab(tabLayout.newTab().setText("Attending"))
        tabLayout.addTab(tabLayout.newTab().setText("Hosting"))
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL)

        val viewPager: ViewPager = mRootView.findViewById(R.id.pager)
        val adapter = PagerAdapter(getChildFragmentManager(), tabLayout.getTabCount())
        viewPager.setAdapter(adapter)
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.setCurrentItem(tab!!.position);
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

        })

        return mRootView
    }



    inner class PagerAdapter(fm: FragmentManager, private val mNumOfTabs: Int) : FragmentStatePagerAdapter(fm) {

        override fun getItem(position: Int):Fragment  {
            when (position) {
                //TODO change this
                0 -> return MyOffersGoingTabFragment()
                1 -> return MyOffersHostedTabFragment()
                else -> throw Exception()
            }
        }

        override fun getCount():Int {
            return mNumOfTabs
        }
    }
}
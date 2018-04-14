package com.hackoholics.onfrugal.main.presentation.findoffer

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.hackoholics.onfrugal.main.R
import com.hackoholics.onfrugal.main.presentation.findoffer.FindOfferMapTabFragment

class FindOfferListTabFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_list_find_offer, container, false)
    }

}
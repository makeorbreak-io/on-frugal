package com.hackoholics.onfrugal.main.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hackoholics.onfrugal.main.R

class FindOfferListTabFragment: Fragment() {
//    @Override
//    override public fun onCreateView(LayoutInflater inflater, ViewGroup container, savedInstanceState:): {
//        return inflater.inflate(R.layout.tab_fragment_2, container, false);
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_list_find_offer, container, false)
    }
}
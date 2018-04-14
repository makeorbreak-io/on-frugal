package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.makeorbreak.hackohollics.onfrugal.R

class FindOfferListTabFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_list_find_offer, container, false)
    }

}
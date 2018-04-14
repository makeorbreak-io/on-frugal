package io.makeorbreak.hackohollics.onfrugal.presentation.findoffer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.makeorbreak.hackohollics.onfrugal.R

class FindOfferMapTabFragment: android.support.v4.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab_map,container,false)
    }
}
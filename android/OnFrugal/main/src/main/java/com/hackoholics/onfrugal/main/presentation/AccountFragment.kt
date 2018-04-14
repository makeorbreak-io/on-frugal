package com.hackoholics.onfrugal.main.presentation

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import com.hackoholics.onfrugal.main.R

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_account, container, false)
        setHasOptionsMenu(true)
        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle("Account")
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        v.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener { view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.account_menu, menu)
    }
}
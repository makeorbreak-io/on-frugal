package io.makeorbreak.hackohollics.onfrugal.presentation

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.R.id.action_about
import io.makeorbreak.hackohollics.onfrugal.R.id.action_logout

class AccountFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_account, container, false)
        setHasOptionsMenu(true)
        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)

        mAuth = FirebaseAuth.getInstance()

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            action_logout -> {

                Toast.makeText(context, "Logging out", Toast.LENGTH_LONG).show()
                mAuth.signOut()
                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity!!.finish()

                return true
            }
            action_about -> {
                val intent = Intent(activity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }
}

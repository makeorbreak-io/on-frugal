package io.makeorbreak.hackohollics.onfrugal.presentation

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.R.id.action_about
import io.makeorbreak.hackohollics.onfrugal.R.id.action_logout
import io.makeorbreak.hackohollics.onfrugal.data.local.UserRepository
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_scrolling.*
import org.w3c.dom.Text

class AccountFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_account, container, false)
        setHasOptionsMenu(true)
        val toolbar = v.findViewById<Toolbar>(R.id.toolbar)

        mAuth = FirebaseAuth.getInstance()

        toolbar.setTitle(mAuth.currentUser!!.displayName.toString())
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        v.findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener { view ->
                Toast.makeText(context, "Not implemented yet :(", Toast.LENGTH_SHORT).show()
            }

        v.findViewById<TextView>(R.id.accountEmail).text = UserRepository.getInstance().getEmail()
        v.findViewById<TextView>(R.id.accountId).text = UserRepository.getInstance().getUID()

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

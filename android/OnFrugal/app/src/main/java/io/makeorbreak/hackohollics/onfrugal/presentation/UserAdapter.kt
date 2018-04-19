package io.makeorbreak.hackohollics.onfrugal.presentation

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.User

class UserAdapter (var users: Array<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(mTextView: View): RecyclerView.ViewHolder(mTextView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var user: User
        override fun onClick(v: View?) {
            if (v != null) {
                val intent = Intent(v.context, UserActivity::class.java)
                intent.putExtra("USER", user)
                v.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_user, parent, false)

        val vh = ViewHolder(v)
        return vh
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val name = holder.itemView.findViewById<TextView>(R.id.textUserName)

        val user = users[position]

        name.text = user.name

        holder.user = user
    }
}

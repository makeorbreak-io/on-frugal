package io.makeorbreak.hackohollics.onfrugal.presentation

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.makeorbreak.hackohollics.onfrugal.R
import io.makeorbreak.hackohollics.onfrugal.domain.model.Offer

class OfferAdapter (var offers: Array<Offer>): RecyclerView.Adapter<OfferAdapter.ViewHolder>() {
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    class ViewHolder(mTextView: View): RecyclerView.ViewHolder(mTextView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        lateinit var offer: Offer
        override fun onClick(v: View?) {
            if (v != null) {
                //TODO Launch Offer Activity
//                val intent = Intent(v.context, OfferActivity::class.java)
//                intent.putExtra("BENCHMARK", offer)
//                v.context.startActivity(intent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_offer, parent, false)

        val vh = ViewHolder(v)
        return vh
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offerName = holder.itemView.findViewById<TextView>(R.id.textOfferName)
        val hostName = holder.itemView.findViewById<TextView>(R.id.textHostName)
        val price = holder.itemView.findViewById<TextView>(R.id.textPrice)
        val endDate = holder.itemView.findViewById<TextView>(R.id.textDate)

        val offer = offers[position]

        offerName.text = offer.name
        hostName.text = offer.host.name
        price.text = offer.price.toString()
        endDate.text = offer.endDate.toString()

        holder.offer = offer
    }

}

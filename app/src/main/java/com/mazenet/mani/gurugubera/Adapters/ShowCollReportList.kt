package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.CollReportmodel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList

class ShowCollReportList(
    private val itemsList: ArrayList<CollReportmodel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<ShowCollReportList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val custname = itemsList[position].getCustomerName()
        println("custnmae adapter //$custname")
        holder.txt_custname.text = itemsList[position].getCustomerName()
        holder.txt_collected.text = Constants.money_convertor(
            Constants.isEmtytoZero(itemsList[position].getReceivedAmount()!!), false
        )
        holder.txt_tobecollected.text = itemsList[position].getPaymentMode()!!
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_custname: TextView
        var txt_collected: TextView
        var txt_tobecollected: TextView


        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_custname = itemView.findViewById(R.id.txt_auctionno)
            txt_collected = itemView.findViewById(R.id.txt_coll_collected) as TextView
            txt_tobecollected = itemView.findViewById(R.id.txt_coll_tobecollected) as TextView
            txt_custname.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

        // onClick Listener for view
        override fun onClick(v: View) {
            listenerRef.get()!!.onPositionClicked(v, adapterPosition)
        }


        //onLongClickListener for view
        override fun onLongClick(v: View): Boolean {

            //            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            //            builder.setTitle("Hello Dialog")
            //                    .setMessage("LONG CLICK DIALOG WINDOW FOR ICON " + String.valueOf(getAdapterPosition()))
            //                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            //                        @Override
            //                        public void onClick(DialogInterface dialog, int which) {
            //
            //                        }
            //                    });
            //
            //            builder.create().show();
            //            listenerRef.get().onLongClicked(getAdapterPosition());
            return true
        }
    }
}
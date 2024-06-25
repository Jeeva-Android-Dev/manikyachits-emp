package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.showleadmodel
import com.mazenet.mani.gurugubera.R

import java.lang.ref.WeakReference
import java.util.ArrayList

class ShowLeadsAdapterK(
    private val itemsList: ArrayList<showleadmodel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<ShowLeadsAdapterK.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.showleads_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_custname.text = itemsList[position].getLeadCustomerName()
        holder.txt_generatedon.text = itemsList[position].getLeadGeneratedOn()
        val date = itemsList[position].getNextFollowupDate()
        if (date.equals("0000-00-00")) {
            holder.txt_nextfollowup.text = "-"
        } else {
            holder.txt_nextfollowup.text = date
        }
        val lostfollowups =
            itemsList[position].getLeadLastFollowupstatus() + " | " + itemsList[position].getLeadLastFollowupdates()
        holder.txt_lastremark.text = lostfollowups
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_custname: TextView
        var txt_generatedon: TextView
        var txt_nextfollowup: TextView
        var txt_lastremark: TextView

        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_custname = itemView.findViewById(R.id.txt_custname)
            txt_generatedon = itemView.findViewById(R.id.txt_followupstatus) as TextView
            txt_nextfollowup = itemView.findViewById(R.id.txt_nextfollowup) as TextView
            txt_lastremark = itemView.findViewById(R.id.txt_lastremark) as TextView
            itemView.setOnClickListener(this)
        }

        // onClick Listener for view
        override fun onClick(v: View) {
            System.out.println("adap pos ${adapterPosition.toString()}")
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
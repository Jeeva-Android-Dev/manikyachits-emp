package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.LastFollowupDetail
import com.mazenet.mani.gurugubera.R

import java.lang.ref.WeakReference
import java.util.ArrayList

class ShowLeadFollowups(
    private val itemsList: ArrayList<LastFollowupDetail>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<ShowLeadFollowups.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.showleadfollowups_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val date = itemsList[position].getFollowupDate()
        if (date.equals("0000-00-00")) {
            holder.txt_folowupdate.text = "-"
        } else {
            holder.txt_folowupdate.text = date
        }
        holder.txt_folowupdate.text = itemsList[position].getFollowupDate()
        holder.txt_followupstatus.text = itemsList[position].getFollowupStatusTypeName()
        holder.txt_remarks.text = itemsList[position].getFollowupRemarks()

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_folowupdate: TextView
        var txt_followupstatus: TextView
        var txt_remarks: TextView

        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_folowupdate = itemView.findViewById(R.id.txt_folowupdate)
            txt_followupstatus = itemView.findViewById(R.id.txt_followupstatus) as TextView
            txt_remarks = itemView.findViewById(R.id.txt_remarks) as TextView

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
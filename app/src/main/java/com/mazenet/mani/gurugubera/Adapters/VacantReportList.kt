package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.VacantChitReportModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList

class VacantReportList(
    private val itemsList: ArrayList<VacantChitReportModel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<VacantReportList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.vacant_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txt_custname.text = itemsList[position].getGroupName()+"\n("+ Constants.money_convertor(
            Constants.isEmtytoZero(itemsList[position].getChitValue()!!), false)+")"
        holder.txt_collected.text = itemsList[position].getGroupStartingDate()!!
        holder.txt_tobecollected.text =itemsList[position].getNoOfVacants()!!
        holder.txt_dummy.text =itemsList[position].getNoOfDummyChits()!!
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_custname: TextView
        var txt_collected: TextView
        var txt_tobecollected: TextView
        var txt_dummy: TextView


        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_custname = itemView.findViewById(R.id.txt_auctionno)
            txt_collected = itemView.findViewById(R.id.txt_coll_collected) as TextView
            txt_tobecollected = itemView.findViewById(R.id.txt_coll_tobecollected) as TextView
            txt_dummy = itemView.findViewById(R.id.txt_dummy) as TextView
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
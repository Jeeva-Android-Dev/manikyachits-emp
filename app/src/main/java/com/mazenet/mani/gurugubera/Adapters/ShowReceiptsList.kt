package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.GeneratedReceiptModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants
import java.lang.ref.WeakReference
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ShowReceiptsList(
    private val itemsList: ArrayList<GeneratedReceiptModel>,
    private val listener: AdapterClickListener, type: String
) : RecyclerView.Adapter<ShowReceiptsList.MyViewHolder>() {
    val typ = type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.collection_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    internal var df = SimpleDateFormat("yyyy-MM-dd")
    internal var df_daily = SimpleDateFormat("dd-MM-yyyy")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val custname = itemsList[position].getPaymentMode()

        holder.txt_custname.text = itemsList[position].getCustomerName()
        if (typ.equals("feedback")) {
            var dat = ""
            try {
                val dates = itemsList[position].getReceivedAmount()!!
                if (dates.equals("0000-00-00")) {
                    dat = "-"
                } else {
                    val d = df.parse(dates)
                    dat = df_daily.format(d)
                }

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            holder.txt_tobecollected.text = dat
        } else {
            holder.txt_tobecollected.text = Constants.money_convertor(
                Constants.isEmtytoZero(itemsList[position].getReceivedAmount()!!), false
            )
        }

        holder.txt_collected.text = Constants.isEmtytoZero(itemsList[position].getPaymentMode()!!)
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
//            txt_custname.setOnClickListener(this)
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
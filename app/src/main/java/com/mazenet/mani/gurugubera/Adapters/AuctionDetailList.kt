package com.mazenet.mani.gurugubera.Adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.AuctionDetail
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList

class AuctionDetailList(
    private val itemsList: ArrayList<AuctionDetail>,
    private val listener: AdapterClickListener

) : RecyclerView.Adapter<AuctionDetailList.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.auctiondetail_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val custname = itemsList[position].getCustomerName()

        holder.txt_custname.text = itemsList[position].getCustomerName()
        holder.txt_ticketno.text = itemsList[position].getTicketNo()
        holder.txt_bidamnt.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getBiddingAmount()!!), false)
        holder.txt_totrelamnt.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getTotalReleaseAmount()!!), false)
        holder.txt_divamnt.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getDividentAmount()!!), false)
        holder.txt_instlamnt.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getInstallmentAmount()!!), false)
        holder.txt_gst.text = Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getGst()!!), false)
        holder.txt_relsdamnt.text =
            Constants.money_convertor(Constants.isEmtytoZero(itemsList[position].getCustomerReleased()!!), false)
        val status = itemsList[position].getpayment_status()!!
        if (status.equals("Payment Pending")) {
            holder.txt_paystatus.text = status+"(" + Constants.money_convertor(
                Constants.isEmtytoZero(itemsList[position].getPendingRelease()!!),
                false
            )+")"
            holder.txt_paystatus.setTextColor(Color.rgb(221, 44, 0))
//            holder.txt_paystatus.setTextColor()
        } else if (status.equals("Part Payment")) {
            holder.txt_paystatus.text = status+"(" + Constants.money_convertor(
                Constants.isEmtytoZero(itemsList[position].getPendingRelease()!!),
                false
            )+")"
            holder.txt_paystatus.setTextColor(Color.rgb(239, 108, 0))
        } else {
            holder.txt_paystatus.text = status
            holder.txt_paystatus.setTextColor(Color.rgb(67, 160, 71))
        }

        holder.txt_auctionno.text = "Au."+itemsList[position].getAuctionNo()!!

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_custname: TextView
        var txt_ticketno: TextView
        var txt_bidamnt: TextView
        var txt_totrelamnt: TextView
        var txt_divamnt: TextView
        var txt_instlamnt: TextView
        var txt_gst: TextView
        var txt_relsdamnt: TextView
        var txt_paystatus: TextView
        var txt_auctionno: TextView


        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_custname = itemView.findViewById(R.id.txt_custname)
            txt_ticketno = itemView.findViewById(R.id.txt_ticketno) as TextView
            txt_bidamnt = itemView.findViewById(R.id.txt_bidamnt) as TextView
            txt_totrelamnt = itemView.findViewById(R.id.txt_totrelamnt) as TextView
            txt_divamnt = itemView.findViewById(R.id.txt_divamnt) as TextView
            txt_instlamnt = itemView.findViewById(R.id.txt_instlamnt) as TextView
            txt_gst = itemView.findViewById(R.id.txt_gst) as TextView
            txt_relsdamnt = itemView.findViewById(R.id.txt_relsdamnt) as TextView
            txt_paystatus = itemView.findViewById(R.id.txt_paystatus) as TextView
            txt_auctionno = itemView.findViewById(R.id.txt_auctionno) as TextView

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
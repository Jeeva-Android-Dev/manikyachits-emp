package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.GroupListModel
import com.mazenet.mani.gurugubera.R
import com.mazenet.mani.gurugubera.Utilities.Constants

import java.lang.ref.WeakReference
import java.util.ArrayList

class GroupsAdapter(
    private val itemsList: ArrayList<GroupListModel>,
    private val listener: AdapterClickListener
) : RecyclerView.Adapter<GroupsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.groups_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_gd_groupname.text = itemsList[position].groupname+" / "+itemsList[position].ticketno
        var chitvalue_amnt =itemsList[position].chitvalue
        if (chitvalue_amnt.isEmpty()) {
            chitvalue_amnt = "0"
        } else {
            chitvalue_amnt = Constants.money_convertor(chitvalue_amnt, false)
        }
        holder.txt_gd_chitvalue.text = chitvalue_amnt
        holder.txt_gd_auctiondate.text = itemsList[position].auctiondate
        holder.txt_gd_prizedstatus.text = itemsList[position].prizedstatus
        var instalmentamount = itemsList[position].instalmentamount
        if (instalmentamount.isEmpty()) {
            instalmentamount = "0"
        } else {
            instalmentamount = Constants.money_convertor(instalmentamount, false)
        }
        holder.txt_gd_installments.text = instalmentamount
        var collectedamnt = itemsList[position].collected
        if (collectedamnt.isEmpty()) {
            collectedamnt = "0"
        } else {
            collectedamnt = Constants.money_convertor(collectedamnt, false)
        }
        holder.txt_gd_collected.text = collectedamnt
        var tobecollected_amnt = itemsList[position].tobecollected
        if (tobecollected_amnt.isEmpty()) {
            tobecollected_amnt = "0"
        } else {
            tobecollected_amnt = Constants.money_convertor(tobecollected_amnt, false)
        }
        holder.txt_gd_tobecollected.text = tobecollected_amnt
        var penaltyamnt = itemsList[position].penaltyamnt
        if (penaltyamnt.isEmpty()) {
            penaltyamnt = "0"
        } else {
            penaltyamnt = Constants.money_convertor(penaltyamnt, false)
        }
        holder.txt_gd_penalty.text = penaltyamnt
        var bonusamnt = itemsList[position].bonusamnt
        if (bonusamnt.isEmpty()) {
            bonusamnt = "0"
        } else {
            bonusamnt = Constants.money_convertor(bonusamnt, false)
        }

        holder.txt_gd_bonus.text = bonusamnt
        var due_no = itemsList[position].auc_no
        if (due_no.isEmpty()) {
            due_no = "0"
        } else {
            due_no = Constants.money_convertor(due_no, false)
        }
        holder.due_no.text = due_no
        var pending_due = itemsList[position].pen_auc_no
        if (pending_due.isEmpty()||pending_due==null) {
            pending_due = "0"
        } else {
            pending_due = Constants.money_convertor(pending_due, false)
        }
        holder.pending_due.text = pending_due

        var advanceamnt = itemsList[position].advanceamnt
        if (advanceamnt.isEmpty()) {
            advanceamnt = "0"
        } else {
            advanceamnt = Constants.money_convertor(advanceamnt, false)
        }
        holder.txt_gd_advance.text = advanceamnt

    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {


        var txt_gd_groupname: TextView
        var txt_gd_chitvalue: TextView
        var txt_gd_auctiondate: TextView
        var txt_gd_prizedstatus: TextView
        var txt_gd_installments: TextView
        var txt_gd_collected: TextView
        var txt_gd_tobecollected: TextView
        var txt_gd_penalty: TextView
        var due_no: TextView
        var pending_due: TextView
        var txt_gd_bonus: TextView
        var txt_gd_advance: TextView

        private val listenerRef: WeakReference<AdapterClickListener>

        init {
            listenerRef = WeakReference(listener)
            txt_gd_groupname = itemView.findViewById(R.id.txt_gd_groupname)
            txt_gd_chitvalue = itemView.findViewById(R.id.txt_gd_chitvalue) as TextView
            txt_gd_auctiondate = itemView.findViewById(R.id.txt_gd_auctiondate) as TextView
            txt_gd_prizedstatus = itemView.findViewById(R.id.txt_gd_prizedstatus) as TextView
            txt_gd_installments = itemView.findViewById(R.id.txt_gd_installments) as TextView
            txt_gd_collected = itemView.findViewById(R.id.txt_gd_collected) as TextView
            txt_gd_tobecollected = itemView.findViewById(R.id.txt_gd_tobecollected) as TextView
            txt_gd_penalty = itemView.findViewById(R.id.txt_gd_penalty) as TextView
            due_no = itemView.findViewById(R.id.due_no) as TextView
            pending_due = itemView.findViewById(R.id.pending_due) as TextView
            txt_gd_bonus = itemView.findViewById(R.id.txt_gd_bonus) as TextView
            txt_gd_advance = itemView.findViewById(R.id.txt_gd_advance) as TextView

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
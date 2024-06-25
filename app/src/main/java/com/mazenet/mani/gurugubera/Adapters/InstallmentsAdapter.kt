package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mazenet.mani.gurugubera.Model.InstallmentListModel
import com.mazenet.mani.gurugubera.R

import java.lang.ref.WeakReference
import java.util.ArrayList

class InstallmentsAdapter(
    private val itemsList: ArrayList<InstallmentListModel>,
    private val listener: AdapterEdittextListener
) : RecyclerView.Adapter<InstallmentsAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.installments_listitem, parent, false)
        return MyViewHolder(view, listener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.txt_auctionno.text = itemsList[position].auctionno
        holder.txt_pending.text = itemsList[position].pending
        holder.txt_penalty.text = itemsList[position].penalty
        holder.txt_bonus.text = itemsList[position].bonus
        holder.txt_total.text = itemsList[position].payableamount
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class MyViewHolder(itemView: View, listener: AdapterEdittextListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener, TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            println("penalty edited "+txt_penalty.text.toString())
//            listenerRef.get()!!.onEdited(adapterPosition, txt_penalty.text.toString())
        }


        var txt_auctionno: TextView
        var txt_pending: TextView
        var txt_penalty: TextView
        var txt_bonus: TextView
        var txt_total: TextView


        private val listenerRef: WeakReference<AdapterEdittextListener>

        init {
            listenerRef = WeakReference(listener)
            txt_auctionno = itemView.findViewById(R.id.txt_auctionno)
            txt_pending = itemView.findViewById(R.id.txt_pending) as TextView
            txt_penalty = itemView.findViewById(R.id.txt_penalty) as TextView
            txt_bonus = itemView.findViewById(R.id.txt_bonus) as TextView
            txt_total = itemView.findViewById(R.id.txt_total) as TextView
//            txt_auctionno.setOnClickListener(this)
            itemView.setOnClickListener(this)
            txt_penalty.setOnClickListener(this)
//            txt_penalty.addTextChangedListener(this)
        }

        // onClick Listener for view
        override fun onClick(v: View) {
            listenerRef.get()!!.onPositionClicked(v, adapterPosition,txt_penalty.text.toString())
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
package com.mazenet.mani.gurugubera.Adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.mazenet.mani.gurugubera.Model.deviceListModel
import java.lang.ref.WeakReference


class ShowDevicesadapter(private val leadsList: ArrayList<deviceListModel>, Listener: AdapterClickListener) :
    RecyclerView.Adapter<ShowDevicesadapter.ViewHolder>() {
    private var listeners: AdapterClickListener? = null

    class ViewHolder(itemView: View, listener: AdapterClickListener) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        override fun onLongClick(v: View?): Boolean {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        val switch_isActive: Switch
        val txt_devicename: TextView
        val txt_devicestatus: TextView
        var seatmodel: deviceListModel? = null
//        this.listeners = listener
        private val listenerRef: WeakReference<AdapterClickListener>

        init {

            listenerRef = WeakReference<AdapterClickListener>(listener)

            txt_devicename = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.txt_devicename) as TextView
            switch_isActive = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.switch_isActive) as Switch
            txt_devicestatus = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.txt_devicestatus) as TextView

            itemView.setOnClickListener(this)
            txt_devicename.setOnClickListener(this)
            switch_isActive.setOnClickListener(this)
        }

        // onClick Listener for view
        override fun onClick(v: View) {

            if (v.id == switch_isActive.id) {
                Toast.makeText(v.context, "ITEM PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(v.context, "ROW PRESSED = $adapterPosition", Toast.LENGTH_SHORT).show()
            }

            listenerRef.get()!!.onPositionClicked(v, adapterPosition)
        }


        //onLongClickListener for view
//        override fun onLongClick(v: View): Boolean {
//
//            val builder = AlertDialog.Builder(v.context)
//            builder.setTitle("Hello Dialog")
//                .setMessage("LONG CLICK DIALOG WINDOW FOR ICON $adapterPosition")
//                .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which -> })
//
//            builder.create().show()
//            listenerRef.get()!!.onLongClicked(adapterPosition)
//            return true
//        }
    }
//    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview),View.OnClickListener {
//        override fun onClick(v: View?) {
//            if (v!!.getId() == switch_isActive.getId()) {
//               System.out.println("pos ${getAdapterPosition() } check")
//               if(switch_isActive.isChecked)
//               {
//                 println("pos ${getAdapterPosition() } disable")
//               }else
//               {
//                   println("pos ${getAdapterPosition() } enabled")
//               }
//            } else {
//                println("pos ${getAdapterPosition() } row clicked")
//            }
//
//            clickListener!!.get()!!.onPositionClicked(v,getAdapterPosition())
//        }
//        var seatmodel: deviceListModel? = null
//        val listener: AdapterClickListener? = null
//        val txt_devicename = itemView.findViewById(com.mazenet.prabakaran.mazechit.R.id.txt_devicename) as TextView
//        val switch_isActive = itemView.findViewById(com.mazenet.prabakaran.mazechit.R.id.switch_isActive) as Switch
//        val txt_devicestatus = itemView.findViewById(com.mazenet.prabakaran.mazechit.R.id.txt_devicestatus) as TextView
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ShowDevicesadapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(com.mazenet.mani.gurugubera.R.layout.showdevices_listitem, parent, false)
        return ViewHolder(view, this.listeners!!)
    }

    override fun getItemCount(): Int {
        return leadsList.size
    }

    override fun onBindViewHolder(viewHolder: ShowDevicesadapter.ViewHolder, position: Int) {
        viewHolder.seatmodel = leadsList[position]
        viewHolder.txt_devicename.text = leadsList[position].getDeviceName()
        if (leadsList[position].getIsActive().equals("Yes")) {
            viewHolder.switch_isActive.isChecked = true
            viewHolder.txt_devicestatus.text = "Active"
        } else {
            viewHolder.switch_isActive.isChecked = false
            viewHolder.txt_devicestatus.text = "Inactive"
        }


    }

}
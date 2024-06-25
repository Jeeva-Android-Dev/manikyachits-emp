package com.mazenet.mani.gurugubera.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import com.mazenet.mani.gurugubera.Model.deviceListModel;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.MyViewHolder> {

    private final AdapterClickListener listener;
    private final ArrayList<deviceListModel> itemsList;
    private final String andid;

    public DevicesAdapter(ArrayList<deviceListModel> itemsList, AdapterClickListener listener, String androidid) {
        this.listener = listener;
        this.itemsList = itemsList;
        this.andid = androidid;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.mazenet.mani.gurugubera.R.layout.showdevices_listitem, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        if (Objects.requireNonNull(itemsList.get(position).getAndroidId()).equalsIgnoreCase(andid)) {
            holder.txt_devicename.setText(itemsList.get(position).getDeviceName() + " ( This device )");
        } else {
            holder.txt_devicename.setText(itemsList.get(position).getDeviceName());
        }
        if (itemsList.get(position).getIsActive().equals("Yes")) {
            holder.switch_isActive.setChecked(true);
            holder.txt_devicestatus.setText("Active");
        } else {
            holder.switch_isActive.setChecked(false);
            holder.txt_devicestatus.setText("Inactive");
        }

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public Switch switch_isActive;
        public TextView txt_devicename;
        public TextView txt_devicestatus;

        private WeakReference<AdapterClickListener> listenerRef;

        public MyViewHolder(final View itemView, AdapterClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            txt_devicename = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.txt_devicename);
            switch_isActive = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.switch_isActive);
            txt_devicestatus = itemView.findViewById(com.mazenet.mani.gurugubera.R.id.txt_devicestatus);
            itemView.setOnClickListener(this);
            switch_isActive.setOnClickListener(this);
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(v, getAdapterPosition());
        }


        //onLongClickListener for view
        @Override
        public boolean onLongClick(View v) {

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
            return true;
        }
    }
}
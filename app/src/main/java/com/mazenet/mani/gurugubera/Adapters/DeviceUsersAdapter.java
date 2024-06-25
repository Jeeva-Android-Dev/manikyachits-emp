package com.mazenet.mani.gurugubera.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import com.mazenet.mani.gurugubera.Model.DeviceUserListModel;
import com.mazenet.mani.gurugubera.R;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class DeviceUsersAdapter extends RecyclerView.Adapter<DeviceUsersAdapter.MyViewHolder> {

    private final AdapterClickListener listener;
    private final ArrayList<DeviceUserListModel> itemsList;
    private final String andid;

    public DeviceUsersAdapter(ArrayList<DeviceUserListModel> itemsList, AdapterClickListener listener, String androidid) {
        this.listener = listener;
        this.itemsList = itemsList;
        this.andid = androidid;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.showdevice_users_listitem, parent, false);
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
System.out.println("userid "+itemsList.get(position).getUserId().toString()+" use "+andid);
        if (Objects.requireNonNull(itemsList.get(position).getUserId().toString()).equalsIgnoreCase(andid)) {
            holder.txt_devicename.setText(itemsList.get(position).getUserName() + " ( Me )");
        } else {
            holder.txt_devicename.setText(itemsList.get(position).getUserName());
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
            txt_devicename = itemView.findViewById(R.id.txt_username);
            switch_isActive = itemView.findViewById(R.id.switch_su_isActive);
            txt_devicestatus = itemView.findViewById(R.id.txt_userstatus);
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
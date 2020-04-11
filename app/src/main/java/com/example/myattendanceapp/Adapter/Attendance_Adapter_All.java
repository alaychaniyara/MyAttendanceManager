package com.example.myattendanceapp.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myattendanceapp.Data.Attendance_List;
import com.example.myattendanceapp.R;

import java.util.ArrayList;

public class Attendance_Adapter_All extends RecyclerView.Adapter<Attendance_Adapter_All.viewHolder> {
    ArrayList<Attendance_List> arrayList;
    public Attendance_Adapter_All(ArrayList<Attendance_List> arrayList) {
        this.arrayList = arrayList;
    }

    public void updateList(ArrayList<Attendance_List> attendance_lists)
    {
        this.arrayList.clear();
        this.arrayList = attendance_lists;
        notifyDataSetChanged();
//        Log.d("ADAPTER", this.arrayList.get(0).getName());
    }
    @Override
    public  viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_attendance_all, viewGroup, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        viewHolder.name.setText(arrayList.get(position).getName());
        viewHolder.status.setText(arrayList.get(position).getStatus());
        if(arrayList.get(position).getStatus().equals("Present")) {
           viewHolder.status.setTextColor(Color.GREEN);
        }
        else
        {
            viewHolder.status.setTextColor(Color.RED);
        }

        //  Log.d("ADAPTER", this.arrayList.get(0).getName());
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class viewHolder extends RecyclerView.ViewHolder {
        static TextView name;
        static TextView status;
        public viewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_item_all_name);
            status = itemView.findViewById(R.id.list_item_all_status);
        }
    }
}


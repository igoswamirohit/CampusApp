package com.ruds.data.viewholder;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruds.data.R;

public class AttendanceViewHolderStd extends RecyclerView.ViewHolder {

    public CheckBox checkBox;

    public AttendanceViewHolderStd(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
    }
}

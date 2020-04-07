package com.ruds.data.viewholder;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruds.data.R;

import org.w3c.dom.Text;

public class AttendanceViewHolderStd extends RecyclerView.ViewHolder {

    public CheckBox checkBox;
    public TextView tt;

    public AttendanceViewHolderStd(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
        tt = (TextView) itemView.findViewById(R.id.tv);
    }
}

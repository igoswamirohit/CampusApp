package com.ruds.data.viewholder;

import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruds.data.R;

public class StudentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CheckBox checkBox;

    public ItemClickListener itemClickListener;

    public StudentsViewHolder(@NonNull View itemView) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.checkBox);
        checkBox.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener ic) {
        this.itemClickListener = ic;
    }

    public interface ItemClickListener {

        void onItemClick(View v, int pos);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v, getLayoutPosition());
    }
}

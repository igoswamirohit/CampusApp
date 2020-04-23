package com.ruds.data.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ruds.data.SubjectsUI.AddSubjectsFragment;
import com.ruds.data.R;

import java.util.ArrayList;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectsViewHolder> {

    ArrayList<String> mValues;

    public SubjectsAdapter(ArrayList<String> items) {
        mValues = items;
    }


    @Override
    public SubjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_list_frgment, parent, false);
        return new SubjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectsViewHolder holder, final int position) {
        holder.mIdView.setText(mValues.get(position));
        holder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddSubjectsFragment.removeAt(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class SubjectsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final ImageButton mImageButton;
        //public final TextView mContentView;

        public SubjectsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
            mIdView = (TextView) itemView.findViewById(R.id.item_number);
            mImageButton = itemView.findViewById(R.id.subjectscloseBtn);
        }
    }
}

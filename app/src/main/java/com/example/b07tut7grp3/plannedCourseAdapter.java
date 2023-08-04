package com.example.b07tut7grp3;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class plannedCourseAdapter extends RecyclerView.Adapter<plannedCourseAdapter.ViewHolder> {

    private int listItemLayout;
    private ArrayList<plannedCourse> itemList;
    private OnItemClickedListener listener;

    public interface OnItemClickedListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener clickListener){
        listener = clickListener;
    }


    public plannedCourseAdapter(int layoutId, ArrayList<plannedCourse> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }


    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        ViewHolder myViewHolder = new ViewHolder(view, listener);
        return  myViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int listPosition) {
        TextView item = holder.item;
        item.setText(itemList.get(listPosition).getName());
    }


    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView item;
        public TextView remove;

        public ViewHolder(View itemView, OnItemClickedListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            item = (TextView) itemView.findViewById(R.id.course);
            remove = itemView.findViewById(R.id.button5);
            remove.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   listener.onItemClick(getAdapterPosition());
               }
           });
        }


        @Override
        public void onClick(View view) {
            Log.d("onclick", "onClick " + getLayoutPosition() + " " + item.getText());
        }


    }
}


package com.example.iti.sidemenumodule.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.helperclasses.Farsi;
import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Users;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private List<Category> dataSet;
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        ImageView imageViewIcon;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public CustomAdapter(Context context, ArrayList<Category> data) {
        this.dataSet = data;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        view.setOnClickListener(MainFragment.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;
        ImageView imageView = holder.imageViewIcon;
        textViewName.setText(dataSet.get(listPosition).getCategoryName());
        Picasso.with(context)
                .load(dataSet.get(listPosition).getImageOfCategoryUrl())
                .placeholder(R.drawable.cat1)
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<Category> getData() {
        return dataSet;
    }
}

package com.sebasdev.gravilitytest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sebasdev.gravilitytest.R;
import com.sebasdev.gravilitytest.interfaces.ItemClickListener;
import com.sebasdev.gravilitytest.model.Category;

import java.util.List;

/**
 * Created by m4605 on 17/03/16.
 * Adapter that show the list of categories using RecyclerView with only a TetView
 */
public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<Category> categories;
    private ItemClickListener mListener;

    public class CategoriesViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;

        public CategoriesViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.tvCategory);
        }

        public void bind(final Category category, final ItemClickListener listener) {

            mTextView.setText(category.getLabel());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(category);
                }
            });
        }
    }

    public CategoriesAdapter(List<Category> categories, ItemClickListener listener) {
        this.categories = categories;
        this.mListener = listener;
    }

    @Override
    public CategoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_categories, parent, false);

        return new CategoriesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoriesViewHolder holder, int position) {
        holder.bind(categories.get(position), mListener);
//        holder.mTextView.setText(categories.get(position).getLabel());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}

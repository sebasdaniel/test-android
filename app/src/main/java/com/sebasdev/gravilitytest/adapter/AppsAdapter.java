package com.sebasdev.gravilitytest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sebasdev.gravilitytest.MainActivity;
import com.sebasdev.gravilitytest.R;
import com.sebasdev.gravilitytest.interfaces.ItemClickListener;
import com.sebasdev.gravilitytest.model.App;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by m4605 on 17/03/16.
 * Adapter that show the list of apps using RecyclerView with CardView layout
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppsViewHolder> {

    private List<App> apps;
    private ItemClickListener mListener;

    public class AppsViewHolder extends RecyclerView.ViewHolder {

        public ImageView appImg;
        public TextView appName;
        public TextView appAuthor;

        public AppsViewHolder(View v) {
            super(v);

            appImg = (ImageView) v.findViewById(R.id.imageApp);
            appName = (TextView) v.findViewById(R.id.tvAppName);
            appAuthor = (TextView) v.findViewById(R.id.tvAppAuthor);
        }

        public void bind(final App app, final ItemClickListener listener) {

            if (MainActivity.isOnline){
                Picasso.with(itemView.getContext()).load(app.getImage()).fit().into(appImg);
            } else {
                appImg.setImageBitmap(app.getImageBitmap());
            }

            appName.setText(app.getName());
            appAuthor.setText(app.getAuthor());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(app);
                }
            });
        }
    }

    public AppsAdapter(List<App> apps, ItemClickListener listener) {
        this.apps = apps;
        this.mListener = listener;
    }

    @Override
    public AppsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_apps, parent, false);
        return new AppsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AppsViewHolder holder, int position) {

        holder.bind(apps.get(position), mListener);
//        holder.appImg.setImageResource(R.mipmap.ic_launcher);
//        holder.appName.setText(apps.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

}

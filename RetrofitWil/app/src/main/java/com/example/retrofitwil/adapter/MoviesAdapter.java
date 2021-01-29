package com.example.retrofitwil.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retrofitwil.R;
import com.example.retrofitwil.model.MovieModel;
import com.example.retrofitwil.util.ItemClickListener;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    private List<MovieModel> moviesList;
    private int rowLayout;
    private Context mContext;

    public MoviesAdapter(List<MovieModel> moviesList, int rowLayout, Context context) {
        this.moviesList = moviesList;
        this.rowLayout = rowLayout;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final MovieModel movie = moviesList.get(i);
        viewHolder.movieName.setText(movie.getTitle());
        Glide.with(mContext)
                .load(movie.getPosterPath())
                .into(viewHolder.movieImage);
        viewHolder.setClickListener((view, position, isLongClick) -> {
            if (isLongClick) {
                Toast.makeText(mContext, "#" + position + " - " + movie.getTitle() + " (Long click)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(mContext, "#" + position + " - " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public TextView movieName;
        public ImageView movieImage;
        public String desc;
        private ItemClickListener clickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.name);
            movieImage = (ImageView) itemView.findViewById(R.id.img);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }
        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}

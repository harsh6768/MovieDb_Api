package com.example.myfriends.parsing_using_asynctask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class MovieArrayAdapter extends ArrayAdapter {

    private List<MovieDetails> movieDetailsList;

    private  Context context;

    private int resource;

    public MovieArrayAdapter(@NonNull Context context, int resource, @NonNull List<MovieDetails> movieDetails) {
        super(context, resource, movieDetails);

        this.context=context;
        this.movieDetailsList=movieDetails;
        this.resource=resource;

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        MovieDetails details=movieDetailsList.get(position);

        View view=LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

         ImageView movieImage=(ImageView)view.findViewById(R.id.movieImageId);
         TextView movieTitle=(TextView)view.findViewById(R.id.listTextViewId);

         movieTitle.setText(details.getOriginal_title());

        //Glide.with(parent.getContext()).load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(movieImage);

         Picasso.get().load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(movieImage);

        return view;
    }

    @Nullable
    @Override
    public Object getItem(int position) {

        return movieDetailsList.get(position);
    }

}
package com.example.myfriends.parsing_using_asynctask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieDetail extends AppCompatActivity {

    private ImageView posterImage;
    private TextView titleText,overviewText,releaseTextView,rateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        posterImage=(ImageView)findViewById(R.id.imageView3);
        titleText=(TextView)findViewById(R.id.titleId);
        overviewText=(TextView)findViewById(R.id.overviewId);
        releaseTextView=(TextView)findViewById(R.id.releaseDateId);
        rateTextView=(TextView)findViewById(R.id.rateId);

        MovieDetails details= (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        if(details!=null)
        {
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+details.getPoster_path()).into(posterImage);

            titleText.setText(details.getOriginal_title());
            overviewText.setText(details.getOverview());
            releaseTextView.setText(details.getRelease_date());
            rateTextView.setText(String.valueOf(details.getVote_average()));

        }
    }
}
package com.example.myfriends.parsing_using_asynctask;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private HttpURLConnection httpURLConnection;
    private URL url;

    List<MovieDetails> movieList=new ArrayList<MovieDetails>();

    private ListView listView;
    private MovieArrayAdapter movieArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.listViewId);

        movieArrayAdapter=new MovieArrayAdapter(this,R.layout.movie_list,movieList);


        //call the AsyncThread so that it will execute the operation
        new AsyncThread().execute("https://api.themoviedb.org/3/movie/popular?api_key=2d3e3995bfdaf5c190e7abac26e49040");

    }

        //used to see the details of the every movie
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Toast.makeText(this,"Clicked",Toast.LENGTH_LONG).show();

            Intent intent=new Intent(MainActivity.this,MovieDetail.class);
            //putExtra method send the Serialized Object to the MovieDetails class so weed to implements Seliazable interface
            intent.putExtra("MOVIE_DETAILS",(MovieDetails)parent.getItemAtPosition(position));
            startActivity(intent);

        }


    class AsyncThread extends AsyncTask<String ,Void,String>
    {
        String readData;
        //doInBackground method is execute in background so don't use the UI thread operation
        @Override
        protected String doInBackground(String... strings) {

            try {
                url=new URL(strings[0]);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            //to open the connection
            try {

                httpURLConnection= (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();

                //If connection successfully connected then it returns the reponsecode we need to use the InputStream
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String readData=bufferedReader.readLine();
                bufferedReader.close();
                inputStream.close();

                return readData;

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpURLConnection.disconnect();
            }
            return null;
        }

        //this method execute in UI thread so you can perform the manipulation

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            try {
                JSONObject jsonObject=new JSONObject(response);


                JSONArray jsonArray=jsonObject.getJSONArray("results");

                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);

                    MovieDetails movieDetails=new MovieDetails();

                     movieDetails.setOriginal_title(object.getString("original_title"));
                     movieDetails.setOverview(object.getString("overview"));
                     movieDetails.setRelease_date(object.getString("release_date"));
                     movieDetails.setVote_average(object.getDouble("vote_average"));
                     movieDetails.setPoster_path(object.getString("poster_path"));

                     movieList.add(movieDetails);
                }

                listView.setAdapter(movieArrayAdapter);
                //checkConnectoin.setText();

               // Log.i("Lag",movieList.get(3).getOriginal_title());


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}

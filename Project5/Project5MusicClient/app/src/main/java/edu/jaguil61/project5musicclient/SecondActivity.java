//Jose M. Aguilar (jaguil61)

package edu.jaguil61.project5musicclient;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.jaguil61.project5aidl.MyAidlInterface;

public class SecondActivity extends AppCompatActivity {

    private List<String> songNameList = new ArrayList<>(); //list of all the song names
    private List<String> artistNameList = new ArrayList<>(); //list of all the artist names
    private List<Bitmap> songCoverList = new ArrayList<>(); //list of all the song photos
    private List<String> songURLList = new ArrayList<>(); //list of all the song URLs
    private RecyclerView songView;
    private Intent musicServiceIntent;// Intent used for starting the MusicService

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        songView = (RecyclerView) findViewById(R.id.recyclerView);

        //setup service
        MyAidlInterface myMusicService = MainActivity.myMusicService;

        try {
            //call the aidl methods to grab the info from service
            songNameList = myMusicService.getSongNames();
            artistNameList = myMusicService.getArtistNames();
            songCoverList = myMusicService.getAllSongCovers();
            songURLList = myMusicService.getURLs();
        } catch (RemoteException e) {
            Log.e("CLIENT_APP", e.toString());
        }

        Log.i("CLIENT_APP", "Song List Size: " + songNameList.size());
        Log.i("CLIENT_APP", "Artist List Size: " + artistNameList.size());
        Log.i("CLIENT_APP", "Song Cover List Size: " + songCoverList.size());
        Log.i("CLIENT_APP", "Song URL List Size: " + songURLList.size());

        //prep intent
        musicServiceIntent = new Intent(getApplicationContext(), MusicService.class);

        //creates listener for recycler view item
        RVClickListener listener = (view, position) -> {
            //end the service if it's already started
            stopService(musicServiceIntent);

            //start the music service intent
            musicServiceIntent.putExtra("songURL", songURLList.get(position));
            startService(musicServiceIntent);
        };

        //Sets up adapter and the view
        MyAdapter adapter = new MyAdapter(songNameList, artistNameList, songCoverList, listener);
        songView.setAdapter(adapter);
        songView.setHasFixedSize(true);
        songView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Stop the MusicService using the Intent
        stopService(musicServiceIntent);
    }
}

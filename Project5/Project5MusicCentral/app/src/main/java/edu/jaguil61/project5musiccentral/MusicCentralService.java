//Jose M. Aguilar (jaguil61)

package edu.jaguil61.project5musiccentral;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import edu.jaguil61.project5aidl.MyAidlInterface;

public class MusicCentralService extends Service {

    private List<String> songNameList = new ArrayList<>(); //list of all the song names
    private List<String> artistNameList = new ArrayList<>(); //list of all the artist names
    private List<Bitmap> songCoverList = new ArrayList<>(); //list of all the song photos
    private List<String> songURLList = new ArrayList<>(); //list of all the song URLs
    private final static Set<UUID> myIDs = new HashSet<>(); //for debugging purposes

    @Override
    public void onCreate() {
        super.onCreate();

        //initialize the lists
        initSongNames();
        initArtistNames();
        initSongCovers();
        initURLs();
    }

    private final MyAidlInterface.Stub myBinder = new MyAidlInterface.Stub() {

        //methods from MyAidlInterface.aidl
        @Override
        public synchronized List<String> getSongNames() {
            return songNameList;
        }

        @Override
        public synchronized List<String> getArtistNames() {
            return artistNameList;
        }

        @Override
        public synchronized List<Bitmap> getAllSongCovers() {
            return songCoverList;
        }

        @Override
        public synchronized List<String> getURLs() throws RemoteException {
            return songURLList;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    //following functions initialize the arrays to their proper values
    private void initSongNames() {
        songNameList.add("love race");
        songNameList.add("DAKITI");
        songNameList.add("Babushka Boi");
        songNameList.add("Hollywood Forever");
        songNameList.add("Norgaard");
        songNameList.add("Little Monster");
    }

    private void initArtistNames () {
        artistNameList.add("Machine Gun Kelly");
        artistNameList.add("Bad Bunny");
        artistNameList.add("A$AP Rocky");
        artistNameList.add("K. Flay");
        artistNameList.add("The Vaccines");
        artistNameList.add("Royal Blood");
    }

    private void initSongCovers () {
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.love_race));
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.dakiti));
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.babushka_boi));
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.hollywood_forever));
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.norgaard));
        songCoverList.add(BitmapFactory.decodeResource(getResources(), R.drawable.little_monster));

        Log.i("SERVICE_APP", "songCoverList.size(): " + songCoverList.size());
    }

    private void initURLs() {
        songURLList.add("https://www.free-stock-music.com/music/mixaund-inspiring-happy-morning.mp3");
        songURLList.add("https://www.free-stock-music.com/music/fsm-team-escp-yellowtree-melancholia-goth-emo-type-beat.mp3");
        songURLList.add("https://www.free-stock-music.com/music/deoxys-beats-fushiguro.mp3");
        songURLList.add("https://www.free-stock-music.com/music/purrple-cat-snooze-button.mp3");
        songURLList.add("https://www.free-stock-music.com/music/wombat-noises-audio-ring-of-iron.mp3");
        songURLList.add("https://www.free-stock-music.com/music/ron-gelinas-chillout-lounge-midnight-shuffle.mp3");
    }
}
/*
Jose M. Aguilar (jaguil61)
Project 2
MainActivity.java
 */

package edu.jaguil16.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Integer> imgIDsList = new ArrayList<>(); // list of all the cover photos
    ArrayList<String> songTitleList = new ArrayList<>(); // list of the all the song titles
    ArrayList<String> artistNameList = new ArrayList<>(); // list of the all the artists
    ArrayList<Uri> videoUriList = new ArrayList<>(); // list of the the video urls
    ArrayList<Uri> songUriList = new ArrayList<>(); // list of the song wiki urls
    ArrayList<Uri> artistUriList = new ArrayList<>(); // list of the artist urls
    RecyclerView songView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songView = (RecyclerView) findViewById(R.id.recyclerView);

        createLists(); // create array lists

        // creates listener for recycler view item (opens youtube link)
        RVClickListener listener = (view, position) -> {
            Uri aUri = videoUriList.get(position);
            Intent aIntent = new Intent(Intent.ACTION_VIEW);
            aIntent.setData(aUri);
            aIntent.addCategory(Intent.CATEGORY_BROWSABLE);
            startActivity(aIntent);
        };

        //Sets up adapter and the view
        MyAdapter adapter = new MyAdapter(imgIDsList, songTitleList, artistNameList,
                videoUriList, songUriList, artistUriList, listener);
        songView.setAdapter(adapter);
        songView.setHasFixedSize(true);
        songView.setLayoutManager(new LinearLayoutManager(this)); // default view
    }

    //Creates the arrays that have all the names/artists/images/urls
    public void createLists() {
        imgIDsList.add(R.drawable.chip_chrome);
        imgIDsList.add(R.drawable.nothing_happens);
        imgIDsList.add(R.drawable.the_1975);
        imgIDsList.add(R.drawable.tickets_to_my_downfall);
        imgIDsList.add(R.drawable.x_100pre);
        imgIDsList.add(R.drawable.zuu);
        imgIDsList.add(R.drawable.use_me);
        imgIDsList.add(R.drawable.girlfriends);


        songTitleList.add("Devils Advocate");
        songTitleList.add("Remember When");
        songTitleList.add("Girls");
        songTitleList.add("concert for aliens");
        songTitleList.add("Otra Noche en Miami");
        songTitleList.add("Ricky");
        songTitleList.add("Dead Weight");
        songTitleList.add("California");

        artistNameList.add("The Neighbourhood");
        artistNameList.add("Wallows");
        artistNameList.add("The 1975");
        artistNameList.add("Machine Gun Kelly");
        artistNameList.add("Bad Bunny");
        artistNameList.add("Denzel Curry");
        artistNameList.add("PVRIS");
        artistNameList.add("girlfriends");

        videoUriList.add(Uri.parse("https://youtu.be/SEp7gnM0Ms8"));
        videoUriList.add(Uri.parse("https://youtu.be/lom6I3EgynY"));
        videoUriList.add(Uri.parse("https://youtu.be/QkubQCI4Fxo"));
        videoUriList.add(Uri.parse("https://youtu.be/dANJlolAYyA"));
        videoUriList.add(Uri.parse("https://youtu.be/hoQmSA6MRAk"));
        videoUriList.add(Uri.parse("https://youtu.be/3WHm6tfvKlk"));
        videoUriList.add(Uri.parse("https://youtu.be/c4iyEXDStgk"));
        videoUriList.add(Uri.parse("https://youtu.be/XQ3IKnB47SU"));

        songUriList.add(Uri.parse("https://genius.com/The-neighbourhood-devils-advocate-lyrics"));
        songUriList.add(Uri.parse("https://genius.com/Wallows-remember-when-lyrics"));
        songUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Girls_(The_1975_song)"));
        songUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Concert_for_Aliens"));
        songUriList.add(Uri.parse("https://genius.com/Bad-bunny-otra-noche-en-miami-lyrics"));
        songUriList.add(Uri.parse("https://genius.com/Denzel-curry-ricky-lyrics"));
        songUriList.add(Uri.parse("https://genius.com/Pvris-dead-weight-lyrics"));
        songUriList.add(Uri.parse("https://genius.com/Girlfriends-california-lyrics"));

        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/The_Neighbourhood"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Wallows"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/The_1975"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Machine_Gun_Kelly_(musician)"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Bad_Bunny"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Denzel_Curry"));
        artistUriList.add(Uri.parse("https://en.wikipedia.org/wiki/Pvris"));
        artistUriList.add(Uri.parse("https://bignoise.com/talent/girlfriends/"));
    }

    //Creates the Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }


    //Handles clicks for the Options Menu and changes the view accordingly
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view1:
                songView.setLayoutManager(new LinearLayoutManager(this));
                Log.i("Menu Click", "View 1 was clicked");
                return true;
            case R.id.view2:
                songView.setLayoutManager(new GridLayoutManager(this, 2));
                Log.i("Menu Click", "View 2 was clicked");
                return  true;
            default:
                return false;
        }
    }
}
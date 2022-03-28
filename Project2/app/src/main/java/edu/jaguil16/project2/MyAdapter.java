/*
Jose M. Aguilar (jaguil61)
Project 2
MyAdapter.java
 */

package edu.jaguil16.project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private ArrayList<Integer> imgIDs; // the ID's of all the imgs
    private ArrayList<String> songTitleList; // the titles of the songs
    private ArrayList<String> artistNameList; // the names of all the artists
    private ArrayList<Uri> videoUriList; // list of urls for the music videos
    private ArrayList<Uri> songUriList; // list of urls for the song wikis
    private ArrayList<Uri> artistUriList; // list of urls for the artists
    private RVClickListener RVlistener; //listener defined in main activity

    public MyAdapter(ArrayList<Integer> theImgIDs, ArrayList<String> theSongTitleList, ArrayList<String> theArtistNameList,
                     ArrayList<Uri> theVideoUriList, ArrayList<Uri> theSongUriList, ArrayList<Uri> theArtistUriList, RVClickListener theListener) {
        imgIDs = theImgIDs;
        songTitleList = theSongTitleList;
        artistNameList = theArtistNameList;
        videoUriList = theVideoUriList;
        songUriList = theSongUriList;
        artistUriList = theArtistUriList;
        this.RVlistener = theListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View listView = inflater.inflate(R.layout.rv_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listView, RVlistener);

        return viewHolder;
    }

    //Attaches the names/titles/imgs to the RecyclerView items list
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songCover.setImageResource(imgIDs.get(position));
        holder.songTitle.setText(songTitleList.get(position));
        holder.artistName.setText(artistNameList.get(position));
    }

    @Override
    public int getItemCount() {
        return songTitleList.size();
    }

    //Class for the individual items in RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, View.OnClickListener {

        public TextView songTitle;
        public TextView artistName;
        public ImageView songCover;
        private View itemView;
        private Context context;
        private RVClickListener listener;

        public ViewHolder(@NonNull View itemView, RVClickListener passedListener) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.SongTitle);
            artistName = itemView.findViewById(R.id.ArtistName);
            songCover = itemView.findViewById(R.id.SongCover);
            this.itemView = itemView;
            itemView.setOnCreateContextMenuListener(this);
            context = itemView.getContext();
            this.listener = passedListener;

            itemView.setOnClickListener(this); // set the listener
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }

        //Listener for context menu item that takes them to the song video
        private final MenuItem.OnMenuItemClickListener onVideoClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("Context_Menu", songTitle.getText() + " adapter pos: " + getAdapterPosition());
                Uri aUri = videoUriList.get(getAdapterPosition());
                Intent aIntent = new Intent(Intent.ACTION_VIEW);
                aIntent.setData(aUri);
                aIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                context.startActivity(aIntent);

                return true;
            }
        };

        //Listener for context menu item that takes them to the song wiki
        private final MenuItem.OnMenuItemClickListener onSongClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("Context_Menu", songTitle.getText() + " adapter pos: " + getAdapterPosition());
                Uri aUri = songUriList.get(getAdapterPosition());
                Intent aIntent = new Intent(Intent.ACTION_VIEW);
                aIntent.setData(aUri);
                aIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                context.startActivity(aIntent);

                return true;
            }
        };

        //Listener for context menu item that takes them to the artist wiki
        private final MenuItem.OnMenuItemClickListener onArtistClick = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.i("Context_Menu", songTitle.getText() + " adapter pos: " + getAdapterPosition());
                Uri aUri = artistUriList.get(getAdapterPosition());
                Intent aIntent = new Intent(Intent.ACTION_VIEW);
                aIntent.setData(aUri);
                aIntent.addCategory(Intent.CATEGORY_BROWSABLE);
                context.startActivity(aIntent);

                return true;
            }
        };

        //Create context menu
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater = new MenuInflater((v.getContext()));
            inflater.inflate(R.menu.context_menu, menu);

            menu.getItem(0).setOnMenuItemClickListener(onVideoClick);
            menu.getItem(1).setOnMenuItemClickListener(onSongClick);
            menu.getItem(2).setOnMenuItemClickListener(onArtistClick);
        }
    }
}


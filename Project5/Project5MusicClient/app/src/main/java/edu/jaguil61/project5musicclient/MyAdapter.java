//Jose M. Aguilar (jaguil61)

package edu.jaguil61.project5musicclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> songNameList = new ArrayList<>(); //list of all the song names
    private List<String> artistNameList = new ArrayList<>(); //list of all the artist names
    private List<Bitmap> songCoverList = new ArrayList<>(); //list of all the song photos
    private RVClickListener RVlistener; //listener defined in main activity

    public MyAdapter(List<String> theSongsNames, List<String> theArtistNames, List<Bitmap> theSongCover,
                     RVClickListener theListener) {
        songNameList = theSongsNames;
        artistNameList = theArtistNames;
        songCoverList = theSongCover;
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

    //assign the textviews/images to what is in the lists
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.songTitle.setText(songNameList.get(position));
        holder.artistName.setText(artistNameList.get(position));
        holder.songCover.setImageBitmap(songCoverList.get(position));
    }

    @Override
    public int getItemCount() {
        return songNameList.size();
    }

    //Class for the individual items in RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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
            context = itemView.getContext();
            this.listener = passedListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}

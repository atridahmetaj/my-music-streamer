package com.sourcey.materiallogindemo;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {

    private Context context;
    private List<MusicBean> musicBeans;

    public MusicAdapter(Context context, List<MusicBean> musicBeans) {
        this.context = context;
        this.musicBeans = musicBeans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.card, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.movieName.setText(musicBeans.get(position).getMovieName());
        holder.movieGenre.setText(musicBeans.get(position).getMovieGenre());
        String link = musicBeans.get(position).getImageLink();
        Glide.with(context).load(musicBeans.get(position).getImageLink()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        return musicBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView movieName;
        public TextView movieGenre;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            movieName = (TextView) itemView.findViewById(R.id.moviename);
            imageView = (ImageView) itemView.findViewById(R.id.movieImage);
            movieGenre = (TextView) itemView.findViewById(R.id.genre);
            imageView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int[] p = new int[]{0,0};
            int position = getAdapterPosition();
            v.getLocationOnScreen(p);

            showPopupMenu(v, position);
            v.setBackgroundResource(0);

        }
    }

    private void showPopupMenu(View view, int poaition) {
        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_context, popup.getMenu());
        popup.setOnMenuItemClickListener(new MenuClickListener(poaition));
        popup.show();

    }
    MediaPlayer mediaPlayer = new MediaPlayer();
    public  void onPlay(String url){

if (!mediaPlayer.isPlaying()) {
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    try {
        mediaPlayer.setDataSource(url);
    } catch (IOException e) {
        e.printStackTrace();
    }
    try {
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
    } catch (IOException e) {
        e.printStackTrace();
    }
    mediaPlayer.start();
}
else{
    mediaPlayer.stop();
    mediaPlayer.reset();
    try {
        mediaPlayer.setDataSource(url);
    } catch (IOException e) {
        e.printStackTrace();
    }
    mediaPlayer.start();
}
    }

    public void onPause(){
        mediaPlayer.stop();
        mediaPlayer.reset();
    }


    class MenuClickListener implements PopupMenu.OnMenuItemClickListener {
        Integer pos;

        public MenuClickListener(int pos) {
            this.pos = pos;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.play:
                    Toast.makeText(context, musicBeans.get(pos).getMovieName() + " is playing", Toast.LENGTH_SHORT).show();
                    String url = musicBeans.get(pos).getUrl();
                    onPlay(url);
                    return true;
                case R.id.pause:
                    Toast.makeText(context, musicBeans.get(pos).getMovieName() + " paused", Toast.LENGTH_SHORT).show();
                    onPause();
                    return true;
                case R.id.add_favourite:
                    Toast.makeText(context, musicBeans.get(pos).getMovieName()+"added to favourite"  , Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.download:
                    Toast.makeText(context, musicBeans.get(pos).getMovieName() + " Downloading...", Toast.LENGTH_SHORT).show();
                    return true;

                default:
                    onMenuItemClick(menuItem);

            }
            return false;
        }


    }
}
package com.codificador.audiocontentprovider;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by Seng on 1/9/2018.
 */

public class MusicPlayer {
    MediaPlayer mediaPlayer;
    String path;
    Context context;

    public MusicPlayer(Context context){
        this.context = context;
    }

    public void play(String path){
        if(path.equals(this.path)){
            mediaPlayer.start();
        }else{
            if(mediaPlayer != null && mediaPlayer.isPlaying()){
                stop();
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(path));
            mediaPlayer.start();
        }
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void stop(){
        mediaPlayer.stop();
    }
}

package com.codificador.audiocontentprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Seng on 1/9/2018.
 */

public class MusicAdapter extends BaseAdapter {
    ArrayList<AudioFile> audioFiles;
    Context context;
    LayoutInflater inflater;

    public MusicAdapter(Context context){
        this.context = context;
        audioFiles = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setList(ArrayList<AudioFile> list){
        audioFiles = list;
    }

    @Override
    public int getCount() {
        return audioFiles.size();
    }

    @Override
    public Object getItem(int i) {
        return audioFiles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootView;
        rootView = inflater.inflate(R.layout.list_item_row,viewGroup,false);
        TextView title = rootView.findViewById(R.id.title);
        TextView album = rootView.findViewById(R.id.album);
        AudioFile audioFile = audioFiles.get(i);
        title.setText(audioFile.getTitle());
        album.setText(audioFile.getAlbum());
        return rootView;
    }
}

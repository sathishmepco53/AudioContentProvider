package com.codificador.audiocontentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    MusicPlayer musicPlayer;
    MusicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        adapter = new MusicAdapter(MainActivity.this);
        musicPlayer = new MusicPlayer(getApplicationContext());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AudioFile audioFile = (AudioFile) adapter.getItem(i);
                musicPlayer.play(audioFile.getPath());
            }
        });
        checkPermission();
    }

    private void loadAudioList(){
        adapter.setList(readMediaStore());
        listView.setAdapter(adapter);
    }

    private ArrayList<AudioFile> readMediaStore(){
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        cursor.moveToFirst();
        ArrayList<AudioFile> list = new ArrayList<>();
        while (!cursor.isAfterLast()){
            AudioFile audioFile = new AudioFile();
            audioFile.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            audioFile.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            audioFile.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            list.add(audioFile);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    private void checkPermission(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},123);
            return;
        }else{
            loadAudioList();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            loadAudioList();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        musicPlayer.stop();
    }
}

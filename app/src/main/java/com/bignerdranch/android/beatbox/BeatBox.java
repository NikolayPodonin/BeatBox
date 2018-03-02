package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by Ybr on 01.03.2018.
 */

public class BeatBox {
    private static final String TAG = "BeatBox";

    private static final String SOUNDS_FOLDER = "sample_sounds";
    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssetManager;
    private List<Sound> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;
    private List<Integer> mStreamIds = new ArrayList<>();

    public BeatBox(Context context){
        mAssetManager = context.getAssets();

        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSound();
    }

    public void play(Sound sound, float volume){
        Integer soundId = sound.getSoundId();
        if(soundId == null){
            return;
        }
        Integer id = mSoundPool.play(soundId, volume, volume, 1,0, 1.0f);
        mStreamIds.add(id);
        if(mStreamIds.size() > 6){
            mStreamIds.remove(1);
        }
    }

    public void setVolume(float volume){
        for(Integer id : mStreamIds){
            mSoundPool.setVolume(id, volume, volume);
        }
    }

    private void loadSound(){
        String[] soundNames;
        try{
            soundNames = mAssetManager.list(SOUNDS_FOLDER);
            Log.i(TAG, "loadSound: Found " + soundNames.length + " sounds");
        } catch (IOException ioe){
            Log.e(TAG, "Could not list assets");
            return;
        }

        for(String filename : soundNames){
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                Sound sound = new Sound(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException ioe) {
                Log.e(TAG, "Could not load sound " + filename, ioe);
            }
        }
    }

    private void load(Sound sound) throws IOException{
        AssetFileDescriptor afd = mAssetManager.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    public void release(){
        mSoundPool.release();
    }
}

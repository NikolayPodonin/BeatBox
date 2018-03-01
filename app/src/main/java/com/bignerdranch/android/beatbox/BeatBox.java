package com.bignerdranch.android.beatbox;

import android.content.Context;
import android.content.res.AssetManager;
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

    private AssetManager mAssetManager;
    private List<Sound> mSounds = new ArrayList<>();

    public BeatBox(Context context){
        mAssetManager = context.getAssets();

        loadSound();
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
            String assetPath = SOUNDS_FOLDER + "/" + filename;
            Sound sound = new Sound(assetPath);
            mSounds.add(sound);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }
}

package com.bignerdranch.android.beatbox;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.widget.SeekBar;

/**
 * Created by Ybr on 01.03.2018.
 */

public class SoundViewModel extends BaseObservable{
    private Sound mSound;
    private BeatBox mBeatBox;
    private static SeekBar sSeekBar;

    public SoundViewModel(BeatBox beatBox) {

        mBeatBox = beatBox;
    }

    @Bindable
    public String getTitle(){
        return mSound.getName();
    }

    public Sound getSound() {
        return mSound;
    }

    public void setSound(Sound sound) {
        mSound = sound;
        notifyChange();
    }

    public void setSeekBar(SeekBar seekBar) {
        if(sSeekBar == null){
            sSeekBar = seekBar;
            sSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mBeatBox.setVolume((float) progress / 100);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    public void onButtonClicked() {
        mBeatBox.play(mSound, (float)sSeekBar.getProgress() / 100);
    }
}

package com.bignerdranch.android.beatbox;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.bignerdranch.android.beatbox.databinding.FragmentBeatBoxBinding;
import com.bignerdranch.android.beatbox.databinding.ListItemSoundBinding;

import java.util.List;


public class BeatBoxFragment extends Fragment {
    private BeatBox mBeatBox;
    private FragmentBeatBoxBinding mBeatBoxBinding;

    public static BeatBoxFragment newInstance(){
        return new BeatBoxFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mBeatBox = new BeatBox(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBeatBoxBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_beat_box, container, false);

        mBeatBoxBinding.recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mBeatBoxBinding.recyclerView.setAdapter(new SoundAdapter(mBeatBox.getSounds()));

        return mBeatBoxBinding.getRoot();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBeatBox.release();
    }

    private class SoundHolder extends RecyclerView.ViewHolder{
        private ListItemSoundBinding mBinding;

        private SoundHolder(ListItemSoundBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.setViewModel(new SoundViewModel(mBeatBox));
        }

        public void bind(Sound sound){
            mBinding.getViewModel().setSound(sound);
            mBinding.getViewModel().setSeekBar(mBeatBoxBinding.seekBar);
            mBinding.executePendingBindings();
        }
    }

    private class SoundAdapter extends RecyclerView.Adapter{
        private List<Sound> mSounds;

        public SoundAdapter(List<Sound> sounds) {
            mSounds = sounds;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            ListItemSoundBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item_sound, parent, false);
            return new SoundHolder((binding));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            SoundHolder newHolder = (SoundHolder)holder;
            Sound sound = mSounds.get(position);
            newHolder.bind(sound);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }
}

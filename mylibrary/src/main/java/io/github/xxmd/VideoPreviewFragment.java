package io.github.xxmd;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;

import java.util.concurrent.TimeUnit;

import io.github.xxmd.databinding.FpFragmentVideoPreviewBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VideoPreviewFragment extends PreviewFragment {
    private FpFragmentVideoPreviewBinding binding;
    public static final int STAT_IDLE = 0;
    public static final int STAT_PLAYING = 1;
    public static final int STAT_PAUSED = 2;
    public static final int STAT_FINISHED = 3;

    private MutableLiveData<Integer> playState = new MutableLiveData<>(STAT_IDLE);
    private Disposable interval;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FpFragmentVideoPreviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.videoView.setVideoPath(filePath);
        bindEvent();
    }

    private void bindEvent() {
        playState.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                binding.ivCover.setVisibility(state == STAT_IDLE ? View.VISIBLE : View.GONE);
                binding.ivPlay.setVisibility(state == STAT_IDLE || state == STAT_PAUSED ? View.VISIBLE : View.GONE);
                binding.ivPause.setVisibility(state == STAT_PLAYING ? View.VISIBLE : View.GONE);
                binding.ivRefresh.setVisibility(state == STAT_FINISHED ? View.VISIBLE : View.GONE);

                switch (state) {
                    case STAT_IDLE:
                        loadCover();
                        break;
                    case STAT_PLAYING:
                        binding.videoView.start();
                        break;
                    case STAT_PAUSED:
                        binding.videoView.pause();
                        break;
                }
            }
        });

        binding.ivPlay.setOnClickListener(v -> playState.setValue(STAT_PLAYING));
        binding.ivPause.setOnClickListener(v -> playState.setValue(STAT_PAUSED));
        binding.ivRefresh.setOnClickListener(v -> {
            binding.videoView.seekTo(0);
            playState.setValue(STAT_PLAYING);
        });

        binding.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playState.setValue(STAT_FINISHED);
            }
        });
        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                monitorPlayProgress();
            }
        });
        binding.videoView.setOnClickListener(v -> binding.playController.setVisibility(View.VISIBLE));
        binding.playController.setOnClickListener(v -> binding.playController.setVisibility(View.GONE));

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    binding.videoView.seekTo(progress);
                    if (playState.getValue() == STAT_FINISHED) {
                        playState.setValue(STAT_PAUSED);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (interval != null) {
            interval.dispose();
        }
    }

    private void monitorPlayProgress() {
        binding.seekBar.setMax(binding.videoView.getDuration());
        interval = Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(times -> {
                    binding.seekBar.setProgress(binding.videoView.getCurrentPosition());
                });
    }

    private void loadCover() {
        Glide.with(binding.ivCover)
                .load(filePath)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.ivCover);
    }
}

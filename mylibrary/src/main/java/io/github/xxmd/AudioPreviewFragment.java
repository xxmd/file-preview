package io.github.xxmd;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.github.xxmd.databinding.FpFragmentAudioPreviewBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AudioPreviewFragment extends PreviewFragment {
    private FpFragmentAudioPreviewBinding binding;
    public static final int STAT_IDLE = 0;
    public static final int STAT_PLAYING = 1;
    public static final int STAT_PAUSED = 2;
    private MutableLiveData<Integer> playState = new MutableLiveData<>(STAT_IDLE);
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private @NonNull Disposable interval;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FpFragmentAudioPreviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        bindEvent();
    }

    private void bindEvent() {
        playState.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer state) {
                binding.ivPlay.setVisibility(state == STAT_IDLE || state == STAT_PAUSED ? View.VISIBLE : View.GONE);
                binding.ivPause.setVisibility(state == STAT_PLAYING ? View.VISIBLE : View.GONE);

                switch (state) {
                    case STAT_PLAYING:
                        play();
                        break;
                    case STAT_PAUSED:
                        pause();
                        break;
                }
            }
        });

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                monitorPlayProgress();
                initViewAfterPrepared();
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playState.setValue(STAT_PAUSED);
            }
        });

        binding.ivPlay.setOnClickListener(v -> playState.setValue(STAT_PLAYING));
        binding.ivPause.setOnClickListener(v -> playState.setValue(STAT_PAUSED));

        binding.ivPlayBack.setOnClickListener(v -> seekTo(-3000));
        binding.ivPlayFast.setOnClickListener(v -> seekTo(3000));

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
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

    private void seekTo(int gap) {
        int currentPosition = mediaPlayer.getCurrentPosition();
        int nextPosition = currentPosition + gap;
        if (nextPosition < 0) {
            nextPosition = 0;
        }
        if (nextPosition > mediaPlayer.getDuration()) {
            nextPosition = mediaPlayer.getDuration();
        }
        mediaPlayer.seekTo(nextPosition);
    }

    private void initViewAfterPrepared() {
        binding.tvTotalDuration.setText(DurationFormatUtils.formatDuration(mediaPlayer.getDuration(), "mm:ss"));
    }

    private void pause() {
        mediaPlayer.pause();
    }

    private void play() {
        if (mediaPlayer.getCurrentPosition() == mediaPlayer.getDuration()) {
            mediaPlayer.seekTo(0);
        }
        mediaPlayer.start();
    }

    private void monitorPlayProgress() {
        binding.seekBar.setMax(mediaPlayer.getDuration());
        interval = Observable.interval(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(times -> {
                    binding.seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    binding.tvCurrentTime.setText(DurationFormatUtils.formatDuration(mediaPlayer.getCurrentPosition(), "mm:ss"));
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
        interval.dispose();
    }

    private void initView() {
        binding.tvAudioName.setText(FilenameUtils.getName(filePath));
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

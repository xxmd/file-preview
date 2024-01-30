package io.github.xxmd;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;

import java.util.concurrent.TimeUnit;

import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;
import io.github.xxmd.databinding.FpActivityVideoPreviewBinding;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class VideoPreviewActivity extends FilePreviewActivity {
    private FpActivityVideoPreviewBinding binding;

    @Override
    public void initView() {
        super.initView();
        loadCover();
        JZDataSource jzDataSource = new JZDataSource(filePath, "");
        binding.jzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
//        binding.coverContainer.setOnClickListener(v -> {
//            binding.coverContainer.setVisibility(View.GONE);
//            binding.jzVideo.startVideo();
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.jzVideo.releaseAllVideos();
    }

    @Override
    public ViewBinding getBinding() {
        binding = FpActivityVideoPreviewBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public Toolbar getToolBar() {
        return binding.toolBar;
    }

    @Override
    public String getPageTitle() {
        return "视频预览";
    }

    private void loadCover() {
        binding.jzVideo.posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this)
                .load(filePath)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.jzVideo.posterImageView);
    }
}

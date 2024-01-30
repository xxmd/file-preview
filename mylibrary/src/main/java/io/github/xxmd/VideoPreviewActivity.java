package io.github.xxmd;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.View;
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

        Uri uri = Uri.parse("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4");
        JZDataSource jzDataSource = new JZDataSource("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4", "");
        binding.jzVideo.setUp(jzDataSource, JzvdStd.SCREEN_NORMAL);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
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
//        Glide.with(binding.ivCover)
//                .load(filePath)
//                .placeholder(R.drawable.image_placeholder)
//                .into(binding.ivCover);
    }
}

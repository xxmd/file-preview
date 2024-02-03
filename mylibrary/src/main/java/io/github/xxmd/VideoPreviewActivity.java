package io.github.xxmd;

import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import cn.jzvd.JZVideoPlayerStandard;
import io.github.xxmd.databinding.FpActivityVideoPreviewBinding;

public class VideoPreviewActivity extends FilePreviewActivity {
    private FpActivityVideoPreviewBinding binding;

    @Override
    public void initView() {
        super.initView();
//        loadCover();
        binding.jzVideo.setUp(filePath,  JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        binding.jzVideo.startVideo();
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
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
        return getString(R.string.video_preview);
    }

    private void loadCover() {
//        binding.jzVideo.posterImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(this)
//                .load(filePath)
//                .placeholder(R.drawable.image_placeholder)
//                .into(binding.jzVideo.posterImageView);
    }
}

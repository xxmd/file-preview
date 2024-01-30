package io.github.xxmd;

import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;

import io.github.xxmd.databinding.FpActivityImagePreviewBinding;

public class ImagePreviewActivity extends FilePreviewActivity {
    private FpActivityImagePreviewBinding binding;

    @Override
    public ViewBinding getBinding() {
        binding = FpActivityImagePreviewBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public Toolbar getToolBar() {
        return binding.toolBar;
    }

    @Override
    public String getPageTitle() {
        return "图片预览";
    }

    @Override
    public void initView() {
        super.initView();

        Glide.with(this)
                .load(filePath)
                .placeholder(R.drawable.image_placeholder)
                .into(binding.photoView);
    }
}

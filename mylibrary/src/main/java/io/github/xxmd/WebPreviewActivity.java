package io.github.xxmd;

import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.bumptech.glide.Glide;

import io.github.xxmd.databinding.FpActivityImagePreviewBinding;
import io.github.xxmd.databinding.FpActivityWebPreviewBinding;

public class WebPreviewActivity extends FilePreviewActivity {
    private FpActivityWebPreviewBinding binding;

    @Override
    public ViewBinding getBinding() {
        binding = FpActivityWebPreviewBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    public Toolbar getToolBar() {
        return binding.toolBar;
    }

    @Override
    public String getPageTitle() {
        return getString(R.string.file_preview);
    }

    @Override
    public void initView() {
        super.initView();
        binding.webView.loadUrl(filePath);
    }
}

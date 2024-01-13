package io.github.xxmd;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.xxmd.databinding.FpActivityFilePreviewBinding;

public class FilePreviewActivity extends AppCompatActivity {
    private FpActivityFilePreviewBinding binding;
    public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";

    public static void previewFile(Context context, String filePath) {
        Intent intent = new Intent(context, FilePreviewActivity.class);
        intent.putExtra(EXTRA_FILE_PATH, filePath);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FpActivityFilePreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolBar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);

        String filePath = getIntent().getStringExtra(EXTRA_FILE_PATH);

        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException(String.format("%s is not existed", filePath));
        }


        String extension = FilenameUtils.getExtension(filePath);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        PreviewFragment previewFragment = null;

        if (mimeType.contains("image")) {
            previewFragment = new ImagePreviewFragment();
            setTitle("图片预览");
        } else if (mimeType.contains("video")) {
            previewFragment = new VideoPreviewFragment();
            setTitle("视频预览");
        } else if (mimeType.contains("audio")) {
            previewFragment = new AudioPreviewFragment();
            setTitle("音频预览");
        }
        previewFragment.setFilePath(filePath);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, previewFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

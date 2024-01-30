package io.github.xxmd;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.jaeger.library.StatusBarUtil;

import org.apache.commons.lang3.StringUtils;

public abstract class FilePreviewActivity extends AppCompatActivity {
    public static final String EXTRA_FILE_PATH = "EXTRA_FILE_PATH";
    public String filePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewBinding binding = getBinding();
        setContentView(binding.getRoot());
        StatusBarUtil.setTransparent(this);
        initView();
        bindEvent();
    }


    public void bindEvent() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public abstract ViewBinding getBinding();
    public abstract Toolbar getToolBar();
    public abstract String getPageTitle();

    public void initView() {
        Toolbar toolBar = getToolBar();
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolBar.getNavigationIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setTitle(getPageTitle());

        filePath = getIntent().getStringExtra(EXTRA_FILE_PATH);

        if (StringUtils.isEmpty(filePath)) {
            throw new IllegalArgumentException(String.format("filePath: %s is not existed", filePath));
        }
    }
}

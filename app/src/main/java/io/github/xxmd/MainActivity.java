package io.github.xxmd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.config.SelectModeConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import java.util.ArrayList;

import io.github.xxmd.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        bindEvent();
    }

    private void bindEvent() {
        binding.btnImage.setOnClickListener(v -> chooseFile(SelectMimeType.ofImage()));
        binding.btnVideo.setOnClickListener(v -> chooseFile(SelectMimeType.ofVideo()));
        binding.btnAudio.setOnClickListener(v -> chooseFile(SelectMimeType.ofAudio()));
    }

    private void chooseFile(int type) {

        PictureSelector.create(this)
                .openGallery(type)
                .setSelectionMode(SelectModeConfig.SINGLE)
                .setImageEngine(GlideEngine.createGlideEngine())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(ArrayList<LocalMedia> result) {
                        FilePreviewer.previewFile(MainActivity.this, result.get(0).getRealPath());
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }
}
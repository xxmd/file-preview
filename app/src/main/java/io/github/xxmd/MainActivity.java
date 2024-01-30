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
//        String videoUrl = "http://v16m-default.akamaized.net/64717172720761fa02141268dc3725b7/65b8a2ba/video/tos/alisg/tos-alisg-v-0000/okcODEbngQGlbVGGF3JyLfnteCdAiDuEeudSoA/?a=2011&ch=0&cr=0&dr=0&net=5&cd=0%7C0%7C0%7C0&br=6460&bt=3230&bti=MzhALjBg&cs=0&ds=4&ft=XE5bCqT0mmjPD12sLY1R3wU7C1JcMeF~O5&mime_type=video_mp4&qs=0&rc=OWgzZGc3Ojk8Zjo7OTQ5ZkBpam07PDY6ZjdnZzMzODYzNEBiNl5iMy9fXjQxXl8wY181YSNwaWwucjRna3NgLS1kMC1zcw%3D%3D&l=202401300058452F9D39B9BA04B204F10F&btag=e000a8000";
//        FilePreviewer.previewFile(MainActivity.this, videoUrl);
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
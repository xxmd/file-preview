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
        String videoUrl = "https://vdept3.bdstatic.com/mda-peb81ifiai953fsz/sc/cae_h264/1683945818917238364/mda-peb81ifiai953fsz.mp4?v_from_s=hkapp-haokan-nanjing&auth_key=1706620064-0-0-5833b88fe12426899f78e5234f3e6782&bcevod_channel=searchbox_feed&cr=2&cd=0&pd=1&pt=3&logid=0464688034&vid=9029381628758960723&klogid=0464688034&abtest=116096_1-115667_1";
        FilePreviewer.previewFile(MainActivity.this, videoUrl);
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
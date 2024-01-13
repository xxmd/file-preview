package io.github.xxmd;

import androidx.fragment.app.Fragment;

public abstract class PreviewFragment extends Fragment {
    public String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

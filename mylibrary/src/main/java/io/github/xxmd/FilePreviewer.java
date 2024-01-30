package io.github.xxmd;

import android.content.Context;
import android.content.Intent;
import android.webkit.MimeTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

public class FilePreviewer {
    public static final int FILE_TYPE_IMAGE = 0;
    public static final int FILE_TYPE_VIDEO = 1;
    public static final int FILE_TYPE_AUDIO = 2;
    public static final int FILE_TYPE_WEB = 3;

    public static void previewFile(Context context, String filePath) {
        String extension = FilenameUtils.getExtension(filePath);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        if (StringUtils.isEmpty(mimeType) && filePath.contains("http")) {
            previewFile(context, filePath, FILE_TYPE_WEB);
            return;
        }

        if (mimeType.contains("image")) {
            previewFile(context, filePath, FILE_TYPE_IMAGE);
        } else if (mimeType.contains("video")) {
            previewFile(context, filePath, FILE_TYPE_VIDEO);
        } else if (mimeType.contains("audio")) {
            previewFile(context, filePath, FILE_TYPE_AUDIO);
        } else if (filePath.contains("http")) {
            previewFile(context, filePath, FILE_TYPE_WEB);
        } else {
            throw new RuntimeException(String.format("cant decide mimeType of filePath: %s", filePath));
        }
    }

    public static void previewFile(Context context, String filePath, int fileType) {
        Class clazz = null;
        switch (fileType) {
            case FILE_TYPE_IMAGE:
                clazz = ImagePreviewActivity.class;
                break;
            case FILE_TYPE_VIDEO:
                clazz = VideoPreviewActivity.class;
                break;
            case FILE_TYPE_AUDIO:
                clazz = AudioPreviewActivity.class;
                break;
            case FILE_TYPE_WEB:
                clazz = WebPreviewActivity.class;
                break;
        }
        Intent intent = new Intent(context, clazz);
        intent.putExtra(FilePreviewActivity.EXTRA_FILE_PATH, filePath);
        context.startActivity(intent);
    }
}

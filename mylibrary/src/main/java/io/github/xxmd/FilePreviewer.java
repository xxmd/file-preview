package io.github.xxmd;

import android.content.Context;
import android.content.Intent;
import android.webkit.MimeTypeMap;

import org.apache.commons.io.FilenameUtils;

public class FilePreviewer {
    public static void previewFile(Context context, String filePath) {
        String extension = FilenameUtils.getExtension(filePath);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);

        Class clazz = null;
//        if (mimeType.contains("image")) {
//            clazz = ImagePreviewActivity.class;
//        } else if (mimeType.contains("video")) {
            clazz = VideoPreviewActivity.class;
//        } else if (mimeType.contains("audio")) {
//            clazz = AudioPreviewActivity.class;
//        }
        Intent intent = new Intent(context, clazz);
        intent.putExtra(FilePreviewActivity.EXTRA_FILE_PATH, filePath);
        context.startActivity(intent);
    }
}

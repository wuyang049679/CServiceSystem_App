package com.hecong.cssystem.utils;

import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.hecong.cssystem.base.BaseApplication;
import com.hecong.cssystem.utils.android.Utils;

import java.io.File;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2018/04/20
 *     desc  : URI 相关
 * </pre>
 */
public final class UriUtils {

    private UriUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * File to uri.
     *
     * @param file The file.
     * @return uri
     */
    public static Uri file2Uri(@NonNull final File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = Utils.getApp().getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(Utils.getApp(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * Uri to file.
     *
     * @param uri        The uri.
     * @param columnName The name of the target column.
     *                   <p>e.g. {@link MediaStore.Images.Media#DATA}</p>
     * @return file
     */
    public static File uri2File(@NonNull final Uri uri, final String columnName) {
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            return new File(uri.getPath());
        }
        CursorLoader cl = new CursorLoader(Utils.getApp());
        cl.setUri(uri);
        cl.setProjection(new String[]{columnName});
        Cursor cursor = null;
        try {
            cursor = cl.loadInBackground();
            int columnIndex = cursor.getColumnIndexOrThrow(columnName);
            cursor.moveToFirst();
            return new File(cursor.getString(columnIndex));
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private static Uri uri;
    private static String path = Constant.STRINGDEFAULT;
    private static int resId = Constant.INTDEFAULT;

    public static Uri getUri(int mode, String url) {
        path = url;
        return !TextUtils.isEmpty(path) ? UriTool(mode) : null;
    }

    public static Uri getUri(int mode, int localResource) {
        resId = localResource;
        return UriTool(mode);
    }

    public static Uri UriTool(int mode) {
        if (mode == Constant.ONLINEPIC && path != Constant.STRINGDEFAULT) {
            uri = Uri.parse(path);
        } else if (mode == Constant.LOCALRESOURCE && resId != Constant.INTDEFAULT) {
            uri = Uri.parse("res://" + BaseApplication.getContext().getPackageName() + "/" + resId);
        }

        return uri;
    }
}
package com.yuanbaogo.libbase.baseutil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 功能：
 * 时间 ： 2021/8/18:.
 * 作者：11190
 */

public class ImageUtils {
    /**
     * Return the size of bitmap.
     *获取图片尺寸
     * @param filePath The path of file.
     * @return the size of bitmap
     */
    public static int[] getSize(String filePath) {
        return getSize(getFileByPath(filePath));
    }

    /**
     * Return the size of bitmap.
     *
     * @param file The file.
     * @return the size of bitmap
     */
    public static int[] getSize(File file) {
        if (file == null) return new int[]{0, 0};
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
        return new int[]{opts.outWidth, opts.outHeight};
    }

    /**
     * Return the file by path.
     *
     * @param filePath The path of file.
     * @return the file
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }
    static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
    /**
     * 保存bitmap图片到本地
     *
     * @param src      bitmap
     * @param filePath 文件路径.
     * @param format   图片后缀 例如 Bitmap.CompressFormat.PNG.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format) {
        return save(src, filePath, format, 100, false);
    }

    /**
     * Save the bitmap.
     *
     * @param src    The source of bitmap.
     * @param file   The file.
     * @param format The format of the image.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src, final File file, final Bitmap.CompressFormat format) {
        return save(src, file, format, 100, false);
    }

    /**
     * Save the bitmap.
     *
     * @param src      The source of bitmap.
     * @param filePath The path of file.
     * @param format   The format of the image.
     * @param recycle  True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        return save(src, filePath, format, 100, recycle);
    }

    /**
     * Save the bitmap.
     *
     * @param src     The source of bitmap.
     * @param file    The file.
     * @param format  The format of the image.
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final boolean recycle) {
        return save(src, file, format, 100, recycle);
    }

    /**
     * Save the bitmap.
     *
     * @param src      The source of bitmap.
     * @param filePath The path of file.
     * @param format   The format of the image.
     * @param quality  Hint to the compressor, 0-100. 0 meaning compress for
     *                 small size, 100 meaning compress for max quality. Some
     *                 formats, like PNG which is lossless, will ignore the
     *                 quality setting
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final int quality) {
        return save(src, getFileByPath(filePath), format, quality, false);
    }

    /**
     * Save the bitmap.
     *
     * @param src    The source of bitmap.
     * @param file   The file.
     * @param format The format of the image.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final int quality) {
        return save(src, file, format, quality, false);
    }

    /**
     * Save the bitmap.
     *
     * @param src      The source of bitmap.
     * @param filePath The path of file.
     * @param format   The format of the image.
     * @param quality  Hint to the compressor, 0-100. 0 meaning compress for
     *                 small size, 100 meaning compress for max quality. Some
     *                 formats, like PNG which is lossless, will ignore the
     *                 quality setting
     * @param recycle  True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final String filePath,
                               final Bitmap.CompressFormat format,
                               final int quality,
                               final boolean recycle) {
        return save(src, getFileByPath(filePath), format, quality, recycle);
    }

    /**
     * Save the bitmap.
     *
     * @param src     The source of bitmap.
     * @param file    The file.
     * @param format  The format of the image.
     * @param quality Hint to the compressor, 0-100. 0 meaning compress for
     *                small size, 100 meaning compress for max quality. Some
     *                formats, like PNG which is lossless, will ignore the
     *                quality setting
     * @param recycle True to recycle the source of bitmap, false otherwise.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean save(final Bitmap src,
                               final File file,
                               final Bitmap.CompressFormat format,
                               final int quality,
                               final boolean recycle) {
        if (isEmptyBitmap(src)) {
            return false;
        }
        if (src.isRecycled()) {
            return false;
        }
        if (!createFileByDeleteOldFile(file)) {
            return false;
        }
        OutputStream os = null;
        boolean ret = false;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
            ret = src.compress(format, quality, os);
            if (recycle && !src.isRecycled()) src.recycle();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    private static boolean isEmptyBitmap(final Bitmap src) {
        return src == null || src.getWidth() == 0 || src.getHeight() == 0;
    }
    /**
     * Create a file if it doesn't exist, otherwise delete old file before creating.
     *
     * @param file The file.
     * @return {@code true}: success<br>{@code false}: fail
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        // file exists and unsuccessfully delete then return false
        if (file.exists() && !file.delete()) return false;
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param dirPath The path of directory.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * Create a directory if it doesn't exist, otherwise do nothing.
     *
     * @param file The file.
     * @return {@code true}: exists or creates successfully<br>{@code false}: otherwise
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }


}

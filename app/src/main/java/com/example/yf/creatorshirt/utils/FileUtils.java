package com.example.yf.creatorshirt.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.example.yf.creatorshirt.app.App;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by yang on 17/05/2017.
 */

public class FileUtils {
    public static String getCacheFile() {
        return App.getInstance().getCacheDir().getAbsolutePath() + File
                .separator + "data";
    }

    /**
     * save image
     *
     * @param bitmap
     * @param context
     * @param name
     * @return
     */
    public static String saveBitmap(@NonNull Bitmap bitmap, @NonNull Context context, String name) {
        String path = null;
        File file = getFile("creator" + name);
        if (file != null) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                path = file.getAbsolutePath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
            LogUtil.e("TAG", "SaveImageToGallery: the path of Bitmap is " + file.getAbsolutePath());
        }
        return path;
    }


    public static File getFile(String sticker) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        String timeStamp = simpleDateFormat.format(new Date());
        String path;
        if (isSDAvailable()) {
            path = getFolderName(sticker) + File.separator + timeStamp + ".jpg";//卡路径
        } else {
            path = App.getInstance().getFilesDir().getPath() + File.separator + sticker + File.separator + timeStamp + ".jpg";//app包路径下面
        }
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        return new File(path);
    }


    /**
     * 存储到本地相册
     *
     * @param name
     * @return
     */
    private static String getFolderName(String name) {
        File storageDir = new File(getCachePath(App.getInstance()), name);
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                return "";
            }
        }
        return storageDir.getAbsolutePath();
    }

    /**
     * 判断SD卡是否可以用
     *
     * @return
     */
    private static boolean isSDAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取缓存路径
     *
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        String cachePath;

        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() == null) {
                cachePath = context.getCacheDir().getPath();
            } else {
                cachePath = context.getExternalCacheDir().getPath();
            }
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return cachePath;
    }

    /**
     * 压缩图片工具
     *
     * @param context
     * @param fileSrc
     * @return
     */
    public static File getSmallBitmap(Context context, String fileSrc) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(fileSrc, options);
        options.inSampleSize = calculateInSampleSize(options, 750, 750);
        Log.i("COMPRESS", "options.inSampleSize-->" + options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap img = BitmapFactory.decodeFile(fileSrc, options);
        Log.i("COMPRESS", "file size after compress-->" + img.getByteCount() / 256);
        String filename = context.getFilesDir() + File.separator + "video-" + img.hashCode() + ".jpg";
        saveBitmap2File(img, filename);
        return new File(filename);

    }

    /**
     * 压缩图片工具
     *
     * @param context
     * @param resid
     * @param width
     * @param height  @return
     */
    public static Bitmap getSmallBitmap(Context context, int resid, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), resid);
        options.inSampleSize = 3;
        options.outWidth = width;
        options.outHeight = height;
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap img = BitmapFactory.decodeResource(context.getResources(), resid, options);
        Log.e("Files", "大小：" + img.getByteCount() / 1024 / 1024);
        return img;

    }

    /**
     * 设置压缩的图片的大小设置的参数
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(height) / reqHeight;
            int widthRatio = Math.round(width) / reqWidth;
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 保存bitmap到文件
     *
     * @param bmp
     * @param filename
     * @return
     */
    public static boolean saveBitmap2File(Bitmap bmp, String filename) {
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(filename);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bmp.compress(format, quality, stream);
    }

    public static Bitmap getZoomImage(Bitmap bitmap, double newWidth, double newHeight) {
        if (bitmap == null) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }
        if (newHeight <= 0 || newHeight <= 0) {
            return null;
        }
        //获取图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = (float) (newWidth / width);
        float scaleHeight = (float) (newHeight / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
        return newBitmap;

    }


    /**
     * 删除指定文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        return file.exists() && file.isFile() && file.delete();
    }

    public static int getResource(String imageName) {
        Context ctx = App.getInstance().getBaseContext();
        int resId = ctx.getResources().getIdentifier(imageName, "mipmap", ctx.getPackageName());
        //如果没有在"mipmap"下找到imageName,将会返回0
        return resId;
    }

    /**
     * 形成遮罩图片
     *
     * @param width
     * @param height
     * @param source
     * @param mask
     * @return
     */
    public static Bitmap getMaskBitmap(final int width, final int height, final Bitmap source, final Bitmap mask) {
        Bitmap bitmap;
        if (source != null && mask != null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            Rect rectF = new Rect(0, 0, width, height);
            Rect rectSource = new Rect(0, 0, source.getWidth(), source.getHeight());
            int layer = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(source, rectSource, rectF, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            Rect maskRect = new Rect(0, 0, mask.getWidth(), mask.getHeight());
            Rect dstRect = new Rect((width - mask.getWidth()) / 2,
                    (height - mask.getHeight()) / 2,
                    (width + mask.getWidth()) / 2, (height + mask.getHeight()) / 2);
            canvas.drawBitmap(mask, maskRect, dstRect, paint);
            paint.setXfermode(null);
            canvas.restoreToCount(layer);
        } else {
            return null;
        }
        return bitmap;
    }


    public static Bitmap getClothesImage(int width, int height, Bitmap bitmapResource) {
        Bitmap bitmap;
        if (bitmapResource != null) {
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            Rect rectF = new Rect(0, 0, width, height);
            Rect rectSource = new Rect(0, 0, bitmapResource.getWidth(), bitmapResource.getHeight());
            canvas.drawBitmap(bitmapResource, rectSource, rectF, paint);
            paint.setXfermode(null);
        } else {
            return null;
        }
        return bitmap;
    }
}

package com.example.yf.creatorshirt.mvp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.mvp.view.BaseView;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.CropUtils;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.PermissionUtil;

import java.io.File;

/**
 * Created by yangfang on 2017/10/11.
 */

public class CommonAvatarPresenter implements BasePresenter {

    public static final int REQUEST_CODE_ALBUM = 0;
    public static final int REQUEST_CODE_TAKE_PHOTO = 1;
    public static final int REQUEST_CODE_CROUP_PHOTO = 2;
    private Context mContext;
    private BaseActivity mActivity;
    private EditUserPopupWindow mPopupWindow;
    private Uri uri;
    private File file;
    private CommonAvatarContract.CommonAvatarView mView;

    @Override
    public void attachView(BaseView view) {
        mView = (CommonAvatarContract.CommonAvatarView) view;
    }

    @Override
    public void detachView(BaseView view) {
        if (mView != null) {
            mView = null;
        }
    }

    public CommonAvatarPresenter(Context context) {
        mContext = context;
        mActivity = (BaseActivity) context;
        file = new File(FileUtils.getCachePath(App.getInstance()), "photo.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要)
            uri = FileProvider.getUriForFile(App.getInstance(), BuildConfig.PROVIDER_CONFIG, file);
        }
    }


    public void setParams(float f) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        mActivity.getWindow().setAttributes(params);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    private View.OnClickListener itemsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.take_photo:
                    if (PermissionUtil.hasCameraPermission(mActivity)) {
                        uploadAvatarFromPhotoRequest();
                    }
                    break;
                case R.id.take_gallery:
                    uploadAvatarFromAlbumRequest();
                    break;
                case R.id.take_cancel:
                    mPopupWindow.dismiss();
                    break;
            }
        }
    };

    /**
     * camera
     */
    public void uploadAvatarFromPhotoRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        mActivity.startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    /**
     * album
     */
    private void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        mActivity.startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }

    public void callback(int requestCode, int resultCode, Intent data) {
        if (resultCode != -1) {
            return;
        }
        if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri newUri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newUri = Uri.parse("file:///" + CropUtils.getPath(mActivity, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                startPhotoZoom(newUri);
            } else {
                Toast.makeText(App.getInstance(), "没有得到相册图片", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            startPhotoZoom(uri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            uploadAvatarFromPhoto();
        }
    }

    /**
     * 裁剪拍照裁剪
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", 700);//图片输出大小
        intent.putExtra("outputY", 700);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        mActivity.startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }


    private void uploadAvatarFromPhoto() {
        compressAndUploadAvatar(file.getPath());

    }

    private void compressAndUploadAvatar(String fileSrc) {
        File cover = FileUtils.getSmallBitmap(mActivity, fileSrc);
        setImageFile(cover);
    }


    private void setImageFile(File cover) {
        if (cover != null) {
            mView.showSuccessAvatar(cover);
        } else {
            mView.showErrorMsg("选择失败");
        }
    }


    @SuppressLint("WrongConstant")
    public EditUserPopupWindow initPopupWindow() {
        mPopupWindow = new EditUserPopupWindow();
        mPopupWindow.setOnclickListener(itemsOnClickListener);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setParams(Constants.NORMAL_ALPHA);
            }
        });
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mPopupWindow;
    }
}

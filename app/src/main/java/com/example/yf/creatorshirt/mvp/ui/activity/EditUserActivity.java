package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.presenter.EditUserInfoPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.EditUserInfoContract;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.DialogPermission;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.utils.CircleAvatar;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.CropUtils;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.PermissionUtil;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesMark;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserActivity extends BaseActivity<EditUserInfoPresenter> implements EditUserInfoContract.EditUserInfoView {


    private static final int REQUEST_CODE_ALBUM = 0;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_CROUP_PHOTO = 2;

    @BindView(R.id.user_edit_avatar)
    ImageView mEditUser;
    @BindView(R.id.user_edit_name)
    EditText mEditName;
    @BindView(R.id.save_user)
    Button mSaveUser;
    @BindView(R.id.user_tv_filter)
    TextView mTextFilter;
    @BindView(R.id.ll_edit_user)
    RelativeLayout mRLUser;

    private Uri uri;
    private File file;
    private EditUserPopupWindow mPopupWindow;
    private String mAvatarUrl;

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getView() {
        return R.layout.activity_edit_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.getToken();
        file = new File(FileUtils.getCachePath(App.getInstance()), "photo.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要)
            uri = FileProvider.getUriForFile(App.getInstance(), BuildConfig.PROVIDER_CONFIG, file);
        }
    }

    @OnClick({R.id.user_edit_avatar, R.id.user_tv_filter, R.id.save_user})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_tv_filter:
                this.finish();
                break;
            case R.id.user_edit_avatar:
                initPopupWindow();
                mPopupWindow.showAtLocation(mRLUser, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                setParams(Constants.CHANGE_ALPHA);
                break;
            case R.id.save_user:
                if (isCheck()) {
                    mPresenter.setUserName(PhoneUtils.getTextString(mEditName));
                    mPresenter.saveUserInfo();
                }
                break;
        }
    }

    private boolean isCheck() {
        if (TextUtils.isEmpty(PhoneUtils.getTextString(mEditName))) {
            ToastUtil.showToast(this, "用户名为空", 0);
            return false;
        }
        if (TextUtils.isEmpty(mAvatarUrl)) {
            ToastUtil.showToast(this, "没有选择头像", 0);
            return false;
        }
        return true;
    }

    private void initPopupWindow() {
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

    }

    private View.OnClickListener itemsOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPopupWindow.dismiss();
            switch (v.getId()) {
                case R.id.take_photo:
                    if (PermissionUtil.hasCameraPermission(EditUserActivity.this)) {
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


    private void setParams(float f) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    public void showSuccessImage(String userAvatar) {
        mAvatarUrl = userAvatar;
        RequestOptions options = new RequestOptions()
                .error(R.mipmap.mm)
                .transform(new CircleAvatar(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this).load(userAvatar).apply(options).into(mEditUser);
    }

    @Override
    public void showSuccessSaveInfo(Integer status) {
        if (status == 1) {
            SharedPreferencesUtil.setUserName(PhoneUtils.getTextString(mEditName));
            ToastUtil.showToast(this, "设置信息成功", 0);
            this.finish();
        }
    }

    /**
     * camera
     */
    private void uploadAvatarFromPhotoRequest() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
    }

    /**
     * album
     */
    private void uploadAvatarFromAlbumRequest() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri newUri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                startPhotoZoom(newUri);
            } else {
                Toast.makeText(this, "没有得到相册图片", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            startPhotoZoom(uri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            uploadAvatarFromPhoto();
        }
    }

    private void uploadAvatarFromPhoto() {
        compressAndUploadAvatar(file.getPath());

    }

    private void compressAndUploadAvatar(String fileSrc) {
        File cover = FileUtils.getSmallBitmap(this, fileSrc);
        mPresenter.setImageFile(cover);
        mPresenter.saveUserAvatar();
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
        intent.putExtra("outputX", 400);//图片输出大小
        intent.putExtra("outputY", 400);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PermissionUtil.REQUEST_SHOWCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    uploadAvatarFromPhotoRequest();

                } else {
                    if (!SharedPreferencesMark.getHasShowCamera()) {
                        SharedPreferencesMark.setHasShowCamera(true);
                        new DialogPermission(this, "关闭摄像头权限影响扫描功能");

                    } else {
                        Toast.makeText(this, "未获取摄像头权限", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}

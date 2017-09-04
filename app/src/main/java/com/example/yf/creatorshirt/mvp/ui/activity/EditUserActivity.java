package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.BuildConfig;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.EditUserPopupWindow;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.CropUtils;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.PermissionUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class EditUserActivity extends BaseActivity {


    private static final int REQUEST_CODE_ALBUM = 0;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_CROUP_PHOTO = 2;

    @BindView(R.id.user_edit_avatar)
    ImageView mEditUser;
    @BindView(R.id.user_edit_name)
    TextView mEditName;
    @BindView(R.id.save_user)
    Button mSaveUser;
    @BindView(R.id.user_tv_filter)
    EditText mTextFilter;
    private EditUserPopupWindow mPopupWindow;
    @BindView(R.id.ll_edit_user)
    RelativeLayout mllUser;
    private Uri uri;
    private File file;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void initData() {
        super.initData();
        file = new File(FileUtils.getCachePath(this), "user-avatar.jpg");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要)
            uri = FileProvider.getUriForFile(this, BuildConfig.PROVIDER_CONFIG, file);
        }
    }

    @OnClick({R.id.user_edit_avatar, R.id.user_edit_name, R.id.user_tv_filter, R.id.save_user})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.user_tv_filter:
                startCommonActivity(this, null, MainActivity.class);
                break;
            case R.id.user_edit_avatar:
                initPopupWindow();
                mPopupWindow.showAtLocation(mllUser, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
                setParams(Constants.CHANGE_ALPHA);
                break;
            case R.id.user_edit_name:
                break;
            case R.id.save_user:
                break;
        }
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
            switch (v.getId()) {
                case R.id.take_photo:
                    if (PermissionUtil.hasCameraPermission(EditUserActivity.this)) {
                        uploadAvatarFromCamera();
                    }
                    break;
                case R.id.take_gallery:
                    uploadAvatarFromGallery();
                    break;
                case R.id.take_cancel:
                    mPopupWindow.dismiss();
                    break;
            }
        }
    };

    private void uploadAvatarFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
    }

    /**
     * 相机
     */
    private void uploadAvatarFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);
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
                //将相册的图片的路径转成uri
                newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                cropImageUri(newUri);
            } else {
                ToastUtil.showToast(this, "没有得到相册图片", 0);
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            cropImageUri(uri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            compressAndUploadAvatar(file.getPath());
        }
    }

    //设置头像和上传服务器
    private void compressAndUploadAvatar(String fileSrc) {
        final File cover = FileUtils.getSmallBitmap(this, fileSrc);
    }

    /**
     * 调用系统相册后进行裁剪图片
     *
     * @param orgUri
     */
    public void cropImageUri(Uri orgUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("outputX", Constants.USER_AVATAR_MAX_SIZE);//图片输出大小
        intent.putExtra("outputY", Constants.USER_AVATAR_MAX_SIZE);
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    private void setParams(float f) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        getWindow().setAttributes(params);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @Override
    protected int getView() {
        return R.layout.activity_edit_user;
    }
}

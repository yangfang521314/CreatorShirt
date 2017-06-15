package com.example.yf.creatorshirt.mvp.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.widget.sticker.BitmapStickerIcon;
import com.example.yf.creatorshirt.widget.sticker.DeleteIconEvent;
import com.example.yf.creatorshirt.widget.sticker.DrawableSticker;
import com.example.yf.creatorshirt.widget.sticker.FlipHorizontallyEvent;
import com.example.yf.creatorshirt.widget.sticker.Sticker;
import com.example.yf.creatorshirt.widget.sticker.StickerView;
import com.example.yf.creatorshirt.widget.sticker.TextSticker;
import com.example.yf.creatorshirt.widget.sticker.ZoomIconEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;


public class DetailDesignActivity extends BaseActivity {


    private static final int PERM_RQST_CODE = 110;
    @BindView(R.id.sticker_view)
    StickerView mStickerView;
    @BindView(R.id.choice)
    RecyclerView mRecyclerChoice;
    @BindView(R.id.choice_style)
    RecyclerView mRecyclerStyle;

    private int[] image = {R.mipmap.mm, R.mipmap.mm, R.mipmap.mm, R.mipmap.mm, R.mipmap.mm};
    private List<Integer> list = new ArrayList<>();

    @Override
    protected void inject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getView() {
        return R.layout.activity_detail_design;
    }

    @Override
    protected void initView() {
        setStickerView();
        //choice clothe style
        mRecyclerStyle.setVisibility(View.VISIBLE);
        mRecyclerStyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        for (int i = 0; i < image.length; i++) {
            list.add(image[i]);
        }
//        StyleAdapter styleAdapter = new StyleAdapter(this);
//        styleAdapter.setData(list);
//        mRecyclerStyle.setAdapter(styleAdapter);
        if (isCheckPermission()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_RQST_CODE);
        } else {
            loadSticker();
        }
    }

    private void loadSticker() {
        Drawable drawable = ContextCompat.getDrawable(App.getInstance(), R.mipmap.haizewang_23);
        mStickerView.addSticker(new DrawableSticker(drawable));
    }

    private void setStickerView() {
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.mipmap.delet), BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.mipmap.scale),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this, R.mipmap.flip),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        mStickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon));
        mStickerView.setLocked(false);
        mStickerView.setConstrained(true);
        mStickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                if (sticker instanceof TextSticker) {

                }
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {

            }
        });
    }

    private boolean isCheckPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERM_RQST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            loadSticker();
        }
    }


}

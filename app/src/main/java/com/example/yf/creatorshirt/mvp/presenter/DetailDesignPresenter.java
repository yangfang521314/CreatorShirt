package com.example.yf.creatorshirt.mvp.presenter;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity.COLOR;
import static com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity.MASK;
import static com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity.PATTERN;
import static com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity.SIGNATURE;


/**
 * Created by yangfang on 2017/8/19.
 */

public class DetailDesignPresenter extends RxPresenter<DetailDesignContract.DetailDesignView>
        implements DetailDesignContract.Presenter {

    private DataManager manager;
    //总样式和每一个具体的样式列表形成ArrayMap;
    private ArrayMap<String, List<DetailColorStyle>> mPatterData = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> mColorData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
    //总样式的集合
    private List<StyleBean> newList = new ArrayList<>();
    private ArrayList<VersionStyle> mTotalClothes;//衣服
    private List<DetailColorStyle> maskList = new ArrayList<>();
    private List<DetailColorStyle> patterList = new ArrayList<>();
    private List<DetailColorStyle> textColorList = new ArrayList<>();


    @Inject
    public DetailDesignPresenter(DataManager dataManager) {
        this.manager = dataManager;
    }

    @Override
    public void getDetailDesign(ArrayList<VersionStyle> mClothesList, final boolean b) {
        this.mTotalClothes = mClothesList;
        getNameDeign(b);

    }

    /**
     * 形成数组
     *
     * @param flag
     */
    private void getNameDeign(boolean flag) {
        StyleBean styleBean;
        String name = null;
        if (newList != null) {
            newList.clear();
        }
        if (clotheKey != null) {
            clotheKey.clear();
        }
        DetailColorStyle maskStyle = null;
        //贴图
        for (int i = 1; i < 48; i++) {
            maskStyle = new DetailColorStyle();
            maskStyle.setName("pattern_" + i);
            patterList.add(maskStyle);
        }
        //文字颜色
        for (int i = 0; i < 10; i++) {
            maskStyle = new DetailColorStyle();
            maskStyle.setValue("e23e2c");
            textColorList.add(maskStyle);
        }
        //遮罩图片
        for (int i = 0; i < 5; i++) {
            maskStyle = new DetailColorStyle();
            maskStyle.setImage(R.mipmap.quan);
            maskList.add(maskStyle);
        }
        if (flag) {
            if (mTotalClothes != null) {
                styleBean = new StyleBean();
                name = "颜色";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(COLOR);
                addColorData(name, mTotalClothes);
            }

            if (patterList != null) {
                styleBean = new StyleBean();
                name = "贴图";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(PATTERN);
                addPatternData(name, patterList);
            }
            if (maskList != null) {
                name = "遮罩";
                styleBean = new StyleBean();
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(MASK);
                addMaskData(name, maskList);
            }

            if (textColorList != null) {
                styleBean = new StyleBean();
                name = "文字";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(SIGNATURE);
                addTextData(name, textColorList);
            }
        } else {

//            if (mTotalClothes != null) {
//                styleBean = new StyleBean();
//                name = "颜色";
//                styleBean.setTitle(name);
//                newList.add(styleBean);
//                clotheKey.add(COLOR);
//                addColorData(name, mTotalClothes);
//            }

            if (patterList != null) {
                styleBean = new StyleBean();
                name = "贴图";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(PATTERN);
                addPatternData(name, patterList);
            }
            if (maskList != null) {
                name = "遮罩";
                styleBean = new StyleBean();
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(MASK);
                addMaskData(name, maskList);
            }
            if (textColorList != null) {
                styleBean = new StyleBean();
                name = "文字";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(SIGNATURE);
                addTextData(name, textColorList);
            }

        }
        mView.showSuccessData(newList, clotheKey, mColorData, mPatterData, mMaskData, mSignatureData);
    }

    private void addMaskData(String name, List<DetailColorStyle> fileList) {
        mMaskData.put(name, fileList);
    }

    /**
     * color data
     *
     * @param s
     * @param fileList
     */
    private void addColorData(String s, List<VersionStyle> fileList) {
        mColorData.put(s, fileList);
    }

    /**
     * clothes data
     *
     * @param title
     * @param fileList
     */
    private void addPatternData(String title, List<DetailColorStyle> fileList) {
        mPatterData.put(title, fileList);
    }


    /**
     * add text signature
     *
     * @param name
     * @param fileList
     */
    private void addTextData(String name, List<DetailColorStyle> fileList) {
        mSignatureData.put(name, fileList);
    }

    public void setImageMask(final Bitmap mask, final Bitmap source) {
        Observable.create(new ObservableOnSubscribe<Bitmap>() {
            @Override
            public void subscribe(ObservableEmitter<Bitmap> e) throws Exception {
                Bitmap bitmap = FileUtils.getMaskBitmap(Constants.WIDTH_MASK, Constants.HEIGHT_MASK, source, mask);
                e.onNext(bitmap);
            }
        }).compose(RxUtils.<Bitmap>rxObScheduleHelper())
                .subscribe(new CommonObserver<Bitmap>(mView) {
                    @Override
                    public void onNext(Bitmap bitmap) {
                        mView.showMaskView(bitmap);
                    }
                });
    }

}

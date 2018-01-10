package com.example.yf.creatorshirt.mvp.presenter;

import android.graphics.Bitmap;
import android.support.v4.util.ArrayMap;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailCommonData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.FileUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.MediaType;
import okhttp3.RequestBody;

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
    private DetailCommonData mDetailStyleFrontData;//现在只拿patter数据
    //    private DetailCommonData mDetailStyleBackData;//背面数据
    //总样式和每一个具体的样式列表形成ArrayMap;
    private ArrayMap<String, List<DetailPatterStyle>> mPatterData = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> mColorData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mMaskData = new ArrayMap<>();
    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
    //总样式的集合
    private List<StyleBean> newList = new ArrayList<>();
    private ArrayList<VersionStyle> mTotalClothes;
    private List<DetailColorStyle> maskList = new ArrayList<>();


    @Inject
    public DetailDesignPresenter(DataManager dataManager) {
        this.manager = dataManager;
    }

    @Override
    public void getDetailDesign(String gender, String type, ArrayList<VersionStyle> mClothesList, final boolean b) {
        this.mTotalClothes = mClothesList;
        JSONObject root = new JSONObject();
        final JSONObject request = new JSONObject();
        try {
            request.put("Gender", gender);
            request.put("Typeversion", type);
            root.put("baseInfo", request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), root.toString());
        addSubscribe(manager.getDetailDesign(requestBody)
                .compose(RxUtils.<HttpResponse<DetailStyleBean>>rxSchedulerHelper())
                .compose(RxUtils.<DetailStyleBean>handleResult())
                .subscribeWith(new CommonSubscriber<DetailStyleBean>(mView) {
                    @Override
                    public void onNext(DetailStyleBean detailStyleBean) {
                        if (detailStyleBean != null)
                            mView.showSuccessData(detailStyleBean);
                        showSucessData(detailStyleBean, b);
                    }
                })

        );

    }

    private void showSucessData(DetailStyleBean detailStyleBean, boolean flag) {
        if (detailStyleBean != null) {
            if (detailStyleBean.getData() == null)
                return;
            if (detailStyleBean.getData().getA() == null) {
                return;
            }
            mDetailStyleFrontData = detailStyleBean.getData().getA();
            getNameDeign(mDetailStyleFrontData, flag);
        }
    }

    /**
     * 形成数组
     *
     * @param mData
     * @param flag
     */
    public void getNameDeign(DetailCommonData mData, boolean flag) {
        StyleBean styleBean;
        String name = null;
        if (newList != null) {
            newList.clear();
        }
        if (clotheKey != null) {
            clotheKey.clear();
        }
        DetailColorStyle maskStyle = null;
        if (flag) {
            for (int i = 0; i < 5; i++) {
                maskStyle = new DetailColorStyle();
                maskStyle.setImage(R.mipmap.quan);
                maskList.add(maskStyle);
            }

            if (mTotalClothes != null) {
                styleBean = new StyleBean();
                name = "颜色";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(COLOR);
                addColorData(name, mTotalClothes);
            }

            if (mData.getPattern() != null) {
                if (mData.getPattern().getFileList() != null && mData.getPattern().getFileList().size() != 0) {
                    styleBean = new StyleBean();
                    name = mData.getPattern().getName();
                    styleBean.setTitle(name);
                    newList.add(styleBean);
                    clotheKey.add(PATTERN);
                    addPatternData(name, mData.getPattern().getFileList());
                }
            }
            if (maskList != null) {
                name = "mask";
                styleBean = new StyleBean();
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(MASK);
                addMaskData(name, maskList);
            }

            if (mData.getText() != null) {
                if (mData.getText().getFileList() != null) {
                    if (mData.getText().getFileList().size() != 0) {
                        styleBean = new StyleBean();
                        name = mData.getText().getName();
                        styleBean.setTitle(name);
                        newList.add(styleBean);
                        clotheKey.add(SIGNATURE);
                        addTextData(name, mData.getText().getFileList());
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                maskStyle = new DetailColorStyle();
                maskStyle.setImage(R.mipmap.quan);
                maskList.add(maskStyle);
            }

            if (mTotalClothes != null) {
                styleBean = new StyleBean();
                name = "颜色";
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(COLOR);
                addColorData(name, mTotalClothes);
            }

            if (mData.getPattern() != null) {
                if (mData.getPattern().getFileList() != null && mData.getPattern().getFileList().size() != 0) {
                    styleBean = new StyleBean();
                    name = mData.getPattern().getName();
                    styleBean.setTitle(name);
                    newList.add(styleBean);
                    clotheKey.add(PATTERN);
                    addPatternData(name, mData.getPattern().getFileList());
                }
            }
            if (maskList != null) {
                name = "mask";
                styleBean = new StyleBean();
                styleBean.setTitle(name);
                newList.add(styleBean);
                clotheKey.add(MASK);
                addMaskData(name, maskList);
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
    private void addPatternData(String title, List<DetailPatterStyle> fileList) {
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
                Bitmap bitmap = FileUtils.getMaskBitmap(Constants.WIDTH_MASK,Constants.HEIGHT_MASK,source,mask);
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

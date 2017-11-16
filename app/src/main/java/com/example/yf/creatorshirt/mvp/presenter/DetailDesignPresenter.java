package com.example.yf.creatorshirt.mvp.presenter;

import android.support.v4.util.ArrayMap;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailColorStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailCommonData;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailOtherStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailPatterStyle;
import com.example.yf.creatorshirt.mvp.model.detaildesign.DetailStyleBean;
import com.example.yf.creatorshirt.mvp.model.detaildesign.StyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailDesignContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.example.yf.creatorshirt.mvp.ui.activity.NewsDesignActivity.COLOR;
import static com.example.yf.creatorshirt.mvp.ui.activity.NewsDesignActivity.MASK;
import static com.example.yf.creatorshirt.mvp.ui.activity.NewsDesignActivity.PATTERN;


/**
 * Created by yangfang on 2017/8/19.
 */

public class DetailDesignPresenter extends RxPresenter<DetailDesignContract.DetailDesignView>
        implements DetailDesignContract.Presenter {

    private DataManager manager;
    private DetailCommonData mDetailStyleFrontData;//正面数据
    private DetailCommonData mDetailStyleBackData;//背面数据
    //总样式和每一个具体的样式列表形成ArrayMap;
    private ArrayMap<String, List<DetailOtherStyle>> NewDetailData = new ArrayMap<>();
    private ArrayMap<String, List<DetailPatterStyle>> mPatternData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mColorData = new ArrayMap<>();
    private ArrayMap<String, List<DetailColorStyle>> mSignatureData = new ArrayMap<>();
    private List<String> clotheKey = new ArrayList<>();//具体样式的字段名
    //总样式的集合
    List<StyleBean> newList = new ArrayList<>();
    private boolean isFont;
    private int[] maskList = {R.mipmap.quan};
    private boolean isFront;


    @Inject
    public DetailDesignPresenter(DataManager dataManager) {
        this.manager = dataManager;
    }

    @Override
    public void getDetailDesign(String gender, String type) {
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
                        showSucessData(detailStyleBean);
                    }
                })

        );
    }

    private void showSucessData(DetailStyleBean detailStyleBean) {
        if (detailStyleBean != null) {
            if (detailStyleBean.getData() == null)
                return;
            if (detailStyleBean.getData().getA() == null) {
                return;
            }
            if (detailStyleBean.getData().getB() == null) {
                return;
            }
            mDetailStyleBackData = detailStyleBean.getData().getB();
            mDetailStyleFrontData = detailStyleBean.getData().getA();
            getNameDeign(mDetailStyleFrontData, "front");
        }
    }

    /**
     * 形成数组
     *
     * @param mData
     * @param flag
     */
    public void getNameDeign(DetailCommonData mData, String flag) {
        StyleBean styleBean;
        String name;
        if (newList != null) {
            newList.clear();
        }
        if (NewDetailData != null) {
            NewDetailData.clear();
        }
        if (clotheKey != null) {
            clotheKey.clear();
        }
        List<DetailColorStyle> fileList = new ArrayList<>();
        DetailColorStyle colorStyle;
        for (int i = 0; i < 5; i++) {
            colorStyle = new DetailColorStyle();
            colorStyle.setImage(R.mipmap.quan);
            fileList.add(colorStyle);
        }
        if (flag.equals("front")) {
            if (mData.getColor() != null) {
                if (mData.getColor().getFileList() != null && mData.getColor().getFileList().size() != 0) {
                    styleBean = new StyleBean();
                    name = mData.getColor().getName();
                    styleBean.setTitle(name);
                    newList.add(styleBean);
                    clotheKey.add(COLOR);
                    addColorData(name, mData.getColor().getFileList());
                }
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
            if (mData.getText() != null) {
                if (mData.getText().getFileList() != null) {
                    if (mData.getText().getFileList().size() != 0) {
                        styleBean = new StyleBean();
                        name = mData.getText().getName();
                        styleBean.setTitle(name);
                        newList.add(styleBean);
                        clotheKey.add(MASK);
                        addTextData(name, fileList);
                    }
                }
            }
        }
//
//        if (flag.equals("back")) {
//
//
//            if (mData.getPattern() != null) {
//                if (mData.getPattern().getFileList() != null && mData.getPattern().getFileList().size() != 0) {
//                    styleBean = new StyleBean();
//                    name = mData.getPattern().getName();
//                    styleBean.setTitle(name);
//                    newList.add(styleBean);
//                    clotheKey.add(PATTERN);
//                    addPatternData(name, mData.getPattern().getFileList());
//                }
//            }
//            if (mData.getText() != null) {
//                if (mData.getText().getFileList() != null) {
//                    if (mData.getText().getFileList().size() != 0) {
//                        styleBean = new StyleBean();
//                        name = mData.getText().getName();
//                        styleBean.setTitle(name);
//                        newList.add(styleBean);
//                        clotheKey.add(SIGNATURE);
//                        addTextData(name, mData.getText().getFileList());
//                    }
//                }
//            }
//        }
        mView.showSuccessData(newList, clotheKey, mColorData, mPatternData, mSignatureData);
    }

    /**
     * color data
     *
     * @param s
     * @param fileList
     */
    private void addColorData(String s, List<DetailColorStyle> fileList) {
        mColorData.put(s, fileList);
    }

    /**
     * patter data
     *
     * @param title
     * @param fileList
     */
    private void addPatternData(String title, List<DetailPatterStyle> fileList) {
        mPatternData.put(title, fileList);
    }

    /**
     * ornamet/arm/neck
     *
     * @param title
     * @param fileList
     */
    private void addNewData(String title, List<DetailOtherStyle> fileList) {
        if (!NewDetailData.containsKey(title) && fileList != null) {
            NewDetailData.put(title, fileList);
        }
    }

    private void addTextData(String name, List<DetailColorStyle> fileList) {
        mSignatureData.put(name, fileList);
    }

    public void setFront(boolean b) {
        isFront = b;
    }

    public void initShowStyle() {
        String neck = null;
        String arm = null;
        String ornament = null;

        if (mDetailStyleBackData.getNeck().getFileList() != null && mDetailStyleBackData.getNeck().getFileList().size() != 0) {
            neck = mDetailStyleBackData.getNeck().getFileList().get(0).getFile();
        }
        if (mDetailStyleBackData.getArm().getFileList().size() != 0 && mDetailStyleBackData.getArm().getFileList() != null) {
            arm = mDetailStyleBackData.getArm().getFileList().get(0).getFile();
        }
        if (mDetailStyleBackData.getOrnament().getFileList().size() != 0 && mDetailStyleBackData.getOrnament().getFileList().size() != 0) {
            ornament = mDetailStyleBackData.getOrnament().getFileList().get(0).getFile();
        }
        mView.showBackData(neck, arm, ornament);
    }

    public void getFrontDeign(String front) {
        if (mDetailStyleFrontData != null) {
            getNameDeign(mDetailStyleFrontData, front);
        }
    }

    public void getBackDeign(String back) {
        if (mDetailStyleBackData != null) {
            getNameDeign(mDetailStyleBackData, back);
        }
    }
}

package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.model.PraiseEntity;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DetailClothesContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by yang on 10/08/2017.
 */

public class DetailClothesPresenter extends RxPresenter<DetailClothesContract.DetailClothesView> implements DetailClothesContract.Presenter {

    private DataManager manager;

    @Inject
    public DetailClothesPresenter(DataManager manager) {
        this.manager = manager;
    }


    @Override
    public void requestOrdersPraise(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orderId", id);
        addSubscribe(manager.requestOrdersPraise(UserInfoManager.getInstance().getLoginResponse().getToken(),
                GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<Integer>>rxSchedulerHelper())
                .compose(RxUtils.<Integer>handleResult())
                .subscribeWith(new CommonSubscriber<Integer>(mView) {
                    @Override
                    public void onNext(Integer integer) {
                        mView.showPraise(integer);
                    }
                })
        );
    }

    public void OrderPraise(int id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("orderId", id);
        addSubscribe(manager.OrderPraise(UserInfoManager.getInstance().getLoginResponse().getToken(),
                GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<PraiseEntity>>rxSchedulerHelper())
                .compose(RxUtils.<PraiseEntity>handleResult())
                .subscribeWith(new CommonSubscriber<PraiseEntity>(mView) {
                    @Override
                    public void onNext(PraiseEntity integer) {
                        if(integer != null)
                        mView.addPraiseNum(integer);
                    }
                })
        );
    }

    public void saveOrdersFromShare(int id, String height,String textUre) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", String.valueOf(id));
        map.put("height",height);
        map.put("texture",textUre);
        addSubscribe(manager.saveOrdersFromShare(UserInfoManager.getInstance().getLoginResponse().getToken(),
                GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<OrderType>>rxSchedulerHelper())
                .compose(RxUtils.<OrderType>handleResult())
                .subscribeWith(new CommonSubscriber<OrderType>(mView,"订单生成出错") {
                    @Override
                    public void onNext(OrderType orderType) {
                        if(orderType != null)
                        mView.showSuccessOrder(orderType);
                    }
                })
        );

    }

    public void getTexture(BombStyleBean mBombStyleBean) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Gender", mBombStyleBean.getGender());
            jsonObject.put("Typeversion", mBombStyleBean.getBaseId());
            addSubscribe(manager.getTextUre(GsonUtils.getGson(jsonObject))
                    .compose(RxUtils.<HttpResponse<List<TextureEntity>>>rxSchedulerHelper())
                    .compose(RxUtils.<List<TextureEntity>>handleResult())
                    .subscribeWith(new CommonSubscriber<List<TextureEntity>>(mView) {
                        @Override
                        public void onNext(List<TextureEntity> textureEntity) {
                            if(textureEntity != null) {
                                if (textureEntity.size() != 0)
                                mView.showSuccessTextUre(textureEntity);
                            }
                        }
                    }));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

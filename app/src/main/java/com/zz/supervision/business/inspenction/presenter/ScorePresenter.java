package com.zz.supervision.business.inspenction.presenter;


import com.zz.supervision.bean.ImageBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

/**
 * Created by 77 on 2018/8/8.
 */

public class ScorePresenter extends MyBasePresenterImpl<Contract.IGetScoreView> implements Contract.IsetScorePresenter {

    public ScorePresenter(Contract.IGetScoreView view) {
        super(view);
    }

    @Override
    public void getData(String url, Map<String, Object> map) {
        RxNetUtils.request(getApi(ApiService.class).getFoodSuperviseList(map), new RequestObserver<JsonT<List<SuperviseBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<SuperviseBean>> jsonT) {
                view.showFoodSuperviseList(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<SuperviseBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void submitData(Map<String, Object> map) {
//        RxNetUtils.request(getApi(ApiService.class).poatCompanyInfo(map), new RequestObserver<JsonT>(this) {
//            @Override
//            protected void onSuccess(JsonT jsonT) {
//                view.showResult();
//                view.showToast(jsonT.getMessage());
//            }
//            @Override
//            protected void onFail2(JsonT stringJsonT) {
//                super.onFail2(stringJsonT);
//                view.showToast(stringJsonT.getMessage());
//            }
//        },mDialog);
    }
}


package com.zz.supervision.business.equipment.mvp.presenter;


import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PipePartBean;
import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 77 on 2018/8/8.
 */

public class PipeAddPresenter extends MyBasePresenterImpl<Contract.IGetPipeAddView> implements Contract.IsetPipeAddPresenter {

    public PipeAddPresenter(Contract.IGetPipeAddView view) {
        super(view);
    }

    @Override
    public void getData( String id) {
        RxNetUtils.request(getApi(ApiService.class).getByPartId(id), new RequestObserver<JsonT<PipePartBean>>(this) {
            @Override
            protected void onSuccess(JsonT<PipePartBean> jsonT) {
                view.showPipeInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<PipePartBean> stringJsonT) {
                super.onFail2(stringJsonT);
                view.showPipeInfo(null);
            }
        }, mDialog);
    }

    @Override
    public void submitData(Map<String,Object> map) {

        if (map.containsKey("id")){
            RxNetUtils.request(getApi(ApiService.class).addPressurepipePart(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showSubmitResult((String) jsonT.getData());
//                    view.showToast(jsonT.getMessage());
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);
        }
        else {
            RxNetUtils.request(getApi(ApiService.class).editPressurepipePart(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showSubmitResult((String) jsonT.getData());
//                    view.showToast(jsonT.getMessage());
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);
        }





    }


}


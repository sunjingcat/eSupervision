package com.zz.supervision.business.equipment.mvp.presenter;


import android.text.TextUtils;

import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DeviceCheck;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.OrganizationBean;
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

public class CheckAddPresenter extends MyBasePresenterImpl<Contract.IGetCheckAddView> implements Contract.IsetCheckAddPresenter {

    public CheckAddPresenter(Contract.IGetCheckAddView view) {
        super(view);
    }

    @Override
    public void getData(String deviceType, String id) {
        String url = deviceType.equals("3")?"getByPartId":"getByDeviceId";
        RxNetUtils.request(getApi(ApiService.class).getCheckInfo(url,id), new RequestObserver<JsonT<DeviceCheck>>(this) {
            @Override
            protected void onSuccess(JsonT<DeviceCheck> jsonT) {
                view.showCheckInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<DeviceCheck> stringJsonT) {
                super.onFail2(stringJsonT);
                view.showCheckInfo(null);
            }
        }, mDialog);
    }

    @Override
    public void getDicts(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", type);
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                view.showDicts(type, jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getCheckCategory(String type) {

        RxNetUtils.request(getApi(ApiService.class).getCheckCategory(type), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                view.showCheckCategory(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getOrganizationalUnit() {

        RxNetUtils.request(getApi(ApiService.class).selectorganizationalUnit(), new RequestObserver<JsonT<List<DictBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<DictBean>> jsonT) {
                view.showOrganizationalUnit( jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<DictBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void beforeAddDeviceCheck(String deviceType,String deviceId) {
        if (deviceType.equals("3")) {
            RxNetUtils.request(getApi(ApiService.class).beforeAddDeviceCheck(deviceId), new RequestObserver<JsonT<List<BeforeAddDeviceCheck>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<BeforeAddDeviceCheck>> jsonT) {
                    view.showBeforeAddDeviceCheck(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<BeforeAddDeviceCheck>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        }else {
            RxNetUtils.request(getApi(ApiService.class).beforeAddDeviceCheck(deviceId), new RequestObserver<JsonT<List<BeforeAddDeviceCheck>>>(this) {
                @Override
                protected void onSuccess(JsonT<List<BeforeAddDeviceCheck>> jsonT) {
                    view.showBeforeAddDeviceCheck(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<List<BeforeAddDeviceCheck>> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, mDialog);
        }
    }


    @Override
    public void submitData(DeviceCheck map) {
        if (map.getDeviceType().equals("3")) {
            RxNetUtils.request(getApi(ApiService.class).addPressurepipePartCheck(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showSubmitResult(jsonT.getData().toString());
//                    view.showToast(jsonT.getMessage());
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);


        } else {
            RxNetUtils.request(getApi(ApiService.class).putTzsbDeviceCheck(map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    view.showSubmitResult( jsonT.getData().toString());
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


package com.zz.supervision.business.equipment.mvp.presenter;

import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
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

public class AccessoryAddPresenter extends MyBasePresenterImpl<Contract.IGetAccessoryAddView> implements Contract.IsetAccessoryAddPresenter {

    public AccessoryAddPresenter(Contract.IGetAccessoryAddView view) {
        super(view);
    }

    @Override
    public void getData( String id) {
        RxNetUtils.request(getApi(ApiService.class).getAccessoryInfo(id), new RequestObserver<JsonT<AccessoryBean>>(this) {
            @Override
            protected void onSuccess(JsonT<AccessoryBean> jsonT) {
                view.showAccessoryInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<AccessoryBean> stringJsonT) {
                super.onFail2(stringJsonT);
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
    public void postImage(int position, String file) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(file), new RequestObserver<JsonT<String>>(this) {
            @Override
            protected void onSuccess(JsonT<String> data) {
                if (data.isSuccess()) {
                    view.showPostImage(position, data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<String> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);

    }

    @Override
    public void uploadEquipmentImgs( String id, String files) {

        RxNetUtils.request(getApi(ApiService.class).uploadCompanyImgs("tzsbDeviceAccessory", id, files), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT data) {
                if (data.isSuccess()) {
                    view.showResult();
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);

    }

    @Override
    public void getImage( String modelId) {
        RxNetUtils.request(getApi(ApiService.class).getImageBase64("tzsbDeviceAccessory", modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    view.showImage(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                view.showToast(userInfoJsonT.getMessage());
            }
        }, mDialog);
    }

    @Override
    public void submitData(Map<String, Object> map) {
        if (map.containsKey("id")) {
            RxNetUtils.request(getApi(ApiService.class).editAccessoryInfo(map), new RequestObserver<JsonT<String>>(this) {
                @Override
                protected void onSuccess(JsonT<String> jsonT) {
                    view.showSubmitResult(jsonT.getData());
//                    view.showToast(jsonT.getMessage());
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);


        } else {
            RxNetUtils.request(getApi(ApiService.class).postAccessory(map), new RequestObserver<JsonT<String>>(this) {
                @Override
                protected void onSuccess(JsonT<String> jsonT) {
                    view.showSubmitResult(jsonT.getData());
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

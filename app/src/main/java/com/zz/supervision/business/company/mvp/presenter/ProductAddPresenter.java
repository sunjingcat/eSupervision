package com.zz.supervision.business.company.mvp.presenter;


import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.CityBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.business.company.mvp.Contract;
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

public class ProductAddPresenter extends MyBasePresenterImpl<Contract.IGetProductAddView> implements Contract.IsetProductAddPresenter {

    public ProductAddPresenter(Contract.IGetProductAddView view) {
        super(view);
    }

    @Override
    public void getData(String id) {

        RxNetUtils.request(getApi(ApiService.class).getZdgypProductInfo(id), new RequestObserver<JsonT<ProductBean>>(this) {
            @Override
            protected void onSuccess(JsonT<ProductBean> jsonT) {
                view.showProductInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<ProductBean> stringJsonT) {
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
    public void getImage(String modelId) {

        RxNetUtils.request(getApi(ApiService.class).getImageBase64("zdgypProduct", modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
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

            RxNetUtils.request(getApi(ApiService.class).editZdgypProductInfo(map), new RequestObserver<JsonT<String>>(this) {
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

            RxNetUtils.request(getApi(ApiService.class).postZdgypProductInfo(map), new RequestObserver<JsonT<String>>(this) {
                @Override
                protected void onSuccess(JsonT<String> jsonT) {
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

    @Override
    public void uploadCompanyImgs(String id, String files) {
        RxNetUtils.request(getApi(ApiService.class).uploadCompanyImgs("zdgypProductInfo", id, files), new RequestObserver<JsonT>(this) {
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


}


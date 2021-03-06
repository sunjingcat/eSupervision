package com.zz.supervision.business.company.mvp.presenter;


import com.google.gson.Gson;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.MyBasePresenterImpl;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by 77 on 2018/8/8.
 */

public class CompanyAddPresenter extends MyBasePresenterImpl<Contract.IGetCompanyAddView> implements Contract.IsetCompanyAddPresenter {

    public CompanyAddPresenter(Contract.IGetCompanyAddView view) {
        super(view);
    }

    @Override
    public void getData(String url) {
        RxNetUtils.request(getApi(ApiService.class).getCompanyInfo(url), new RequestObserver<JsonT<CompanyBean>>(this) {
            @Override
            protected void onSuccess(JsonT<CompanyBean> jsonT) {
                view.showCompanyInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<CompanyBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getBusinessType(Map<String, Object> map) {
        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                view.showBusinessType(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getBusiness2Type(Map<String, Object> map) {
        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                view.showBusiness2Type(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void getCompanyType(Map<String, Object> map) {
        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                view.showCompanyType(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, mDialog);
    }

    @Override
    public void postImage(int position,String file) {

        RxNetUtils.request(getApi(ApiService.class).uploadImg(file), new RequestObserver<JsonT<String>>(this) {
            @Override
            protected void onSuccess(JsonT<String> data) {
                if (data.isSuccess()) {
                    view.showPostImage(position,data.getData());
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
    public void getImage(String type, String modelId) {
        RxNetUtils.request(getApi(ApiService.class).getImageBase64(type, modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
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
            RxNetUtils.request(getApi(ApiService.class).editCompanyInfo(map), new RequestObserver<JsonT<String>>(this) {
                @Override
                protected void onSuccess(JsonT<String> jsonT) {
                    view.showSubmitResult( jsonT.getData());
//                    view.showToast(jsonT.getMessage());
                }

                @Override
                protected void onFail2(JsonT stringJsonT) {
                    super.onFail2(stringJsonT);
                    view.showToast(stringJsonT.getMessage());
                }
            }, mDialog);
        } else {
            RxNetUtils.request(getApi(ApiService.class).postCompanyInfo(map), new RequestObserver<JsonT<String>>(this) {
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
    public void uploadCompanyImgs(String id,String files) {

        RxNetUtils.request(getApi(ApiService.class).uploadCompanyImgs(id,files), new RequestObserver<JsonT>(this) {
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


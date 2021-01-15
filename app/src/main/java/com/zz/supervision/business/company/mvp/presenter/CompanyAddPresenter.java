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

import java.util.HashMap;
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
    public void getData(String type,String id) {
        String url = "companyInfo";
        if (type.equals("3")) {
            url = "ypCompanyInfo";
        }else if (type.equals("4")) {
            url = "ylqxCompanyInfo";
        }
        RxNetUtils.request(getApi(ApiService.class).getCompanyInfo(url,id), new RequestObserver<JsonT<CompanyBean>>(this) {
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
    public void getImage(String type, String modelId) {
        String url = "company";
        if (type.equals("2")) {
            url = "coldstorage";
        }else if (type.equals("3")) {
            url = "ypCompany";
        }else if (type.equals("4")) {
            url = "ylqxCompany";
        }
        RxNetUtils.request(getApi(ApiService.class).getImageBase64(url, modelId), new RequestObserver<JsonT<List<ImageBack>>>(this) {
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
            if (map.containsKey("companyType") && (map.get("companyType").equals("3") || map.get("companyType").equals("4"))) {
                String url = "ylqxCompanyInfo";
                if (map.get("companyType").equals("3")) {
                    url = "ypCompanyInfo";
                }
                RxNetUtils.request(getApi(ApiService.class).editYaoCompanyInfo(url,map), new RequestObserver<JsonT<String>>(this) {
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
            }else {
                RxNetUtils.request(getApi(ApiService.class).editCompanyInfo(map), new RequestObserver<JsonT<String>>(this) {
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

        } else {
            if (map.containsKey("companyType") && (map.get("companyType").equals("3") || map.get("companyType").equals("4"))) {
                String url = "ylqxCompanyInfo";
                if (map.get("companyType").equals("3")) {
                    url = "ypCompanyInfo";
                }
                RxNetUtils.request(getApi(ApiService.class).postYaoCompanyInfo(url, map), new RequestObserver<JsonT<String>>(this) {
                    @Override
                    protected void onSuccess(JsonT<String> jsonT) {
                        view.showSubmitResult((String) jsonT.getData());
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
    }

    @Override
    public void uploadCompanyImgs(String type, String id, String files) {
        String url = "companyInfo";
        if (type.equals("3")) {
            url = "ypCompanyInfo";
        }else if (type.equals("4")) {
            url = "ylqxCompanyInfo";
        }
        RxNetUtils.request(getApi(ApiService.class).uploadCompanyImgs(url, id, files), new RequestObserver<JsonT>(this) {
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


package com.zz.supervision.business.company.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.UserBasicBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetCompanyAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);

        void getData(String url,Map<String, Object> map);

        void uploadImage(List<MultipartBody.Part> imgs);
    }

    public interface IGetCompanyAddView extends BaseView {
        void showPatrolInfo(CompanyBean data);

        void showResult();

        void showImage(String url);
    }

}
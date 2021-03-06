package com.zz.supervision.business.company.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.UserBasicBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetCompanyAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String url);

        void getBusinessType(Map<String, Object> map);
        void getBusiness2Type(Map<String, Object> map);
        void getCompanyType(Map<String, Object> map);

        void postImage(int position,String files);
        void uploadCompanyImgs(String id,String files);

        void getImage(String type,String modelId);
    }

    public interface IGetCompanyAddView extends BaseView {
        void showCompanyInfo(CompanyBean data);

        void showSubmitResult(String id);

        void showResult();
        void showPostImage(int position,String id);

        void showBusinessType(List<BusinessType> list);
        void showBusiness2Type(List<BusinessType> list);
        void showCompanyType(List<BusinessType> list);

        void showImage(List<ImageBack> list);

    }

}

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

        void postImage(String id,String files,List<Integer> ids);

        void getImage(String type,String modelId);
    }

    public interface IGetCompanyAddView extends BaseView {
        void showCompanyInfo(CompanyBean data);

        void showSubmitResult(String id);

        void showResult();
        void showPostImage();

        void showBusinessType(List<BusinessType> list);

        void showImage(List<ImageBack> list);

    }

}

package com.zz.supervision.business.inspenction;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetSupervisePresenter extends BasePresenter {
        void submitData(Map<String, Object> map);

        void getData(String url);

    }

    public interface IGetSuperviseView extends BaseView {
        void showFoodSuperviseList(List<SuperviseBean> data);

        void showResult();
    }

}

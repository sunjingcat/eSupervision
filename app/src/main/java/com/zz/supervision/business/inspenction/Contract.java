package com.zz.supervision.business.inspenction;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetScorePresenter extends BasePresenter {
        void submitData(Map<String, Object> map);

        void getData(String url, Map<String, Object> map);

    }

    public interface IGetScoreView extends BaseView {
        void showFoodSuperviseList(List<SuperviseBean> data);

        void showResult();
    }

}

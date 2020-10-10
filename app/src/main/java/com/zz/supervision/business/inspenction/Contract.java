package com.zz.supervision.business.inspenction;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;

public class Contract {
    public interface IsetSupervisePresenter extends BasePresenter {
        void submitData(String url,String id,ArrayList<SuperviseBean.PostBean> spxsInspectionPoints);

        void submitReData(String url,String id, ArrayList<SuperviseBean.PostBean> spxsInspectionPoints);

        void getData(String url);
        void delete(String url,String id);

    }

    public interface IGetSuperviseView extends BaseView {
        void showFoodSuperviseList(List<SuperviseBean> data);

        void showReResult(SuperviseBean.ResposeConfirmBean resposeBean);

        void showResult(SuperviseBean.ResposeBean resposeBean);
        void showDelete();
    }

}

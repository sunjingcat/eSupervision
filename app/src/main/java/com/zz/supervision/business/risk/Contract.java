package com.zz.supervision.business.risk;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.ArrayList;
import java.util.List;

public class Contract {
    public interface IsetRiskSupervisePresenter extends BasePresenter {
        void submitData(String url,String id, RiskSuperviseBean.PostBean spxsInspectionPoints);

        void submitReData(String url,String id, RiskSuperviseBean.PostBean spxsInspectionPoints);

        void getData(String url);

        void delete(String url,String id);

    }

    public interface IGetRiskSuperviseView extends BaseView {
        void showSuperviseList(RiskSuperviseBean data);

        void showReResult(SuperviseBean.ResposeConfirmBean resposeBean);

        void showResult(Integer resposeBean);
        void showDelete();
    }

}

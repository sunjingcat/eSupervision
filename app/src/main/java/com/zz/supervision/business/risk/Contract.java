package com.zz.supervision.business.risk;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.ArrayList;
import java.util.List;

public class Contract {
    public interface IsetRiskSupervisePresenter extends BasePresenter {
        void submitData(String id, RiskSuperviseBean.PostBean spxsInspectionPoints);

        void submitReData(String id, RiskSuperviseBean.PostBean spxsInspectionPoints);

        void getData(String url);

    }

    public interface IGetRiskSuperviseView extends BaseView {
        void showSuperviseList(RiskSuperviseBean data);

        void showReResult(SuperviseBean.ResposeConfirmBean resposeBean);

        void showResult(SuperviseBean.ResposeBean resposeBean);
    }

}

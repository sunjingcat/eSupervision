package com.zz.supervision.business.main.mvp;

import com.zz.supervision.bean.MapListBean;
import com.zz.supervision.bean.UserBasicBean;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;

import java.util.List;
import java.util.Map;

public class Contract {


    public interface IGetMapView extends BaseView {
        void showResult(List<MapListBean> listBeans);
        void showUserInfo(UserBasicBean userInfo);
        void showError();

    }
}

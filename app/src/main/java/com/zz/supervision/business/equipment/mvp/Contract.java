package com.zz.supervision.business.equipment.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;

import java.util.List;
import java.util.Map;

public class Contract {
    public interface IsetEquipmentAddPresenter extends BasePresenter {
        void submitData(Map<String, Object> map);
        void getData(String url);

        void getDicts(String type);
        void getDeviceType();


        void postImage(int position,String files);
        void uploadEquipmentImgs(String id,String files);

        void getImage(String modelId);
    }

    public interface IGetEquipmentAddView extends BaseView {
        void showEquipmentInfo(EquipmentBean data);

        void showSubmitResult(String id);

        void showResult();
        void showPostImage(int position,String id);

        void showDicts(String type,List<BusinessType> list);
        void showDeviceType(List<DictBean> list);

        void showImage(List<ImageBack> list);

    }

}

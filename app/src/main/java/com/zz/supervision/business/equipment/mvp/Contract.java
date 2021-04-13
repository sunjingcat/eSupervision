package com.zz.supervision.business.equipment.mvp;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.ui.mvp.BaseView;
import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DeviceCheck;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PipePartBean;

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
    public interface IsetCheckAddPresenter extends BasePresenter {
        void submitData(DeviceCheck map);
        void getData(String id);
        void getDicts(String type);
        void getOrganizationalUnit();
        void beforeAddDeviceCheck(String deviceId);

    }

    public interface IGetCheckAddView extends BaseView {
        void showCheckInfo(DeviceCheck data);
        void showSubmitResult(String id);
        void showResult();
        void showDicts(String type,List<BusinessType> list);
        void showOrganizationalUnit(List<DictBean> list);
        void showBeforeAddDeviceCheck(List<BeforeAddDeviceCheck> list);

    }

    public interface IsetPipeAddPresenter extends BasePresenter {
        void submitData(Map<String,Object>  map);
        void getData(String id);
    }

    public interface IGetPipeAddView extends BaseView {
        void showPipeInfo(PipePartBean data);
        void showSubmitResult(String id);
        void showResult();

    }

}

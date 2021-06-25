package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.codbking.widget.utils.UIAdjuster;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.widget.ItemArea;
import com.zz.supervision.widget.ItemGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 添加笔录详情
 */
public class SceneRecordDetailActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_sign1)
    ImageView iv_sign1;
    @BindView(R.id.iv_sign2)
    ImageView iv_sign2;
    @BindView(R.id.iv_sign3)
    ImageView iv_sign3;
    @BindView(R.id.ig_party_is_see)
    ItemGroup ig_party_is_see;
    @BindView(R.id.ig_party_apply_void)
    ItemGroup ig_party_apply_void;
    @BindView(R.id.ig_party_is_told)
    ItemGroup ig_party_is_told;
    @BindView(R.id.ig_is_check_site)
    ItemGroup ig_is_check_site;
    @BindView(R.id.ia_partyDescription)
    ItemArea ia_partyDescription;
    @BindView(R.id.ia_siteCondition)
    ItemArea ia_siteCondition;
    @BindView(R.id.ia_partyAttendance)
    ItemArea ia_partyAttendance;
    String sign1;
    String sign2;
    String sign3;
    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_scene_record_detail;

    }

    List<BusinessType> party_is_see = new ArrayList<>();
    List<BusinessType> party_apply_void = new ArrayList<>();
    List<BusinessType> party_is_told = new ArrayList<>();
    List<BusinessType> is_check_site = new ArrayList<>();

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getDicts("party_is_see");
        getDicts("party_apply_void");
        getDicts("party_is_told");
        getDicts("is_check_site");
        ig_party_is_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect(ig_party_is_see, party_is_see);
            }
        });
        ig_party_apply_void.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect(ig_party_apply_void, party_apply_void);
            }
        });
        ig_party_is_told.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect(ig_party_is_told, party_is_told);
            }
        });
        ig_is_check_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelect(ig_is_check_site, is_check_site);
            }
        });
    }

    int code = 1001;

    @OnClick({R.id.ll_sign1, R.id.ll_sign2, R.id.bt_ok, R.id.ll_sign3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sign1:
                code = 1001;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(SceneRecordDetailActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;
            case R.id.ll_sign2:
                code = 1002;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(SceneRecordDetailActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;
            case R.id.ll_sign3:
                code = 1003;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(SceneRecordDetailActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;
            case R.id.bt_ok:
                postData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("sign");
            if (requestCode == 1001) {
                sign1 = path;
                GlideUtils.loadImage(SceneRecordDetailActivity.this, path, iv_sign1);
            } else if (requestCode == 1002) {
                sign2 = path;
                GlideUtils.loadImage(SceneRecordDetailActivity.this, path, iv_sign2);

            } else if (requestCode == 1003) {
                sign3 = path;
                GlideUtils.loadImage(SceneRecordDetailActivity.this, path, iv_sign3);

            }
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void getDicts(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", type);
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {

                if (type.equals("party_is_see")){
                    party_is_see.clear();
                    party_is_see.addAll(jsonT.getData());
                }else if (type.equals("party_apply_void")){
                    party_apply_void.clear();
                    party_apply_void.addAll(jsonT.getData());
                }else if (type.equals("party_is_told")){
                    party_is_told.clear();
                    party_is_told.addAll(jsonT.getData());
                }else if (type.equals("is_check_site")){
                    is_check_site.clear();
                    is_check_site.addAll(jsonT.getData());
                }
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    SelectPopupWindows selectPopupWindows;

    private void showSelect(ItemGroup ig, List<BusinessType> businessTypeList) {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < businessTypeList.size(); i++) {
            list.add(businessTypeList.get(i).getDictLabel());
            list1.add(businessTypeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                ig.setChooseContent(msg, values[index]);
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    private void postData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        map.put("isCheckSite", ig_is_check_site.getSelectValue());
        map.put("partyApplyVoid", ig_party_apply_void.getSelectValue());
        map.put("partyAttendance", getText(ia_partyAttendance));
        map.put("partyDescription", getText(ia_partyDescription));
        map.put("partyIsSee", ig_party_is_see.getSelectValue());
        map.put("partyIsTold", ig_party_is_told.getSelectValue());

        map.put("observerSign", BASE64.imageToBase64(sign2));
        map.put("officerSign", BASE64.imageToBase64(sign3));
        map.put("partySign", BASE64.imageToBase64(sign1));
        map.put("siteCondition", getText(ia_siteCondition));
        RxNetUtils.request(getApi(ApiService.class).submitSceneRecordSign(map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                startActivity(new Intent(SceneRecordDetailActivity.this, ShowDocActivity.class).putExtra("id", id).putExtra("type", 666).putExtra("tinspectSheetType", 1).putExtra("tinspectType", 666).putExtra("read", 1));

            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

}
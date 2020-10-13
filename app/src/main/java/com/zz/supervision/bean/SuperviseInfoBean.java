package com.zz.supervision.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;
import com.zz.supervision.CompanyBean;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuperviseInfoBean  {
    List<SuperviseBean> data;
   String remark;

    public String getRemark() {
        return remark;
    }

    public List<SuperviseBean> getData() {
        return data;
    }


}

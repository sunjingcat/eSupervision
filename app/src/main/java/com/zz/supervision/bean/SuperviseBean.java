package com.zz.supervision.bean;

import android.sax.RootElement;

import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SuperviseBean extends BaseNode {
    private String createBy;// ,
    private String createTime;// ,
    private String deptId;// 0,
    private String id;// 0,
    private String itemName;// ,
    private String itemNum;// ,
    private String itemRemark;// ,
    private String params;// {},
    private String remark;// ,
    private String searchValue;// ,
    private String updateBy;// ,
    private String updateTime;//
    private ArrayList<Children> childrenList;

    public ArrayList<Children> getChildrenList() {
        return childrenList;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemNum() {
        return itemNum;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public String getParams() {
        return params;
    }

    public String getRemark() {
        return remark;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return null;
    }

    public class Children extends BaseNode{
        private String	createBy;// ,
        private String       createTime;// ,
        private String       id;// 0,
        private String       isImportant;// 0,
        private String     isSatisfy;// 0,
        private String     itemName;// ,
        private String    itemNum;// ,
        private String     itemPid;// 0,
        private String      itemType;// ,
        private String      params;// {},
        private String    remark;// ,
        private String    searchValue;// ,
        private String    updateBy;// ,
        private String    updateTime;//

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getId() {
            return id;
        }

        public String getIsImportant() {
            return isImportant;
        }

        public String getIsSatisfy() {
            return isSatisfy;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemNum() {
            return itemNum;
        }

        public String getItemPid() {
            return itemPid;
        }

        public String getItemType() {
            return itemType;
        }

        public String getParams() {
            return params;
        }

        public String getRemark() {
            return remark;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            return null;
        }
    }
}

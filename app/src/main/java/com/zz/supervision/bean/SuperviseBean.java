package com.zz.supervision.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SuperviseBean extends BaseExpandNode {
    private String createBy;// ,
    private String createTime;// ,
    private String deptId;// 0,
    private String id;// 0,
    private String itemName;// ,
    private String itemNum;// ,
    private String itemRemark;// ,
    private Object params;// {},
    private String remark;// ,
    private String searchValue;// ,
    private String updateBy;// ,
    private String updateTime;//
    private boolean check;//
    private ArrayList<Children> childrenList;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

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

    public Object getParams() {
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
        if (childrenList == null) return null;
        List<BaseNode> childs = new ArrayList<>();
        for (BaseNode node : childrenList) {
            childs.add(node);
        }
        return childs;
    }

    public class Children extends BaseExpandNode {
        private String createBy;// ,
        private String createTime;// ,
        private String id;// 0,
        private int isImportant;// 0,
        private String isSatisfy;// 0,
        private String itemName;// ,
        private String itemNum;// ,
        private String itemPid;// 0,
        private String itemType;// ,
        private Object params;// {},
        private String remark;// ,
        private String searchValue;// ,
        private String updateBy;// ,
        private String updateTime;//
        private boolean check;//

        public void setCheck(boolean check) {
            this.check = check;
        }

        public boolean isCheck() {
            return check;
        }

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getId() {
            return id;
        }

        public int getIsImportant() {
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

        public Object getParams() {
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

package com.zz.supervision.bean;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.JSectionEntity;
import com.chad.library.adapter.base.entity.node.BaseNode;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ScoreBean extends BaseNode {
    List<BaseNode > childrenList ;
    private String  searchValue;// null,
    private String       createBy;// null,
    private String       createTime;// null,
    private String     updateBy;// null,
    private String     updateTime;// null,
    private String    remark;// null,
    private String      params;// {},
    private String      deptId;// null,
    private String      id;// 1,
    private String      itemNum;// 一,
    private String      itemName;// 一、许可管理,
    private String     itemType;// null,
    private String     itemRemark;// 重点项（*）0项,一般项1项,
    @Nullable
    @Override
    public List<BaseNode> getChildNode() {
        return childrenList;
    }

    public List<BaseNode> getChildrenList() {
        return childrenList;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getParams() {
        return params;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getItemNum() {
        return itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public String getItemRemark() {
        return itemRemark;
    }

    public class ChildrenItem extends BaseNode{
        private String searchValue;// null,
        private String createBy;// ,
        private String createTime;// null,
        private String updateBy;// ,
        private String updateTime;// null,
        private String remark;// null,
        private String params;// {},
        private String deptId;// null,
        private String id;// 2,
        private String itemNum;// 1,
        private String itemName;// 食品经营许可证合法有效，经营场所、主体业态、经营项目等事项与食品经营许可证一致。,
        private String itemPid;// 1,
        private String itemType;// null,
        private int isImportant;// 0,
        private String isSatisfy;// null

        public String getSearchValue() {
            return searchValue;
        }

        public String getCreateBy() {
            return createBy;
        }

        public String getCreateTime() {
            return createTime;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public String getRemark() {
            return remark;
        }

        public String getParams() {
            return params;
        }

        public String getDeptId() {
            return deptId;
        }

        public String getId() {
            return id;
        }

        public String getItemNum() {
            return itemNum;
        }

        public String getItemName() {
            return itemName;
        }

        public String getItemPid() {
            return itemPid;
        }

        public String getItemType() {
            return itemType;
        }

        public int getIsImportant() {
            return isImportant;
        }

        public String getIsSatisfy() {
            return isSatisfy;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            return null;
        }
    }

}

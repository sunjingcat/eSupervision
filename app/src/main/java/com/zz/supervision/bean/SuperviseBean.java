package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

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
        private int isSatisfy;// 0,
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

        public int getIsSatisfy() {
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

    public static class PostBean{
        String itemId;
        int  isSatisfy;

        public PostBean() {
        }

        public PostBean(String itemId, int isSatisfy) {
            this.itemId = itemId;
            this.isSatisfy = isSatisfy;
        }

        public String getItemId() {
            return itemId;
        }

        public int getIsSatisfy() {
            return isSatisfy;
        }
    }
    public  class ResposeBean implements Parcelable {
        private String	id;// 17,
        private String          companyId;// null,
        private String        serialNumber;// null,
        private String       sumCount;// 2,
        private String       importantCount;// 0,
        private String      generalCount;// 2,
        private String      importantProblemCount;// 0,
        private String      generalProblemCount;// 2,
        private String       inspectionResult;// 不符合,
        private String      inspectionResultReduction;// 食品生产经营者立即停止食品生产经营活动

        protected ResposeBean(Parcel in) {
            id = in.readString();
            companyId = in.readString();
            serialNumber = in.readString();
            sumCount = in.readString();
            importantCount = in.readString();
            generalCount = in.readString();
            importantProblemCount = in.readString();
            generalProblemCount = in.readString();
            inspectionResult = in.readString();
            inspectionResultReduction = in.readString();
        }

        public  final Creator<ResposeBean> CREATOR = new Creator<ResposeBean>() {
            @Override
            public ResposeBean createFromParcel(Parcel in) {
                return new ResposeBean(in);
            }

            @Override
            public ResposeBean[] newArray(int size) {
                return new ResposeBean[size];
            }
        };

        public String getCompanyId() {
            return companyId;
        }

        public String getGeneralCount() {
            return generalCount;
        }

        public String getGeneralProblemCount() {
            return generalProblemCount;
        }

        public String getId() {
            return id;
        }

        public String getImportantCount() {
            return importantCount;
        }

        public String getImportantProblemCount() {
            return importantProblemCount;
        }

        public String getInspectionResult() {
            return inspectionResult;
        }

        public String getInspectionResultReduction() {
            return inspectionResultReduction;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public String getSumCount() {
            return sumCount;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(companyId);
            dest.writeString(serialNumber);
            dest.writeString(sumCount);
            dest.writeString(importantCount);
            dest.writeString(generalCount);
            dest.writeString(importantProblemCount);
            dest.writeString(generalProblemCount);
            dest.writeString(inspectionResult);
            dest.writeString(inspectionResultReduction);
        }
    }
    
}

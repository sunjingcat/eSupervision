package com.zz.supervision.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;
import com.zz.supervision.CompanyBean;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SuperviseBean extends BaseExpandNode implements NodeFooterImp {
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
    private int isLastNotSatisfy;//
    private boolean check;//
    private ArrayList<Children> childrenList;

    public SuperviseBean() {
        setExpanded(false);
    }

    public boolean isCheck() {
        return check;
    }

    public int getIsLastNotSatisfy() {
        return isLastNotSatisfy;
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

    @Nullable
    @Override
    public BaseNode getFooterNode() {
        return new RootFooterNode(isExpanded(), id);
    }

    public class RootFooterNode extends BaseNode {
        private boolean isExpanded;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public RootFooterNode(boolean isExpanded, String id) {
            this.isExpanded = isExpanded;
            this.id = id;
        }

        public boolean isExpanded() {
            return isExpanded;
        }

        public void setExpanded(boolean expanded) {
            isExpanded = expanded;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            return null;
        }
    }

    public class Children extends BaseExpandNode {
        private String createBy;// ,
        private String createTime;// ,
        private String id;// 0,
        private int isImportant;// 0,
        private int isSatisfy ;// 0,
        private String itemName;// ,
        private String itemNum;// ,
        private String itemPid;// 0,
        private String itemType;// ,
        private Object params;// {},
        private String remark;// ,
        private String searchValue;// ,
        private String updateBy;// ,
        private String updateTime;//
        private int isLastNotSatisfy;//
        private ArrayList<Children> childrenList;

        public int getIsLastNotSatisfy() {
            return isLastNotSatisfy;
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

        public void setIsSatisfy(int isSatisfy) {
            this.isSatisfy = isSatisfy;
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
            if (childrenList == null) return null;
            List<BaseNode> childs = new ArrayList<>();
            for (BaseNode node : childrenList) {
                childs.add(node);
            }
            return childs;
        }
    }

    public static class PostBean {
        String itemId;
        int isSatisfy;

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

    public class ResposeConfirmBean implements Serializable {
        private String id;// 17,
        private String companyId;// null,
        private String serialNumber;// null,
        private String sumCount;// 2,
        private String problemCount;// 2,
        private String importantCount;// 0,
        private String generalCount;// 2,
        private String importantProblemCount;// 0,
        private String generalProblemCount;// 2,
        private String inspectionResult;// 不符合,
        private String inspectionResultText;// 不符合,
        private String inspectionResultReduction;// 食品生产经营者立即停止食品生产经营活动
        private String staticScore;
        private String dynamicScore;
        private String totalScore;
        private String level;
        private String resultReductionText;

        public String getProblemCount() {
            return problemCount;
        }

        public String getResultReductionText() {
            return resultReductionText;
        }

        public String getInspectionResultText() {
            return inspectionResultText;
        }

        public String getStaticScore() {
            return staticScore;
        }

        public String getDynamicScore() {
            return dynamicScore;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public String getLevel() {
            return level;
        }

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


    }

    public class ResposeBean implements Serializable {
        private String searchValue;// null,
        private String createBy;// 张东旭,
        private String createTime;// 2020-09-28 14;//01;//46,
        private String updateBy;// ,
        private String updateTime;// 2020-09-28 14;//02;//09,
        private String remark;// null,
        private Object params;// {},
        private String deptId;// 110,
        private String reformTime;// 110,
        private String reformTimeText;// 110,
        private String id;// 22,
        private String serialNumber;// spxs20200928140145564,
        private String companyId;// 22,
        private int status;// 2,
        private String yearCount;// 0,
        private String reason;// null,
        private String inspectionTime;// 2020年09月28日,
        private String sumCount;// 5,
        private int problemCount;// 5,
        private String importantCount;// 1,
        private String importantDetail;// *3.3,
        private String importantProblemCount;// 1,
        private String importantProblemDetail;// ,
        private String generalCount;// 4,
        private String generalDetail;// 1.1，1.2，2.2，3.6,
        private String generalProblemCount;// 4,
        private String generalProblemDetail;// 1.1，1.2，2.2，*3.3，3.6,
        private String inspectionResult;// 3,
        private String inspectionResultText;// 3,
        private String resultReduction;// 3,
        private String resultReductionText;// 3,
        private String violation;// 1.1 经营者持有的食品经营许可证是否合法有效。\n1.2 食品经营许可证载明的有关内容与实际经营是否相符。\n2.2 经营场所环境是否整洁，是否与污染源保持规定的距离。\n*3.3 经营的肉及肉制品是否具有检验检疫证明。\n3.6 经营的食品的标签、说明书是否清楚、明显，生产日期、保质期等事项是否显著标注，容易辨识。\n,
        private String officerSign;// data;//image/png;base64,null,
        private String lawEnforcer1;// 101,
        private String lawEnforcer2;// 103,
        private String companySign;// data;//image/png;base64,null,
        private String deptName;// 铁北所,
        private String lawEnforcer1Name;// 张东旭,
        private String lawEnforcer2Name;// 焦川,
        private CompanyBean companyInfo;
        private CompanyBean company;
        private String statusText;// 待签字
        private String fillerSign;// 待签字
        private String ownerSign;// 待签字
        private String reviewerSign;// 待签字

        private String staticScore;
        private String dynamicScore;
        private String totalScore;
        private String level;
        private String typeText;

        public String getReformTimeText() {
            return reformTimeText;
        }

        public String getReformTime() {
            return reformTime;
        }

        public int getProblemCount() {
            return problemCount;
        }

        public String getTypeText() {
            return typeText;
        }

        public String getInspectionResultText() {
            return inspectionResultText;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public String getStaticScore() {
            return staticScore;
        }

        public String getDynamicScore() {
            return dynamicScore;
        }

        public String getTotalScore() {
            return totalScore;
        }

        public String getLevel() {
            return level;
        }

        public String getFillerSign() {
            return fillerSign;
        }

        public String getOwnerSign() {
            return ownerSign;
        }

        public String getReviewerSign() {
            return reviewerSign;
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

        public Object getParams() {
            return params;
        }

        public String getDeptId() {
            return deptId;
        }

        public String getId() {
            return id;
        }

        public String getSerialNumber() {
            return serialNumber;
        }

        public String getCompanyId() {
            return companyId;
        }

        public int getStatus() {
            return status;
        }

        public String getYearCount() {
            return yearCount;
        }

        public String getReason() {
            return reason;
        }

        public String getInspectionTime() {
            return inspectionTime;
        }

        public String getSumCount() {
            return sumCount;
        }

        public String getImportantCount() {
            return importantCount;
        }

        public String getImportantDetail() {
            return importantDetail;
        }

        public String getImportantProblemCount() {
            return importantProblemCount;
        }

        public String getImportantProblemDetail() {
            return importantProblemDetail;
        }

        public String getGeneralCount() {
            return generalCount;
        }

        public String getGeneralDetail() {
            return generalDetail;
        }

        public String getGeneralProblemCount() {
            return generalProblemCount;
        }

        public String getGeneralProblemDetail() {
            return generalProblemDetail;
        }

        public String getInspectionResult() {
            return inspectionResult;
        }

        public String getResultReductionText() {
            return resultReductionText;
        }

        public String getResultReduction() {
            return resultReduction;
        }

        public String getViolation() {
            return violation;
        }

        public String getOfficerSign() {
            return officerSign;
        }

        public String getLawEnforcer1() {
            return lawEnforcer1;
        }

        public String getLawEnforcer2() {
            return lawEnforcer2;
        }

        public String getCompanySign() {
            return companySign;
        }

        public String getDeptName() {
            return deptName;
        }

        public String getLawEnforcer1Name() {
            return lawEnforcer1Name;
        }

        public String getLawEnforcer2Name() {
            return lawEnforcer2Name;
        }

        public CompanyBean getCompanyInfo() {
            if (companyInfo == null) {
                return company;
            } else return companyInfo;
        }

        public String getStatusText() {
            return statusText;
        }
    }

}

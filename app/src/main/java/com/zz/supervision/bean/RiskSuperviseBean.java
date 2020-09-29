package com.zz.supervision.bean;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.entity.node.NodeFooterImp;
import com.zz.supervision.CompanyBean;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RiskSuperviseBean {

    private ArrayList<RiskItem> dynamicRisks;
    private ArrayList<RiskItem> staticRisks;

    public ArrayList<RiskItem> getDynamicRisks() {
        return dynamicRisks;
    }

    public ArrayList<RiskItem> getStaticRisks() {
        return staticRisks;
    }

    public class RiskItem extends BaseExpandNode  {
        private String childType;//
        private String content;//
        private String createBy;//
        private String createTime;//
        private String deptId;//
        private String id;//
        private String isDelete;//
        private Object params;
        private String pid;//
        private String remark;//
        private String score;//
        private String searchValue;//
        private String unit;//
        private String updateBy;//
        private String updateTime;
        private boolean check;

        private String isKey;// 0,
        private String orderNum;//
        private String type;// 0,
        private ArrayList<ChildRisk> childRisks;

        public RiskItem() {
            setExpanded(true);
        }

        public String getChildType() {
            return childType;
        }

        public String getContent() {
            return content;
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

        public String getIsDelete() {
            return isDelete;
        }

        public Object getParams() {
            return params;
        }

        public String getPid() {
            return pid;
        }

        public String getRemark() {
            return remark;
        }

        public String getScore() {
            return score;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public String getUnit() {
            return unit;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public ArrayList<ChildRisk> getChildRisks() {
            return childRisks;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        @Nullable
        @Override
        public List<BaseNode> getChildNode() {
            if (childRisks == null) return null;
            List<BaseNode> childs = new ArrayList<>();
            for (BaseNode node : childRisks) {
                childs.add(node);
            }
            return childs;
        }

    }

    public class ChildRisk extends BaseExpandNode {
        private String childType;//
        private String content;//
        private String createBy;//
        private String createTime;//
        private String deptId;//
        private String id;//
        private String isDelete;//
        private Object params;
        private String pid;//
        private String remark;//
        private String score;//
        private String searchValue;//
        private String unit;//
        private String updateBy;//
        private String updateTime;
        private boolean check;
        private List<ChildRisk2> childRisks;

        public List<ChildRisk2> getChildRisks() {
            return childRisks;
        }

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getChildType() {
            return childType;
        }

        public String getContent() {
            return content;
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

        public String getIsDelete() {
            return isDelete;
        }

        public Object getParams() {
            return params;
        }

        public String getPid() {
            return pid;
        }

        public String getRemark() {
            return remark;
        }

        public String getScore() {
            return score;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public String getUnit() {
            return unit;
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
            if (childRisks == null) return null;
            List<BaseNode> childs = new ArrayList<>();
            for (BaseNode node : childRisks) {
                childs.add(node);
            }
            return childs;
        }
    }

    public class ChildRisk2 extends BaseExpandNode {
        private String childType;//
        private String content;//
        private String createBy;//
        private String createTime;//
        private String deptId;//
        private String id;//
        private String isDelete;//
        private Object params;
        private String pid;//
        private String remark;//
        private String score;//
        private String searchValue;//
        private String unit;//
        private String updateBy;//
        private String updateTime;
        private boolean check;

        public boolean isCheck() {
            return check;
        }

        public void setCheck(boolean check) {
            this.check = check;
        }

        public String getChildType() {
            return childType;
        }

        public String getContent() {
            return content;
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

        public String getIsDelete() {
            return isDelete;
        }

        public Object getParams() {
            return params;
        }

        public String getPid() {
            return pid;
        }

        public String getRemark() {
            return remark;
        }

        public String getScore() {
            return score;
        }

        public String getSearchValue() {
            return searchValue;
        }

        public String getUnit() {
            return unit;
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
        private String importantCount;// 0,
        private String generalCount;// 2,
        private String importantProblemCount;// 0,
        private String generalProblemCount;// 2,
        private String inspectionResult;// 不符合,
        private String inspectionResultReduction;// 食品生产经营者立即停止食品生产经营活动


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
        private String id;// 22,
        private String serialNumber;// spxs20200928140145564,
        private String companyId;// 22,
        private String status;// 2,
        private String yearCount;// 0,
        private String reason;// null,
        private String inspectionTime;// 2020年09月28日,
        private String sumCount;// 5,
        private String importantCount;// 1,
        private String importantDetail;// *3.3,
        private String importantProblemCount;// 1,
        private String importantProblemDetail;// ,
        private String generalCount;// 4,
        private String generalDetail;// 1.1，1.2，2.2，3.6,
        private String generalProblemCount;// 4,
        private String generalProblemDetail;// 1.1，1.2，2.2，*3.3，3.6,
        private String inspectionResult;// 3,
        private String resultReduction;// 3,
        private String violation;// 1.1 经营者持有的食品经营许可证是否合法有效。\n1.2 食品经营许可证载明的有关内容与实际经营是否相符。\n2.2 经营场所环境是否整洁，是否与污染源保持规定的距离。\n*3.3 经营的肉及肉制品是否具有检验检疫证明。\n3.6 经营的食品的标签、说明书是否清楚、明显，生产日期、保质期等事项是否显著标注，容易辨识。\n,
        private String officerSign;// data;//image/png;base64,null,
        private String lawEnforcer1;// 101,
        private String lawEnforcer2;// 103,
        private String companySign;// data;//image/png;base64,null,
        private String deptName;// 铁北所,
        private String lawEnforcer1Name;// 张东旭,
        private String lawEnforcer2Name;// 焦川,
        private CompanyBean companyInfo;
        private String statusText;// 待签字

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

        public String getStatus() {
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
            return companyInfo;
        }

        public String getStatusText() {
            return statusText;
        }
    }

}

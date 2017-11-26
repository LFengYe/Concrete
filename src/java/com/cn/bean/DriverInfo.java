/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

/**
 *
 * @author LFeng
 */
public class DriverInfo {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    @FieldDescription(description = "司机ID", operate = "auto")
    private int driverID;
    @FieldDescription(description = "司机名称", operate = "import")
    private String driverName;
    @FieldDescription(description = "登录名", operate = "import")
    private String driverLoginName;
    @FieldDescription(description = "登录密码", operate = "import")
    private String driverPassword;
    @FieldDescription(description = "行车证号", operate = "import")
    private String driverLinsence;
    @FieldDescription(description = "是否可用")
    private int isCanUse;
    @FieldDescription(description = "是否可用", operate = "display")
    private String isCanUseName;
    @FieldDescription(description = "备注")
    private String remark;

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int DriverID) {
        this.driverID = DriverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String DriverName) {
        this.driverName = DriverName;
    }

    public String getDriverLoginName() {
        return driverLoginName;
    }

    public void setDriverLoginName(String DriverLoginName) {
        this.driverLoginName = DriverLoginName;
    }

    public String getDriverPassword() {
        return driverPassword;
    }

    public void setDriverPassword(String DriverPassword) {
        this.driverPassword = DriverPassword;
    }

    public String getDriverLinsence() {
        return driverLinsence;
    }

    public void setDriverLinsence(String DriverLinsence) {
        this.driverLinsence = DriverLinsence;
    }

    public String getIsCanUse() {
        return isCanUse == 0 ? "启用": "禁用";
    }

    public void setIsCanUse(int isCanUse) {
        this.isCanUse = isCanUse;
    }

    public String getIsCanUseName() {
        return isCanUse == 0 ? "启用": "禁用";
    }

    public void setIsCanUseName(String isCanUseName) {
        this.isCanUseName = isCanUseName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

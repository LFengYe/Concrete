/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.bean;

import com.cn.util.Units;

/**
 *
 * @author LFeng
 */
public class CarInfo {

    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }

    @FieldDescription(description = "车辆ID", operate = "auto")
    private int carID;
    @FieldDescription(description = "车牌号", operate = "import")
    private String carNO;
    @FieldDescription(description = "登录名", operate = "import")
    private String loginName;
    @FieldDescription(description = "登录密码")
    private String loginPass;
    @FieldDescription(description = "车队名称", operate = "import")
    private String motorcadeName;
    @FieldDescription(description = "车型", operate = "import")
    private String carType;
    @FieldDescription(description = "车辆载重", operate = "import")
    private float carWeight;
    @FieldDescription(description = "GPS系统编号", operate = "import")
    private String systemNo;
    @FieldDescription(description = "是否可用")
    private int isCanUse;
    @FieldDescription(description = "是否可用", operate = "display")
    private String isCanUseName;
    @FieldDescription(description = "电子封签")
    private String electronicSeal;
    @FieldDescription(description = "到期时间")
    private String expiredDate;
    @FieldDescription(description = "备注")
    private String remark;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public float getCarWeight() {
        return carWeight;
    }

    public void setCarWeight(float carWeight) {
        this.carWeight = carWeight;
    }

    public String getSystemNo() {
        return systemNo;
    }

    public void setSystemNo(String systemNo) {
        this.systemNo = systemNo;
    }

    public int getIsCanUse() {
        return isCanUse;
    }

    public void setIsCanUse(int isCanUse) {
        this.isCanUse = isCanUse;
    }

    public String getIsCanUseName() {
        return isCanUse == 0 ? "启用" : "禁用";
    }

    public void setIsCanUseName(String isCanUseName) {
        this.isCanUseName = isCanUseName;
    }

    public String getElectronicSeal() {
        return electronicSeal;
    }

    public void setElectronicSeal(String electronicSeal) {
        this.electronicSeal = electronicSeal;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMotorcadeName() {
        return motorcadeName;
    }

    public void setMotorcadeName(String motorcadeName) {
        this.motorcadeName = motorcadeName;
    }

    public boolean isGPSDataChange(String gpsNumber, String motorcadeName, String expireDate) {
        if (Units.strIsEmpty(this.systemNo))
            return true;
        if (this.systemNo.compareToIgnoreCase(gpsNumber) != 0)
            return true;
        if (Units.strIsEmpty(this.motorcadeName))
            return true;
        if (this.motorcadeName.compareToIgnoreCase(motorcadeName) != 0)
            return true;
        if (Units.strIsEmpty(this.expiredDate))
            return true;
        return this.expiredDate.compareToIgnoreCase(expireDate) != 0;
    }
}

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
    private int isDisabled;
    @FieldDescription(description = "是否可用", operate = "display")
    private String isDisabledName;
    @FieldDescription(description = "GPS定位时间")
    private String gpsTime;
    @FieldDescription(description = "是否在线")
    private int isOnline;
    @FieldDescription(description = "是否在线", operate = "display")
    private String isOnlineName;
    @FieldDescription(description = "电子封签")
    private String electronicSeal;
    @FieldDescription(description = "到期时间")
    private String expiredDate;
    @FieldDescription(description = "临时车辆")
    private int isTemp;
    @FieldDescription(description = "临时车辆", operate = "display")
    private String isTempName;
    @FieldDescription(description = "到期时间")
    private String tempExpiredTime;
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

    public int getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(int isDisabled) {
        this.isDisabled = isDisabled;
    }

    public String getIsDisabledName() {
        return isDisabled == 0 ? "启用" : "禁用";
    }

    public void setIsDisabledName(String isDisabledName) {
        this.isDisabledName = isDisabledName;
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
    
    public int getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(int isOnline) {
        this.isOnline = isOnline;
    }

    public String getIsOnlineName() {
        return isOnline == 0 ? "离线" : "在线";
    }

    public void setIsOnlineName(String isOnlineName) {
        this.isOnlineName = isOnlineName;
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
        return this.expiredDate.compareToIgnoreCase(expireDate) < 0;
    }
    
    public boolean isGPSDataChange(String gpsNumber, String motorcadeName) {
        if (Units.strIsEmpty(this.systemNo))
            return true;
        if (this.systemNo.compareToIgnoreCase(gpsNumber) != 0)
            return true;
        if (Units.strIsEmpty(this.motorcadeName))
            return true;
        return this.motorcadeName.compareToIgnoreCase(motorcadeName) != 0;
    }

    public String getGpsTime() {
        return gpsTime;
    }

    public void setGpsTime(String gpsTime) {
        this.gpsTime = gpsTime;
    }

    public int getIsTemp() {
        return isTemp;
    }

    public void setIsTemp(int isTemp) {
        this.isTemp = isTemp;
    }

    public String getTempExpiredTime() {
        return tempExpiredTime;
    }

    public void setTempExpiredTime(String tempExpiredTime) {
        this.tempExpiredTime = tempExpiredTime;
    }

    public String getIsTempName() {
        return (isTemp == 1) ? "是" : "否";
    }

    public void setIsTempName(String isTempName) {
        this.isTempName = isTempName;
    }
}

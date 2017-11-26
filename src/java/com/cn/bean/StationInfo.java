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
public class StationInfo {

    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }

    @FieldDescription(description = "行政区域ID", operate = "auto")
    private String stationID;
    @FieldDescription(description = "行政区域名称", operate = "display")
    private String stationName;
    @FieldDescription(description = "省名称", operate = "import")
    private String proviceName;
    @FieldDescription(description = "市名称", operate = "import")
    private String cityName;
    @FieldDescription(description = "区域名称", operate = "import")
    private String countyName;
    @FieldDescription(description = "省GB码", operate = "import")
    private String proviceGB;
    @FieldDescription(description = "市GB码", operate = "import")
    private String cityGB;
    @FieldDescription(description = "区域GB码", operate = "import")
    private String countyGB;
    @FieldDescription(description = "别名", operate = "import")
    private String alias;
    @FieldDescription(description = "备注", operate = "import")
    private String remark;

    public String getStationID() {
        return stationID;
    }

    public void setStationID(String stationID) {
        this.stationID = stationID;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getProviceGB() {
        return proviceGB;
    }

    public void setProviceGB(String proviceGB) {
        this.proviceGB = proviceGB;
    }

    public String getCityGB() {
        return cityGB;
    }

    public void setCityGB(String cityGB) {
        this.cityGB = cityGB;
    }

    public String getCountyGB() {
        return countyGB;
    }

    public void setCountyGB(String countyGB) {
        this.countyGB = countyGB;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStationName() {
        return this.proviceName + this.cityName + this.countyName;
    }

    public void setStationName(String stationName) {
        this.stationName = this.proviceName + this.cityName + this.countyName;
    }
    
}

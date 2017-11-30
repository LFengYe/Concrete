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
public class CustomerInfo {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    
    @FieldDescription(description = "客户名称", operate = "import")
    private String customerName;
    @FieldDescription(description = "工厂名称", operate = "import")
    private String factoryName;
    @FieldDescription(description = "行政区域编码", operate = "import")
    private String countyGB;
    @FieldDescription(description = "行政区域简称", operate = "display")
    private String countyName;
    @FieldDescription(description = "行政区域", operate = "display")
    private String destinationStation;
    @FieldDescription(description = "区域类型", operate = "import")
    private String destinationType;
    @FieldDescription(description = "区域名称", operate = "import")
    private String destinationName;
    @FieldDescription(description = "区域内容")
    private String destinationContent;
    @FieldDescription(description = "备注", operate = "import")
    private String remark;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCountyGB() {
        return countyGB;
    }

    public void setCountyGB(String countyGB) {
        this.countyGB = countyGB;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDestinationContent() {
        return destinationContent;
    }

    public void setDestinationContent(String destinationContent) {
        this.destinationContent = destinationContent;
    }
    
}

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
    @FieldDescription(description = "行政区域", operate = "display")
    private String destinationStation;
    @FieldDescription(description = "自定义区域")
    private String polygonName;
    @FieldDescription(description = "自定义区域路径")
    private String polygonPath;

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

    public String getPolygonName() {
        return polygonName;
    }

    public void setPolygonName(String polygonName) {
        this.polygonName = polygonName;
    }

    public String getPolygonPath() {
        return polygonPath;
    }

    public void setPolygonPath(String polygonPath) {
        this.polygonPath = polygonPath;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }
    
}

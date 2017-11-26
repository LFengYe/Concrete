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
public class OrderInfo {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    @FieldDescription(description = "订单编号", operate = "import")
    private String orderID;
    @FieldDescription(description = "开票时间")
    private String orderTime;
    @FieldDescription(description = "司机ID")
    private int driverID;
    @FieldDescription(description = "司机名称", operate = "display")
    private String driverName;
    @FieldDescription(description = "车牌号", operate = "import")
    private String carNO;
    @FieldDescription(description = "客户名称", operate = "import")
    private String customerName;
    @FieldDescription(description = "工厂名称", operate = "import")
    private String factoryName;
//    @FieldDescription(description = "区域编码", operate = "display")
//    private String countyGB;
//    @FieldDescription(description = "行政区域", operate = "display")
//    private String destinationStation;
//    @FieldDescription(description = "自定义区域", operate = "display")
//    private String polygonName;
//    @FieldDescription(description = "自定义区域路径", operate = "display")
//    private String polygonPath;
    @FieldDescription(description = "货物名称", operate = "import")
    private String goodsName;
    @FieldDescription(description = "货物类型", operate = "display")
    private String goodsType;
    @FieldDescription(description = "地区名称")
    private String areaName;
    @FieldDescription(description = "订单状态")
    private int orderStatus;
    @FieldDescription(description = "状态名称", operate = "display")
    private String orderStatusName;
    @FieldDescription(description = "是否验证图片")
    private boolean isRequireImg;
    @FieldDescription(description = "是否验证图片", operate = "display")
    private String isRequireImgName;
    @FieldDescription(description = "备注", operate = "import")
    private String orderRemark;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public int getDriverID() {
        return driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getOrderStatusName() {
        return orderStatus == 0 ? "未完成" : "已完成";
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public boolean isIsRequireImg() {
        return isRequireImg;
    }

    public void setIsRequireImg(boolean isRequireImg) {
        this.isRequireImg = isRequireImg;
    }

    public String getIsRequireImgName() {
        return isRequireImg ? "上传" : "不上传";
    }

    public void setIsRequireImgName(String isRequireImgName) {
        this.isRequireImgName = isRequireImgName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}

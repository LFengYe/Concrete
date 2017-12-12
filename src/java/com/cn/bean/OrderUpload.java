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
public class OrderUpload {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    @FieldDescription(description = "订单ID")
    private String orderID;
    @FieldDescription(description = "车牌号", operate = "display")
    private String carNO;
    @FieldDescription(description = "客户名称", operate = "display")
    private String customerName;
    @FieldDescription(description = "工厂名称", operate = "display")
    private String factoryName;
    @FieldDescription(description = "货物名称", operate = "display")
    private String goodsName;
    @FieldDescription(description = "地区名称")
    private String areaName;
    @FieldDescription(description = "上报时间")
    private String uploadTime;
    @FieldDescription(description = "上传纬度")
    private String uploadLat;
    @FieldDescription(description = "上传经度")
    private String uploadLng;
    @FieldDescription(description = "上传位置")
    private String uploadAddress;
    @FieldDescription(description = "车辆纬度")
    private String carLat;
    @FieldDescription(description = "车辆经度")
    private String carLng;
    @FieldDescription(description = "车辆位置")
    private String carAddress;
    @FieldDescription(description = "上传车辆图片")
    private String uploadCarImg;
    @FieldDescription(description = "上传司机图片")
    private String uploadDriverImg;
    @FieldDescription(description = "上报状态")
    private int uploadStatus;
    @FieldDescription(description = "上报信息")
    private String uploadMessage;
    @FieldDescription(description = "备注")
    private String uploadRemark;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getUploadLat() {
        return uploadLat;
    }

    public void setUploadLat(String uploadLat) {
        this.uploadLat = uploadLat;
    }

    public String getUploadLng() {
        return uploadLng;
    }

    public void setUploadLng(String uploadLng) {
        this.uploadLng = uploadLng;
    }

    public String getUploadCarImg() {
        return uploadCarImg;
    }

    public void setUploadCarImg(String uploadCarImg) {
        this.uploadCarImg = uploadCarImg;
    }

    public String getUploadDriverImg() {
        return uploadDriverImg;
    }

    public void setUploadDriverImg(String uploadDriverImg) {
        this.uploadDriverImg = uploadDriverImg;
    }

    public String getUploadStatus() {
        return uploadStatus == 0 ? "上报成功" : "上报失败";
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getUploadMessage() {
        return uploadMessage;
    }

    public void setUploadMessage(String uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public String getUploadRemark() {
        return uploadRemark;
    }

    public void setUploadRemark(String uploadRemark) {
        this.uploadRemark = uploadRemark;
    }

    public String getUploadAddress() {
        return uploadAddress;
    }

    public void setUploadAddress(String uploadAddress) {
        this.uploadAddress = uploadAddress;
    }

    public String getCarLat() {
        return carLat;
    }

    public void setCarLat(String carLat) {
        this.carLat = carLat;
    }

    public String getCarLng() {
        return carLng;
    }

    public void setCarLng(String carLng) {
        this.carLng = carLng;
    }

    public String getCarAddress() {
        return carAddress;
    }

    public void setCarAddress(String carAddress) {
        this.carAddress = carAddress;
    }

    public String getCarNO() {
        return carNO;
    }

    public void setCarNO(String carNO) {
        this.carNO = carNO;
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

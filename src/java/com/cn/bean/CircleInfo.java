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
public class CircleInfo {

    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }

    @FieldDescription(description = "名称", operate = "import")
    private String circleName;
    @FieldDescription(description = "行政区域编码", operate = "import")
    private String countyGB;
    @FieldDescription(description = "行政区域", operate = "display")
    private String countyName;
    @FieldDescription(description = "圆点纬度", operate = "import")
    private String circleDotLat;
    @FieldDescription(description = "圆点经度", operate = "import")
    private String circleDotLng;
    @FieldDescription(description = "半径(米)", operate = "import")
    private String circleRadius;
    @FieldDescription(description = "区域内容", operate = "display")
    private String circleContent;
    @FieldDescription(description = "备注", operate = "import")
    private String remark;

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCountyGB() {
        return countyGB;
    }

    public void setCountyGB(String countyGB) {
        this.countyGB = countyGB;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getCircleDotLat() {
        return circleDotLat;
    }

    public void setCircleDotLat(String circleDotLat) {
        this.circleDotLat = circleDotLat;
    }

    public String getCircleDotLng() {
        return circleDotLng;
    }

    public void setCircleDotLng(String circleDotLng) {
        this.circleDotLng = circleDotLng;
    }

    public String getCircleRadius() {
        return circleRadius;
    }

    public void setCircleRadius(String circleRadius) {
        this.circleRadius = circleRadius;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCircleContent() {
        return this.circleDotLng + "," + this.circleDotLat + "," + this.circleRadius;
    }

    public void setCircleContent(String circleContent) {
        this.circleContent = circleContent;
    }
}

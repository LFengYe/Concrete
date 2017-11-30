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
public class PolygonInfo {

    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }

    @FieldDescription(description = "名称", operate = "import")
    private String polygonName;
    @FieldDescription(description = "行政区域编码", operate = "import")
    private String countyGB;
    @FieldDescription(description = "行政区域", operate = "display")
    private String countyName;
    @FieldDescription(description = "自定义区域路径")
    private String polygonPath;
    @FieldDescription(description = "备注", operate = "import")
    private String Remark;

    public String getPolygonName() {
        return polygonName;
    }

    public void setPolygonName(String polygonName) {
        this.polygonName = polygonName;
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

    public String getPolygonPath() {
        return polygonPath;
    }

    public void setPolygonPath(String PolygonPath) {
        this.polygonPath = PolygonPath;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String Remark) {
        this.Remark = Remark;
    }
}

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
public class DestinationInfo {
    private static int recordCount;

    public static int getRecordCount() {
        return recordCount;
    }

    public static void setRecordCount(int aRecordCount) {
        recordCount = aRecordCount;
    }
    
    @FieldDescription(description = "目的地ID", operate = "auto")
    private int destinationID;
    @FieldDescription(description = "目的地", operate = "import")
    private String destinationName;
    @FieldDescription(description = "行政区域", operate = "import")
    private String destinationStation;
    @FieldDescription(description = "行政区域编码", operate = "import")
    private String countyGB;
    @FieldDescription(description = "自定义区域", operate = "import")
    private String polygonName;
    @FieldDescription(description = "自定义区域路径")
    private String polygonPath;

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationStation() {
        return destinationStation;
    }

    public void setDestinationStation(String destinationStation) {
        this.destinationStation = destinationStation;
    }

    public String getCountyGB() {
        return countyGB;
    }

    public void setCountyGB(String countyGB) {
        this.countyGB = countyGB;
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
    
}

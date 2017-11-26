/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.util;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LFeng
 */
public class GeoUtils {

    /**
     * 检查一个坐标是否在多边形内
     *
     * @param x 纬度 31.000...
     * @param y 经度 121.000...
     * @param polygonPoints 多边形边界的经纬度数组
     * @return
     */
    public static boolean isPointInPolygon(double x, double y, List<Map<String, Double>> polygonPoints) {
        Point2D.Double geoPoint = buildPoint(x, y);
        List<Point2D.Double> geoPolygon = buildPolygon(polygonPoints);
        return GeoUtils.isPointInPolygon(geoPoint, geoPolygon);
    }

    /**
     * 检查一个坐标是否在多边形内
     *
     * @param point 检查的点坐标
     * @param polygon 参照的多边形
     * @return
     */
    public static boolean isPointInPolygon(Point2D.Double point, List<Point2D.Double> polygon) {
        GeneralPath p = new GeneralPath();

        Point2D.Double first = polygon.get(0);
        p.moveTo(first.x, first.y);
        polygon.remove(0);

        polygon.forEach(d -> p.lineTo(d.x, d.y));

        p.lineTo(first.x, first.y);

        p.closePath();

        return p.contains(point);
    }

    /**
     * 构建一个坐标点
     *
     * @param x 纬度 31.000...
     * @param y 经度 121.000...
     * @return
     */
    public static Point2D.Double buildPoint(double x, double y) {
        return new Point2D.Double(x, y);
    }

    /**
     * 构建一个多边形
     *
     * @param polygonPoints
     * @return
     */
    public static List<Point2D.Double> buildPolygon(List<Map<String, Double>> polygonPoints) {
        List<Point2D.Double> geoPolygon = new ArrayList<>();

        polygonPoints.forEach(map -> geoPolygon.add(buildPoint(map.get("x"), map.get("y"))));

        return geoPolygon;
    }
}

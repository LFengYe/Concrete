/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.servlet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.bean.CarInfo;
import com.cn.bean.CustomerInfo;
import com.cn.bean.DriverInfo;
import com.cn.bean.Latest;
import com.cn.bean.OrderInfo;
import com.cn.bean.UserInfo;
import com.cn.controller.CommonController;
import com.cn.controller.InterfaceController;
import com.cn.util.DatabaseOpt;
import com.cn.util.GeoUtils;
import com.cn.util.Response;
import com.cn.util.Units;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class AppInterface extends HttpServlet {

    private static final Logger logger = Logger.getLogger(AppInterface.class);
    private static final long limitTime = 5 * 60 * 1000;//五分钟

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @param params
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String params)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        String subUri = uri.substring(uri.lastIndexOf("/") + 1,
                uri.lastIndexOf("."));
        String json = null;
        InterfaceController interfaceController = new InterfaceController();
        //logger.info(Units.getIpAddress(request) + "accept:" + subUri + ",time:" + (new Date().getTime()));

        try {
            logger.info(subUri + ",params:" + params);
            JSONObject paramsJson = JSONObject.parseObject(params.replace(" ", "%20"));

            String module = paramsJson.getString("module");
            String operation = paramsJson.getString("operation");
            String rely = (paramsJson.getString("rely") == null) ? ("{}") : (paramsJson.getString("rely"));
            String datas = (paramsJson.getString("datas") == null) ? ("") : paramsJson.getString("datas");
            int pageIndex = paramsJson.getIntValue("pageIndex");
            int pageSize = paramsJson.getIntValue("pageSize");

            HttpSession session = request.getSession();
            String classPath = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ");
            String servletPath = getServletContext().getRealPath("/").replace("\\", "/");

            /*验证是否登陆*/
            if ((!"reg".equals(operation))
                    && session.getAttribute("user") == null && session.getAttribute("loginType") == null) {
                session.invalidate();
                json = Units.objectToJson(-99, "未登陆", null);
                PrintWriter out = response.getWriter();
                try {
                    response.setContentType("text/html;charset=UTF-8");
                    response.setHeader("Cache-Control", "no-store");
                    response.setHeader("Pragma", "no-cache");
                    response.setDateHeader("Expires", 0);
                    out.print(json);
                } finally {
                    out.close();
                }
                return;
            }

            UserInfo user = null;
            DriverInfo driver = null;
            CarInfo carInfo = null;
            if (session.getAttribute("loginType") != null && session.getAttribute("loginType").toString().compareTo("userLogin") == 0) {
                user = (UserInfo) session.getAttribute("user");
            }
            if (session.getAttribute("loginType") != null && session.getAttribute("loginType").toString().compareTo("driverLogin") == 0) {
                driver = (DriverInfo) session.getAttribute("user");
            }
            if (session.getAttribute("loginType") != null && session.getAttribute("loginType").toString().compareTo("carLogin") == 0) {
                carInfo = (CarInfo) session.getAttribute("user");
            }

            switch (module) {
                //<editor-fold desc="driver">
                case "driver": {
                    switch (operation) {
                        case "reg": {
                            String add = "[" + paramsJson.getString("datas") + "]";
                            json = interfaceController.submitOperate("com.cn.bean.", "DriverInfo", null, add, null, "data");
                            break;
                        }
                        case "finishOrder": {
                            if (driver == null) {
                                json = Units.objectToJson(-99, "未登陆", null);
                                break;
                            }
                            json = "";
                            break;
                        }
                    }

                    break;
                }
                //</editor-fold>
                
                //<editor-fold desc="carInfo">
                case "carInfo": {
                    switch (operation) {
                        case "finishOrder": {
                            if (carInfo == null) {
                                json = Units.objectToJson(-99, "未登陆", null);
                                break;
                            }
                            json = driverFinishOrder(carInfo, paramsJson, servletPath);
                            break;
                        }
                    }

                    break;
                }
                //</editor-fold>

                //<editor-fold desc="order">
                case "order": {
                    switch (operation) {
                        case "getAll": {
                            if (carInfo == null) {
                                json = Units.objectToJson(-99, "未登陆", null);
                                break;
                            }
                            String whereCase = "carNO = '" + carInfo.getCarNO() + "'";
                            json = interfaceController.queryOperateWithFilter("com.cn.bean.", "view", "OrderInfo", "OrderID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "getUnfinished": {
                            if (carInfo == null) {
                                json = Units.objectToJson(-99, "未登陆", null);
                                break;
                            }
                            String whereCase = "orderStatus = 0 and carNO = '" + carInfo.getCarNO() + "'";
                            json = interfaceController.queryOperateWithFilter("com.cn.bean.", "table", "OrderInfo", "OrderID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>
            }

        } catch (Exception e) {
            logger.info(subUri);
            logger.error("错误信息:" + e.getMessage(), e);
            json = Units.objectToJson(-1, "输入参数错误!", e.toString());
        }
        //logger.info(Units.getIpAddress(request) + "response:" + subUri + ",time:" + (new Date().getTime()));

        PrintWriter out = response.getWriter();

        try {
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Cache-Control", "no-store");
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            out.print(json);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private String driverFinishOrder(CarInfo carInfo, JSONObject paramsJson, String servletPath) throws Exception {
        CommonController commonController = new CommonController();
        String whereCase = "carNO = '" + carInfo.getCarNO() + "' and OrderStatus = 0";
        List<Object> res = commonController.dataBaseQuery("view", "com.cn.bean.", "OrderInfo", "*", whereCase, 1, 1, "OrderID", 1, DatabaseOpt.DATA);
        if (res != null && res.size() > 0) {
            OrderInfo orderInfo = (OrderInfo) res.get(0);

            //判断车辆是否有车辆定位信息
            Latest latest;
            String whereSql = "number = '" + carInfo.getSystemNo() + "'";
            List<Object> gpsLocal = commonController.dataBaseQuery("latest", "com.cn.bean.Latest", "*", whereSql, 11, 1, "number", 0, DatabaseOpt.GPS_LOCAL);
            if (gpsLocal != null && gpsLocal.size() > 0) {
                latest = (Latest) gpsLocal.get(0);
                String gpsTime = latest.getGpsTime();
                Date date = DateFormat.getDateTimeInstance().parse(gpsTime.substring(0, 19));
                if (Units.getIntervalTimeWithNow(date) > limitTime) {
                    return Units.objectToJson(-1, "车辆定位信息已过期", null);
                }
            } else {
                return Units.objectToJson(-1, "没有车辆定位信息", null);
            }

            //base64图片信息转成图片保存到本地
            String carImgPath = null;
            String driverImgPath = null;
            if (orderInfo.isIsRequireImg()) {
                carImgPath = Units.GenerateImage(paramsJson.getString("carImg").replace("data:image/jpeg;base64,", ""), servletPath + "upload/");
                driverImgPath = Units.GenerateImage(paramsJson.getString("driverImg").replace("data:image/jpeg;base64,", ""), servletPath + "upload/");
            }

            //解析手机地理位置和车辆地理位置
            String address = Units.getBaiduRenderReverse(paramsJson.getString("curLat") + "," + paramsJson.getString("curLog"), "wgs84ll");
            String carAddress = Units.getBaiduRenderReverse(latest.getLat() + "," + latest.getLng(), "wgs84ll");

            //上报信息
            JSONObject uploadObj = new JSONObject();
            uploadObj.put("orderID", orderInfo.getOrderID());
            uploadObj.put("uploadTime", Units.getNowTime());
            uploadObj.put("uploadLat", paramsJson.getString("curLat"));
            uploadObj.put("uploadLng", paramsJson.getString("curLog"));
            uploadObj.put("uploadAddress", JSONObject.parseObject(address).getJSONObject("result").getString("formatted_address"));
            uploadObj.put("carLat", latest.getLat());
            uploadObj.put("carLng", latest.getLng());
            uploadObj.put("carAddress", JSONObject.parseObject(carAddress).getJSONObject("result").getString("formatted_address"));
            uploadObj.put("uploadCarImg", carImgPath);
            uploadObj.put("uploadDriverImg", driverImgPath);

            //判断车辆位置和手机上传位置是否到达目标区域(有多个区域时, 到达任意一个就算作到达)
            boolean isCarArrive = false;
            boolean isUserArrive = false;
            whereCase = "customerName = '" + orderInfo.getCustomerName() + "' and factoryName = '" + orderInfo.getFactoryName() + "'";
            List cusList = commonController.dataBaseQuery("table", "com.cn.bean.", "CustomerInfo", "*", whereCase, Integer.MAX_VALUE, 1, "CustomerName", 0, DatabaseOpt.DATA);
            if (cusList != null && cusList.size() > 0) {
                for (int i = 0; i < cusList.size(); i++) {
                    CustomerInfo customerInfo = (CustomerInfo) cusList.get(i);
                    if (!isCarArrive)
                        isCarArrive = isArrive(carAddress, latest.getLat(), latest.getLng(), customerInfo);
                    if (!isUserArrive)
                        isUserArrive = isArrive(address, paramsJson.getString("curLat"), paramsJson.getString("curLog"), customerInfo);
                }
            } else {
                //没有客户基础信息(测试时可以通过, 正式上线需要另做处理)
                isCarArrive = true;
                isUserArrive = true;
            }
            
            //判断车辆位置是否在行政区域
            if (!isCarArrive) {
                uploadObj.put("uploadStatus", -1);
                uploadObj.put("uploadMessage", "车辆位置没有在目的地区域内");
                int result = commonController.dataBaseOperate("[" + uploadObj.toJSONString() + "]", "com.cn.bean.", "OrderUpload", "add", DatabaseOpt.DATA).get(0);
                if (result == 0) {
                    return Units.objectToJson(-1, "信息上报成功, 您的车辆当前没有在目的地区域内", null);
                } else {
                    return Units.objectToJson(-1, "信息上报失败", null);
                }
            }

            //判断手机上传位置是否在行政区域
            if (!isUserArrive) {
                uploadObj.put("uploadStatus", -1);
                uploadObj.put("uploadMessage", "上传位置没有在目的地区域内");
                int result = commonController.dataBaseOperate("[" + uploadObj.toJSONString() + "]", "com.cn.bean.", "OrderUpload", "add", DatabaseOpt.DATA).get(0);
                if (result == 0) {
                    return Units.objectToJson(-1, "信息上报成功, 您当前没有在目的地区域内", null);
                } else {
                    return Units.objectToJson(-1, "信息上报失败", null);
                }
            }

            if (orderInfo.isIsRequireImg()) {
                //识别车牌号并判断
                //Response r = Units.getCarNoWithImg(paramsJson.getString("carImg").replace("data:image/jpeg;base64,", ""));//使用上传的base64数据车牌识别失败, 没有查到原因
                Response r = Units.getCarNoWithImg(Units.getImageStr(servletPath + "upload/" + carImgPath + ".jpg"));
                if (r.getStatus() == 0 && r.getData().compareTo(orderInfo.getCarNO()) == 0) {
                    uploadObj.put("uploadStatus", 0);
                    uploadObj.put("uploadMessage", "任务完成");
                    int result = commonController.dataBaseOperate("[" + uploadObj.toJSONString() + "]", "com.cn.bean.", "OrderUpload", "add", DatabaseOpt.DATA).get(0);
                    if (result == 0) {
                        return updateOrderInfoFinished(orderInfo.getOrderID());
                    } else {
                        return Units.objectToJson(-1, "信息上报失败", null);
                    }
                    
                } else {
                    uploadObj.put("uploadStatus", -1);
                    uploadObj.put("uploadMessage", "上报车辆照片不包含指定车牌号");
                    int result = commonController.dataBaseOperate("[" + uploadObj.toJSONString() + "]", "com.cn.bean.", "OrderUpload", "add", DatabaseOpt.DATA).get(0);
                    if (result == 0) {
                        return Units.objectToJson(-1, "信息上报成功, 您上报的车辆照片不包含指定车牌号", null);
                    } else {
                        return Units.objectToJson(-1, "信息上报失败", null);
                    }
                }
            } else {
                return updateOrderInfoFinished(orderInfo.getOrderID());
            }
        } else {
            return Units.objectToJson(-1, "您当前没有正在进行的任务", null);
        }
    }
    
    /**
     * 判断给定的地址和经纬度是否在目标区域内
     * @param address -- 地址(包含行政区域信息, 该地址信息也是由经纬度解析得来, 实际就是判断指定点是否在多边形区域)
     * @param lat -- 纬度
     * @param lng -- 经度
     * @param info -- 目标区域信息, 包含行政区域信息和自定义区域信息
     * @return 
     */
    private boolean isArrive(String address, String lat, String lng, CustomerInfo info) {
        boolean isArrive = Units.isAddressContainsAdCode(address, info.getCountyGB());
        if (isArrive && !Units.strIsEmpty(info.getPolygonPath())) {
            //目标点
            Point2D.Double point = GeoUtils.buildPoint(Double.valueOf(lat), Double.valueOf(lng));
            //多边形区域
            List<Point2D.Double> polygon = new ArrayList<>();
            
            JSONArray pathAry = JSONArray.parseArray(info.getPolygonPath());
            for (int i = 0; i < pathAry.size(); i++) {
                JSONObject obj = pathAry.getJSONObject(i);
                Point2D.Double pathPoint = GeoUtils.buildPoint(obj.getDouble(lat), obj.getDouble(lng));
                polygon.add(pathPoint);
            }
            
            return GeoUtils.isPointInPolygon(point, polygon);
        }
        return isArrive;
    }

    /**
     * 将指定订单修改为完成状态
     * @param orderID
     * @return
     * @throws Exception 
     */
    private String updateOrderInfoFinished(String orderID) throws Exception {
        CommonController commonController = new CommonController();
        JSONArray paramAry = new JSONArray();
        JSONObject updateObj = new JSONObject();
        updateObj.put("orderStatus", 1);
        JSONObject whereObj = new JSONObject();
        whereObj.put("orderID", orderID);
        paramAry.add(updateObj);
        paramAry.add(whereObj);
        int result = commonController.dataBaseOperate(paramAry.toJSONString(), "com.cn.bean.", "OrderInfo", "update", DatabaseOpt.DATA).get(0);
        if (result == 0) {
            return Units.objectToJson(0, "信息上报成功, 您本次任务已完成", null);
        } else {
            return Units.objectToJson(-1, "数据提交出错", null);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String params = request.getParameter("params");
//        String params = new String(request.getQueryString().getBytes("iso-8859-1"),"utf-8").replaceAll("%22", "\"");
        processRequest(request, response, params);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String params = getRequestPostStr(request);
        processRequest(request, response, params);
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    private byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.servlet;

import com.alibaba.fastjson.JSONObject;
import com.cn.bean.CarInfo;
import com.cn.bean.DriverInfo;
import com.cn.controller.InterfaceController;
import com.cn.task.SyncGPSData;
import com.cn.task.SyncGPSStatus;
import com.cn.util.DatabaseOpt;
import com.cn.util.RedisAPI;
import com.cn.util.Units;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
public class PCInterface extends HttpServlet {

    private static final Logger logger = Logger.getLogger(PCInterface.class);

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
        InterfaceController interfaceController = new InterfaceController();
        String json = null;
        //logger.info(Units.getIpAddress(request) + "accept:" + subUri + ",time:" + (new Date().getTime()));

        try {
            logger.info(subUri + ",params:" + params);
            JSONObject paramsJson = JSONObject.parseObject(params);
            //logger.info("send:" + subUri + ",time:" + paramsJson.getString("timestamp"));
            String module = paramsJson.getString("module");
            String operation = paramsJson.getString("operation");
            String rely = (paramsJson.getString("rely") == null) ? ("{}") : (paramsJson.getString("rely"));
            String target = paramsJson.getString("target");
            String datas = (paramsJson.getString("datas") == null) ? ("") : paramsJson.getString("datas");
            String update = paramsJson.getString("update");
            String add = paramsJson.getString("add");
            String delete = paramsJson.getString("del");
            String item = paramsJson.getString("item");
            String details = paramsJson.getString("details");
            String detail = paramsJson.getString("detail");
            String fileName = paramsJson.getString("fileName");
            String operateType = paramsJson.getString("type");
            String start = paramsJson.getString("start");
            String end = paramsJson.getString("end");
            int isHistory = paramsJson.getIntValue("isHistory");
            int pageIndex = paramsJson.getIntValue("pageIndex");
            int pageSize = paramsJson.getIntValue("pageSize");

            //logger.info(Units.getIpAddress(request) + ",accept:" + module + ",time:" + (new Date().getTime()));
            HttpSession session = request.getSession();
            String path = this.getClass().getClassLoader().getResource("/").getPath().replaceAll("%20", " ");
            String importPath = getServletContext().getRealPath("/").replace("\\", "/") + "excelFile/";
            String filePath = getServletContext().getRealPath("/").replace("\\", "/") + "exportFile/";
            String servletPath = getServletContext().getContextPath();

            /*验证是否登陆*/
            if ((!"userLogin".equals(module) || !"版本信息".equals(module))
                    && (session.getAttribute("user") == null || session.getAttribute("loginType") == null)) {
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

            switch (module) {
                /**
                 * ***************************************基础信息管理**************************************
                 */
                //<editor-fold desc="基础信息管理">

                //<editor-fold desc="行政区域管理">
                case "行政区域管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "StationInfo", "StationID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "StationInfo", "StationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "StationInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "StationInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "StationInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "StationInfo", "StationID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "StationInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="定点区域">
                case "定点区域": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "view", "com/cn/json/", "com.cn.bean.", "CircleInfo", "CircleName", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "view", "CircleInfo", "DestinationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "CircleInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "CircleInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CircleInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "CircleInfo", "CircleName", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("countyGB") == 0) {
                                String[] keys = {"countyGB", "destinationStation", "countyName"};
                                String[] keysName = {"行政区域编码", "行政区域名称", "区域名称"};
                                int[] keysWidth = {50, 50, 0};
                                String[] fieldsName = {"countyGB", "stationName", "countyName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "StationInfo", "StationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "CircleInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>
                
                //<editor-fold desc="手绘区域">
                case "手绘区域": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "view", "com/cn/json/", "com.cn.bean.", "PolygonInfo", "PolygonName", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "view", "PolygonInfo", "PolygonName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "PolygonInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "PolygonInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "PolygonInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "PolygonInfo", "PolygonName", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("countyGB") == 0) {
                                String[] keys = {"countyGB", "destinationStation", "countyName"};
                                String[] keysName = {"行政区域编码", "行政区域名称", "区域名称"};
                                int[] keysWidth = {50, 50, 0};
                                String[] fieldsName = {"countyGB", "stationName", "countyName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "StationInfo", "StationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "PolygonInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>
                
                //<editor-fold desc="目的地管理">
                case "目的地管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "DestinationInfo", "DestinationID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "DestinationInfo", "DestinationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "DestinationInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "DestinationInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "DestinationInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "DestinationInfo", "DestinationID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("countyGB") == 0) {
                                String[] keys = {"countyGB", "destinationStation", "countyName"};
                                String[] keysName = {"行政区域编码", "行政区域名称", "区域名称"};
                                int[] keysWidth = {50, 50, 0};
                                String[] fieldsName = {"countyGB", "stationName", "countyName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "StationInfo", "StationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "DestinationInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="厂区管理">
                case "厂区管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "FactoryInfo", "FactoryName", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "FactoryInfo", "FactoryName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "FactoryInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "FactoryInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "FactoryInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "FactoryInfo", "FactoryName", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "FactoryInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="客户管理">
                case "客户管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "view", "com/cn/json/", "com.cn.bean.", "CustomerInfo", "CustomerName", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "view", "CustomerInfo", "CustomerName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "CustomerInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "CustomerInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CustomerInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "CustomerInfo", "CustomerName", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("countyGB") == 0) {
                                String[] keys = {"countyGB", "destinationStation", "countyName"};
                                String[] keysName = {"行政区域编码", "行政区域名称", "区域名称"};
                                int[] keysWidth = {50, 50, 0};
                                String[] fieldsName = {"countyGB", "stationName", "countyName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "StationInfo", "StationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            if (target.compareToIgnoreCase("factoryName") == 0) {
                                String[] keys = {"factoryName", "companyName"};
                                String[] keysName = {"工厂名称", "公司名称"};
                                int[] keysWidth = {50, 50};
                                String[] fieldsName = {"factoryName", "companyName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "FactoryInfo", "FactoryName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            if (target.compareToIgnoreCase("destinationName") == 0) {
                                JSONObject relyObj = JSONObject.parseObject(rely);
                                
                                String[] keys = {"destinationName", "destinationContent"};
                                String[] keysName = {"区域名称", "区域内容"};
                                int[] keysWidth = {50, 50};
                                if (relyObj.getString("destinationType").compareTo("定点区域") == 0) {
                                    String[] fieldsName = {"circleName", "circleContent"};
                                    json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "CircleInfo", "CircleName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                                } else if (relyObj.getString("destinationType").compareTo("手绘区域") == 0) {
                                    String[] fieldsName = {"polygonName", "polygonPath"};
                                    json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "PolygonInfo", "PolygonName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                                    //System.out.println("json:" + json);
                                } else {
                                    json = Units.objectToJson(-1, "内部错误, 请联系管理员!", null);
                                }
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "CustomerInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="物料管理">
                case "物料管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "GoodsInfo", "GoodsName", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "GoodsInfo", "GoodsName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "GoodsInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "GoodsInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "GoodsInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "GoodsInfo", "GoodsName", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "GoodsInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="车辆管理">
                case "车辆管理": {
                    switch (operation) {
                        case "create": {
                            String whereCase = "isTemp = 0";
                            json = interfaceController.createOperateWithFilter(15, "table", "com/cn/json/", "com.cn.bean.", "CarInfo", whereCase, "CarID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            String whereCase = "isTemp = 0";
                            json = interfaceController.queryOperateWithFilter("com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "CarInfo", importPath + fileName, DatabaseOpt.DATA);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "CarInfo", null);
                            break;
                        }
                        case "export": {
                            //json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CarInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            String whereCase = "isTemp = 0";
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CarInfo", (ArrayList<Object>) interfaceController.queryOperate("com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("autoStylingName") == 0) {
                                String[] keys = {"autoStylingName"};
                                String[] keysName = {"使用车型"};
                                int[] keysWidth = {100};
                                String[] fieldsName = {"autoStylingName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "AutoStyling", "AutoStylingName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "CarInfo", update, add, delete, "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "disable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "CarInfo", datas, "isCanUse", "1", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "enable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "CarInfo", datas, "isCanUse", "0", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "syncdata": {
                            new Thread(new SyncGPSData()).start();
                            json = Units.objectToJson(0, "同步完成!", null);
                            break;
                        }
                        case "syncstatus": {
                            new Thread(new SyncGPSStatus()).start();
                            json = Units.objectToJson(0, "同步完成!", null);
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>
                
                //<editor-fold desc="临时车辆">
                case "临时车辆": {
                    switch (operation) {
                        case "create": {
                            //json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "CarInfo", "CarID", DatabaseOpt.DATA);
                            String whereCase = "isTemp = 1";
                            json = interfaceController.createOperateWithFilter("com/cn/json/", "com.cn.bean.", "TempCarInfo", "tblCarInfo", whereCase, "CarID", 15, DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            String whereCase = "isTemp = 1";
                            json = interfaceController.queryOperateWithFilter("com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "export": {
                            String whereCase = "isTemp = 1";
                            //json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CarInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CarInfo", (ArrayList<Object>) interfaceController.queryOperate("com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "CarInfo", update, add, delete, "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "disable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "CarInfo", datas, "isCanUse", "1", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "enable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "CarInfo", datas, "isCanUse", "0", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            CarInfo info = (CarInfo) iterator.next();
                                            RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="司机管理">
                case "司机管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "DriverInfo", "DriverID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "DriverInfo", "DriverID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "DriverInfo", importPath + fileName, DatabaseOpt.DATA);
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "DriverInfo", "DriverID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            DriverInfo info = (DriverInfo) iterator.next();
                                            RedisAPI.set("driverInfo_" + info.getDriverID(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "DriverInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "DriverInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "DriverInfo", "DriverID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("autoStylingName") == 0) {
                                String[] keys = {"autoStylingName"};
                                String[] keysName = {"使用车型"};
                                int[] keysWidth = {100};
                                String[] fieldsName = {"autoStylingName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "AutoStyling", "AutoStylingName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "DriverInfo", update, add, delete, "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "DriverInfo", "DriverID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            DriverInfo info = (DriverInfo) iterator.next();
                                            RedisAPI.set("driverInfo_" + info.getDriverID(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "disable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "DriverInfo", datas, "isCanUse", "1", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "DriverInfo", "DriverID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            DriverInfo info = (DriverInfo) iterator.next();
                                            RedisAPI.set("driverInfo_" + info.getDriverID(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                        case "enable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "DriverInfo", datas, "isCanUse", "0", "data");
                            new Thread() {
                                @Override
                                public void run() {
                                    try {
                                        /*导入部品基础信息到Redis中*/
                                        List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "DriverInfo", "DriverID", "*", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                                        Iterator<Object> iterator = list.iterator();
                                        while (iterator.hasNext()) {
                                            DriverInfo info = (DriverInfo) iterator.next();
                                            RedisAPI.set("driverInfo_" + info.getDriverID(), JSONObject.toJSONString(info));
                                        }
                                    } catch (Exception e) {
                                        logger.error("Redis数据同步出错!", e);
                                    }
                                }
                            }.start();
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="车辆绑定">
                case "车辆绑定": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "view", "com/cn/json/", "com.cn.bean.", "CarDriverInfo", "CarID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "view", "CarDriverInfo", "CarID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "CarDriverInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "CarDriverInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "CarDriverInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "CarDriverInfo", "CarID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "request_table": {
                            if (target.compareToIgnoreCase("driverID") == 0) {
                                String[] keys = {"driverID", "driverName"};
                                String[] keysName = {"司机ID", "司机名称"};
                                int[] keysWidth = {50, 50};
                                String[] fieldsName = {"driverID", "driverName"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "DriverInfo", "DriverID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            if (target.compareToIgnoreCase("carID") == 0) {
                                String[] keys = {"carID", "carNO"};
                                String[] keysName = {"车辆ID", "车牌号"};
                                int[] keysWidth = {50, 50};
                                String[] fieldsName = {"carID", "carNO"};
                                json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
                            }
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "CarDriverInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="用户管理">
                case "用户管理": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "UserInfo", "UserID", DatabaseOpt.DATA);
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "UserInfo", "UserID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            json = interfaceController.importData("com.cn.bean.", "UserInfo", importPath + fileName, DatabaseOpt.DATA);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "UserInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "UserInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "UserInfo", "UserID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "UserInfo", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //</editor-fold>
                
                //<editor-fold desc="订单管理">
                //<editor-fold desc="订单发布">
                case "订单发布": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "view", "com/cn/json/", "com.cn.bean.", "OrderInfo", "OrderID", DatabaseOpt.DATA);
                            json = Units.insertStr(json, "\\\"订单编号\\", ",@CON-" + Units.getNowTimeNoSeparator());
                            json = Units.insertStr(json, "\\\"开票时间\\", ",@" + Units.getNowTime());
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "view", "OrderInfo", "OrderID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "import": {
                            //json = interfaceController.importData("com.cn.bean.", "OrderInfo", importPath + fileName, DatabaseOpt.DATA);
                            json = Units.objectToJson(-1, "功能已禁用", null);
                            break;
                        }
                        case "exportTemplate": {
                            json = interfaceController.exportTemplate(filePath, servletPath, "com.cn.bean.", "OrderInfo", null);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "OrderInfo", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "OrderInfo", "OrderID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "enable": {
                            json = interfaceController.batchUpdateField("com.cn.bean.", "OrderInfo", datas, "orderStatus", "1", "data");
                            break;
                        }
                        case "submit": {
                            json = Units.objectToJson(-1, "功能已禁用", null);
                            //json = interfaceController.submitOperate("com.cn.bean.", "OrderInfo", update, add, delete, "data");
                            break;
                        }
                        case "request_table": {
                            json = Units.objectToJson(-1, "数据为空", null);
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>

                //<editor-fold desc="订单完成上报">
                case "订单完成上报": {
                    switch (operation) {
                        case "create": {
                            json = interfaceController.createOperate(15, "table", "com/cn/json/", "com.cn.bean.", "OrderUpload", "OrderID", DatabaseOpt.DATA);
                            json = Units.insertStr(json, "\\\"订单ID\\", ",@CON-" + Units.getNowTimeNoSeparator());
                            break;
                        }
                        case "request_page": {
                            json = interfaceController.queryOperate("com.cn.bean.", "table", "OrderUpload", "OrderID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex);
                            break;
                        }
                        case "export": {
                            json = interfaceController.exportData(filePath, servletPath, "com.cn.bean.", "OrderUpload", (ArrayList<Object>) interfaceController.queryData("com.cn.bean.", "table", "OrderUpload", "OrderID", datas, DatabaseOpt.DATA, Integer.MAX_VALUE, 1));
                            break;
                        }
                        case "submit": {
                            json = interfaceController.submitOperate("com.cn.bean.", "OrderUpload", update, add, delete, "data");
                            break;
                        }
                    }
                    break;
                }
                //</editor-fold>
                //</editor-fold>
            }
            //logger.info(Units.getIpAddress(request) + ",response:" + module + ",time:" + (new Date().getTime()));
        } catch (Exception e) {
            logger.info(subUri);
            logger.error("错误信息:" + e.getMessage(), e);
            json = Units.objectToJson(-1, "输入参数错误!", e.toString());
        }

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

    private String orderRequestTable(String target, String datas, String rely, int pageIndex, int pageSize) throws Exception {
        String json = null;
        InterfaceController interfaceController = new InterfaceController();
        if (target.compareToIgnoreCase("carNO") == 0) {
            String[] keys = {"carID", "carNO"};
            String[] keysName = {"车辆ID", "车牌号"};
            int[] keysWidth = {50, 50};
            String[] fieldsName = {"carID", "carNO"};
            String whereCase = "CarID not in (select CarID from tblOrderInfo where OrderStatus = 0)";
            json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "CarInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
        }
        if (target.compareToIgnoreCase("driverID") == 0) {
            String[] keys = {"driverID", "driverName"};
            String[] keysName = {"司机ID", "司机名称"};
            int[] keysWidth = {50, 50};
            String[] fieldsName = {"driverID", "driverName"};
            String whereCase = "DriverID not in (select DriverID from tblOrderInfo where OrderStatus = 0)";
            json = interfaceController.queryOperate(target, "com.cn.bean.", "view", "CarDriverInfo", "CarID", datas, rely, whereCase, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
        }
        if (target.compareToIgnoreCase("factoryName") == 0) {
            String[] keys = {"factoryName", "companyName"};
            String[] keysName = {"工厂名称", "公司名称"};
            int[] keysWidth = {50, 50};
            String[] fieldsName = {"factoryName", "companyName"};
            json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "FactoryInfo", "FactoryName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
        }
        if (target.compareToIgnoreCase("customerName") == 0) {
            String[] keys = {"customerName", "companyName"};
            String[] keysName = {"工厂名称", "公司名称"};
            int[] keysWidth = {50, 50};
            String[] fieldsName = {"factoryName", "companyName"};
            json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "FactoryInfo", "FactoryName", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
        }
        if (target.compareToIgnoreCase("destinationID") == 0) {
            String[] keys = {"destinationID", "destinationName", "countyGB", "destinationStation"};
            String[] keysName = {"目的地ID", "目的地", "行政区域编码", "行政区域"};
            int[] keysWidth = {30, 20, 20, 30};
            String[] fieldsName = {"destinationID", "destinationName", "countyGB", "destinationStation"};
            json = interfaceController.queryOperate(target, "com.cn.bean.", "table", "DestinationInfo", "DestinationID", datas, rely, true, DatabaseOpt.DATA, pageSize, pageIndex, keys, keysName, keysWidth, fieldsName);
        }
        
        return json;
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

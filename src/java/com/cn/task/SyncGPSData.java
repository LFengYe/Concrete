/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.bean.CarInfo;
import com.cn.controller.CommonController;
import com.cn.controller.InterfaceController;
import com.cn.util.DatabaseOpt;
import com.cn.util.RedisAPI;
import com.cn.util.Units;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class SyncGPSData implements Runnable {

    private static final Logger LOG = Logger.getLogger(SyncGPSData.class);

    @Override
    public void run() {
        LOG.info("开始同步车辆资料...");
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = null;
        CallableStatement statement = null;
        try {
            conn = opt.getConnection(DatabaseOpt.GPS_INFO);
            statement = conn.prepareCall("select * from viewSyncGPSData");
            ResultSet set = statement.executeQuery();
            JSONArray addList = new JSONArray();
            JSONArray updateList = new JSONArray();
            
            while (set.next()) {
                String carNo = set.getString("PLATENUMBER");
                String gpsNumber = set.getString("DEVICENUMBER");
                String motorcadeName = set.getString("NAME");
                String expiredDate = set.getString("EXPIREDATE");
                String info = RedisAPI.get("carInfo_" + carNo);
                //System.out.println("row num:" + set.getRow() + ",info:" + info);
                if (Units.strIsEmpty(info)) {
                    //GPS有新增车辆信息
                    JSONObject addObj = new JSONObject();
                    addObj.put("carNO", carNo);
                    addObj.put("loginName", carNo);
                    addObj.put("systemNo", gpsNumber);
                    addObj.put("expiredDate", expiredDate);
                    addObj.put("motorcadeName", motorcadeName);
                    addList.add(addObj);
                } else {
                    CarInfo carInfo = JSONObject.parseObject(info, CarInfo.class);
                    if (carInfo.isGPSDataChange(gpsNumber, motorcadeName, expiredDate)) {
                        //GPS平台和业务平台车辆信息不一致
                        JSONObject setObj = new JSONObject();
                        setObj.put("systemNo", gpsNumber);
                        setObj.put("expiredDate", expiredDate);
                        setObj.put("motorcadeName", motorcadeName);
                        updateList.add(setObj);

                        JSONObject whereObj = new JSONObject();
                        whereObj.put("carID", carInfo.getCarID());
                        updateList.add(whereObj);
                    } else {
                        // GPS平台和业务平台车辆信息一致
                    }
                }
            }
            
            try {
                CommonController controller = new CommonController();
                if (!updateList.isEmpty()) {
                    ArrayList<Integer> result = controller.dataBaseOperate(JSONObject.toJSONString(updateList, Units.features), "com.cn.bean.", "CarInfo", "update", DatabaseOpt.DATA);
                    if (result.get(0) != 0) {
                        LOG.error("同步更新车辆资料失败!");
                    }
                }
                if (addList.size() > 0) {
                    LOG.info("新增条数:" + addList.size());
                    ArrayList<Integer> result = controller.dataBaseOperate(addList.toJSONString(), "com.cn.bean.", "CarInfo", "add", DatabaseOpt.DATA);
                    if (result.get(0) != 0) {
                        LOG.error("同步添加车辆资料失败!");
                    }
                }

                try {
                    /*导入部品基础信息到Redis中*/
                    InterfaceController interfaceController = new InterfaceController();
                    List<Object> list = interfaceController.queryData("com.cn.bean.", "table", "CarInfo", "CarID", "", DatabaseOpt.DATA, Integer.MAX_VALUE, 1);
                    Iterator<Object> iterator = list.iterator();
                    while (iterator.hasNext()) {
                        CarInfo info = (CarInfo) iterator.next();
                        RedisAPI.set("carInfo_" + info.getCarNO(), JSONObject.toJSONString(info));
                    }
                } catch (Exception e) {
                    LOG.error("Redis车辆数据同步出错!", e);
                }
            } catch (Exception e) {
                LOG.error("同步车辆资料出错!", e);
            }
        } catch (SQLException e) {
            LOG.error("数据库操作失败!", e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                LOG.error("数据库关闭失败!", e);
            }
        }
        LOG.info("同步车辆资料完成...");
    }

    public static String escape(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] b = md.digest();
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; ++offset) {
            int m = b[offset];
            if (m < 0) {
                m += 256;
            }
            if (m < 16) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(m));
        }
        return buf.toString();
    }

}

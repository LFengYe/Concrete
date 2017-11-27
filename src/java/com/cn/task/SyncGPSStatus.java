/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.controller.CommonController;
import com.cn.util.DatabaseOpt;
import com.cn.util.Units;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author LFeng
 */
public class SyncGPSStatus implements Runnable {

    private static final Logger LOG = Logger.getLogger(SyncGPSStatus.class);

    @Override
    public void run() {
        LOG.info("开始更新车辆状态...");
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = null;
        CallableStatement statement = null;
        try {
            conn = opt.getConnection(DatabaseOpt.GPS_INFO);
            statement = conn.prepareCall("select * from viewSyncGPSData");
            ResultSet set = statement.executeQuery();
            JSONArray updateList = new JSONArray();
            
            while (set.next()) {
                String carNo = set.getString("PLATENUMBER");
                String gpsTime = set.getString("GPSTIME");
                int gpsExpiredSecond = set.getInt("GPSEXPIREDTIME");
                JSONObject setObj = new JSONObject();
                setObj.put("gpsTime", gpsTime);
                //System.out.println("gpsExpiredSecond:" + gpsExpiredSecond + ",gpsTime:" + gpsTime);
                if (gpsExpiredSecond < 600 && !Units.strIsEmpty(gpsTime)) {//GPS定位时间超过10分钟, 判定为离线
                    setObj.put("isOnline", 1);
                    System.out.println(carNo + "在线");
                } else {
                    setObj.put("isOnline", 0);
                }
                updateList.add(setObj);
                
                JSONObject whereObj = new JSONObject();
                whereObj.put("carNO", carNo);
                updateList.add(whereObj);
            }
            
            try {
                CommonController controller = new CommonController();
                if (!updateList.isEmpty()) {
                    ArrayList<Integer> result = controller.dataBaseOperate(JSONObject.toJSONString(updateList, Units.features), "com.cn.bean.", "CarInfo", "update", DatabaseOpt.DATA);
                    if (result.get(0) != 0) {
                        LOG.error("更新车辆状态失败!");
                    }
                }
            } catch (Exception e) {
                LOG.error("更新车辆状态出错!", e);
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
        LOG.info("更新车辆状态完成...");
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

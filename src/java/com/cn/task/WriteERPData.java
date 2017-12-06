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
import com.cn.util.RedisAPI;
import com.cn.util.Units;
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
public class WriteERPData implements Runnable {

    private static final Logger LOG = Logger.getLogger(WriteERPData.class);

    @Override
    public void run() {
        LOG.info("开始同步订单资料...");
        DatabaseOpt opt = new DatabaseOpt();
        Connection conn = null;
        CallableStatement statement = null;
        try {
            conn = opt.getConnection(DatabaseOpt.ORDER_ZZ);
            statement = conn.prepareCall("insert into tableName() values(?, ?, ?)");
            statement.setString(2, Units.getNowDate() + " 23:59:59");
            ResultSet set = statement.executeQuery();
            JSONArray addList = new JSONArray();
            ArrayList<String> orderIDList = new ArrayList<>();
            while (set.next()) {
                String orderID = set.getString("VGP_LadeID");
                String info = RedisAPI.get("orderInfo_" + orderID);
                if (Units.strIsEmpty(info)) {
                    //新增订单
                    JSONObject addObj = new JSONObject();
                    addObj.put("orderID", orderID);
                    addObj.put("orderTime", set.getString("VGP_CDate"));
                    addObj.put("carNO", set.getString("VGP_CarCODE"));
                    addObj.put("customerName", set.getString("VGP_Name"));
                    addObj.put("factoryName", set.getString("VGP_Firm"));
                    addObj.put("goodsName", set.getString("VGP_Mater"));
                    addObj.put("areaName", set.getString("VGP_Block"));

                    addList.add(addObj);
                    orderIDList.add(orderID);
                } else {
                    //已同步订单
                }
            }

            try {
                CommonController controller = new CommonController();
                if (addList.size() > 0) {
                    LOG.info("新增条数:" + addList.size());
                    ArrayList<Integer> result = controller.dataBaseOperate(addList.toJSONString(), "com.cn.bean.", "OrderInfo", "add", DatabaseOpt.DATA);
                    if (result.get(0) == 0) {
                        orderIDList.forEach((str) -> {
                            RedisAPI.set("orderInfo_" + str, str);
                        });
                        LOG.info("同步添加订单资料成功!");
                    } else {
                        LOG.error("同步添加订单资料失败!");
                    }
                }
            } catch (Exception e) {
                LOG.error("同步订单资料出错!", e);
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
        LOG.info("同步订单资料完成...");
    }

}

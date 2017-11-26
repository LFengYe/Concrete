/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.listener;

import com.alibaba.fastjson.JSONObject;
import com.cn.bean.CarInfo;
import com.cn.bean.CustomerInfo;
import com.cn.bean.DriverInfo;
import com.cn.controller.CommonController;
import com.cn.task.SyncERPData;
import com.cn.task.SyncGPSData;
import com.cn.util.DatabaseOpt;
import com.cn.util.RedisAPI;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 *
 * @author LFeng
 */
public class StartInit implements ServletContextListener {

    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(SyncGPSData.class);
    private static final int GPS_SYNC_FREQUENCY = 12;
    private static final int ERP_SYNC_FREQUENCY = 30;
    
    //private ScheduledFuture future;
    private final ScheduledExecutorService timeOutScheduler = Executors.newSingleThreadScheduledExecutor();
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            LOG.info("初始化开始...");
            initData();
            LOG.info("初始化完成...");
            /*
            //向定时任务线程池提交一个固定时间间隔执行的任务
            public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit);
            //向定时任务线程池提交一个固定延时间隔执行的任务
            public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit);
             */
            //30分钟执行一次
            timeOutScheduler.scheduleWithFixedDelay(new SyncGPSData(), 0, GPS_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
//            timeOutScheduler.scheduleWithFixedDelay(new SyncERPData(), 0, ERP_SYNC_FREQUENCY, TimeUnit.MINUTES);
            
            LOG.info("启动定时任务成功!");
            
        } catch (Exception ex) {
            LOG.error("启动定时任务失败!", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (timeOutScheduler != null)
            timeOutScheduler.shutdownNow();
        LOG.info("定时任务结束");
    }

    private void initData() {
        CommonController commonController = new CommonController();
        DatabaseOpt opt = new DatabaseOpt();
        Jedis jedis = RedisAPI.getJedis();
        try {
            Connection conn = opt.getConnection(DatabaseOpt.DATA);

            Transaction transaction = jedis.multi();
            //RedisAPI.flushDB();

            /*导入部品基础信息到Redis中*/
            List<Object> list = commonController.dataBaseQueryWithNotCloseConn("table", "com.cn.bean.", "CarInfo", "*", "", Integer.MAX_VALUE, 1, "CarID", 0, conn);
            Iterator<Object> iterator = list.iterator();
            while (iterator.hasNext()) {
                CarInfo baseInfo = (CarInfo) iterator.next();
                transaction.set("carInfo_" + baseInfo.getCarNO(), JSONObject.toJSONString(baseInfo));
            }

            list.clear();
            /*导入客户基础信息到Redis中*/
            list = commonController.dataBaseQueryWithNotCloseConn("table", "com.cn.bean.", "DriverInfo", "*", "", Integer.MAX_VALUE, 1, "DriverID", 0, conn);
            iterator = list.iterator();
            while (iterator.hasNext()) {
                DriverInfo customer = (DriverInfo) iterator.next();
                transaction.set("driverInfo_" + customer.getDriverID(), JSONObject.toJSONString(customer));
            }
            
            list.clear();
            /*导入客户基础信息到Redis中*/
            list = commonController.dataBaseQueryWithNotCloseConn("table", "com.cn.bean.", "CustomerInfo", "*", "", Integer.MAX_VALUE, 1, "CustomerName", 0, conn);
            iterator = list.iterator();
            while (iterator.hasNext()) {
                CustomerInfo customer = (CustomerInfo) iterator.next();
                transaction.set("customerInfo" + customer.getCustomerName(), JSONObject.toJSONString(customer));
            }

            transaction.exec();
            if (conn != null) {
                conn.close();
            }

        } catch (Exception e) {
            LOG.error("初始化出错!", e);
        }
    }
}

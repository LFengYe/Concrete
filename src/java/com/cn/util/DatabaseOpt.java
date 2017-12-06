/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cn.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author LFeng
 */
public class DatabaseOpt {

    public static final String ORDER_ZZ = "zizhong";
    public static final String ORDER_RF = "ruifeng";
    public static final String ORDER_RD = "ruida";
    public static final String ORDER_JB = "jianbao";
    public static final String ORDER_RX = "ruixing";
    public static final String DATA = "data";
    public static final String HIS = "isHis";
    public static final String GPS_LOCAL = "gpsLocal";
    public static final String GPS_INFO = "gpsInfo";
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DatabaseOpt.class);

    public Connection getConnectOrderZZ() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("sqlserverDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("orderUrl_zizhong"), prop.getProperty("orderUser"), prop.getProperty("orderPassword"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectOrderRF() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("sqlserverDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("orderUrl_ruifeng"), prop.getProperty("orderUser"), prop.getProperty("orderPassword"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectOrderRD() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("sqlserverDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("orderUrl_ruida"), prop.getProperty("orderUser"), prop.getProperty("orderPassword"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectOrderJB() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("sqlserverDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("orderUrl_jianbao"), prop.getProperty("orderUser"), prop.getProperty("orderPassword"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectOrderRX() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("sqlserverDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("orderUrl_ruixing"), prop.getProperty("orderUser"), prop.getProperty("orderPassword"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectHis() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));

            Class.forName(prop.getProperty("mysqlDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("hisUrl"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnectGPSLocal() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));

            Class.forName(prop.getProperty("mysqlDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("gpsLocalUrl"));
            return connect;
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        }
        return null;
    }

    public Connection getConnectGPSInfo() {
        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));

            Class.forName(prop.getProperty("mysqlDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("gpsInfoUrl"));
            return connect;
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        }
        return null;
    }

    /**
     * 连接数据库
     *
     * @return
     */
    public Connection getConnect() {

        try {
            Properties prop = new Properties();
            prop.load(DatabaseOpt.class.getClassLoader().getResourceAsStream("./config.properties"));
            Class.forName(prop.getProperty("mysqlDriver"));
            Connection connect = DriverManager.getConnection(prop.getProperty("url"));
            return connect;
        } catch (ClassNotFoundException ex) {
            LOG.error("找不类名错误", ex);
        } catch (IOException ex) {
            LOG.error("IO错误", ex);
        } catch (SQLException ex) {
            LOG.error("SQL错误", ex);
        } finally {
        }
        return null;
    }

    public Connection getConnection(String connType) {
        Connection conn;
        switch (connType) {
            case DATA:
                conn = getConnect();
                break;
            case HIS:
                conn = getConnectHis();
                break;
            case GPS_LOCAL:
                conn = getConnectGPSLocal();
                break;
            case GPS_INFO:
                conn = getConnectGPSInfo();
                break;
            case ORDER_ZZ:
                conn = getConnectOrderZZ();
                break;
            case ORDER_RF:
                conn = getConnectOrderRF();
                break;
            case ORDER_RD:
                conn = getConnectOrderRD();
                break;
            case ORDER_RX:
                conn = getConnectOrderRX();
                break;
            case ORDER_JB:
                conn = getConnectOrderJB();
                break;
            default:
                conn = getConnect();
                break;
        }
        return conn;
    }
}

package com.broad.kq.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


/**
 * @author vill
 *
 */
public class DBUtil_MS {
    private static String driver = PropertyUtil.getProperty("jdbc.mysql.driver");
    private static String url = PropertyUtil.getProperty("jdbc.mysql.url");
    private static String user = PropertyUtil.getProperty("jdbc.mysql.un");
    private static String pwd = PropertyUtil.getProperty("jdbc.mysql.pw");

    /**
     * 获取与数据库的连接
     * @return Connection
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        Class.forName(driver);
        return DriverManager.getConnection(url, user, pwd);
    }
    /**
     * 关闭连接
     * @param con
     * @param stm
     * @param rst
     */
    public static void closeConnection(Connection con, Statement stm, ResultSet rst){
        try {
            if (con != null) con.close();
            if (stm != null) stm.close();
            if (rst != null) rst.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("error");
        }

    }

    public static void main(String[] args) throws Exception {
        System.out.println(new DBUtil_MS().getConnection().getClass().getName());
    }


}
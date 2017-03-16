package com.broad.kq.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * 检查机器参数是否匹配
 */
// 防止用户偷改机器打卡
public class CheckUtil {

    public static String MD5(String str) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    public static boolean isAllow(String mac,String mid) {
        boolean flag = false;
        //("BROAD" + mac 最后两位 + 机器序列号) 的md5加密
        try{
            String checkId = PropertyUtil.getProperty("kqj.id").toUpperCase();
            String[] macs = mac.split(":");
            String machineId = MD5(
                    "BROAD"             // BROAD 注意是大写
                    + macs[macs.length-1]   // mac最后冒号后最后两位
                    + mid                   // 机器序列号
            ).toUpperCase();
            flag = checkId.equals(machineId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }


}

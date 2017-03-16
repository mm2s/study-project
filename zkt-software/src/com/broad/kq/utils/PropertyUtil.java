package com.broad.kq.utils;

import java.io.*;
import java.util.Properties;

/**
 * properties文件获取工具类
 */
public class PropertyUtil {

    private static Properties props;

    static {
        init();
    }

    synchronized static private void init() {
        props = new Properties();
        InputStream in = null;
        try {
            String path = getPath("res", "kq.properties");
            in = new FileInputStream(path);
            props.load(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != in) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getProperty(String key) {
        if (null == props) init();
        return props.getProperty(key);
    }

    private static String getPath(String parentDir, String fileName) {
        String path;
        String userDir = System.getProperty("user.dir");
        String userDirName = new File(userDir).getName();
        if (userDirName.equalsIgnoreCase("lib")
                || userDirName.equalsIgnoreCase("bin")) {

            File newF = new File(userDir);
            File newP = new File(newF.getParent());
            if (fileName.trim().equals("")) {
                path = newP.getPath() + File.separator + parentDir;
            } else {
                path = newP.getPath() + File.separator + parentDir + File.separator + fileName;
            }
        } else {
            if (fileName.trim().equals("")) {
                path = userDir + File.separator + parentDir;
            } else {
                path = userDir + File.separator + parentDir + File.separator + fileName;
            }
        }
        return path;

    }
}
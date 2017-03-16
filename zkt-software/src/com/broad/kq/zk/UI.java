package com.broad.kq.zk;

import com.broad.kq.utils.CheckUtil;
import com.broad.kq.utils.PropertyUtil;
import com.jacob.com.MainSTA;
import com.zk.sdk.ZkemSDK;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * java 桌面ui 程序
 * 控制机器的启动和关闭
 */
public class UI extends Thread {

    private static ZkemSDK sdk;
    private static MainSTA sta;

    public UI() {}

    public UI(ZkemSDK sdk, MainSTA sta) {
        super();
        this.sdk = sdk;
        this.sta = sta;
    }

    public void run() {

        synchronized (sdk) {
            // 创建 JFrame 实例
            JFrame frame = new JFrame("远大考勤系统");
            frame.setSize(250, 250);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setResizable(false);

            JPanel panel = new JPanel();
            frame.add(panel);
            panel.setLayout(null);

            // 添加文本信息
            text_panel(panel);
            close_btn(panel);

            frame.setVisible(true);
        }

    }

    /**
     * 文本显示内容
     */
    private static void text_panel(JPanel panel) {
//        ZkemSDK sdk = new ZkemSDK();
        int machineNumber = Integer.parseInt(PropertyUtil.getProperty("kqj.machineNumber"));
        boolean connect = sdk.connect();

        int x = 20;
        int y = 20;
        int w = 500;
        int h = 25;

        if (connect) {
            boolean isAllow = CheckUtil.isAllow(
                    sdk.GetDeviceMAC(machineNumber),
                    sdk.GetSerialNumber(machineNumber)
            );

            if (isAllow) {
                JLabel label0 = new JLabel("连接成功 !!! ");
                label0.setBounds(x, y, w, h);
                panel.add(label0);
            } else {
                JLabel label0 = new JLabel("机器参数非法!!! ");
                label0.setBounds(x, y, w, h);
                label0.setForeground(Color.red);
                panel.add(label0);
            }

            // 机器制造商名称
            JLabel label1 = new JLabel(
                    "机器制造商名称   :  "
                            + sdk.GetVendor());
            y = y + 30;
            label1.setBounds(x, y, w, h);
            panel.add(label1);

            JLabel label2 = new JLabel(
                    " 机器名称   :  "
                            + sdk.GetProductCode(machineNumber));
            y = y + 30;
            label2.setBounds(x, y, w, h);
            panel.add(label2);

            JLabel label3 = new JLabel(
                    " 机器序列号   :  "
                            + sdk.GetSerialNumber(machineNumber));
            y = y + 30;
            label3.setBounds(x, y, w, h);
            panel.add(label3);

            JLabel label4 = new JLabel(
                    "  mac地址   :  "
                            + sdk.GetDeviceMAC(machineNumber));
            y = y + 30;
            label4.setBounds(x, y, w, h);
            panel.add(label4);

            // 断开连接
            sdk.Disconnect();
        } else {
            JLabel label0 = new JLabel("连接失败, 请检查连接 !!! ");
            label0.setBounds(x, y, w, h);
            panel.add(label0);
        }
    }

    /**
     * 关闭按钮
     */
    private static void close_btn(JPanel panel) {
        // 关闭按钮
        JButton closeButton = new JButton("退出");
        closeButton.setBounds(90, 180, 60, 25);
        panel.add(closeButton);
        closeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sdk.Disconnect();
                        System.exit(0);
                    }
                });

        // 关闭监听
        JButton closejtButton = new JButton("监听");
        closejtButton.setBounds(160, 180, 60, 25);
        panel.add(closejtButton);
        closejtButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sta.quit();

                    }
                });

        // 重启
        JButton openAgainButton = new JButton("重启");
        openAgainButton.setBounds(20, 180, 60, 25);
        panel.add(openAgainButton);
        openAgainButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        sdk.connect();
//                        sta.doMessagePump();
                        ss(sta.getState());
                    }
                });


    }

    private static void ss(Object o) {
        System.out.println(o);
    }
}

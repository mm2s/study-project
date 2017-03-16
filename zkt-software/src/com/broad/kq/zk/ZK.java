package com.broad.kq.zk;

import com.broad.kq.utils.CheckUtil;
import com.broad.kq.utils.PropertyUtil;
import com.jacob.com.MainSTA;
import com.zk.sdk.ZkemSDK;

/**
 * 中控考勤机监听程序
 */
public class ZK extends Thread {

    private ZkemSDK sdk;
    private MainSTA sta;

    public ZK() {}

    public ZK(ZkemSDK sdk,MainSTA sta) {
        super();
        this.sdk = sdk;
        this.sta = sta;
    }

    public synchronized void run() {
        synchronized (sdk) {
            // 机器号
            Integer num = Integer.parseInt(PropertyUtil.getProperty("kqj.machineNumber"));

            // 获得实例,创建连接
//            ZkemSDK sdk = new ZkemSDK();
            boolean connect = sdk.connect();

            if (connect) {
                // 机器码是否合法
                boolean isAllow = CheckUtil.isAllow(
                        sdk.GetDeviceMAC(num),
                        sdk.GetSerialNumber(num)
                );

                if (isAllow) {
                    // 注册监听事件
                    sdk.RegEvent(num);
                    // 监听
                    sta.doMessagePump();
//                    sta.run();
                }
            }

            System.out.println("关闭");

            // 关闭连接
            sdk.Disconnect();
        }
    }

}

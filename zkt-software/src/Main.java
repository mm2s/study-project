import com.broad.kq.utils.HttpUtil;
import com.broad.kq.zk.UI;
import com.broad.kq.zk.ZK;
import com.jacob.com.MainSTA;
import com.zk.sdk.ZkemSDK;

/**
 * 主程序入口
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        // 线程同步锁
        ZkemSDK sdk = new ZkemSDK();
        MainSTA sta = new MainSTA();

        HttpUtil.getSalt();

        // ui 线程,控制程序可见性
        Thread ui = new UI(sdk,sta);
        ui.start();

        Thread.sleep(80);

        // 中控 监听程序
        Thread zk = new ZK(sdk,sta);
        zk.setDaemon(true);
        zk.start();

    }
}

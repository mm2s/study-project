package com.zk.sdk;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.broad.kq.utils.Base64Util;
import com.broad.kq.utils.HttpUtil;
import com.broad.kq.utils.PropertyUtil;
import com.jacob.com.Variant;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EventObject;

public class ZkemEvent extends EventObject {

    public ZkemEvent(Object source) {
        super(source);
    }

    /**
     * 当成功连接机器时触发该事件
     */
    public void OnConnected(Variant[] arge) {
        System.out.print("当成功连接机器时触发该事件，无返回值 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当断开机器连接时触发该事件
     */
    public void OnDisConnected(Variant[] arge) {
        System.out.print("当断开机器时触发该事件，无返回值 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当机器警报的时候触发该事件
     * 函数原型 OnAlarm(long AlarmType,long EnrollNumber,long Verified)
     * 函数说明:
     * AlarmType:警报类型 55为拆机报警，58为错按报警，32为胁迫报警，34为反潜报警
     * EnrollNumber:当拆机或按错报警或胁迫警报时，该值为0，当为其他胁迫或反潜报警时，该值为用户id号
     * Verifyed:当为拆机或错按该报警或胁迫按键报警时，该值为0，其余报警该值为1
     */
    public void OnAlarm(Variant[] arge) {
        System.out.print("当机器报警时触发该事件 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当机器开门时发生该事件
     * 以下参数全为返回值
     * 函数原型:onDoor(long EventType)
     * EventType:开门类型  4表示门未关好或者门已打开，53表示出门按钮，5表示门已关闭，1表示门被意外打开
     */
    public long OnDoor(Variant[] arge) {
        System.out.print("OnDoorEvent ==== ");
        System.out.println(Arrays.toString(arge));
        if (arge[0].getInt() == 4) {
            System.out.print("开门 ==== ");
        } else if (arge[0].getInt() == 5) {
            System.out.print("关门 ==== ");
        } else if (arge[0].getInt() == 53) {
            System.out.print("出门了 ==== ");
        } else if (arge[0].getInt() == 1) {
            System.out.print("门被意外打开 ==== ");
        }
        System.out.println(Arrays.toString(arge));
        return 1;
    }

    /**
     * 当验证通过时触发该事件
     * 以下参数全为返回值
     * 函数原型:OnAttTransactionEx(BSTR ErollNumber,LONG IsInValid,LONG AttState,LONG VerifyMethod,
     * LONG Year,LONG Month,LONG Day,LONG Hour,LONG Minute,LONG Second,LONG WorkCode)
     * <p>
     * EnrollNumber:该用户的UserID
     * IsInValid:该记录是否为有效记录，1为无效记录，0为有效记录
     * AttSate:考勤状态  默认0 Check-In, 1 Check-Out, 2 Break-Out, 3 Break-In, 4 OT-In, 5 OT-Out
     * VerifyMethod:验证方式  0为密码验证，1为指纹验证，2为卡验证
     * Year/Month/Day/Hour/Minute/Second:为验证通过的时间
     * WorkCode:返回验证时WorkCode值，当机器无Workcode功能时，该值返回0
     */
    public void OnAttTransactionEx(Variant[] arge) throws Exception {
        pressKeyboard(encrypt(arge));
    }

    /**
     * 登记指纹时触发该事件
     * 以下参数全为返回值
     * 函数原型:OnEnrollFingerEx(BSTR EnrollNumber,LONG FingerIndex,LONG ActionResult,LONG TemplateLength)
     * <p>
     * EnrollNumber:当前登记指纹的用户ID
     * FingerIndex:当前指纹的索引号
     * ActionResult:操作结果，成功为0，失败则返回值大于0
     * TemplateLength:返回该指纹模板的长度
     */
    public void OnEnrollFingerEx(Variant[] arge) {
        System.out.print("登记指纹时触发该事件 ==== ");
        System.out.println(Arrays.toString(arge));

    }

    /**
     * 当机器上指纹头检测到有指纹时触发该消息
     */
    public void OnFinger(Variant[] arge) throws AWTException {
        System.out.println("开始验证指纹信息 ==== ");
    }

    /**
     * 登记用户指纹时，当有指纹按下时触发该消息
     * 函数原型:OnFingerFeature(LONG Score)
     * Score:该指纹的质量分数
     */
    public void OnFingerFeature(Variant[] arge) {
        System.out.print("登记用户指纹时，当有指纹按下时触发该消息 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当刷卡时触发该事件
     * 函数原型:OnHIDNum(LONG CardNumber)
     * CardNumber:该卡的卡号，卡类型可以是ID卡，HID卡。MIFARE卡需要被作为ID卡使用才会触发该事件
     */
    public void OnHIDNum(Variant[] arge) {
        System.out.print("当刷卡时触发该消息 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当成功登记新用户时触发该消息
     * 函数原型:OnNewUser(LONG EnrollNumber)
     * EnrollNumber:新登记用户的UserID
     */
    public void OnNewUser(Variant[] arge) {
        System.out.print("当成功登记新用户时触发该消息 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当用户验证时触发该消息
     * 函数原型:OnVerify(LONG UserID)
     * UserID:当验证成功返回UserID为该用户ID，当卡验证通过时，该值返回卡号，验证失败时返回-1
     */
    public void OnVerify(Variant[] arge) throws AWTException {
        if (arge[0].getInt() == -1) {
            System.out.println("指纹不匹配 ==== ");
        } else {
            System.out.print("当用户验证时触发该消息 ==== ");
            System.out.println(Arrays.toString(arge));
        }
    }

    /**
     * 当机器进行写卡操作时触发该事件
     * 函数原型:OnWriteCard(LONG EnrollNumber,LONG ActionResult,LONG Length)
     * EnrollNumber:当前需写卡内用户的用户ID
     * ActionResult:为写卡操作的结果，0为成功，其他值为失败
     * Length:为写入卡内总的数据大小
     */
    public void OnWriteCard(Variant[] arge) {
        System.out.print("当机器进行写卡操作时触发该事件 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当情况MIFARE卡操作时触发该事件
     * 函数原型:OnEmptyCard(LONG ActionResult)
     * ActionResult:清卡操作的结果，0为成功，其他值为失败
     */
    public void OnEmptyCard(Variant[] arge) {
        System.out.print("当清空 MIFARE 卡操作时触发该事件 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /**
     * 当机器向SDK发送未知事件时，触发该事件
     * 函数原型:OnEMData(LONG DataType,LONG DataLen,CHAR* DataBuffer)
     * DataType:返回事件类型
     * DataLen:返回整个数据长度
     * DataBuffer:为实际数据
     */
    public void OnEMData(Variant[] arge) {
        System.out.print("当机器向 SDK 发送未知事件时，触发该事件 ==== ");
        System.out.println(Arrays.toString(arge));
    }

    /************************************ 键盘操作 *******************************/
    // 键盘按键灵敏度
    private static final byte DELAY_TIME = Byte.parseByte(PropertyUtil.getProperty("item.delay.time"));

    //单个 按键
    private static void keyPress(Robot r, int key) {
        r.keyPress(key);
        r.keyRelease(key);
        r.delay(DELAY_TIME);
    }

    // ctrl+ 按键
    private static void keyPressWithShift(Robot r, int key) {
        r.keyPress(KeyEvent.VK_SHIFT);
        r.keyPress(key);
        r.keyRelease(key);
        r.keyRelease(KeyEvent.VK_SHIFT);
        r.delay(DELAY_TIME);
    }

    /**
     * 将考勤机加密数据通过模拟键盘 按键输入
     */
    private static void pressKeyboard(String mdkxh) throws AWTException {

        //创建一个键盘对象
        Robot r = new Robot();

        // 循环字符
        for (int i = 0; i < mdkxh.length(); i++) {
            // 属于大写字母就按 shift + 字母 输出大写
            char s = mdkxh.charAt(i);
            if (s >= 'A' && s <= 'Z') {
                keyPressWithShift(r, s);
            } else if (s >= 'a' && s <= 'z') {
                keyPress(r, s - 32);
            } else {
                keyPress(r, s);
            }
        }

        // 间隔 x 毫秒 再回车
        r.delay(Short.parseShort(PropertyUtil.getProperty("item.delay.enter")));
        keyPress(r, KeyEvent.VK_ENTER);

    }

    /**
     * 加密考勤机数据
     */
    private static String encrypt(Variant[] arge) {

        System.out.println(Arrays.toString(arge));

        String s = HttpUtil.getSalt();

        HttpUtil.sendPost("http://192.9.200.253/Report/hr/kqxt/swskqbase.jsp","dkxh=I623");

        // 解析json数据
        JSONObject jsonObj = JSON.parseObject(s);
        String time = jsonObj.getString("time");
        String random = jsonObj.getString("random");

        s = Base64Util.getBase64(""
                + random    // 随机数(固定3位)
                + time      // 时间
                + arge[2]   // 上班类别
                + arge[0]   // 卡号
        );

        return s;
    }


}
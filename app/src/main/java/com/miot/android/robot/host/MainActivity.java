package com.miot.android.robot.host;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.miot.android.robot.host.base.BaseActivity;
import com.miot.android.robot.host.callback.RobotCallback;
import com.miot.android.robot.host.entity.RobotVersion;
import com.miot.android.robot.host.mvp.udp.UDPContract;
import com.miot.android.robot.host.mvp.udp.UDPModel;
import com.miot.android.robot.host.mvp.udp.UDPPersenter;
import com.miot.android.robot.host.service.NetworkService;
import com.miot.android.robot.host.service.RobotHostService;
import com.miot.android.robot.host.utils.CRC16;
import com.miot.android.robot.host.utils.FormatConsts;
import com.miot.android.robot.host.utils.WifiAdmin;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity<UDPPersenter,UDPModel> implements RobotCallback,UDPContract.View{

    private String mac=null;
    private String ip=null;

    private String fileName="";

    private WifiAdmin wifiAdmin=null;

    private boolean isSmartUpdate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, NetworkService.class));
        startService(new Intent(this, RobotHostService.class));
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void Message(String s,String ip,int port) {
        if (isSmartUpdate){
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("message",s);
        map.put("host",ip);
        map.put("port",port);
        Message message=new Message();
        message.obj=map;
        message.what=1001;
        handler.sendMessage(message);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1001:
                    Map<String,Object> map=(HashMap<String,Object>)msg.obj;
                    if (map.get("message")!=null){
                        RobotVersion robotVersion=gson.fromJson(map.get("message").toString(),RobotVersion.class);
                        if (robotVersion!=null){
                            if (robotVersion.getSEARCH().equals("START")){
                                if (map!=null){
                                    map.put("mac",mac);
                                    map.put("localhost",ip);
                                    map.put("cmdPort", FormatConsts.COMMAND_CONTROL_PORT+"");
                                    map.put("tranPort",FormatConsts.TRAN_SERVER_PORT+"");
//                                    mPresenter.robotInitData(map);
                                }
                            }
                        }
                    }
                    break;
            }
        }
    };




    public static String set_wd(int kt_wd) {
        String hexWd = Integer.toHexString(kt_wd);
        String cmd = "02 00 07 00 " + hexWd;
        String crc = getCRC(cmd);

        return "AA " + cmd + " 00 " + crc + " 55";

    }

    @SuppressLint("DefaultLocale")
    private static String getCRC(String str) {
        if (str.equals("") || str == null) {
            return "";
        }
        byte[] raw = CRC16.getSendBuf(str);
        str = CRC16.getBufHexStr(raw);

        return CRC16.getCRC16(str).toLowerCase();
    }


}

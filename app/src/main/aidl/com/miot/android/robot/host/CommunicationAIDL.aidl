// CommunicationAIDL.aidl
package com.miot.android.robot.host;

// Declare any non-default types here with import statements

interface CommunicationAIDL {
     /**
      * 小智机器人接收数据传送给平台接口
      */
       void receiverRebotVoice(String voiceCode);
      /**
      * 更新平台数据传输给小智机器人接口
      */
       void receiverPlatform(boolean update,String json);
        /**
        * 更新数据是否成功
        */
       void notifySuccess(boolean isSuccess,String message);
       /**
       *错误信息
       */
       void errorMessage(String message);

       /**
       *接收REBOT信息、保存
       */
       String rebotConfigMessage(String type,String message);
}

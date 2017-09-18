package com.miot.android.robot.host.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cncrit.qiaoqiao.VspCallback;
import com.cncrit.qiaoqiao.VspOperation;
import com.miot.android.robot.host.CommunicationAIDL;
import com.miot.android.robot.host.base.BaseVspService;
import com.miot.android.robot.host.db.ModelManager;
import com.miot.android.robot.host.db.PlatformVoiceManager;
import com.miot.android.robot.host.db.PuManager;
import com.miot.android.robot.host.db.SceenManager;
import com.miot.android.robot.host.db.SnCodeManager;
import com.miot.android.robot.host.entity.Control;
import com.miot.android.robot.host.entity.Cu;
import com.miot.android.robot.host.entity.Device;
import com.miot.android.robot.host.entity.DeviceCtrlList;
import com.miot.android.robot.host.entity.Model;
import com.miot.android.robot.host.entity.PlatformVoice;
import com.miot.android.robot.host.entity.Pu;
import com.miot.android.robot.host.entity.Scene;
import com.miot.android.robot.host.entity.SenceModelList;
import com.miot.android.robot.host.task.EmailTask;
import com.miot.android.robot.host.utils.ACache;
import com.miot.android.robot.host.utils.BroadcastUtil;
import com.miot.android.robot.host.utils.CommonUtil;
import com.miot.android.robot.host.utils.FormatConsts;
import com.miot.android.robot.host.utils.JsonUtils;
import com.miot.android.robot.host.utils.MD5Util;
import com.miot.android.robot.host.utils.MacUtils;
import com.miot.android.robot.host.utils.MiotCommunicationProtocol;
import com.miot.android.robot.host.utils.MmwParseUartUtils;
import com.miot.android.robot.host.utils.MmwWebServiceErrorCode;
import com.miot.android.robot.host.utils.SharedPreferencesUtil;
import com.miot.android.robot.host.utils.WifiAdmin;

import com.miot.android.robot.host.vsp.VSPContract;
import com.miot.android.robot.host.vsp.VSPModel;
import com.miot.android.robot.host.vsp.VSPPersenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * 网络Service用于登录设备上妙联平台
 * Created by Administrator on 2017/1/6 0006.
 */
public class NetworkService extends BaseVspService<VSPPersenter, VSPModel> implements VSPContract.View, VspCallback,BroadcastUtil.IReceiver{

	public static final int VSP_LOGIN_SUCCESS = 20000;
	public static final int VSP_HOST_RESULT = 100002;
	public static final int WEB_PULIST_RESULT = 50000;

	public static final int VSP_LOGIN_FAIL = 20001;

	public static final int VSP_SEND_SUCCESS = 20003;

	public static final int VSP_RECEIVER_TTCONTENT = 20003;

	public static final int VSP_RECEIVSR_TTCONTENT = 20004;

	public static final int VSP_RECEIVER_LOGOUT = 20005;

	public static final int VSP_TIME_OUT = 20006;

	public static  int VspcuId=0;

	public static  String lastSnCode="";

	private static final String START_CODE = "SA_";

	private String snCode = "";

	private boolean isLoginSuccess = true;

	private boolean isSendData = true;

	private int puId = 0;

	private int cuId = 0;

	private MyConn conn = null;

	private boolean isStart=false;

	private CommunicationAIDL myCommunicationAIDL = null;

	private SharedPreferencesUtil sharedPreferencesUtil = null;

	private Timer mTimer = new Timer(true);//定时器
	private TimerTask mTimerTask;
	WifiAdmin wifiAdmin=null;

	@Override
	public void onCreate() {
		super.onCreate();
		myCommunicationAIDL = new MyCommunicationAIDL();
		if (conn == null) {
			conn = new MyConn();
		}
		sharedPreferencesUtil = SharedPreferencesUtil.getInstance(this);
		BroadcastUtil.registerReceiver(this,this,"com.miot.android.robot.host.state");
		snCode=FormatConsts.getSnCode();
		lastSnCode=snCode;
		wifiAdmin=new WifiAdmin(this);
		isStart=true;
		VspOperation.setVspCallback(this);
		if (ACache.get(this).getAsString(FormatConsts.FORMAL_URL)==null){
			mPresenter.getHostAddress(FormatConsts.FORMAL_URL);
		}else{
			VspOperation.rsIp =ACache.get(this).getAsString(FormatConsts.FORMAL_URL);
			VspOperation.setVspCallback(this);
			mPresenter.Login(snCode, MacUtils.snCodePassword(snCode));
		}

	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return new MyCommunicationAIDL();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		sharedPreferencesUtil.setPlatformDevceiData("");
		mTimerTask = new TimerTask()
		{
			public void run()
			{
				handler.sendEmptyMessage(WEB_PULIST_RESULT);
			}
		};
		mTimer.schedule(mTimerTask,5*60*1000,6*60*60*1000);
		Intent hostIntent=new Intent(NetworkService.this,RobotHostService.class);
		hostIntent.setAction(FormatConsts.HOST_ACTION_SERVICE);
		hostIntent.setPackage(context.getPackageName());
		NetworkService.this.bindService(hostIntent, conn, Context.BIND_IMPORTANT);
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void initPresenter() {
		mPresenter.setVM(this, mModel);
	}

	@Override
	public void hostAddressRes(String ip) {
		if (ip.isEmpty()) {
			handler.sendEmptyMessageDelayed(VSP_HOST_RESULT,5000);
		} else {
			VspOperation.rsIp = ip;
			VspOperation.setVspCallback(this);
			mPresenter.Login(snCode, MacUtils.snCodePassword(snCode));
		}

	}

	private Device device = null;

	private String platformData = "";

	ArrayList<SenceModelList> senceModelLists = new ArrayList<>();

	private boolean isUpdating=false;

	@Override
	public void getPuList(String json) {
		try {
			if (MmwWebServiceErrorCode.webserviceRequest(json)) {
				if (!isStart) {
					Log.e("--shared-",sharedPreferencesUtil.getPlatformDevceiData());
					Log.e("--UTF-",MD5Util.getMD5(json, "UTF-8"));
					if (MD5Util.getMD5(json, "UTF-8").equals(sharedPreferencesUtil.getPlatformDevceiData())) {
						myCommunicationAIDL.errorMessage("已经是最新的数据");
						String uart = MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getUPDATE_RebotInfo("01"));
						mPresenter.ttContent(cuId, uart);
						isSynchronized=true;
						handler.removeMessages(VSP_TIME_OUT);
						return;
					}
				}
				platformData = json;
				device = MmwWebServiceErrorCode.getDevice(json);
				if (device != null) {
						if (device.getSceens() != null) {
							SceenManager.getInstance(context).delAllSceen();;
							SceenManager.getInstance(context).saveAll(device.getSceens());
							isUpdating=true;
							senceModelLists.clear();
							PlatformVoiceManager.getInstance(context).dbUtils.dropTable(PlatformVoice.class);
							PlatformVoiceManager.getInstance(context).dbUtils.createTableIfNotExist(PlatformVoice.class);
							ArrayList<PlatformVoice> platformVoices = new ArrayList<>();
							for (int i = 0; i < device.getSceens().size(); i++) {
								PlatformVoice platformVoice = new PlatformVoice();
								Scene sceen = device.getSceens().get(i);
								SenceModelList senceModelList = new SenceModelList();
								String code = "SS_" + sceen.getId() + "_" + i;
								platformVoice.setCode(code);
								platformVoice.setName(sceen.getName());
								platformVoice.setSceenId(sceen.getId());
								platformVoice.setDeamonCuId(sceen.getDeamonCuId());
								platformVoice.setType("scene");
								PlatformVoiceManager.getInstance(context).saveOrUpdate(platformVoice);
								senceModelList.setCode(code);
								senceModelList.setModel(sceen.getName());
								senceModelLists.add(senceModelList);
								platformVoices.add(platformVoice);
							}
						}
						if (device.getPus() != null) {
							deviceCtrlLists.clear();
							lastPu.clear();
							ArrayList<Model> models = ModelManager.getInstance(context).getModels();
							Log.e("models", models + "");
							for (int i = 0; i < device.getPus().size(); i++) {
								String modelTocken = "";
								Model model = ModelManager.getInstance(context).getModel(device.getPus().get(i).getModelId());
								if (model != null) {
									modelTocken = model.getOperationListToken();
								}
								Thread.sleep(100);
								mPresenter.getModelConfigUserData(device.getPus().get(i), device.getPus().get(i).getModelId(), modelTocken);
							}
						}
					}

				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getPuState(String json) {
		SceenManager.getInstance(context).delAllSceen();
		PuManager.getInstance(context).deleteAllPu();
		Device deviceState = MmwWebServiceErrorCode.getDevice(json);
		if (deviceState!=null){
			ArrayList<Scene> scenes=deviceState.getSceens();
			ArrayList<Pu> pus=deviceState.getPus();
			if (scenes!=null){
				SceenManager.getInstance(context).saveAll(scenes);
			}
			if (pus!=null){
				PuManager.getInstance(context).saveAll(pus);
			}
		}
	}

	private ArrayList<DeviceCtrlList> deviceCtrlLists = new ArrayList<>();

	private ArrayList<Pu> lastPu=new ArrayList<>();
	@Override
	public void getModelConfigUserData(Pu pu, String json) {
		try {
			lastPu.add(pu);
			Model model = MmwWebServiceErrorCode.getModelList(json);
			if (model != null) {
				Model model1=ModelManager.getInstance(context).getModel(model.getModelId());
				if (model1!=null){
					if (!model1.getOperationListToken().equals(model.getOperationListToken())){
						ModelManager.getInstance(context).saveOrUpdate(model);
					}
				}else{
					ModelManager.getInstance(context).saveOrUpdate(model);
				}
				if (ModelManager.getInstance(context).getModel(model.getModelId())==null){
					return;
				}
				String operationList = ModelManager.getInstance(context).getModel(model.getModelId()).getOperationList();
				if (!operationList.equals("[]")) {
				JSONArray jsonArray = new JSONArray(operationList);
				if (jsonArray.length() != 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						try {
							DeviceCtrlList deviceCtrlList = new DeviceCtrlList();
							JSONObject jsonObject = new JSONObject(jsonArray.get(i).toString());
							String operation = jsonObject.getString("operation");
							String encode = jsonObject.getString("encode");
							String dev_class_type = jsonObject.getString("dev_class_type");
							String control = jsonObject.getString("control");
							String parameter="";
							if (jsonObject.has("parameter")){
								parameter=jsonObject.getString("parameter");
							}
							String voiceCode = "";
							voiceCode = START_CODE + pu.getPuId() + "_" + i;
							PlatformVoice platformVoice = new PlatformVoice();
							platformVoice.setCode(voiceCode);
							platformVoice.setState(pu.getState());
							platformVoice.setPuId(pu.getPuId());
							String name = "";
							if (MmwParseUartUtils.getNameLen(pu.getPuNickname()) != 0) {
								name = pu.getPuNickname().substring(0, MmwParseUartUtils.getNameLen(pu.getPuNickname()));
							} else {
								name = pu.getPuNickname();
							}
							if (name.length()>4){
								if (name.startsWith("智能")){
									name=name.substring(2,name.length());
								}
							}
							platformVoice.setName(name);
							platformVoice.setParentId(pu.getParentId());
							platformVoice.setOperation(operation);
							platformVoice.setControl(control);
							platformVoice.setEncode(encode);
							platformVoice.setType("device");
							PlatformVoiceManager.getInstance(context).saveOrUpdate(platformVoice);
							deviceCtrlList.setCode(voiceCode);
							deviceCtrlList.setDev_class_type(dev_class_type);
							deviceCtrlList.setDevice_name(name);
							deviceCtrlList.setRoom_name(pu.getRoomName());
							if (parameter.isEmpty()){
								deviceCtrlList.setParameter("null");
							}
							deviceCtrlList.setOperation(operation);
							if (pu.getFloorName().isEmpty() || pu.getFloorName().equals("-10000")) {
								deviceCtrlList.setFloor_name("null");
							} else {
								deviceCtrlList.setFloor_name(pu.getFloorName() + "楼");
							}
							deviceCtrlLists.add(deviceCtrlList);
						} catch (Exception e) {
							isUpdating=false;
						}
					}
				}

				}

			}
			if (device.getPus().size()==lastPu.size()){
				if (deviceCtrlLists.size() <= 0 && senceModelLists.size() <= 0) {
					myCommunicationAIDL.errorMessage("更新数据失败");
					isSynchronized=true;
					handler.removeMessages(VSP_TIME_OUT);
					mPresenter.ttContent(cuId, MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getUPDATE_RebotInfo("00")));
					return;
				}
				JSONObject jsonObject = new JSONObject();
				JSONArray DeviceCtrlLists=new JSONArray();
				for (int i=0;i<deviceCtrlLists.size();i++){
					DeviceCtrlList deviceCtrlList=deviceCtrlLists.get(i);
					JSONObject device=new JSONObject();
					device.put("floor_name",deviceCtrlList.getFloor_name());
					device.put("room_name",deviceCtrlList.getRoom_name());
					device.put("device_name",deviceCtrlList.getDevice_name());
					device.put("operation",deviceCtrlList.getOperation());
					device.put("dev_class_type",deviceCtrlList.getDev_class_type());
					device.put("parameter",deviceCtrlList.getParameter());
					device.put("value",deviceCtrlList.getValue());
					device.put("code",deviceCtrlList.getCode());
					DeviceCtrlLists.put(device);
				}
				jsonObject.put("DeviceCtrlList", DeviceCtrlLists);
				if (senceModelLists.size() > 0) {
					JSONArray SenceModelLists =new JSONArray();
					for (int i=0;i<senceModelLists.size();i++){
						SenceModelList senceModelList=senceModelLists.get(i);
						JSONObject senceModel=new JSONObject();
						senceModel.put("model",senceModelList.getModel());
						senceModel.put("code",senceModelList.getCode());
						SenceModelLists.put(senceModel);
					}
					jsonObject.put("SenceModelList", SenceModelLists);
				}
				myCommunicationAIDL.receiverPlatform(true, jsonObject.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			isUpdating=false;
		}
	}


	@Override
	public void loginRes(int type, int id, int sessionId, int domId) {
		count=0;
		VspcuId=id;
		if (ACache.get(this).getAsString(FormatConsts.FORMAL_SERVER_URL)==null){
			ACache.get(this).put(FormatConsts.FORMAL_SERVER_URL,VspOperation.rsIp,60*60*24);
		}
		String time=new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
		handler.removeMessages(30000);
		new EmailTask().execute("snCode:"+snCode+",puId:"+id+"设备已经登录," +"WIFI名称:"+ wifiAdmin.getSSID()+",登录时间:"+time);
		puId = id;
		Log.e("puId",puId+"");
		isLoginSuccess = true;
		isSendData = true;
	}

	private boolean isSynchronized=true;

	private int count=0;

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
				case 1000:
					try {
						myCommunicationAIDL.errorMessage("登录妙联平台失败,请检查网络是否正确");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				case 30000:
					if (!isLoginSuccess) {
						mPresenter.Login(snCode, MacUtils.snCodePassword(snCode));
					}
					break;
				case 30001:
					if (!isSendData) {
						isSendData = true;
						mPresenter.Login(snCode, MacUtils.snCodePassword(snCode));
					}

					break;
				case VSP_RECEIVER_TTCONTENT:
					String message = msg.obj.toString();
					if (message.isEmpty()) {
						return;
					}
					switch (MiotCommunicationProtocol.resultCallBack(message)) {
						case "F0":
							Cu cu= SnCodeManager.getInstance(context).getCu(snCode);
							String uart="";
							if (cu==null){
								 uart = MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getQueryHostInfo(CommonUtil.getVersionCode(context), 0,0));
							}else{
								uart = MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getQueryHostInfo(CommonUtil.getVersionCode(context), cu.getId(),cu.getSysTime()));
							}
							Log.e("uart",uart);
							mPresenter.ttContent(cuId, uart);
							break;
						case "02":
							handler.sendEmptyMessageDelayed(VSP_TIME_OUT,12*10*1000);
							if (isSynchronized){
								isSynchronized=false;
								mPresenter.getPuList(puId + "", cuId + "");
							}
							break;
						case "03":
							if (MiotCommunicationProtocol.updateCallBack(message).equals("01")){
								BroadcastUtil.send(context,null,"com.android.robot.update", CommonUtil.getVersionCode(context)+"");
							}
							break;
					}
					break;
				case VSP_TIME_OUT:
					try {
						isSynchronized=true;
						myCommunicationAIDL.errorMessage("更新数据超时，请重试");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
					break;
				case WEB_PULIST_RESULT:
					Cu cu=SnCodeManager.getInstance(context).getCu(snCode);
					if (cu!=null&&puId!=0){
						mPresenter.getPuState(puId+"",cu.getId()+"");
					}
					break;
				case VSP_HOST_RESULT:
					mPresenter.getHostAddress(FormatConsts.FORMAL_URL);
					break;
			}
		}
	};

	private int username=0;

	@Override
	public void loginFail(int code) {
		String time=new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
		count++;
		String s="";
		if (wifiAdmin==null){
			wifiAdmin=new WifiAdmin(context);
		}
		if (count>=100){
			count=0;
			if (wifiAdmin.getOpenState()){
				s="snCode:"+snCode+"登录失败"+"address"+VspOperation.rsIp+"WIFI名称:"+wifiAdmin.getSSID()+wifiAdmin.getIPAddress()+"time:"+time;
			}else{
				s="登录失败"+FormatConsts.FORMAL_SERVER_IP+"WIFI状态"+wifiAdmin.getOpenState();
			}
			new EmailTask().execute(s+time);
		}
		isLoginSuccess = false;
		isSendData = false;
		mPresenter.Logout();
		handler.sendEmptyMessageDelayed(30000, 5000);

	}

	@Override
	public void timeOut() {
		String time=",上报时间"+new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
		if (wifiAdmin!=null) {
			new EmailTask().execute("snCode:"+snCode+"心跳超时退出," + "WIFINAME:"+wifiAdmin.getSSID()+time);
		}
		isLoginSuccess = false;
		handler.sendEmptyMessageDelayed(30000, 5000);
	}

	@Override
	public void sendDataFail() {
		isLoginSuccess = false;
		isSendData = false;
		handler.sendEmptyMessageDelayed(30001, 1000);
	}

	@Override
	public void receiverTTContent(int id, String mlcc) {
		cuId = id;
		if (mlcc.isEmpty()) {
			return;
		}
		String result = MmwParseUartUtils.getMlccUart(mlcc);
		if (result.isEmpty()) {
			return;
		}
		if (!MmwParseUartUtils.isMlccUartFormat(result)) {
			return;
		}
		String s = MiotCommunicationProtocol.resultCallBack(result);
		if (s.isEmpty()) {
			return;
		}
		Message message = new Message();
		message.obj = result;
		message.what = VSP_RECEIVER_TTCONTENT;
		handler.sendMessage(message);
	}
	@Override
	public void logout(int id) {
		String time="上报时间:"+new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss").format(new Date());
		if (wifiAdmin!=null) {
			new EmailTask().execute(snCode+"强制登出");
		}
		mPresenter.Logout();
		isLoginSuccess = false;
		handler.sendEmptyMessageDelayed(30000, 1000);
	}

	@Override
	public void onReceive(Context ctx, Intent intent) {
		if (intent.getAction().equals("com.miot.android.robot.host.state")){
			String parmas=intent.getStringExtra("state");
			if (!parmas.isEmpty()) {
             try {
				switch (parmas){
					case "01":
						myCommunicationAIDL.errorMessage("开始更新软件，请勿切断电源");
						break;
					case "02":
						myCommunicationAIDL.errorMessage("更新软件失败，请重试");
						break;
					case "03":
						myCommunicationAIDL.errorMessage("更新软件成功");
						break;
					case "04":
						myCommunicationAIDL.errorMessage("已经是最新的版本。");
						break;
				  }
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				mPresenter.ttContent(cuId, MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getUPDATE_RebotDevice(parmas)));
			}
		}
	}

	class MyCommunicationAIDL extends CommunicationAIDL.Stub {

		@Override
		public void receiverRebotVoice(String voiceCode) throws RemoteException {
			if (voiceCode.isEmpty()) {
				myCommunicationAIDL.errorMessage("指令不正确");
				return;
			}
			String code = JsonUtils.getVoiceCode(voiceCode);
			Log.e("code",code);
			if (code.isEmpty()) {
				return;
			}
			PlatformVoice platformVoice = PlatformVoiceManager.getInstance(context).getPlatformVoice(code);

			if (platformVoice!=null){
				Log.e("platformVoice",platformVoice.toString());
				if (platformVoice.getType().equals("device")){
					Pu pu=PuManager.getInstance(context).getPu(platformVoice.getPuId());
					if (pu==null){
						myCommunicationAIDL.errorMessage(platformVoice.getName() +"已经删除,请添加设备");
						return;
					}
					if (pu.getState().equals("0")){
						myCommunicationAIDL.errorMessage(platformVoice.getName() +"已经离线,请检查设备状态");
						return;
					}
					if (!platformVoice.getControl().isEmpty()){
						Control control=gson.fromJson(platformVoice.getControl(),Control.class);
						Log.e("contorl",control.toString());
						if (control!=null){
							if (control.getPu().equals(FormatConsts.MIOT_DEVCEI_TYPE)){
									mPresenter.toPuttContent(Integer.parseInt(platformVoice.getPuId()), MmwParseUartUtils.doLinkBindMake(platformVoice.getEncode()));
									myCommunicationAIDL.errorMessage("执行" + platformVoice.getName() + platformVoice.getOperation() + "完毕");
								return;
							}
							if (control.getPu().equals(FormatConsts.MIOT_DEVICE_TYPE_ZGB)){
								String puId="";
								String encode="";
								encode=platformVoice.getEncode();
								String replaceChar=control.getReplaceChar();
								String replaceNewCharField=control.getReplaceNewCharField();
								String sendId =control.getSendId();
								String replaceBytes=control.getReplaceBytes();
								if (replaceBytes.equals("4")){
									if (!platformVoice.getPuId().isEmpty()){
										if (replaceNewCharField.equals("puId")) {
											puId = MiotCommunicationProtocol.bytesFeiBiToHexString(MiotCommunicationProtocol.intToByteArray(Integer.parseInt(platformVoice.getPuId())));
											puId = MmwParseUartUtils.stringFill(puId, 8, '0', true);
											encode = encode.replaceAll(replaceChar, puId).replaceAll(" ","");
											if (sendId.equals("parentId")) {
												mPresenter.toPuttContent(Integer.parseInt(platformVoice.getParentId()), MmwParseUartUtils.doLinkBindMake(encode));
												myCommunicationAIDL.errorMessage("执行"+platformVoice.getName()+platformVoice.getOperation()+"完毕");
											}
										}
									}
									return;
								}
								return;
							}
						}
					}
					return;
				}
				if (platformVoice.getType().equals("scene")){
					Scene scene=SceenManager.getInstance(context).getSceen(platformVoice.getSceenId());
					if (scene==null){
						myCommunicationAIDL.errorMessage(platformVoice.getName()+"场景不存在");
						return;
					}
					mPresenter.ttCuContent(Integer.parseInt(platformVoice.getDeamonCuId()),JsonUtils.getSceneCu(platformVoice.getSceenId()));
					myCommunicationAIDL.errorMessage("执行"+platformVoice.getName()+"场景触发完毕");
					return;
				}
				return;
			}
			myCommunicationAIDL.errorMessage("该设备指令无效");
		}

		@Override
		public void receiverPlatform(boolean update, String json) throws RemoteException {


		}
		@Override
		public void notifySuccess(boolean isSuccess,String message) throws RemoteException {
			handler.removeMessages(VSP_TIME_OUT);
			isUpdating=false;
			isSynchronized=true;
			if (isSuccess) {
				sharedPreferencesUtil.setRobotSavePlatform(message);
				isStart=false;
				String uart = MmwParseUartUtils.doLinkBindMake(MiotCommunicationProtocol.getUPDATE_RebotInfo("01"));
				mPresenter.ttContent(cuId, uart);
				Cu cu=SnCodeManager.getInstance(context).getCu(snCode);
				if (cu==null){
					cu=new Cu();
					cu.setId(cuId);
					cu.setSn(snCode);
					cu.setVersion(CommonUtil.getVersionCode(context));
					cu.setSys(true);
					cu.setSysTime(System.currentTimeMillis());
				}else {
					cu.setId(cuId);
					cu.setVersion(CommonUtil.getVersionCode(context));
					cu.setSys(true);
					cu.setSysTime(System.currentTimeMillis());
					cu.setSn(snCode);
				}
				SnCodeManager.getInstance(context).saveCu(cu);
				if (!platformData.isEmpty()) {
					sharedPreferencesUtil.setPlatformDevceiData(MD5Util.getMD5(platformData, "UTF-8"));
				}
				if(cuId!=0){
					sharedPreferencesUtil.setCuId(cuId);
				}
			}
		}

		@Override
		public void errorMessage(String message) throws RemoteException {

		}

		@Override
		public String rebotConfigMessage(String type, String message) throws RemoteException {
			if(type.equals("platform")){
				return sharedPreferencesUtil.getRobotSavePlatform();
			}
			if (type.equals("saveAddress")){
				sharedPreferencesUtil.setIpAddress(message);
				return "";
			}
			if (type.equals("getAddress")){
				return sharedPreferencesUtil.getIpAddress();
			}
			return null;
		}


	}

	class MyConn implements ServiceConnection {

		@Override
		public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
			myCommunicationAIDL = MyCommunicationAIDL.Stub.asInterface(iBinder);
		}

		@Override
		public void onServiceDisconnected(ComponentName componentName) {
			NetworkService.this.startService(new Intent(NetworkService.this, RobotHostService.class));
			Intent hostIntent=new Intent(NetworkService.this,RobotHostService.class);
			hostIntent.setAction(FormatConsts.HOST_ACTION_SERVICE);
			hostIntent.setPackage(context.getPackageName());
			NetworkService.this.startService(hostIntent);
			NetworkService.this.bindService(hostIntent, conn, Context.BIND_IMPORTANT);
		}
	}
}

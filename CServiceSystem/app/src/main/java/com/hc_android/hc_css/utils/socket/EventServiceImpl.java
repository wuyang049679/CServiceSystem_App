/*
 * Copyright 2018 Mayur Rokade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.hc_android.hc_css.utils.socket;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.entity.ErrorEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;

import static android.os.Looper.getMainLooper;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_HASH_STATE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NETWORK_STATE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NOTIFICATION_STATE;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

/**
 * Implementation of {@link EventService} which connects and disconnects to the server.
 * It also sends and receives events from the server.
 */
public class EventServiceImpl implements EventService {

    private static final String TAG = EventServiceImpl.class.getSimpleName();
    private static final String EVENT_CONNECT = Socket.EVENT_CONNECT;
    private static final String EVENT_ERROR = Socket.EVENT_ERROR;
    private static final String EVENT_DISCONNECT = Socket.EVENT_DISCONNECT;
    private static final String EVENT_CONNECT_ERROR = Socket.EVENT_CONNECT_ERROR;
    private static final String EVENT_CONNECT_TIMEOUT = Socket.EVENT_CONNECT_TIMEOUT;
    private static final String EVENT_NEW_MESSAGE = "client";
    //消息类型
    private static final String MESSAGE_LOGINSUC = "loginSuc";
    private static final String MESSAGE_JOIN = "join";
    private static final String MESSAGE_LEAVE = "leave";
    private static final String MESSAGE_SERVICELEAVE = "serviceLeave";
    private static final String MESSAGE_NEW = "message";
    private static final String MESSAGE_NEWDIALOG = "newdialog";
    private static final String MESSAGE_INPUTING = "inputing";
    private static final String MESSAGE_ONLINE = "online";
    private static final String MESSAGE_OFFLINE = "offline";
    private static final String MESSAGE_RECEPTION = "reception";
    private static final String MESSAGE_SERVICEONLY = "serviceOnly";
    private static final String MESSAGE_REALTIME_ADD = "realtime_add";
    private static final String MESSAGE_REALTIME_MODIFY = "realtime_modify";
    private static final String MESSAGE_REALTIME_DEL = "realtime_del";
    private static final String MESSAGE_STATEUPATE = "stateUpate";
    private static final String MESSAGE_UPATEDIALOG = "upateDialog";
    private static final String MESSAGE_REPORT_STATE = "report_state";
    private static EventService INSTANCE;
    private static EventListener mEventListener;
    private  Socket mSocket;
    private List<String> roomList;
    private boolean isConnect=true;
    public static  boolean isFirst = true;
    private static final int MESSAGE_KEEPLINK=1;
    private static final int OPTIME = 2000;
    private long lasttime = 0;
    private Handler postDelayed=new Handler(getMainLooper());
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==MESSAGE_KEEPLINK){
                isConnect=false;
                postDelayed.removeCallbacks(runnable);
                String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
//                    disconnect();
                try {
                    connect(hash);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    });
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "Runnable: 20s一次");
            keepLink();
        }
    };
    // Prevent direct instantiation
    private EventServiceImpl() {
    }

    /**
     * Returns single instance of this class, creating it if necessary.
     *
     * @return
     */
    public static EventService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventServiceImpl();
        }
        return INSTANCE;
    }

    /**
     * Connect to the server.
     * 连接服务器socket
     *
     * @param hash
     * @throws URISyntaxException
     */
    @Override
    public synchronized void connect(String hash) throws URISyntaxException {


        //判断是否已连接，如果连接重新连接
//        if (mSocket!=null&&mSocket.connected()){
//            Log.i(TAG,"open进来了");
//            mSocket.open();
//            return;
//        }
        disconnect();
        IO.Options opts = new IO.Options();
        opts.transports = new String[]{WebSocket.NAME};
        //失败重试次数
        opts.reconnectionAttempts = 30;
        //失败重连的时间间隔
        opts.reconnectionDelay = 3000;
        //连接超时时间(ms)
        opts.timeout = 3000;

        mSocket = IO.socket(Address.SOCKET_URL + "?type=service&hash=" + hash,opts);
        // Register the incoming events and their listeners
        // on the socket.
        mSocket.on(EVENT_CONNECT, onConnect);
        mSocket.on(EVENT_ERROR, onError);
        mSocket.on(EVENT_DISCONNECT, onDisconnect);
        mSocket.on(EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(EVENT_NEW_MESSAGE, onNewMessage);
        mSocket.connect();

        Log.i(TAG,"open进来了"+isFirst);
    }

    /**
     * 加入房间
     * @param roomId
     */
    @Override
    public void joinRoom(String roomId) {
        if (roomList==null)roomList=new ArrayList<>();
//        if (mSocket!=null && mSocket.connected() && !roomList.contains(roomId)) {//已经加入过了就不再重新加入

            if (mSocket != null) mSocket.emit("join", roomId);
            roomList.add(roomId);
//        Log.i(TAG, "roomId: "+roomId);
//        if (mSocket!=null)Log.i(TAG, "mSocket: "+mSocket.connected());
//        }
    }
    /**
     * 新对话加入房间
     * @param roomId
     */
    @Override
    public void joinRoom(String roomId,Ack ack) {

        if (roomList==null)roomList=new ArrayList<>();
//        if (!roomList.contains(roomId)) {//已经加入过了就不再重新加入
            if (mSocket != null) mSocket.emit("join", roomId,ack);
            roomList.add(roomId);

//        }
//        Log.i(TAG, "roomId: "+roomId);
//        mSocket.emit("keepLink", new Ack() {
//            @Override
//            public void call(Object... args) {
//
//            }
//        });
    }

    /**
     * 离开房间
     * @param roomId
     */
    @Override
    public void leaveRoom(String roomId) {

        if (mSocket!=null)mSocket.emit("leave",roomId);
        if (roomList!=null&&roomList.contains(roomId))roomList.remove(roomId);

    }

    /**
     * 询问用户是否在线
     * @param customerId
     * @param serviceId
     */
    @Override
    public void isOffLine(String customerId, String serviceId) {
        if (mSocket!=null)mSocket.emit("isOffline",customerId,serviceId);
    }

    @Override
    public void keepLink() {

        if (true)return;
        postDelayed.removeCallbacks(runnable);
        //判断是否链接成功

        if (mSocket!=null)mSocket.emit("keepLink", "1", new Ack() {
            @Override
            public void call(Object... args) {
                for (int i = 0; i < args.length; i++) {
                    try {
                        JSONObject obj = new JSONObject(args[i].toString());
                        int suc = obj.getInt("_suc");
                        if (suc==1){
                            isConnect=true;
                            handler.removeMessages(MESSAGE_KEEPLINK);
                            if (isConnect) {
                                postDelayed.postDelayed(runnable, 20000);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        handler.sendEmptyMessageDelayed(MESSAGE_KEEPLINK,5000);
    }

    /**
     * Disconnect from the server.
     */
    @Override
    public synchronized void disconnect() {

        if (mSocket != null) {
            handler.removeMessages(MESSAGE_KEEPLINK);
            postDelayed.removeCallbacks(runnable);

            mSocket.disconnect();
            mSocket.off();
            mSocket.close();
            mSocket=null;
            isFirst = true;
            if (!NullUtils.isEmptyList(roomList))roomList.clear();
        }
    }
    /**
     * Disconnect from the server.
     */
    @Override
    public synchronized void disconnect(boolean isMinu) {

        if (mSocket != null) {
            handler.removeMessages(MESSAGE_KEEPLINK);
            postDelayed.removeCallbacks(runnable);
            mSocket.disconnect();
            mSocket.off();
            mSocket.close();
            mSocket=null;
            if (!NullUtils.isEmptyList(roomList))roomList.clear();
        }
    }
    /**
     * Send chat message to the server.
     *
     * @param chatMessage
     * @return
     */
//    @Override
//    public Flowable<ChatMessage> sendMessage(@NonNull final ChatMessage chatMessage) {
//        return Flowable.create(new FlowableOnSubscribe<ChatMessage>() {
//            @Override
//            public void subscribe(FlowableEmitter<ChatMessage> emitter) throws Exception {
//                /*
//                 * Socket.io supports acking messages.
//                 * This feature can be used as
//                 * mSocket.emit("EVENT_NEW_MESSAGE", chatMessage.getMessage(), new Ack() {
//                 *   @Override
//                 *   public void call(Object... args) {
//                 *       // Do something with args
//                 *
//                 *       // On success
//                 *       emitter.onNext(chatMessage);
//                 *
//                 *       // On error
//                 *       emitter.onError(new Exception("Sending message failed."));
//                 *    }
//                 * });
//                 *
//                 * */
//
//                mSocket.emit(EVENT_NEW_MESSAGE, chatMessage.getMessage());
//                emitter.onNext(chatMessage);
//            }
//        }, BackpressureStrategy.BUFFER);
//    }

    /**
     * Send typing event to the server.
     */
    @Override
    public void onTyping() {
        Log.i(TAG,"onTyping"+isFirst);
        if (!isFirst) {//首次登陆进来不重连
            String hash = BaseApplication.getUserEntity().getHash();
            if (hash == null)hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
            if (mSocket == null) {
                try {
                    connect(hash);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            } else {
                mSocket.open();
            }
        }

        isFirst=false;
    }

    /**
     * Send stop typing event to the server.
     */
    @Override
    public void onStopTyping() {

    }

    /**
     * Set eventListener.
     * <p>
     * When server sends events to the socket, those events are passed to the
     * RemoteDataSource -> Repository -> Presenter -> View using EventListener.
     *
     * @param eventListener
     */
    @Override
    public void setEventListener(EventListener eventListener) {
        mEventListener = eventListener;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "call: onConnect");
            if (mEventListener != null) mEventListener.onConnect(args);

        }
    };
    private Emitter.Listener onError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            for (int i = 0; i < args.length; i++) {
                Log.i(TAG, "call: onError" + args[i]);
                try {


                ErrorEntity errorEntity = JsonParseUtils.parseToObject(args[i].toString(), ErrorEntity.class);
                if (errorEntity!=null&&errorEntity.getPlaces()!=null&&errorEntity.getPlaces().equals("gobeyond")) {//坐席数已满切换离线
//                    MessageEntity message = new MessageEntity();
//                    message.setAct(EVENTBUS_NOTIFICATION_STATE);//通知离线
//                    message.setState("break");
//                    message.setConfigId(BaseConfig.ONLINE_STATE_MAX);
//                    MessageEvent event = new MessageEvent(EventMessage, message);
//                    EventBus.getDefault().postSticky(event);
                    BaseConfig.setStateChange("break",true,BaseConfig.ONLINE_STATE_MAX);

                }
                if (errorEntity!=null&&errorEntity.getErrorNo()!=null&&errorEntity.getErrorNo().equals("10003")){
                    MessageEntity message = new MessageEntity();
                    message.setAct(EVENTBUS_HASH_STATE);
                    MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
                    EventBus.getDefault().postSticky(event);
                }

                    if (errorEntity!=null&&errorEntity.getPlaces()!=null&&errorEntity.getPlaces().equals("realNameAuth")) {//要求实名认证
//                    MessageEntity message = new MessageEntity();
//                    message.setAct(EVENTBUS_NOTIFICATION_STATE);//通知离线
//                    message.setState("break");
//                    message.setConfigId(BaseConfig.ONLINE_STATE_MAX);
//                    MessageEvent event = new MessageEvent(EventMessage, message);
//                    EventBus.getDefault().postSticky(event);
                        BaseConfig.setStateChange("break",true,BaseConfig.ONLINE_STATE_REALAUTHEN);

                    }
                }catch (Exception e){}
            }
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "call: onDisconnect");
            if (mEventListener != null) mEventListener.onDisconnect(args);
            if (!NullUtils.isEmptyList(roomList)) {
                roomList.clear();
            }
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "call: onConnectError");
            if (mEventListener != null) mEventListener.onConnectError(args);
            boolean isConnected = NetworkUtils.isConnected(BaseApplication.getBaseApplication().getApplicationContext());
            if (isConnected){
                MessageEntity message = new MessageEntity();
                message.setAct(EVENTBUS_NETWORK_STATE);//在线状态
                message.setConnected(false);
                message.setNetWorkType(100);//100为socket服务器连接错误
                MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
                EventBus.getDefault().postSticky(event);
            }
        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            for (int i = 0; i < args.length; i++) {
                Log.i(TAG, "call: onNewMessage" + args[i]);
                if (args[i] instanceof Ack) {
                    try {
                        //回复服务器_suc，获取连接权限
                        Ack ack = (Ack) args[i];
                        JSONObject obj = new JSONObject();
                        obj.put("_suc", 1);
                        ack.call(obj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    MessageEntity message = JsonParseUtils.parseToObject(args[i].toString(), MessageEntity.class);
                    MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
                    EventBus.getDefault().postSticky(event);


                }
            }
        }
    };




}

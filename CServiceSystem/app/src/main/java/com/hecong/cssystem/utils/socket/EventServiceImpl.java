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

package com.hecong.cssystem.utils.socket;

import android.util.Log;

import com.hecong.cssystem.api.Address;
import com.hecong.cssystem.entity.MessageEntity;
import com.hecong.cssystem.utils.JsonParseUtils;

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

/**
 * Implementation of {@link EventService} which connects and disconnects to the server.
 * It also sends and receives events from the server.
 */
public class EventServiceImpl implements EventService {

    private static final String TAG = EventServiceImpl.class.getSimpleName();
    private static final String EVENT_CONNECT = Socket.EVENT_CONNECT;
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
    private static Socket mSocket;

    private List<String> roomList;

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
    public void connect(String hash) throws URISyntaxException {
        //判断是否已连接，如果连接重新连接
        if (mSocket!=null&&mSocket.connected()){
            mSocket.disconnect();
        }
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.secure = true;
        opts.transports = new String[]{WebSocket.NAME};
        opts.reconnection = true;
        opts.reconnectionAttempts = -1;
        mSocket = IO.socket(Address.SOCKET_URL + "?type=service&hash=" + hash);
        // Register the incoming events and their listeners
        // on the socket.
        mSocket.on(EVENT_CONNECT, onConnect);
        mSocket.on(EVENT_DISCONNECT, onDisconnect);
        mSocket.on(EVENT_CONNECT_ERROR, onConnectError);
        mSocket.on(EVENT_CONNECT_TIMEOUT, onConnectError);
        mSocket.on(EVENT_NEW_MESSAGE, onNewMessage);
        mSocket.connect();
    }
    /**
     * 加入房间
     * @param roomId
     */
    @Override
    public void joinRoom(String roomId) {
        if (roomList==null)roomList=new ArrayList<>();
        if (!roomList.contains(roomId)) {//已经加入过了就不再重新加入
            if (mSocket != null) mSocket.emit("join", roomId);
            roomList.add(roomId);
        }
        Log.i(TAG, "roomId: "+roomId);
    }
    /**
     * 新对话加入房间
     * @param roomId
     */
    @Override
    public void joinRoom(String roomId,Ack ack) {

        if (roomList==null)roomList=new ArrayList<>();
        if (!roomList.contains(roomId)) {//已经加入过了就不再重新加入
            if (mSocket != null) mSocket.emit("join", roomId,ack);
            roomList.add(roomId);
        }
        Log.i(TAG, "roomId: "+roomId);

    }
    /**
     * Disconnect from the server.
     */
    @Override
    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
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
//        mSocket.emit(EVENT_TYPING);
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

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "call: onDisconnect");
            if (mEventListener != null) mEventListener.onDisconnect(args);
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TAG, "call: onConnectError");
            if (mEventListener != null) mEventListener.onConnectError(args);
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
                    uploadDate(args[i].toString());
                }
            }
        }
    };

    /**
     * 根据消息类型更新数据
     *
     * @param
     */
    private void uploadDate(String message) {

        MessageEntity entity = JsonParseUtils.parseToObject(message, MessageEntity.class);
        switch (entity.getAct()) {
            //连接成功
            case MESSAGE_LOGINSUC:
                if (mEventListener != null) mEventListener.onLogSuc(message);
                break;
            //加入房间
            case MESSAGE_JOIN:
                break;
            //结束对话后退出房间
            case MESSAGE_LEAVE:
                break;
            //只有客服能收到的退出房间消息
            case MESSAGE_SERVICELEAVE:
                break;
            //收到新消息
            case MESSAGE_NEW:
                if (mEventListener != null) mEventListener.onNewMessage(message);
                break;
            //新对话加入
            case MESSAGE_NEWDIALOG:
                if (mEventListener!=null)mEventListener.onNewDialog(message);
                break;
            //接收顾客输入的文字
            case MESSAGE_INPUTING:
                break;
            //顾客上线
            case MESSAGE_ONLINE:
                if (mEventListener != null) mEventListener.onLine(message);
                break;
            //顾客离线
            case MESSAGE_OFFLINE:
                if (mEventListener != null) mEventListener.onOffLine(message);
                break;
            //对话被接待
            case MESSAGE_RECEPTION:
                break;
            //客服账号登录唯一性验证//保存serviceId
            case MESSAGE_SERVICEONLY:
                
                break;
            //实时访客增加
            case MESSAGE_REALTIME_ADD:
                break;
            //实时访客修改
            case MESSAGE_REALTIME_MODIFY:
                break;
            //实时访客删除
            case MESSAGE_REALTIME_DEL:
                break;
            //客服在线状态变化
            case MESSAGE_STATEUPATE:
                break;
            //更新对话（团队协作可能更新）
            case MESSAGE_UPATEDIALOG:
                break;
            //收到客服汇报的在线状态
            case MESSAGE_REPORT_STATE:
                break;
        }
    }


}

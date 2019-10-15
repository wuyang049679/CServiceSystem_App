package com.hecong.cssystem.utils.socket;


import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.transports.WebSocket;
import okhttp3.OkHttpClient;


public class ServerConnection {
    private Socket mSocket;
    private String TGA = "TGA";

    public enum ConnectionStatus {
        DISCONNECTED,
        CONNECTED
    }

    public interface ServerListener {
        void onNewMessage(String message);

        void onStatusChange(ConnectionStatus status);
    }

    private OkHttpClient mClient;
    private String mServerUrl;
    private Handler mMessageHandler;
    private Handler mStatusHandler;
    private ServerListener mListener;


//    private class SocketListener extends WebSocketListener {
//        @Override
//        public void onOpen(WebSocket webSocket, Response response) {
//            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.CONNECTED);
//            mStatusHandler.sendMessage(m);
//        }
//
//        @Override
//        public void onMessage(WebSocket webSocket, String text) {
//            Message m = mMessageHandler.obtainMessage(0, text);
//            mMessageHandler.sendMessage(m);
//        }
//
//        @Override
//        public void onClosed(WebSocket webSocket, int code, String reason) {
//            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.DISCONNECTED);
//            mStatusHandler.sendMessage(m);
//        }
//
//        @Override
//        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
//            disconnect();
//        }
//    }

    /**
     * socket初始化
     *
     * @param url
     */
    public ServerConnection(String url) {
        mServerUrl = url;
        Log.i(TGA, url);
        try {
            IO.Options opts = new IO.Options();
            opts.forceNew = true;
            opts.secure = true;
            opts.transports = new String[]{WebSocket.NAME};
            opts.reconnection = true;
            opts.reconnectionAttempts = -1;
            mSocket = IO.socket(mServerUrl, opts);
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            mSocket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectTimeOut);
            mSocket.on("client", onClientMessage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接socket
     *
     * @param listener
     */
    public void connect(ServerListener listener) {

        mSocket.connect();
        mListener = listener;
        mMessageHandler = new Handler(msg -> {
            mListener.onNewMessage((String) msg.obj);
            return true;
        });
        mStatusHandler = new Handler(msg -> {
            mListener.onStatusChange((ConnectionStatus) msg.obj);
            return true;
        });
        Log.i("TGA", "尝试连接。。。");
    }

    /**
     * 关闭socket
     */
    public void disconnect() {
//        mWebSocket.cancel();
        mListener = null;
        mMessageHandler.removeCallbacksAndMessages(null);
        mStatusHandler.removeCallbacksAndMessages(null);
    }

    public void sendMessage(String message) {
//        mWebSocket.send(message);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.i(TGA, "onConnect:" + args.toString());
//            Message m = mStatusHandler.obtainMessage(0, ConnectionStatus.CONNECTED);
//            mStatusHandler.sendMessage(m);
        }
    };
    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Log.i(TGA, "onDisconnect:" + args[0].toString());
        }
    };
    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TGA, "onConnectError:" + args[0].toString());

        }
    };
    private Emitter.Listener onConnectTimeOut = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Log.i(TGA, "onConnectTimeOut:" + args[0].toString());

        }
    };

    /**
     * 消息接收
     */
    private Emitter.Listener onClientMessage = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            for (int i = 0; i < args.length; i++) {
                Log.i(TGA, "onClientMessage:" + args[i]);
                if (args[i] instanceof Ack) {
                    //回复服务器_suc，连接权限
                    Ack ack = (Ack) args[i];
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("_suc", 1);
                        ack.call(obj);
                        joinRoom();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    Gson gson=new Gson();

                }
            }
        }
    };

    private void joinRoom() {

        mSocket.emit("join", "5da56df82a91b28c743199b1");
    }

}
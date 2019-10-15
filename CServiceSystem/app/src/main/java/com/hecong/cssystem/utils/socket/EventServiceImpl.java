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

import java.net.URISyntaxException;

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

    private static EventService INSTANCE;
    private static EventListener mEventListener;
    private static Socket mSocket;

    // Prevent direct instantiation
    private EventServiceImpl() {}

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
     *  连接服务器socket
     * @param hash
     * @throws URISyntaxException
     */
    @Override
    public void connect(String hash) throws URISyntaxException {
        IO.Options opts = new IO.Options();
        opts.forceNew = true;
        opts.secure = true;
        opts.transports = new String[]{WebSocket.NAME};
        opts.reconnection = true;
        opts.reconnectionAttempts = -1;
        mSocket = IO.socket(Address.SOCKET_URL+"?type=service&hash="+hash);
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
     * Disconnect from the server.
     *
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
     *
     */
    @Override
    public void onTyping() {
//        mSocket.emit(EVENT_TYPING);
    }

    /**
     * Send stop typing event to the server.
     *
     */
    @Override
    public void onStopTyping() {

    }

    /**
     * Set eventListener.
     *
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
            Log.i(TAG, "call: onNewMessage");
            if (mEventListener != null) mEventListener.onNewMessage(args);
        }
    };


}

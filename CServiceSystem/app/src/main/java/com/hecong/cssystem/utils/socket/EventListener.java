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

/**
 * Main interface to listen to server events.
 *
 */
public interface EventListener {

    void onConnect(Object... args);

    void onDisconnect(Object... args);

    void onConnectError(Object... args);

    void onConnectTimeout(Object... args);

    void onNewMessage(Object... args);

    void onNewDialog(Object... args);

    void onUpdateDialog(Object... args);

    void onLeaveDialog(Object... args);

    void onReception(Object... args);

    void onUserJoined(Object... args);

    void onUserLeft(Object... args);

    void onTyping(Object... args);

    void onStopTyping(Object... args);

    void onUserState(Object... args);

    void onOffLine(Object... args);

    void onLine(Object... args);

    void onLogSuc(Object... args);
}

package com.example.iotmqtt.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {
    //双重加锁单例模式
  /*  private WebSocketServer(){}
    private static volatile WebSocketServer instance;
    public static WebSocketServer getInstance(){
        if (instance == null){
            synchronized (WebSocketServer.class){
                if (instance == null){
                    instance = new WebSocketServer();
                }
            }
        }
        return instance;
    }*/

    static Log log = LogFactory.getLog(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数，应该把它设计成线程安全的
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet
            = new CopyOnWriteArraySet<>();
    //与某个客户端的连接回话，需要通过它来给客户端发送数据
    private Session session;
    //接受sid
    private String sid = "";
    /**
     * 连接建立成功的调用方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        log.info("有新窗口开始监听："+sid+",当前在线人数为"+getOnlineCount());
        this.sid = sid;
        try {
            sendMessage("连接成功");
        }catch (IOException e){
            log.error("websoket IO异常");
        }

    }

    /**
     * 连接关闭调用方法
     * @return
     */
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        subOnlineCount();
        log.info("有一个连接关闭！当前在线人数为"+getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("收到来自窗口"+sid+"的信息："+message);
        //群发消息
        for (WebSocketServer item:webSocketSet) {
            try {
                item.sendMessage(message);
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    @OnError
    public void onError(Session session,Throwable error){
        log.error("发生错误");
        error.printStackTrace();
    }
    //实现服务器主动推送
    public void sendMessage(String message) throws IOException{
        this.session.getBasicRemote().sendText(message);
    }
    //群发自定义消息
    public static void sendInfo(String message,@PathParam("sid") String sid) throws IOException{
        log.info("推送消息到窗口"+sid+",推送内容："+message);
        for (WebSocketServer item:webSocketSet) {
            try {
                //这里可以设定只推给这个sid的，为null则全部推送
                if (sid == null){
                    item.sendMessage(message);
                }else if (item.sid.equals(sid)){
                    item.sendMessage(message);
                }
            }catch (IOException e){
                continue;
            }
        }
    }

    public static synchronized int getOnlineCount(){
        return onlineCount;
    }
    public static synchronized void addOnlineCount(){
        WebSocketServer.onlineCount++;
    }
    public static synchronized void subOnlineCount(){
        WebSocketServer.onlineCount--;
    }

}

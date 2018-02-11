package xin.mafh.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * LiveWebsocket 继承WebSocketHandler对象
 *
 * @author creator mafh 2018年2月11日 上午10:26:42
 * @author updater
 * @version 1.0.0
 */
public class WebsocketHandler extends TextWebSocketHandler {
    // 这个会出现性能问题，最好用Map来存储，key用userid
    private static final ArrayList<WebSocketSession> users;

    static {
        users = new ArrayList<WebSocketSession>();
    }

    /**
     * (连接成功时候，会触发页面上onopen方法)<br>
     *
     * @param session 会话
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("connect to the websocket success......当前数量:" + users.size());
        users.add(session);
        // 这块会实现自己业务，比如，当用户登录后，会把离线消息推送给用户
        // TextMessage returnMessage = new TextMessage("你将收到的离线");
        // session.sendMessage(returnMessage);
    }

    /**
     * (关闭连接时触发)<br>
     *
     * @param session 会话
     * @param status  状态
     * @throws Exception 异常
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        String username = (String) session.getAttributes().get("WEBSOCKET_USERNAME");
        System.out.println("用户" + username + "已退出！");
        users.remove(session);
        System.out.println("剩余在线用户" + users.size());
    }

    /**
     * (js调用websocket.send时候，会调用该方法)<br>
     *
     * @param session 会话
     * @param message 信息
     * @throws Exception 异常
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        sendMessageToAll(message);
//        super.handleTextMessage(session, message);
    }

    /**
     * (发生异常是处理)<br>
     *
     * @param session   会话
     * @param exception 异常
     * @throws Exception 异常
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

        if (session.isOpen()) {
            session.close();
        }
        System.out.println("websocket connection closed......");
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

//    /**
//     * (给特定用户发送消息)<br>
//     *
//     * @param session 用户会话
//     * @param message  消息
//     */
//    public void sendMessageToUser(WebSocketSession session, TextMessage message) {
//        for (WebSocketSession user : users) {
//            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
//                try {
//                    if (user.isOpen()) {
//                        user.sendMessage(message);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }

    /**
     * (给特定用户发送消息)<br>
     *
     * @param userName 用户名
     * @param message  消息
     */
    public void sendMessageToUser(String userName, TextMessage message) {
        for (WebSocketSession user : users) {
            if (user.getAttributes().get("WEBSOCKET_USERNAME").equals(userName)) {
                try {
                    if (user.isOpen()) {
                        user.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    /**
     * (给所有在线用户发送消息)<br>
     *
     * @param message 消息
     */
    public void sendMessageToAll(TextMessage message) {
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

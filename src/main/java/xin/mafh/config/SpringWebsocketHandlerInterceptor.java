package xin.mafh.config;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ObjectUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


/**
 * 
 * SpringWebsocketHandlerInterceptor websocket拦截器
 * 
 * @author creator mafh 2018年2月11日 上午10:31:27
 * @author updater
 *
 * @version 1.0.0
 */
public class SpringWebsocketHandlerInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                    Map<String, Object> map) throws Exception {
        if (request instanceof ServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession(false);
            if (!ObjectUtils.isEmpty(session)) {
                // 使用userName区分WebSocketHandler，以便定向发送信息
                String userName = (String) session.getAttribute("SESSION_USERNAME");
                if (ObjectUtils.isEmpty(userName)) {
                    userName = "default-system";
                }
                map.put("WEBSOCKET_USERNAME", userName);
            }
        }
        return super.beforeHandshake(request, response, wsHandler, map);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                    Exception ex) {
        // TODO Auto-generated method stub
        super.afterHandshake(request, response, wsHandler, ex);
    }
}

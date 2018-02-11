package xin.mafh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.socket.TextMessage;
import xin.mafh.config.WebsocketHandler;

/**
 * WebSocketAction 定义一个控制器用来做连接标识和发送消息
 *
 * @author creator mafh 2018年2月11日 上午11:15:07
 * @author updater
 * @version 1.0.0
 */
@Controller
public class WebSocketAction {

    /**
     * 取出bean
     */
    @Bean
    public WebsocketHandler websocketHandler() {
        return new WebsocketHandler();
    }

    @RequestMapping("/websocket/login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        System.out.println(username + "登录");
        HttpSession session = request.getSession(false);
        session.setAttribute("SESSION_USERNAME", username);
        return new ModelAndView("send");
    }

    @RequestMapping("/websocket/send")
    @ResponseBody
    public String send(HttpServletRequest request) {
        String username = request.getParameter("username");
        websocketHandler().sendMessageToUser(username, new TextMessage("你好，测试！！！！"));
        return null;
    }
}

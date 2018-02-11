package xin.mafh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;


/**
 * SpringWebsocketConfig 实现WebsocketConfig接口
 *
 * @author creator mafh 2018年2月11日 上午10:23:34
 * @author updater
 * @version 1.0.0
 */
@Configuration
@EnableWebMvc
@EnableWebSocket
public class SpringWebsocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    /**
     * (注入实例对象)<br>
     *
     * @return 对象
     */
    @Bean
    public TextWebSocketHandler webSocketHandler() {
        return new WebsocketHandler();
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler(), "/websocket/socketServer")
                .addInterceptors(new SpringWebsocketHandlerInterceptor());
        registry.addHandler(webSocketHandler(), "/sockjs/socketServer")
                .addInterceptors(new SpringWebsocketHandlerInterceptor()).withSockJS();

    }

}

package xin.mafh.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author creator mafh 2018/2/11 11:42
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@Controller
public class HelloController {
    //添加一个日志器
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    //映射一个action
    @RequestMapping("/index")
    public String index() {
        //输出日志文件
        logger.info("the first jsp pages");
        //返回一个index.jsp这个视图
        return "index";
    }
}

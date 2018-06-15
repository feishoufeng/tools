package cc.zytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by ZHANGYANG on 2016/10/18.
 */
@Controller
public class Application {
    @RequestMapping({"/","/index"})
    public String index(){
        return "view/index";
    }
}

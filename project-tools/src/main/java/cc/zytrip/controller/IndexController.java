package cc.zytrip.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2017/4/20.
 *
 * @author YangZhang
 */
@Controller
public class IndexController {
    @RequestMapping(value={"","/","/index"},method = RequestMethod.GET)
    public String index(){
        return "/index";
    }
}

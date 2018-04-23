package cn.e3mall.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Registration function
 * @author Darwin
 * @date 2018/4/23
 */
@Controller
public class RegisterController {

    @RequestMapping("/page/register")
    public String showRegister() {
        return "register";
    }

}

package com.Gu.token.Controller;

import ch.qos.logback.core.joran.action.AppenderRefAction;
import com.Gu.token.create.Token04Sample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource
    private Token04Sample token;

    @GetMapping("create")
    public String CreateToken(@RequestParam("userId") String userId) {
        return token.CreateToken(userId);
    }
}

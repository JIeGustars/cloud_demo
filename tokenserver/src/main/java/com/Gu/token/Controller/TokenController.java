package com.Gu.token.Controller;

import com.Gu.token.create.BaiduToken;
import com.Gu.token.create.Token04Sample;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/token")
@ResponseBody
public class TokenController {
    @Resource
    private Token04Sample token;

    @GetMapping("zego")
    public String CreateZeGoToken(@RequestParam("userId") String userId) {
        return token.CreateToken(userId);
    }

    @GetMapping("baidu")
    public String CreateBaiDuToken() {
        return BaiduToken.getAuth();
    }
}

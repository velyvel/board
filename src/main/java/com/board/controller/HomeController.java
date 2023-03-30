package com.board.controller;

import com.board.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    @Qualifier("homeService")
    private HomeService homeService;

    @GetMapping(path={"/","/index"})
    public String home(){
        return "index";
    }


}

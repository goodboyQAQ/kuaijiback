package org.wang.kuaijiback;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @RequestMapping(value="/",produces = "text/plain;charset=UTF-8")
    String index(){
        return "Hello Spring Boot!";
    }

    @RequestMapping("/i")
    String i(){
        return "i";
    }

    @RequestMapping("/api/company/getcompany")
    String aa(){
        return "iaaaaaaa";
    }

}

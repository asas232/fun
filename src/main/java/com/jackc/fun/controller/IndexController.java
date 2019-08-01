package com.jackc.fun.controller;

import com.jackc.fun.selenium.MfcWebsite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 9:39
 */
@RestController
public class IndexController {

    @Autowired
     private MfcWebsite mfcWebsite;

      @GetMapping("/")
      public ModelAndView index(){
          return new ModelAndView("index");
      }

    @GetMapping("/openMfcIndex")
    public String openMfcIndex(){
        mfcWebsite.openIndex();
        return "ok";
    }
}

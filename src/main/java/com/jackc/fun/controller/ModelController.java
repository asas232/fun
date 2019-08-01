package com.jackc.fun.controller;

import com.jackc.fun.pojo.MfcModelVo;
import com.jackc.fun.selenium.MfcWebsite;
import com.jackc.fun.service.MfcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description
 * @Author chenjie
 * @DATE 2019/6/28 9:39
 */
@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
     private MfcWebsite mfcWebsite;

      @RequestMapping()
      public ModelAndView model(Model model){
          model.addAttribute("models",MfcService.OnlineModels);
          return new ModelAndView("model");
      }

    @GetMapping("/list")
    public List<MfcModelVo> modelJson(){
       return MfcService.OnlineModels;
    }


    @GetMapping("/add")
    public String add(@RequestParam Integer uid,@RequestParam String userName){
        MfcModelVo mfcModelVo = new MfcModelVo();
        mfcModelVo.setUid(uid);
        mfcModelVo.setName(userName);
        MfcService.addLikeModel(mfcModelVo);
        return "ok";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam Integer uid){
        MfcModelVo mfcModelVo = new MfcModelVo();
        mfcModelVo.setUid(uid);
        MfcService.removeLikeModel(mfcModelVo);
        return "ok";
    }

}

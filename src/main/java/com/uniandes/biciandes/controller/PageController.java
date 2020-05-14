package com.uniandes.biciandes.controller;

import com.uniandes.biciandes.config.ProductoConfig;
import com.uniandes.biciandes.exception.NullAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    ProductoConfig productoConfig;

    @GetMapping({"/","/index"})
    public String index(Model model, @RequestParam(value = "sidebar", required = false) String id) {

        int flagSideBar = 1;
        if (id == null) flagSideBar = 1;
        if (id != null) {
            if (id.equals("1"))
                flagSideBar = 0;
            if (id.equals("0"))
                flagSideBar = 1;
        }else
            flagSideBar=0;

        try {
            //TODO: Do something with model
            Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).orElseThrow(() -> new NullAuthenticationException("User has no session"));
        }catch (NullAuthenticationException e) {
            return "login";
        }

        model.addAttribute("titulo", "BiciAndes");
        model.addAttribute("sidebar", flagSideBar);
        model.addAttribute("version", productoConfig.getVersion());

        model.addAttribute("bicycle", productoConfig.getHasBicycle());
        model.addAttribute("publish", productoConfig.getHasPublish());
        model.addAttribute("groups", productoConfig.getHasGroups());

        return "index";
    }

    @GetMapping("/secret")
    @ResponseBody
    public String greetings() {
        return "Hello world";
    }

}

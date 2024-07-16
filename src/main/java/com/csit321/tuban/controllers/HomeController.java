package com.csit321.tuban.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String getHome(){
        return "<h1>Weclome</h1>" +
                "<ul><li>Name: JIV SABOLO TUBAN</li><li>Nickname: DODONG</li></ul>";
    }

    @GetMapping("/mypersonaldata")
    public String getPersonalData(){
        return  "<h1>My Personal Data</h1><br>"+"<b>Name:</b>"+
                " Jiv S. Tuban<br>"+"<b>Nickname:</b>"+" Dodong<br>"+
                "<b>Address:</b>"+" Babag 2, Supersunlight Trigon<br>"+
                "<b>Course & Year:</b>"+" BSIT 3";
    }

    @GetMapping("/personaldatawithparams")
    public String getPersonalData(@RequestParam String name){
        return "<h1>My Personal Data</h1><br>"+
                "<b>Name:</b>"+" "+name+"<br>"+
                "<b>Nickname:</b>"+" Dodong<br>"+
                "<b>Address:</b>"+" Babag 2, Supersunlight Trigon<br>"+
                "<b>Course & Year:</b>"+" BSIT 3";
    }

}

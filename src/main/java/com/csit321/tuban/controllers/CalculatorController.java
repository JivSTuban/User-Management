package com.csit321.tuban.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator")
public class CalculatorController {
    @GetMapping
    public String getCalculator(){
        return "My Calculator<br><br>" +
                "<a href='http://localhost:8080/calculator/add?var1=6&var2=2'>Add</a><br>"+"<a href='http://localhost:8080/calculator/subtract?var1=6&var2=2'>Subtract</a><br>"+"<a href='http://localhost:8080/calculator/multiply?var1=6&var2=2'>Multiply</a><br>"+"<a href='http://localhost:8080/calculator/divide?var1=6&var2=2'>Divide</a><br>";
    }

    @GetMapping("/add")
    public String add(@RequestParam int var1, @RequestParam int var2){
        return "Output: "+(var1+var2);
    }
    @GetMapping("/subtract")
    public String subtract(@RequestParam int var1, @RequestParam int var2){
        return "Output: "+(var1-var2);
    }
    @GetMapping("/multiply")
    public String multiply(@RequestParam int var1, @RequestParam int var2){
        return "Output: "+(var1*var2);
    }
    @GetMapping("/divide")
    public String divide(@RequestParam int var1, @RequestParam int var2){
        return "Output: "+(var1/var2);
    }

}

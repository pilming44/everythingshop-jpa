package com.jpa.everythingshop.controller;

import com.jpa.everythingshop.form.ProductSearchForm;
import com.jpa.everythingshop.logTrace.Trace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
@Slf4j
@Trace
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(@Validated @ModelAttribute ProductSearchForm productSearchForm, BindingResult bindingResult, Model model) {
        log.info("시작시 productSearchForm : {}", productSearchForm);
        if(bindingResult.hasErrors()) {
            //검색값중 잘못된 값이 있다면 검색값 초기화
            productSearchForm = null;
            log.info("바인딩오류발생");
        }

        if(productSearchForm.getFromPrice() != null && productSearchForm.getToPrice() != null
                && (productSearchForm.getFromPrice() > productSearchForm.getToPrice())) {
            //검색 시작가격이 종료가격보다 크다면 스왑
            Integer tempPrice = productSearchForm.getFromPrice();
            productSearchForm.setFromPrice(productSearchForm.getToPrice());
            productSearchForm.setToPrice(tempPrice);
        }

        if(!bindingResult.hasErrors()) {
            model.addAttribute("productSearchForm", productSearchForm);
        }
        log.info("종료시 productSearchForm : {}", productSearchForm);
        log.info("종료시 bindingResult : {}", bindingResult);
        return "home";
    }
}

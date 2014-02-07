package tut.webdata.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {

	@RequestMapping(value = "/signin")  //bez lomitka
	public String signin() {
        return "/signin/signin";		//bez lomitka
    }
}

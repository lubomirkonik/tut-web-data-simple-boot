package tut.webdata.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

//import thymeleafexamples.layouts.account.*;
//import thymeleafexamples.layouts.support.web.*;


import tut.webdata.domain.Account;
//import tut.webdata.repository.AccountRepository;
import tut.webdata.services.AccountService;
import tut.webdata.services.UserService;
import tut.webdata.support.web.*;
import tut.webdata.domain.SignupForm;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "/signup/signup";  //bez lomitka

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/signup")    							  //bez lomitka
	public String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(new SignupForm());
        if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return SIGNUP_VIEW_NAME.concat(" :: signupForm");
        }
        return SIGNUP_VIEW_NAME;
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.POST)	  //bez lomitka
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountService.save(signupForm.createAccount());
		userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
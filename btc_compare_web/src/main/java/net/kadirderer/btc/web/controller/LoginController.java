package net.kadirderer.btc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request) {		
		
		if(request.getRemoteUser() != null) {
			return "redirect:index.html";
		}	
		
		return "login";
	}
	
}

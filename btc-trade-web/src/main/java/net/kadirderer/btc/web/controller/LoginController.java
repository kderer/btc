package net.kadirderer.btc.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import net.kadirderer.btc.web.util.WebUtil;

@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, ModelMap model) {		
		
		if (request.getRemoteUser() != null) {
			return "redirect:index.html";
		}
		else if (request.getParameter("error") != null) {
			WebUtil.addMessage(model, "label.login.error", false);
		}
		
		return "login";
	}
	
}

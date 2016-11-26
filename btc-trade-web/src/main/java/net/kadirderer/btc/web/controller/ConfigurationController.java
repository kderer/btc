package net.kadirderer.btc.web.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.kadirderer.btc.db.model.Configuration;
import net.kadirderer.btc.service.ConfigurationService;
import net.kadirderer.btc.web.dto.ConfigurationDto;
import net.kadirderer.btc.web.dto.DatatableAjaxResponse;
import net.kadirderer.btc.web.util.WebUtil;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController {
	
	@Autowired
	private ConfigurationService configService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {		
		return "tiles.configuration";
	}
	
	@ResponseBody
	@RequestMapping(value = "/query", method = RequestMethod.GET)	
	public DatatableAjaxResponse<Configuration> query(@RequestParam("length") int pageSize, 
			@RequestParam("start") int start, @RequestParam("draw") int draw) {
				
		DatatableAjaxResponse<Configuration> response = configService.query(pageSize, start/pageSize);
		response.setDraw(draw);
		
		return response;
	}	
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("editConfig", new ConfigurationDto());		
		return "config/edit_config";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String update(ModelMap model, @RequestParam("id") Integer id) {		
		model.addAttribute("editConfig", configService.findById(id));
		return "config/edit_config";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String save(ModelMap model,
			@ModelAttribute("editConfig") @Valid ConfigurationDto configuration,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "config/edit_config";
		};
		
		try {
			Configuration conf = configService.save(configuration);
			model.addAttribute("editConfig", configService.findById(conf.getId()));
			WebUtil.addMessage(model, "message.configuration.save.successful", true);
		} catch (Exception e) {
			WebUtil.addMessage(model, "message.configuration.save.error", false);
		}
		
		return "config/edit_config";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String confirmDelete(ModelMap model, @RequestParam("id") @NotNull Integer id) {
		model.addAttribute("deleteConfig", configService.findById(id));
		
		return "config/delete_config";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(ModelMap model,
			@ModelAttribute("deleteConfig") ConfigurationDto configuration,
			RedirectAttributes redirectAttributes) {		
		
		try {
			configService.delete(configuration.getId());
			WebUtil.addRedirectMessage(redirectAttributes, "message.configuration.delete.successful", true);
		} catch(Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.configuration.delete.error", true);
		}

		return "redirect:/configuration.html";
	}
	
	
	
	

}

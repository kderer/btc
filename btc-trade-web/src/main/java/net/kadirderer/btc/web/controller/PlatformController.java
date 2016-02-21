package net.kadirderer.btc.web.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.service.BtcPlatformService;
import net.kadirderer.btc.web.dto.BtcPlatformDto;
import net.kadirderer.btc.web.util.WebUtil;
import net.kadirderer.btc.web.validator.BtcPlatformDtoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/platform")
public class PlatformController {

	@Autowired
	private BtcPlatformService bpService;
	
	/* If this is not commented out, hibernate validator will be ignored.
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(new BtcPlatformDtoValidator());
	}
	*/

	@RequestMapping(method = RequestMethod.GET)
	public String index(ModelMap model) {
		model.addAttribute("platforms", bpService.queryAll());
		return "platform/list_platform";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String newPlatform(ModelMap model) {
		model.addAttribute("newPlatform", new BtcPlatformDto());
		return "platform/new_platform";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPlatform(ModelMap model,
			@ModelAttribute("newPlatform") @Valid BtcPlatformDto btcPlatform,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		//run Spring validator manually
		new BtcPlatformDtoValidator().validate(btcPlatform, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "platform/new_platform";
		}

		BtcPlatform bp = bpService.save(btcPlatform);

		if (bp.getId() == null) {
			WebUtil.addMessage(model, "message.newplatform.saved.error", false);
			return "platform/new_platform";
		} else {
			WebUtil.addRedirectMessage(redirectAttributes, "message.newplatform.saved.succesful", true);			
		}
		
		return "redirect:edit.html?id=" + bp.getId();	
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editPlatform(ModelMap model, RedirectAttributes redirectAttributes,
			@RequestParam(value = "id") @NotNull int platformId) {
		
		BtcPlatformDto editPlatform = bpService.queryById(platformId);
		
		if(editPlatform == null) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.editplatform.notfound", false);
			return "redirect:/platform.html";
		}
		
		model.addAttribute("editPlatform", editPlatform);

		return "platform/edit_platform";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPlatform(ModelMap model,
			@ModelAttribute("editPlatform") @Valid BtcPlatformDto btcPlatform,
			BindingResult bindingResult) {
		
		new BtcPlatformDtoValidator().validate(btcPlatform, bindingResult);
		
		if (bindingResult.hasErrors()) {
			BtcPlatformDto bp = bpService.queryById(btcPlatform.getId());
			btcPlatform.setApiList(bp.getApiList());
			return "platform/edit_platform";
		}
		
		BtcPlatform bp = bpService.save(btcPlatform);
		
		BtcPlatformDto bpDto = bpService.queryById(btcPlatform.getId());
		btcPlatform.setApiList(bpDto.getApiList());

		if (bp.getId() == null) {
			WebUtil.addMessage(model, "message.editplatform.saved.error", false);
		} else {
			WebUtil.addMessage(model, "message.editplatform.saved.succesful", true);			
		}

		return "platform/edit_platform";
	}
	
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deletePlatform(ModelMap model,
			@RequestParam(value = "id") @NotNull int platformId) {

		model.addAttribute("deletePlatform", bpService.queryById(platformId));

		return "platform/delete_platform";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePlatformSubmit(ModelMap model,
			@RequestParam(value = "id") @NotNull int platformId, 
			RedirectAttributes redirectAttributes) {	
		
		try {
			bpService.deleteById(platformId);
			WebUtil.addRedirectMessage(redirectAttributes, "message.deleteplatform.deleted.succesful", true);
		} catch(Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.deleteplatform.deleted.error", true);
		}

		return "redirect:/platform.html";
	}

}

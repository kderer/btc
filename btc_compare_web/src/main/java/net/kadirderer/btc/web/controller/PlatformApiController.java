package net.kadirderer.btc.web.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import net.kadirderer.btc.api.ApiType;
import net.kadirderer.btc.db.model.PlatformAPI;
import net.kadirderer.btc.service.PlatformApiService;
import net.kadirderer.btc.web.dto.PlatformApiDto;
import net.kadirderer.btc.web.util.WebUtil;

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
@RequestMapping("/platformApi")
public class PlatformApiController {
	
	@Autowired
	private PlatformApiService paService;
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String newPlatform(ModelMap model, @RequestParam(value="platformId") int platformId) {		
		PlatformApiDto paDto = new PlatformApiDto();
		paDto.setPlatformId(platformId);
		
		model.addAttribute("newPlatformApi", paDto);		
		return "api/new_api";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addPlatformApi(ModelMap model, @ModelAttribute("newPlatformApi") @Valid PlatformApiDto platformApi,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		
		if (bindingResult.hasErrors()) {
			return "api/new_api";
		}

		PlatformAPI pa = paService.save(platformApi);

		if (pa.getId() == null) {
			WebUtil.addMessage(model, "message.newapi.save.error", false);
			return "api/new_api";
		} else {
			WebUtil.addRedirectMessage(redirectAttributes, "message.newapi.save.successful", true);			
		}
		
		return "redirect:/platformApi/edit.html?id=" + pa.getId() ;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editPlatformApi(ModelMap model, @RequestParam(value="id") int platformApiId) {
		
		model.addAttribute("editPlatformApi", paService.queryById(platformApiId));
		
		return "api/edit_api";
	}
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String editPlatformApi(ModelMap model,
			@ModelAttribute("editPlatformApi") @Valid PlatformApiDto platformApi,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "api/edit_api";
		}
		
		try {
			paService.save(platformApi);
			WebUtil.addMessage(model, "message.editplatform.saved.succesful", true);
		} catch (Exception e) {
			WebUtil.addMessage(model, "message.editplatform.saved.error", false);
		}

		return "api/edit_api";
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deletePlatformApi(ModelMap model,
			@RequestParam(value = "id") @NotNull int platformApiId) {

		model.addAttribute("deletePlatformApi", paService.queryById(platformApiId));

		return "api/delete_api";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deletePlatformSubmit(ModelMap model,
			@RequestParam(value = "id") @NotNull int platformApiId, @RequestParam(value = "platformId") @NotNull int platformId,
			RedirectAttributes redirectAttributes) {	
		
		try {
			paService.deleteById(platformApiId);
			WebUtil.addRedirectMessage(redirectAttributes, "message.deleteapi.delete.successful", true);
		} catch(Exception e) {
			WebUtil.addRedirectMessage(redirectAttributes, "message.deleteapi.delete.error", true);
		}

		return "redirect:/platform/edit.html?id=" + platformId;
	}
	
	
	@ModelAttribute("apiTypes")
	public ApiType[] apiTypes() {
		return ApiType.values();
	}

}

package net.kadirderer.btc.web.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class WebUtil {

	@SuppressWarnings("unchecked")
	public static void addRedirectMessage(RedirectAttributes redirectAttributes,
			String messageKey, boolean isSuccessMessage) {
		
		List<String> messageKeyList;		
		
		if (isSuccessMessage) {
			messageKeyList = (List<String>)redirectAttributes.getFlashAttributes().get(WebConstants.REQUEST_SUCCESS_MESSAGES);
			if(messageKeyList == null) {
				messageKeyList = new ArrayList<String>();
			}
			messageKeyList.add(messageKey);
			redirectAttributes.addFlashAttribute(WebConstants.REQUEST_SUCCESS_MESSAGES, messageKeyList);
		} else {
			messageKeyList = (List<String>)redirectAttributes.getFlashAttributes().get(WebConstants.REQUEST_ERROR_MESSAGES);
			if(messageKeyList == null) {
				messageKeyList = new ArrayList<String>();
			}
			messageKeyList.add(messageKey);
			redirectAttributes.addFlashAttribute(WebConstants.REQUEST_ERROR_MESSAGES, messageKeyList);
		}

	}
	
	
	@SuppressWarnings("unchecked")
	public static void addMessage(ModelMap model,
			String messageKey, boolean isSuccessMessage) {
		
		List<String> messageKeyList;		
		
		if (isSuccessMessage) {
			messageKeyList = (List<String>)model.get(WebConstants.REQUEST_SUCCESS_MESSAGES);
			if(messageKeyList == null) {
				messageKeyList = new ArrayList<String>();
			}
			messageKeyList.add(messageKey);
			model.addAttribute(WebConstants.REQUEST_SUCCESS_MESSAGES, messageKeyList);
		} else {
			messageKeyList = (List<String>)model.get(WebConstants.REQUEST_ERROR_MESSAGES);
			if(messageKeyList == null) {
				messageKeyList = new ArrayList<String>();
			}
			messageKeyList.add(messageKey);
			model.addAttribute(WebConstants.REQUEST_ERROR_MESSAGES, messageKeyList);
		}
	}

}

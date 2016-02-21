package net.kadirderer.btc.web.validator;

import net.kadirderer.btc.web.dto.BtcPlatformDto;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BtcPlatformDtoValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BtcPlatformDto.class.equals(clazz);
	}

	@Override
	public void validate(Object dto, Errors errors) {
		BtcPlatformDto bpDto = (BtcPlatformDto)dto;
		
		if(bpDto.getCode().contains(" ")) {
			errors.rejectValue("code", "NoSpace.newPlatform.code");
		}		
	}	

}

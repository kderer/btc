package net.kadirderer.btc.web.test.service;

import java.lang.reflect.InvocationTargetException;

import net.kadirderer.btc.db.model.BtcPlatform;
import net.kadirderer.btc.web.dto.BtcPlatformDto;

import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

public class BtcPlatformServiceTest {
	
	
	@Test
	public void testBeanUtils() {
		
		BtcPlatform plt = new BtcPlatform();
		
		plt.setHomeUrl("aasdas");
		
		BtcPlatformDto dto = new BtcPlatformDto();
		
		
		try {
			Object value = BeanUtils.getPropertyDescriptor(plt.getClass(), "homeUrl").getReadMethod().invoke(plt);
			System.out.println(value);
			
			BeanUtils.getPropertyDescriptor(dto.getClass(), "url").getWriteMethod().invoke(dto, value);
			System.out.println(dto.getUrl());
		} catch (BeansException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}

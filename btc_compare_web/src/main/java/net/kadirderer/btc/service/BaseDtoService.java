package net.kadirderer.btc.service;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public abstract class BaseDtoService<T, S> {

	public abstract void customCopyToDto(T modelObject, S dtoObject);
	
	public abstract void customCopyToModel(S dtoObject, T modelObject);

	public T createModel(S dtoObject) {
		T modelObject = instantiateModel();
		BeanUtils.copyProperties(dtoObject, modelObject);
		customCopyToModel(dtoObject, modelObject);
		
		return modelObject;
	}

	public List<S> createDtoList(List<T> modelList) {
		List<S> dtoList = new ArrayList<S>();

		for (T modelObject : modelList) {
			S dtoObject = instantiateDto();
			BeanUtils.copyProperties(modelObject, dtoObject);
			customCopyToDto(modelObject, dtoObject);			

			dtoList.add(dtoObject);
		}

		return dtoList;
	}
	
	public S createDto(T modelObject) {
		
		if(modelObject == null) {
			return null;
		}
		
		S dtoObject = instantiateDto();
		BeanUtils.copyProperties(modelObject, dtoObject);
		customCopyToDto(modelObject, dtoObject);

		return dtoObject;
	}
	

	@SuppressWarnings("unchecked")
	private T instantiateModel() {
		T modelObject = BeanUtils
				.instantiate((Class<T>) ((ParameterizedType) getClass()
						.getGenericSuperclass()).getActualTypeArguments()[0]);

		return modelObject;
	}

	@SuppressWarnings("unchecked")
	private S instantiateDto() {
		S dtoObject = BeanUtils
				.instantiate((Class<S>) ((ParameterizedType) getClass()
						.getGenericSuperclass()).getActualTypeArguments()[1]);

		return dtoObject;
	}

}

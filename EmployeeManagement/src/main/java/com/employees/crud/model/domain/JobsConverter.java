package com.employees.crud.model.domain;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class JobsConverter implements AttributeConverter<Jobs, String> {

	@Override
	public String convertToDatabaseColumn(Jobs jobs) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(jobs).map(Jobs::getJobTitle).orElse(null);		
	}


	@Override
	public Jobs convertToEntityAttribute(String dbData) {
		
		return Jobs.decode(dbData);
	}
}

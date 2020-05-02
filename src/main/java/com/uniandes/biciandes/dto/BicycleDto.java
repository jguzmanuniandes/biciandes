package com.uniandes.biciandes.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.uniandes.biciandes.model.Bicycle;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BicycleDto {
	
	private Long id;
	
	@NotNull
    @NotEmpty
    private String model;
	
	@NotNull
    @NotEmpty
    private String brand;
	
	@NotNull
    @NotEmpty
    private String color;
	
	
	public Bicycle toBicycleEntity() {
		
		Bicycle bicycle = new Bicycle();
		bicycle.setId(id);
		bicycle.setModel(model);
		bicycle.setBrand(brand);
		bicycle.setColor(color);
		
		//TODO: completar usuario
		
		return bicycle;
	}

}

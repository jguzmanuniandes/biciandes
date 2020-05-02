package com.uniandes.biciandes.dto;

import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.model.Video;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class FileDto {

    private String url;
	
	@NotNull
    @NotEmpty
    private String description;
	
	@NotNull
    @NotEmpty
    private String type;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

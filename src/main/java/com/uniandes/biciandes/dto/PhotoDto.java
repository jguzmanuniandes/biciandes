package com.uniandes.biciandes.dto;

import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.model.Photo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PhotoDto {

	@NotNull
    @NotEmpty
    private String url;
	
	@NotNull
    @NotEmpty
    private String description;
	
	@NotNull
    @NotEmpty
    private LocalDate date;
	

    public Photo toPhotoEntity() {

    	Photo photo = new Photo();
    	photo.setUrl(url);
    	photo.setDescription(description);
    	photo.setDate(date);

        //TODO: Fill more fields
        return photo;
    }
    
}

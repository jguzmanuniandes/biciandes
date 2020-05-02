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
public class VideoDto {

	@NotNull
    @NotEmpty
    private String url;
	
	@NotNull
    @NotEmpty
    private String description;
	
	@NotNull
    @NotEmpty
    private LocalDate date;
	

    public Video toVideoEntity() {

    	Video video = new Video();
    	video.setUrl(url);
    	video.setDescription(description);
    	video.setDate(date);

        //TODO: Fill more fields
        return video;
    }    
}

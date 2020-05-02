package com.uniandes.biciandes.dto;

import com.uniandes.biciandes.model.Publish;
import com.uniandes.biciandes.model.Video;
import com.uniandes.biciandes.model.Photo;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PublishDto {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String description;
    
    @NotNull
    @NotEmpty
    private String url;


    public Publish toPublishEntity() {

        Publish publish = new Publish();
        
        publish.setDescription(description);

        //TODO: Fill more fields
        return publish;
    }
    
    public Video toVideoEntity() {

        Video video = new Video();
        
        video.setDescription(description);

        //TODO: Fill more fields
        return video;
    }
    
    public Photo toPhotoEntity() {

        Photo photo = new Photo();
        
        photo.setDescription(description);

        //TODO: Fill more fields
        return photo;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
    
}

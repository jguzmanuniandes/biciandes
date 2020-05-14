package com.uniandes.biciandes.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("producto")
@Data
public class ProductoConfig {
	
	private String version; //Autowired by confProperties annotation
	private Boolean hasPicture;
	private Boolean hasBicycle;
	private Boolean hasPublish;
	private Boolean hasPublishPhoto;
	private Boolean hasPublishVideo;
	private Boolean hasGroups;

	public ProductoConfig(@Value("${feature.profile.picture}") String picture,
						  @Value("${feature.form.bicycle}") String bicycle,
						  @Value("${feature.publish}") String publish,
						  @Value("${feature.publish.photo}") String publishPhoto,
						  @Value("${feature.publish.video}") String publishVideo,
						  @Value("${feature.groups}") String groups) {
		hasPicture = Boolean.parseBoolean(picture);
		hasBicycle = Boolean.parseBoolean(bicycle);
		hasPublish = Boolean.parseBoolean(publish);
		hasPublishPhoto = Boolean.parseBoolean(publishPhoto);
		hasPublishVideo = Boolean.parseBoolean(publishVideo);
		hasGroups = Boolean.parseBoolean(groups);
	}

}

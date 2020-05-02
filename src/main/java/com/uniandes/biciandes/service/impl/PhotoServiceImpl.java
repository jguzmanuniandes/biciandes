package com.uniandes.biciandes.service.impl;


import com.uniandes.biciandes.model.Photo;
import com.uniandes.biciandes.repository.PhotoRepository;
import com.uniandes.biciandes.service.PhotoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PhotoServiceImpl implements PhotoService {

    private PhotoRepository photoRepository;

    @Autowired
    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Photo savePhoto(Photo photo) {

        //TODO: Validate information
        return photoRepository.save(photo);

    }
    
    @Override
	public List<Photo> getUserPhotos(String userId) {
		return photoRepository.findByUserEmail(userId);
	}
}

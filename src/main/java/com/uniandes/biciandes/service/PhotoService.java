package com.uniandes.biciandes.service;

import java.util.List;

import com.uniandes.biciandes.model.Photo;

public interface PhotoService {

    Photo savePhoto(Photo photo);
      
    List<Photo> getUserPhotos(String userId);

}

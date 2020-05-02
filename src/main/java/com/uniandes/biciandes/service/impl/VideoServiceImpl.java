package com.uniandes.biciandes.service.impl;

import com.uniandes.biciandes.model.Video;
import com.uniandes.biciandes.repository.VideoRepository;
import com.uniandes.biciandes.service.VideoService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class VideoServiceImpl implements VideoService {

    private VideoRepository videoRepository;

    @Autowired
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video saveVideo(Video video) {

        //TODO: Validate information
        return videoRepository.save(video);

    }
    
    @Override
	public List<Video> getUserVideos(String userId) {
		return videoRepository.findByUserEmail(userId);
	}
}

package com.uniandes.biciandes.service;

import java.util.List;

import com.uniandes.biciandes.model.Video;

public interface VideoService {

    Video saveVideo(Video video);

    List<Video> getUserVideos(String userId);
}

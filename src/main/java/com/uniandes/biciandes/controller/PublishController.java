package com.uniandes.biciandes.controller;
import com.amazonaws.auth.AWSCredentials;

import com.uniandes.biciandes.config.ProductoConfig;
import com.uniandes.biciandes.dto.PublishDto;
import com.uniandes.biciandes.dto.UserDto;
import com.uniandes.biciandes.dto.VideoDto;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.uniandes.biciandes.dto.FileDto;
import com.uniandes.biciandes.dto.PhotoDto;
import com.uniandes.biciandes.exception.NullAuthenticationException;
import com.uniandes.biciandes.model.Photo;
import com.uniandes.biciandes.model.User;
import com.uniandes.biciandes.model.Video;
import com.uniandes.biciandes.service.PhotoService;
import com.uniandes.biciandes.service.UserService;
import com.uniandes.biciandes.service.VideoService;
import com.uniandes.biciandes.util.S3Upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

@Controller
public class PublishController {
	
	private S3Upload s3Upload;
	
	private PhotoService photoService;
	
	private VideoService videoService;
	
	private UserService userService;

	@Autowired
	ProductoConfig productoConfig;

	public PublishController(S3Upload s3Upload, PhotoService photoService, VideoService videoService, UserService userService) {
		this.s3Upload = s3Upload;
		this.photoService = photoService;
		this.videoService = videoService;
		this.userService = userService;
	}
	
	@GetMapping("/publish")
	public String publish(Model model, Authentication authentication) {
		model.addAttribute("publish", new PublishDto());
		
		model.addAttribute("photos", photoService.getUserPhotos(authentication.getName()));
		model.addAttribute("videos", videoService.getUserVideos(authentication.getName()));
		model.addAttribute("version", productoConfig.getVersion());

		model.addAttribute("publishVideo", productoConfig.getHasPublishVideo());
		
		return "publish";
	}

    @PostMapping("/upload")
    public String upload(@Valid @ModelAttribute FileDto fileDto, Model model, @RequestParam(value = "file", required = false) MultipartFile archivo, Authentication authentication) {

    	LocalDate lt = LocalDate.now();
    	
    	User usuario = userService.getUser(authentication.getName());
    	
    	System.out.println(archivo);

        if (archivo!=null && !archivo.isEmpty()) {
        	
        	String url = s3Upload.uploadFile(archivo);
        	
        	System.out.println(url);
        	
        	if(fileDto.getType().equals("photo")) {
        		Photo photo = new Photo();
        		photo.setUrl(url);
        		photo.setDescription(fileDto.getDescription());
        		photo.setDate(lt);
        		photo.setUser(usuario);
        		
        		photoService.savePhoto(photo);
        	}
        	else if(fileDto.getType().equals("video")) {
        		Video video = new Video();
        		video.setUrl(url);
        		video.setDescription(fileDto.getDescription());
        		video.setDate(lt);
        		video.setUser(usuario);
        		
        		videoService.saveVideo(video);
        		
        	}
            
        }

        return "redirect:publish";
    }

}

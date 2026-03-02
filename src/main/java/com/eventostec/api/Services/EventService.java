package com.eventostec.api.Services;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.Domain.event.Event;
import com.eventostec.api.Domain.event.EventRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {
    public Event createEvent(EventRequestDTO data) {
        String imgUrl = null;
        if (data.image() != null){
            imgUrl = uploadImage(data.image());
        }

        Event event = new Event();
        event.setTitle(data.title());
        event.setDescription(data.description());
        event.setDate(new Date(data.date()));
        event.setEventUrl(data.eventUrl());
        event.setRemote(data.remote());
        event.setImgUrl(imgUrl);

        return event;
    }

//    @Autowired
//    private AmazonS3 s3Client(){
//
//    }

    private String uploadImage(MultipartFile image) {
        String imgName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        try{
            File file = this.convertMultipartToFile(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "";
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write((file.getBytes()));
        fos.close();
        return convFile;
    }
}

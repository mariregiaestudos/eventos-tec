package com.eventostec.api.Services;

import com.amazonaws.services.s3.AmazonS3;
import com.eventostec.api.Domain.event.Event;
import com.eventostec.api.Domain.event.EventRequestDTO;
import com.eventostec.api.Domain.event.EventResponseDTO;
import com.eventostec.api.Repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EventService {

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

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
        event.setImageUrl(imgUrl);

        eventRepository.save(event);

        return event;
    }

//    @Autowired
//    private AmazonS3 s3Client(){
//
//    }
    @Value("${aws.bucket.name}")
    private String bucketName;

    private String uploadImage(MultipartFile image) {
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();
        try{
            File file = this.convertMultipartToFile(image);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            return s3Client.getUrl(bucketName, fileName).toString();
        } catch (Exception e) {
            System.out.println("erro ao subir arquivo");
            return "";
        }
    }

    public List<EventResponseDTO> getAllEvent(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Event> eventsPage = this.eventRepository.findAll(pageable);
        return eventsPage.map(event -> new EventResponseDTO(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getDate(),"", "",
                event.getRemote(),
                event.getEventUrl(),
                event.getImageUrl())
        ).stream().toList();
    }

//    public List<EventResponseDTO> getAllUpcomingEvent(int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<Event> eventsPage = this.eventRepository.findAllUpcomingEvents(pageable);
//        return eventsPage.map(event -> new EventResponseDTO(
//                event.getId(),
//                event.getTitle(),
//                event.getDescription(),
//                event.getDate(),"", "",
//                event.getRemote(),
//                event.getEventUrl(),
//                event.getImageUrl())
//        ).stream().toList();
//    }

    private File convertMultipartToFile(MultipartFile file) throws IOException {
        File convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write((file.getBytes()));
        fos.close();
        return convFile;
    }
}

package com.eventostec.api.Repositories;

import com.eventostec.api.Domain.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

//    public List<Event> findAllUpcomingEvents(){
//        "SELECT "
//    }
}

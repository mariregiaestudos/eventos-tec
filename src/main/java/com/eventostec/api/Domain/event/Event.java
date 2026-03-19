package com.eventostec.api.Domain.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Table(name = "event")
@Entity
@Getter
@Setter
//para construtor que receba todos ou nenhum argumentos dessa classe
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    //significa que eh gerado automaticamente pela table
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private String imageUrl;
    private String eventUrl;
    private Boolean remote;
    private Date date;

}

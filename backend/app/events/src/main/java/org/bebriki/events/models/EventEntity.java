package org.bebriki.events.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.MonthDay;
import java.time.Year;

@Data
@Entity
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String sourceUrl;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Year eventYear;

    @Column(nullable = false)
    private MonthDay eventDate;

//
//
//    @ManyToMany
//    @JoinTable(
//            name = "pet_friends",
//            joinColumns = @JoinColumn(name = "pet_id"),
//            inverseJoinColumns = @JoinColumn(name = "friend_id")
//    )
//    private Collection<PetEntity> friends = new HashSet<>();
}

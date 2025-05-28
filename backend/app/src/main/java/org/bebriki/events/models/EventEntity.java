package org.bebriki.events.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.MonthDay;
import java.time.Year;

@Entity
@Table(name = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(nullable = false)
    private int month;

    @Column(nullable = false)
    private int day;

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

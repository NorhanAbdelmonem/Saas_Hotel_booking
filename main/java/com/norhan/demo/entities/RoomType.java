package com.norhan.demo.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "room_types")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;  // مثل Single, Double, Suite

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "roomType", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    public void addRoom(Room room) {
        rooms.add(room);
        room.setRoomType(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.setRoomType(null);
    }

    @Override
    public String toString() {
        return "RoomType(id=" + id + ", name=" + name + ")";
    }
}

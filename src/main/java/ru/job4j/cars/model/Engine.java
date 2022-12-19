package ru.job4j.cars.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;
import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@Table(name = "engine")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Include
    private int id;
    private String name;
}
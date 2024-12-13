package org.identileaf.identileafcore.model;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String scientificName;
    private String commonName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plant_type_id")
    private PlantType plantType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaf_type_id")
    private LeafType leafType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bark_type_id")
    private BarkType barkType;
}

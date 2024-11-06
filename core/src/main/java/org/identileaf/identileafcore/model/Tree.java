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

    private int PlantType_ID;
    private int LeafType_ID;
    private int BarkType_ID;
}

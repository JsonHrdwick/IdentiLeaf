package org.identileaf.identileafcore.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String role;
}
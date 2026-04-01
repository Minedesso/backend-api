package com.minedesso.backendapi.ban.persistence;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "reason")
public class ReasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reasonId;
    private String reason;

}

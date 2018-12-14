package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "locations")
@Data
@NoArgsConstructor
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLocation;

    private String name;

    private String city;

    private String province;

    private String country;
}

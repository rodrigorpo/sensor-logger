package com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "measures")
@Data
@NoArgsConstructor
public class Measures implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long measureId;

    private BigDecimal airTemperature;

    private BigDecimal airHumidity;

    private BigDecimal soilHumidity;

    private BigDecimal lighting;

    @ManyToOne
    @JoinColumn(name = "users")
    private User user;

    @ManyToOne
    @JoinColumn(name = "locations")
    private Location location;

}

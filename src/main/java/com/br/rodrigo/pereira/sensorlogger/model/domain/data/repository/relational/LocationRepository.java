package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {

    Location findByName(String name);

    @Override
    List<Location> findAll();

    List<Location> findByCity(String city);

    List<Location> findByProvince(String province);

    List<Location> findByCountry(String country);
}

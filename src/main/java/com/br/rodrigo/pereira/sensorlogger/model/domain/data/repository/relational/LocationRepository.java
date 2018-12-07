package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
}

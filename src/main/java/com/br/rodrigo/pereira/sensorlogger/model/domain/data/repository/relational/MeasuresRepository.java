package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Measures;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasuresRepository extends CrudRepository<Measures, Long> {
}

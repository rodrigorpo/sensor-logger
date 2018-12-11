package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.documents.LogDocumment;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.Location;
import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDocumentRepository extends MongoRepository<LogDocumment, String> {

    User findByUser(User user);

    Location findByLocation(Location location);
}

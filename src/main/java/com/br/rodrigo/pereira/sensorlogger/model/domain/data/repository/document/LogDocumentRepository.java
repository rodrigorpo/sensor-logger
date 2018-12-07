package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.document;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.documents.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogDocumentRepository extends MongoRepository<Log, String> {

}

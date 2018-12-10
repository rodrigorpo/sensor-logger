package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.UserIdentity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserIdentityRepository extends CrudRepository<UserIdentity, Long> {
    UserIdentity findByUsername(String username);

    Boolean existsByUsername(String username);
}
package com.br.rodrigo.pereira.sensorlogger.model.domain.data.repository.relational;

import com.br.rodrigo.pereira.sensorlogger.model.domain.data.persistent.relational.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User save(User user);

    User findByUsername(String username);
}

package de.codeforheilbronn.mycfhn.strichliste.repository;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    List<User> findAll(Sort sort);
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}

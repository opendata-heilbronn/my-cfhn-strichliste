package de.codeforheilbronn.mycfhn.strichliste.repository;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
}

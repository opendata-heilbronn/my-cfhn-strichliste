package de.codeforheilbronn.mycfhn.strichliste.repository;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, ObjectId> {
}

package de.codeforheilbronn.mycfhn.strichliste.repository;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.LogEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogEntryRepository extends MongoRepository<LogEntry, ObjectId> {
}

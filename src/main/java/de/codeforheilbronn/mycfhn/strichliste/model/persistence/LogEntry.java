package de.codeforheilbronn.mycfhn.strichliste.model.persistence;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;
import org.springframework.ldap.odm.annotations.Id;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Document
public class LogEntry {
    @Id
    private ObjectId id;
    @NonNull
    private LocalDateTime date;
    @NonNull
    private LogEntryType type;
    @NonNull
    private String username;
    @Nullable
    private Map<ObjectId, Long> products;
    @Nullable
    private Long balanceDiff;

    public enum LogEntryType {
        PAY, CONSUME
    }
}

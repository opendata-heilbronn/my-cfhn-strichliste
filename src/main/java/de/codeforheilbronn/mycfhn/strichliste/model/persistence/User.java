package de.codeforheilbronn.mycfhn.strichliste.model.persistence;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Map;

@Data
@Document
@NoArgsConstructor
@RequiredArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @NonNull
    private String username;
    @NonNull
    private boolean isCfhn;
    @NonNull
    private Long balance = 0L;
    @NonNull
    private Map<ObjectId, Long> consumption = Collections.emptyMap();
}

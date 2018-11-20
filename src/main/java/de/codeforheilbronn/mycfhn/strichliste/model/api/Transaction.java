package de.codeforheilbronn.mycfhn.strichliste.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.codeforheilbronn.mycfhn.strichliste.model.persistence.LogEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class Transaction {
    private LocalDateTime date;
    private String username;
    private LogEntry.LogEntryType type;
    private Map<String, Long> products;
    private Long balanceDifference;
}

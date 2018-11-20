package de.codeforheilbronn.mycfhn.strichliste.service;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.LogEntry;
import de.codeforheilbronn.mycfhn.strichliste.repository.LogEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoggingService {

    private LogEntryRepository logEntryRepository;

    public LoggingService(LogEntryRepository logEntryRepository) {
        this.logEntryRepository = logEntryRepository;
    }

    public void logPayment(String username, Long amount) {
        LogEntry logEntry = new LogEntry(LocalDateTime.now(), LogEntry.LogEntryType.PAY, username);
        logEntry.setBalanceDiff(amount);

        logEntryRepository.save(logEntry);
    }

    public void logConsumption(String username, Map<String, Long> products) {
        LogEntry logEntry = new LogEntry(LocalDateTime.now(), LogEntry.LogEntryType.CONSUME, username);
        logEntry.setProducts(products.entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> new ObjectId(entry.getKey()), Map.Entry::getValue)));

        logEntryRepository.save(logEntry);
    }
}

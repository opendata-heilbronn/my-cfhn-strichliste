package de.codeforheilbronn.mycfhn.strichliste.controller;

import de.codeforheilbronn.mycfhn.strichliste.auth.Authenticated;
import de.codeforheilbronn.mycfhn.strichliste.auth.CfhnAuthenticationService;
import de.codeforheilbronn.mycfhn.strichliste.auth.TokenData;
import de.codeforheilbronn.mycfhn.strichliste.model.api.Transaction;
import de.codeforheilbronn.mycfhn.strichliste.repository.LogEntryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class LogController {

    private CfhnAuthenticationService authenticationService;
    private LogEntryRepository logEntryRepository;

    @GetMapping("/{username}")
    @Authenticated
    public List<Transaction> getUserTransactions(
            @PathVariable String username
    ) {
        Optional<TokenData> tokenDataOptional = authenticationService.getUserData();
        if (tokenDataOptional.isPresent()) {
            TokenData tokenData = tokenDataOptional.get();

            if (authenticationService.hasAnyGroupMember("boardMembers", "infrastructureAdmins") || tokenData.getUsername().equalsIgnoreCase(username)) {
                return logEntryRepository.findAll(Sort.by(Sort.Direction.ASC, "date")).stream()
                        .map(logEntry -> Transaction.builder()
                                .date(logEntry.getDate())
                                .type(logEntry.getType())
                                .balanceDifference(logEntry.getBalanceDiff())
                                .products(logEntry.getProducts() != null ? logEntry.getProducts().entrySet().stream().collect(Collectors.toMap(entry -> entry.getKey().toString(), Map.Entry::getValue)) : null)
                                .username(logEntry.getUsername())
                                .build()
                        ).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}

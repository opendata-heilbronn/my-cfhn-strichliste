package de.codeforheilbronn.mycfhn.strichliste.service;

import de.codeforheilbronn.mycfhn.strichliste.model.persistence.User;
import de.codeforheilbronn.mycfhn.strichliste.repository.LdapUserRepository;
import de.codeforheilbronn.mycfhn.strichliste.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LdapService {

    private LdapUserRepository ldapUserRepository;
    private UserRepository userRepository;

    public LdapService(LdapUserRepository ldapUserRepository, UserRepository userRepository) {
        this.ldapUserRepository = ldapUserRepository;
        this.userRepository = userRepository;
    }

    @Scheduled(fixedDelay = 10*60*1000, initialDelay = 1000)
    public void syncLdapUsers() {
        log.info("Starting LDAP user synchronization");
        List<User> currentUsers = userRepository.findAll();
        List<User> missingUsers = ldapUserRepository.findAllByUserType("member")
                .stream()
                .filter(ldapUser -> currentUsers.stream()
                        .noneMatch(user -> user.getUsername().equals(ldapUser.getUsername())))
                .map(ldapUser -> new User(ldapUser.getUsername(), true))
                .collect(Collectors.toList());
        userRepository.saveAll(missingUsers);
        log.info("Synchronized {} LDAP users", missingUsers.size());
    }
}

package de.codeforheilbronn.mycfhn.strichliste.repository;

import de.codeforheilbronn.mycfhn.strichliste.model.ldap.LdapUser;
import org.springframework.data.ldap.repository.LdapRepository;

import java.util.List;

public interface LdapUserRepository extends LdapRepository<LdapUser> {
    List<LdapUser> findAllByUserType(String usertype);
}

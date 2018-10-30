package de.codeforheilbronn.mycfhn.strichliste.model.ldap;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

@Entry(objectClasses = {"inetOrgPerson", "member"}, base = "ou=users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class LdapUser {
    @Id
    private Name dn;

    @Attribute(name = "cn")
    @DnAttribute(value = "cn", index = 1)
    private String username;

    @Attribute(name = "employeeType")
    private String userType;
}

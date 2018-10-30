package de.codeforheilbronn.mycfhn.strichliste.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
@AllArgsConstructor
public class TokenData {
    private String username;
    private List<String> groups;
}

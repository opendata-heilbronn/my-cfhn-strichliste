package de.codeforheilbronn.mycfhn.strichliste.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Map;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class UserModel {
    private String id;
    private String username;
    private boolean isCfhn;
    private Long outstandingBalance;
    private Map<String, Long> consumption;
}

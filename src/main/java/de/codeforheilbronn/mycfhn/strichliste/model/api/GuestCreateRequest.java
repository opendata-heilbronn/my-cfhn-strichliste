package de.codeforheilbronn.mycfhn.strichliste.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(onConstructor = @__(@JsonCreator))
public class GuestCreateRequest {
    @NonNull
    private String username;
}

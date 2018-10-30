package de.codeforheilbronn.mycfhn.strichliste.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(onConstructor = @__(@JsonCreator))
public class ProductCreateModel {
    private String name;
    private Long price;
    private byte[] photo;
}

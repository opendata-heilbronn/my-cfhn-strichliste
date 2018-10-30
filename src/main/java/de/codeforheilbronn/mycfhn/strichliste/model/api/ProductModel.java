package de.codeforheilbronn.mycfhn.strichliste.model.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(onConstructor = @__({@JsonCreator}))
public class ProductModel {
    private String id;
    private String name;
    private Long price;
}

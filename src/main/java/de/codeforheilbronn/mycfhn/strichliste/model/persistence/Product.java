package de.codeforheilbronn.mycfhn.strichliste.model.persistence;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Product {
    @Id
    private ObjectId id;
    private String name;
    private Long price;
    private byte[] picture;
}

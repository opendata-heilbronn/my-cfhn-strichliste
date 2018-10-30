package de.codeforheilbronn.mycfhn.strichliste.service;

import de.codeforheilbronn.mycfhn.strichliste.model.api.UserModel;
import de.codeforheilbronn.mycfhn.strichliste.model.persistence.Product;
import de.codeforheilbronn.mycfhn.strichliste.model.persistence.User;
import de.codeforheilbronn.mycfhn.strichliste.repository.ProductRepository;
import de.codeforheilbronn.mycfhn.strichliste.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private ProductRepository productRepository;
    private MongoTemplate mongoTemplate;

    public List<UserModel> getUserOverview() {
        Map<ObjectId, Product> products = getProducts();
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> toModel(user, products))
                .collect(Collectors.toList());
    }

    private UserModel toModel(User user, Map<ObjectId, Product> products) {
        Map<Product, Long> productConsumption = user.getConsumption()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(entry -> products.get(entry.getKey()), Map.Entry::getValue));

        long outstanding = productConsumption.entrySet().stream()
                .mapToLong(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum() - user.getBalance();

        return UserModel.builder()
                .id(user.getId().toString())
                .username(user.getUsername())
                .isCfhn(user.isCfhn())
                .outstandingBalance(outstanding)
                .consumption(productConsumption
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(entry -> entry.getKey().getId().toString(), Map.Entry::getValue))
                ).build();
    }

    private Map<ObjectId, Product> getProducts() {
        return productRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));
    }

    public Optional<UserModel> consume(String username, Map<String, Long> products) {
        mongoTemplate.updateFirst(Query.query(where("username").is(username)), buildUpdates(products), User.class);
        return userRepository.findByUsername(username).map(user -> toModel(user, getProducts()));
    }

    private Update buildUpdates(Map<String, Long> products) {
        Update update = new Update();
        for(Map.Entry<String, Long> product : products.entrySet()) {
            update = update.inc("consumption." + product.getKey(), product.getValue());
        }
        return update;
    }

    public Optional<UserModel> pay(String username, Long amount) {
        mongoTemplate.updateFirst(Query.query(where("username").is(username)), new Update().inc("balance", amount), User.class);
        return userRepository.findByUsername(username).map(user -> toModel(user, getProducts()));
    }
}

package springboot.flowershop_be.repositories;

import java.util.List;
import springboot.flowershop_be.entities.Flower;

public interface FlowerRepository {
    List<Flower> getAll();

    Flower getById(int flowerId);

    int create(String name, String imageUrl, int price, int quantity, String category);

    void update(int flowerId, Flower flower);

    void delete(int flowerId);
}

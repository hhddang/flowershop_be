package springboot.flowershop_be.services;

import java.util.List;

import springboot.flowershop_be.entities.Flower;

public interface FlowerService {
    // General
    List<Flower> getAllFlower();

    Flower getFlowerById(int flowerId);

    void buyFlower(int flowerId, int quantity);

    // Only owner
    int addFlower(String name, String imageUrl, int price, int quantity, String category);

    void editFlower(int flowerId, Flower flower);

    void deleteFlower(int flowerId);
}

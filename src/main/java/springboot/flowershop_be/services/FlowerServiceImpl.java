package springboot.flowershop_be.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springboot.flowershop_be.entities.Flower;
import springboot.flowershop_be.repositories.FlowerRepository;

@Service
@Transactional
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    FlowerRepository flowerRepository;

    @Override
    public List<Flower> getAllFlower() {
        return flowerRepository.getAll();
    }

    @Override
    public Flower getFlowerById(int flowerId) {
        return flowerRepository.getById(flowerId);
    }

    @Override
    public void buyFlower(int flowerId, int quantity) {
        Flower flower = flowerRepository.getById(flowerId);
        int currQuantity = flower.getQuantity();
        flower.setQuantity(currQuantity - quantity);
        flowerRepository.update(flowerId, flower);
    }

    @Override
    public int addFlower(String name, String imageUrl, int price, int quantity, String category) {
        return flowerRepository.create(name, imageUrl, price, quantity, category);
    }

    @Override
    public void editFlower(int flowerId, Flower flower) {
        flowerRepository.update(flowerId, flower);
    }

    @Override
    public void deleteFlower(int flowerId) {
        flowerRepository.delete(flowerId);
    }

}

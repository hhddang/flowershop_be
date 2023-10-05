package springboot.flowershop_be.resources;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.flowershop_be.entities.Flower;
import springboot.flowershop_be.services.FlowerService;

@RestController
@RequestMapping("/api/flowers")
public class FlowerResource {
    @Autowired
    FlowerService flowerService;

    @GetMapping("")
    public ResponseEntity<List<Flower>> getAllFlowers() {
        List<Flower> flowers = flowerService.getAllFlower();
        return new ResponseEntity<List<Flower>>(flowers, HttpStatus.OK);
    }

    @GetMapping("/{flowerId}")
    public ResponseEntity<Flower> getFlowerById(@PathVariable("flowerId") Integer flowerId) {
        Flower flower = flowerService.getFlowerById(flowerId);
        return new ResponseEntity<Flower>(flower, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Map<String, Boolean>> addFlower(@RequestBody Map<String, Object> flowerMap) {
        String name = (String) flowerMap.get("name");
        String imageUrl = (String) flowerMap.get("imageUrl");
        int price = (int) flowerMap.get("price");
        int quantity = (int) flowerMap.get("quantity");
        String category = (String) flowerMap.get("category");

        flowerService.addFlower(name, imageUrl, price, quantity, category);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/update/{flowerId}")
    public ResponseEntity<Map<String, Boolean>> editFlower(@PathVariable("flowerId") int flowerId,
            @RequestBody Flower flower) {
        flowerService.editFlower(flowerId, flower);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/delete/{flowerId}")
    public ResponseEntity<Map<String, Boolean>> deleteFlower(@PathVariable("flowerId") int flowerId) {
        flowerService.deleteFlower(flowerId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/buy/{flowerId}")
    public ResponseEntity<Map<String, Boolean>> buyFlower(@PathVariable("flowerId") int flowerId,
            @RequestBody Map<String, Integer> quantityObject) {
        int quantity = (int) quantityObject.get("quantity");
        flowerService.buyFlower(flowerId, quantity);
        Map<String, Boolean> map = new HashMap<>();
        map.put("success", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}

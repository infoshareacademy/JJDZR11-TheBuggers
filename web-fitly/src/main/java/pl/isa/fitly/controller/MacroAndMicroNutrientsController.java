package pl.isa.fitly.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.isa.fitly.model.Product;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MacroAndMicroNutrientsController {
    @GetMapping("/MacroAndMicroNutrients")
    public String MacroAndMicroNutrients() {
        return "macroAndMicroNutrients";
    }

    @PostMapping("/MacroAndMicroNutrients/search")
    public String search(@RequestParam("productName") String productName, Model model) {
        try {
            ClassPathResource resource = new ClassPathResource("foods.json");
            byte[] jsonData = FileCopyUtils.copyToByteArray(resource.getInputStream());
            String jsonStr = new String(jsonData, StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();

            List<Product> products = objectMapper.readValue(jsonStr, new TypeReference<List<Product>>() {
            });

            productName = productName.toLowerCase();

            List<Product> foundProducts = new ArrayList<>();

            for (Product product : products) {
                String name = product.getName().toLowerCase();

                if (name.contains(productName)) {
                    foundProducts.add(product);
                }
            }

            model.addAttribute("foundProducts", foundProducts);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "macroAndMicroNutrients";
    }
}

package pl.isa.fitly.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


@Component
public class TrainingFileReader {
    private static final Logger logger = LoggerFactory.getLogger(TrainingFileReader.class);

    public void run(Model model) {
        displayTrainingFile(model);
    }

    public void displayTrainingFile(Model model) {
        try {
            InputStream inputStream = TrainingFileReader.class.getResourceAsStream("/trainings/training.txt");
            String fileContent = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            model.addAttribute("fileContent", fileContent);
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }
    }
}
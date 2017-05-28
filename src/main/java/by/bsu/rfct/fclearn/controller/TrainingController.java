package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/{userId:[\\d]+}/collections/{collectionId:[\\d]+}/training")
public class TrainingController {

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/card")
    public ResponseEntity getNextTrainingCard(@PathVariable("userId") Long userId, @PathVariable("collectionId") Long collectionId) {
        return new ResponseEntity<>(trainingService.getNextCard(userId, collectionId), HttpStatus.OK);
    }
}

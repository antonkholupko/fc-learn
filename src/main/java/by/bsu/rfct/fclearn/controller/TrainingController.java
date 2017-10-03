package by.bsu.rfct.fclearn.controller;

import by.bsu.rfct.fclearn.service.TrainingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainingController {

    private TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping("/users/{userId:[\\d]+}/collections/{collectionId:[\\d]+}/training/card")
    public ResponseEntity getNextTrainingCard(@PathVariable("userId") Long userId, @PathVariable("collectionId") Long collectionId) {
        return new ResponseEntity<>(trainingService.getNextCard(userId, collectionId), HttpStatus.OK);
    }

    @GetMapping("/users/{userId:[\\d]+}/cards/{cardId:[\\d]+}/known")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity knownCard(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId) {
        trainingService.knownCard(userId, cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/users/{userId:[\\d]+}/cards/{cardId:[\\d]+}/unknown")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity unknownCard(@PathVariable("userId") Long userId, @PathVariable("cardId") Long cardId) {
        trainingService.unknownCard(userId, cardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

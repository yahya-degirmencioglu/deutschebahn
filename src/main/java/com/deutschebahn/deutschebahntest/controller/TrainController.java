package com.deutschebahn.deutschebahntest.controller;

import com.deutschebahn.deutschebahntest.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/station")
public class TrainController {


    @Autowired
    private TrainService trainService;

    @GetMapping("/{ril100}/train/{trainNumber}/waggon/{number}")
    public ResponseEntity<Map<String, List<String>>> getTrainSections(@PathVariable("ril100") String ril100,
                                                                      @PathVariable("trainNumber") Long trainNumber,
                                                                      @PathVariable("number") int number) {
        System.out.println(ril100 + " - " + trainNumber + " - " + number);

        Map<String, List<String>> response = trainService.getTrainSections(ril100, trainNumber.toString(), String.valueOf(number));
        return  ResponseEntity.ok(response);

    }

}
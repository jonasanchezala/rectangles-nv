package com.nuvalence.rectangles.controller;

import com.nuvalence.rectangles.dto.CompareTwoRectangleDto;
import com.nuvalence.rectangles.exception.EmptyInterceptionException;
import com.nuvalence.rectangles.model.AdjacencyTypes;
import com.nuvalence.rectangles.model.ContainedTypes;
import com.nuvalence.rectangles.model.Rectangle;
import com.nuvalence.rectangles.service.RectangleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RectangleController {

    private final RectangleService rectangleService;

    @PostMapping("/intersection")
    public ResponseEntity<?> intersection(@RequestBody CompareTwoRectangleDto compareTwoRectangleDto) {
        try{
            return ResponseEntity.ok(rectangleService.calculateIntersection(compareTwoRectangleDto.rectangleA(),
                    compareTwoRectangleDto.rectangleB()));
        }
        catch (EmptyInterceptionException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/containment")
    public ResponseEntity<ContainedTypes> containment(@RequestBody CompareTwoRectangleDto compareTwoRectangleDto) {
        try{
            return ResponseEntity.ok(rectangleService.isContainment(compareTwoRectangleDto.rectangleA(),
                    compareTwoRectangleDto.rectangleB()));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/adjacency")
    public ResponseEntity<AdjacencyTypes> adjacency(@RequestBody CompareTwoRectangleDto compareTwoRectangleDto) {
        try{
            return ResponseEntity.ok(rectangleService.isAdjacent(compareTwoRectangleDto.rectangleA(),
                    compareTwoRectangleDto.rectangleB()));
        }
        catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
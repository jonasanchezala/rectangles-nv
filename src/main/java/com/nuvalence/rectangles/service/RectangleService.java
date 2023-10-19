package com.nuvalence.rectangles.service;

import com.nuvalence.rectangles.dto.RectangleDto;
import com.nuvalence.rectangles.exception.EmptyInterceptionException;
import com.nuvalence.rectangles.model.AdjacencyTypes;
import com.nuvalence.rectangles.model.ContainedTypes;
import com.nuvalence.rectangles.model.Rectangle;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RectangleService {


    public Rectangle calculateIntersection(RectangleDto rectangleA, RectangleDto rectangleB) {
        Optional<Rectangle> rectangle = rectangleA.toModel().getIntersectionRectangle(rectangleB.toModel());
        if(rectangle.isEmpty()){
            throw new EmptyInterceptionException("There is no interception");
        }
        return rectangle.get();
    }

    public ContainedTypes isContainment(RectangleDto rectangleA, RectangleDto rectangleB) {
        return rectangleA.toModel().isContained(rectangleB.toModel());
    }

    public AdjacencyTypes isAdjacent(RectangleDto rectangleA, RectangleDto rectangleB) {
        return rectangleA.toModel().isAdjacent(rectangleB.toModel());
    }
}
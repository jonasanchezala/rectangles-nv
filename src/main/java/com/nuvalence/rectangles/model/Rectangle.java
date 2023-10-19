package com.nuvalence.rectangles.model;

import com.nuvalence.rectangles.exception.InvalidRectangleException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
public class Rectangle {
    private Map<SideTypes, Line> lines;
    private Point bottomLeft;
    private Point topRight;

    public Rectangle(int x, int y, int width, int height) {
        if (width <= 0 && height <= 0) {
            throw new InvalidRectangleException("Invalid rectangle");
        }
        this.bottomLeft = Point.builder().x(x).y(y).build();
        this.topRight = Point.builder().x(x + width).y(y + height).build();
        this.lines = buildLines(bottomLeft, topRight);
    }

    private Map<SideTypes, Line> buildLines(Point bottomLeft, Point topRight) {
        Map<SideTypes, Line> lines = new HashMap<>();
        lines.put(SideTypes.TOP, Line.builder().start(
                        Point.builder().x(bottomLeft.getX()).y(topRight.getY()).build())
                .end(Point.builder().x(topRight.getX()).y(topRight.getY()).build()).sideTypes(SideTypes.TOP)
                .build());
        lines.put(SideTypes.BOTTOM, Line.builder().start(
                        Point.builder().x(bottomLeft.getX()).y(bottomLeft.getY()).build())
                .end(Point.builder().x(topRight.getX()).y(bottomLeft.getY()).build()).sideTypes(SideTypes.BOTTOM)
                .build());
        lines.put(SideTypes.RIGHT, Line.builder().start(
                        Point.builder().x(topRight.getX()).y(bottomLeft.getY()).build())
                .end(Point.builder().x(topRight.getX()).y(topRight.getY()).build()).sideTypes(SideTypes.RIGHT)
                .build());
        lines.put(SideTypes.LEFT, Line.builder().start(
                        Point.builder().x(bottomLeft.getX()).y(bottomLeft.getY()).build())
                .end(Point.builder().x(bottomLeft.getX()).y(topRight.getY()).build()).sideTypes(SideTypes.LEFT)
                .build());
        return lines;
    }

    public Optional<Rectangle> getIntersectionRectangle(Rectangle other) {
        int x1 = Math.max(this.getBottomLeft().getX(), other.getBottomLeft().getX());
        int y1 = Math.max(this.getBottomLeft().getY(), other.getBottomLeft().getY());
        int x2 = Math.min(this.getTopRight().getX(), other.getTopRight().getX());
        int y2 = Math.min(this.getTopRight().getY(), other.getTopRight().getY());

        if (isIntersected(x1, y1, x2, y2)) {
            return Optional.empty();
        }

        return Optional.of(new Rectangle(x1, y1, x2-x1, y2-y1));
    }

    private boolean isIntersected(int x1, int y1, int x2, int y2) {
        return x1 > x2 || y1 > y2;
    }

    public ContainedTypes isContained(Rectangle other) {
        if(isRectangleContained(other)){
            return ContainedTypes.CONTAINMENT;
        }

        Optional<Rectangle> optionalIntersectedRectangle = getIntersectionRectangle( other);

        if(optionalIntersectedRectangle.isEmpty()){
            return ContainedTypes.NO_CONTAINMENT;
        }

        return ContainedTypes.INTERSECTED;
    }

    private boolean isRectangleContained(Rectangle other) {
        return this.getBottomLeft().getX() <= other.getBottomLeft().getX()
                && this.getTopRight().getY() >= other.getTopRight().getY();
    }

    public AdjacencyTypes isAdjacent(Rectangle other){
        AdjacencyTypes verticalAdjacency = this.getLines().get(SideTypes.TOP)
                .getAdjacency(other.getLines().get(SideTypes.BOTTOM));
        if(!verticalAdjacency.equals(AdjacencyTypes.NOT_ADJACENT)){
            return verticalAdjacency;
        }
        return this.getLines().get(SideTypes.RIGHT).getAdjacency(other.getLines().get(SideTypes.LEFT));
    }

}

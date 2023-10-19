package com.nuvalence.rectangles.model;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Line {
    private Point start;
    private Point end;
    private SideTypes sideTypes;

    public AdjacencyTypes getAdjacency(Line other) {
        if(this.equals(other)){
            return AdjacencyTypes.PROPER;
        } else if(isHorizontal() && this.getStart().getY() == other.getStart().getY() ){
            return calculateHorizontalAdjacency(other);

        } else if(isVertical() && this.getStart().getX() == other.getStart().getX()){
            return calculateVerticalAdjacency(other);
        }
        return AdjacencyTypes.NOT_ADJACENT;
    }

    private boolean isHorizontal() {
        return sideTypes.equals(SideTypes.TOP) || sideTypes.equals(SideTypes.BOTTOM);
    }

    private boolean isVertical() {
        return sideTypes.equals(SideTypes.RIGHT) || sideTypes.equals(SideTypes.LEFT);
    }

    private AdjacencyTypes calculateHorizontalAdjacency(Line other) {
        if(this.getStart().getX() < other.getStart().getX() &&
                this.getEnd().getX() > other.getEnd().getX()
        ){
            return AdjacencyTypes.SUB_LINE;
        } else {
            return AdjacencyTypes.PARTIAL;
        }
    }

    private AdjacencyTypes calculateVerticalAdjacency(Line other) {
        if(this.getStart().getY() < other.getStart().getY() &&
                this.getEnd().getY() > other.getEnd().getY()
        ){
            return AdjacencyTypes.SUB_LINE;
        } else {
            return AdjacencyTypes.PARTIAL;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Line line = (Line) o;

        if (!Objects.equals(start, line.start)) return false;
        return Objects.equals(end, line.end);
    }

    @Override
    public int hashCode() {
        int result = start != null ? start.hashCode() : 0;
        result = 31 * result + (end != null ? end.hashCode() : 0);
        return result;
    }

}

package com.nuvalence.rectangles.dto;

import com.nuvalence.rectangles.model.Rectangle;

public record RectangleDto(int x, int y, int width, int height) {

    public Rectangle toModel(){
        return new Rectangle(x, y, width, height);
    }
}

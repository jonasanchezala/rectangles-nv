package com.nuvalence.rectangles.model;

import com.nuvalence.rectangles.exception.InvalidRectangleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class RectangleTest {

    private Rectangle rectangle;

    @BeforeEach
    public void setUp() {
        rectangle = new Rectangle(0, 0, 5, 5);
    }

    @Test
    public void testValidRectangleCreation() {
        assertDoesNotThrow(() -> new Rectangle(0, 0, 5, 5));
    }

    @Test
    public void testInvalidRectangleCreation() {
        assertThrows(InvalidRectangleException.class, () -> new Rectangle(0, 0, -1, -1));
    }

    @ParameterizedTest
    @MethodSource("getRectanglesWithIntersection")
    public void testGetIntersectionRectangle(Rectangle other, Rectangle expectedIntersection) {
        // Test the getIntersectionRectangle method
        Rectangle intersection = rectangle.getIntersectionRectangle(other).get();

        // Check if the intersection coordinates are as expected
        assertEquals(expectedIntersection, intersection);
    }

    private static Stream<Arguments> getRectanglesWithIntersection() {
        return Stream.of(
                Arguments.of(new Rectangle(2, 3,10,5), new Rectangle(2, 3,3,2)),
                Arguments.of(new Rectangle(1, 1,7,8), new Rectangle(1, 1,4,4)),
                Arguments.of(new Rectangle(4, 3,100,100), new Rectangle(4, 3,1,2))
        );
    }

    @ParameterizedTest
    @MethodSource("getRectanglesWithNoIntersection")
    public void testGetIntersectionRectangleIsEmpty(Rectangle other) {
        Optional<Rectangle> intersection = rectangle.getIntersectionRectangle(other);

        assertTrue(intersection.isEmpty());
    }

    private static Stream<Arguments> getRectanglesWithNoIntersection() {
        return Stream.of(
                Arguments.of(new Rectangle(6, 6,1,1)),
                Arguments.of(new Rectangle(10, 50,1,1)),
                Arguments.of(new Rectangle(300, 356,10,10))
        );
    }


    @ParameterizedTest
    @MethodSource("getRectanglesWithNoIntersection")
    public void testIsNoContained(Rectangle other) {
        ContainedTypes result = rectangle.isContained(other);

        assertEquals(ContainedTypes.NO_CONTAINMENT, result);
    }

    @ParameterizedTest
    @MethodSource("getRectanglesWithContainment")
    public void testIsContained(Rectangle other) {
        ContainedTypes result = rectangle.isContained(other);

        assertEquals(ContainedTypes.CONTAINMENT, result);
    }

    private static Stream<Arguments> getRectanglesWithContainment() {
        return Stream.of(
                Arguments.of(new Rectangle(1,1,2,2)),
                Arguments.of(new Rectangle(0,0,1,1)),
                Arguments.of(new Rectangle(2,3,1,1))
        );
    }

    @ParameterizedTest
    @MethodSource("getRectanglesWithNoContainmentButIntersection")
    public void testIsNotContainedButIntersected(Rectangle other) {
        ContainedTypes result = rectangle.isContained(other);

        assertEquals(ContainedTypes.INTERSECTED, result);
    }

    private static Stream<Arguments> getRectanglesWithNoContainmentButIntersection() {
        return Stream.of(
                Arguments.of(new Rectangle(1,1,20,24)),
                Arguments.of(new Rectangle(0,0,15,14)),
                Arguments.of(new Rectangle(2,3,11,15))
        );
    }

    @ParameterizedTest
    @MethodSource("getProperAdjacentRectangles")
    public void testIsProperAdjacent(Rectangle other) {
        AdjacencyTypes result = rectangle.isAdjacent(other);

        assertEquals(AdjacencyTypes.PROPER, result);
    }

    private static Stream<Arguments> getProperAdjacentRectangles() {
        return Stream.of(
                Arguments.of(new Rectangle(5,0,5,5)),
                Arguments.of(new Rectangle(0,5,5,5))
        );
    }

    @ParameterizedTest
    @MethodSource("getSublineAdjacentRectangles")
    public void testIsSublineAdjacent(Rectangle other) {
        AdjacencyTypes result = rectangle.isAdjacent(other);

        assertEquals(AdjacencyTypes.SUB_LINE, result);
    }

    private static Stream<Arguments> getSublineAdjacentRectangles() {
        return Stream.of(
                Arguments.of(new Rectangle(5,1,2,2)),
                Arguments.of(new Rectangle(1,5,2,2))
        );
    }


    @ParameterizedTest
    @MethodSource("getPartialAdjacentRectangles")
    public void testIsPartialAdjacent(Rectangle other) {
        // Test the isAdjacent method
        Rectangle adjacentRectangle = new Rectangle(5, 0, 5, 5);
        AdjacencyTypes result = rectangle.isAdjacent(other);

        // Ensure that the rectangles are horizontally adjacent
        assertEquals(AdjacencyTypes.PARTIAL, result);
    }

    private static Stream<Arguments> getPartialAdjacentRectangles() {
        return Stream.of(
                Arguments.of(new Rectangle(5,1,4,4)),
                Arguments.of(new Rectangle(1,5,4,4))
        );
    }

    @ParameterizedTest
    @MethodSource("getRectanglesWithNoIntersection")
    public void testIsNotAdjacent(Rectangle other) {
        AdjacencyTypes result = rectangle.isAdjacent(other);

        assertEquals(AdjacencyTypes.NOT_ADJACENT, result);
    }

}
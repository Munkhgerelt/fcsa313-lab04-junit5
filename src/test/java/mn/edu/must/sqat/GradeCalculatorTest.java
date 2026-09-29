package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class GradeCalculatorTest {

    @Test
    @DisplayName("90 оноо яг A дүн байна")
    void ninetyIsExactlyA() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(90);
        // Assert
        assertEquals("A", actual);
    }

    @Test
    @DisplayName("89.99 оноо B дүн байна")
    void justBelowNinetyIsB() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(89.99);
        // Assert
        assertEquals("B", actual);
    }

    @Test
    @DisplayName("60 оноо яг D дүн байна")
    void sixtyIsExactlyD() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(60);
        // Assert
        assertEquals("D", actual);
    }

    @Test
    @DisplayName("59.99 оноо F дүн байна")
    void justBelowSixtyIsF() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(59.99);
        // Assert
        assertEquals("F", actual);
    }

    @Test
    @DisplayName("0 оноо зөвшөөрөгдөж F дүн байна")
    void zeroIsF() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(0);
        // Assert
        assertEquals("F", actual);
    }

    @Test
    @DisplayName("100 оноо зөвшөөрөгдөж A дүн байна")
    void hundredIsA() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(100);
        // Assert
        assertEquals("A", actual);
    }

    @Test
    @DisplayName("Сөрөг нийт оноонд алдаа шиднэ")
    void negativeLetterScoreThrows() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.letterGrade(-1);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    @DisplayName("100-аас их нийт оноонд алдаа шиднэ")
    void aboveHundredThrows() {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.letterGrade(101);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }
}

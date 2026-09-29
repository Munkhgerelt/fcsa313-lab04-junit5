package mn.edu.must.sqat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({"95,A", "85,B", "75,C", "65,D", "30,F"})
    @DisplayName("Ердийн оноонууд зөв үсгэн дүнтэй байна")
    void typicalLetterGrades(double score, String expected) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(score);
        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} -> {1}")
    @CsvSource({"80,B", "79.99,C", "70,C", "69.99,D"})
    @DisplayName("80 ба 70 онооны зааг дээр зөв дүн гарна")
    void additionalLetterBoundaries(double score, String expected) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        String actual = calculator.letterGrade(score);
        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest(name = "{0} + {1} + {2} + {3} + {4} = {5}")
    @CsvSource({
            "10,40,10,10,30,100",
            "0,0,0,0,0,0",
            "8,32,7,9,20,76",
            "8.5,20.25,6.75,7,9,51.5",
            "0.1,0.1,0.1,0.1,0.1,0.5",
            "10,35,10,10,25,90"
    })
    @DisplayName("Зөвшөөрөгдсөн оноонуудын нийлбэр зөв байна")
    void validComponentTotals(double att, double lab, double quiz1,
                              double quiz2, double exam, double expected) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act
        double actual = calculator.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert: tolerate only floating-point rounding, not grading errors.
        assertEquals(expected, actual, 1e-9);
    }

    @ParameterizedTest(name = "negative component: {0}, {1}, {2}, {3}, {4}")
    @CsvSource({
            "-5,20,5,5,15",
            "5,-0.01,5,5,15",
            "5,20,-1,5,15",
            "5,20,5,-1,15",
            "5,20,5,5,-1"
    })
    @DisplayName("Бүрэлдэхүүн бүрийн сөрөг оноонд алдаа шиднэ")
    void negativeComponentsThrow(double att, double lab, double quiz1,
                                 double quiz2, double exam) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest(name = "excessive component: {0}, {1}, {2}, {3}, {4}")
    @CsvSource({
            "10.01,20,5,5,15",
            "5,41,5,5,15",
            "5,20,10.01,5,15",
            "5,20,5,10.01,15",
            "5,20,5,5,30.01"
    })
    @DisplayName("Бүрэлдэхүүн бүрийн дээд хязгаарыг давбал алдаа шиднэ")
    void aboveMaximumComponentsThrow(double att, double lab, double quiz1,
                                     double quiz2, double exam) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest(name = "invalid score: {0}")
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    @DisplayName("NaN болон хязгааргүй нийт оноонд алдаа шиднэ")
    void nonFiniteLetterScoresThrow(double score) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.letterGrade(score);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest(name = "non-finite component: {0}, {1}, {2}, {3}, {4}")
    @CsvSource({
            "NaN,20,5,5,15", "5,NaN,5,5,15", "5,20,NaN,5,15",
            "5,20,5,NaN,15", "5,20,5,5,NaN",
            "Infinity,20,5,5,15", "5,Infinity,5,5,15", "5,20,Infinity,5,15",
            "5,20,5,Infinity,15", "5,20,5,5,Infinity",
            "-Infinity,20,5,5,15", "5,-Infinity,5,5,15", "5,20,-Infinity,5,15",
            "5,20,5,-Infinity,15", "5,20,5,5,-Infinity"
    })
    @DisplayName("Бүрэлдэхүүн бүрийн NaN ба хязгааргүй оноог хориглоно")
    void nonFiniteComponentsThrow(double att, double lab, double quiz1,
                                 double quiz2, double exam) {
        // Arrange
        GradeCalculator calculator = new GradeCalculator();
        // Act (deferred until assertThrows)
        Executable action = () -> calculator.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }
}

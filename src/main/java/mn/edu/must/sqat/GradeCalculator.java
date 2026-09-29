package mn.edu.must.sqat;

/** Calculates grades using the F.CSA313 assessment limits. */
public class GradeCalculator {

    public String letterGrade(double score) {
        validateScore("score", score, 100);
        if (score >= 90) {
            return "A";
        }
        if (score >= 80) {
            return "B";
        }
        if (score >= 70) {
            return "C";
        }
        if (score >= 60) {
            return "D";
        }
        return "F";
    }

    public double totalScore(double att, double lab, double quiz1,
                             double quiz2, double exam) {
        validateScore("att", att, 10);
        validateScore("lab", lab, 40);
        validateScore("quiz1", quiz1, 10);
        validateScore("quiz2", quiz2, 10);
        validateScore("exam", exam, 30);
        return att + lab + quiz1 + quiz2 + exam;
    }

    private void validateScore(String name, double value, double maximum) {
        if (!Double.isFinite(value) || value < 0 || value > maximum) {
            throw new IllegalArgumentException(
                    name + " must be a finite number between 0 and " + maximum);
        }
    }
}

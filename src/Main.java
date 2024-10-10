import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String circleFilePath;
        String pointsFilePath;

        if (args.length < 2) {
            System.out.println("Please enter the path to the circle file:");
            circleFilePath = scanner.nextLine();
            System.out.println("Please enter the path to the points file:");
            pointsFilePath = scanner.nextLine();
        } else {
            circleFilePath = args[0];
            pointsFilePath = args[1];
        }

        try {
            // Read the coordinates of the circle's center and its radius
            BufferedReader circleReader = new BufferedReader(new FileReader(circleFilePath));
            String[] circleCenter = circleReader.readLine().split(" ");
            BigDecimal circleX = new BigDecimal(circleCenter[0]);
            BigDecimal circleY = new BigDecimal(circleCenter[1]);
            BigDecimal radius = new BigDecimal(circleReader.readLine());
            circleReader.close();

            // Validate circle coordinates and radius
            if (!isValidRange(circleX) || !isValidRange(circleY) || !isValidRange(radius)) {
                System.out.println("Circle coordinates or radius are out of valid range.");
                return;
            }

            // Read the coordinates of the points
            BufferedReader pointsReader = new BufferedReader(new FileReader(pointsFilePath));
            String point;
            int pointCount = 0;
            while ((point = pointsReader.readLine()) != null) {
                pointCount++;
                if (pointCount > 100) {
                    System.out.println("Number of points exceeds the limit of 100.");
                    return;
                }

                String[] pointCoords = point.split(" ");
                BigDecimal pointX = new BigDecimal(pointCoords[0]);
                BigDecimal pointY = new BigDecimal(pointCoords[1]);

                // Validate point coordinates
                if (!isValidRange(pointX) || !isValidRange(pointY)) {
                    System.out.println("Point coordinates are out of valid range.");
                    return;
                }

                // Calculate the distance from the point to the center of the circle
                BigDecimal distance = pointX.subtract(circleX).pow(2).add(pointY.subtract(circleY).pow(2)).sqrt(MathContext.DECIMAL128);

                // Determine the position of the point relative to the circle
                if (distance.compareTo(radius) < 0) {
                    System.out.println(1); // Point is inside the circle
                } else if (distance.compareTo(radius) == 0) {
                    System.out.println(0); // Point is on the circle
                } else {
                    System.out.println(2); // Point is outside the circle
                }
            }
            pointsReader.close();
        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }

    private static boolean isValidRange(BigDecimal value) {
        BigDecimal min = new BigDecimal("1E-38");
        BigDecimal max = new BigDecimal("1E38");
        return value.compareTo(min) >= 0 && value.compareTo(max) <= 0;
    }
}

import java.util.Scanner;

public class CalorieCalculator {

    // Constants for BMR formula coefficients and activity multipliers
    private static final double MALE_BMR_CONSTANT = 88.362;
    private static final double FEMALE_BMR_CONSTANT = 447.593;
    private static final double MALE_WEIGHT_COEFFICIENT = 13.397;
    private static final double FEMALE_WEIGHT_COEFFICIENT = 9.247;
    private static final double MALE_HEIGHT_COEFFICIENT = 4.799;
    private static final double FEMALE_HEIGHT_COEFFICIENT = 3.098;
    private static final double MALE_AGE_COEFFICIENT = 5.677;
    private static final double FEMALE_AGE_COEFFICIENT = 4.330;

    private static final double SEDENTARY_MULTIPLIER = 1.2;
    private static final double MODERATE_MULTIPLIER = 1.55;
    private static final double ACTIVE_MULTIPLIER = 1.725;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Calorie Calculator");

        //Info prompt
        System.out.print("Enter your gender(M/F): ");
        String gender = scanner.nextLine().trim().toUpperCase();

        //Check if the gender input is valid
        if(!gender.equals("M") && !gender.equals("F")){
            System.out.println("Invalid gender. Please enter M or F.");
            return;
        }

        System.out.print("Enter your age: ");
        int age = getValidIntInput(scanner);

        System.out.print("Enter your weight (in kilograms): ");
        double weight = getValidDoubleInput(scanner);

        System.out.print("Enter your height (in centimeters):");
        double height = getValidDoubleInput(scanner);
        scanner.nextLine();

        System.out.print("Enter your activity level (sedentary/moderate/active): ");
        String activityLevel = scanner.nextLine().trim().toLowerCase();

        //Check if activity level is valid
        if(!isValidActivityLevel(activityLevel)){
            System.out.println("Invalid activity level input. Please choose 'sedentary', 'moderate', or 'active'.");
            return;
        }

        //Calculate BMR
        double bmr = calculateBMR(gender, weight, height, age);

        //Calculate daily calories
        double calorieNeeds = calculateCalorieNeeds(bmr, activityLevel);

        System.out.printf("Your BMR is: %.0f calories per day.\n", bmr);
        System.out.printf("Your estimated daily calorie needs are: %.0f calories per day.\n", calorieNeeds);

        scanner.close();
    }
    //Int validation
    private static int getValidIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.next();  // Clear invalid input
        }
        return scanner.nextInt();
    }

    //Double validation
    private static double getValidDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();  // Clear invalid input
        }
        return scanner.nextDouble();
    }

    //Activity validation
    public static boolean isValidActivityLevel(String activityLevel) {
        return activityLevel.equals("sedentary") || activityLevel.equals("moderate") || activityLevel.equals("active");
    }

    //BMR Calculator
    public static double calculateBMR (String gender, double weight, double height, int age) {
        double bmr;
        if(gender.equals("M")){
            bmr = MALE_BMR_CONSTANT + (MALE_WEIGHT_COEFFICIENT * weight) + (MALE_HEIGHT_COEFFICIENT * height) - (MALE_AGE_COEFFICIENT * age);
        } else {
            bmr = FEMALE_BMR_CONSTANT + (FEMALE_WEIGHT_COEFFICIENT * weight) + (FEMALE_HEIGHT_COEFFICIENT * height) - (FEMALE_AGE_COEFFICIENT * age);
        }
        return bmr;
    }

    //Calorie calculator
    private static double calculateCalorieNeeds(double bmr, String activityLevel) {
        return switch (activityLevel) {
            case "sedentary" -> bmr * SEDENTARY_MULTIPLIER;
            case "moderate" -> bmr * MODERATE_MULTIPLIER;
            case "active" -> bmr * ACTIVE_MULTIPLIER;
            default -> throw new IllegalArgumentException("Invalid activity level");
        };
    }


}
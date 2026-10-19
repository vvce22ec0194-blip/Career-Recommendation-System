package Project;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

// Student class
class Student {
    private String name;
    private int javaSkill, sqlSkill, htmlSkill, electronicsSkill;
    private int programmingInterest, testingInterest, embeddedInterest;

    public Student(String name, int javaSkill, int sqlSkill,
            int htmlSkill, int electronicsSkill,
            int programmingInterest, int testingInterest,
            int embeddedInterest) {

        this.name = name;
        this.javaSkill = javaSkill;
        this.sqlSkill = sqlSkill;
        this.htmlSkill = htmlSkill;
        this.electronicsSkill = electronicsSkill;
        this.programmingInterest = programmingInterest;
        this.testingInterest = testingInterest;
        this.embeddedInterest = embeddedInterest;
    }

    public String getName() { return name; }
    public int getJavaSkill() { return javaSkill; }
    public int getSqlSkill() { return sqlSkill; }
    public int getHtmlSkill() { return htmlSkill; }
    public int getElectronicsSkill() { return electronicsSkill; }
    public int getProgrammingInterest() { return programmingInterest; }
    public int getTestingInterest() { return testingInterest; }
    public int getEmbeddedInterest() { return embeddedInterest; }
}

// Parent class
abstract class Career {
    private String careerName;
    private int score;

    public Career(String careerName) {
        this.careerName = careerName;
    }

    public String getCareerName() { return careerName; }
    public int getScore() { return score; }

    public void setScore(int score) {
        this.score = score;
    }

    public abstract void calculateScore(Student student);
}

// Career 1
class JavaDeveloper extends Career {
    public JavaDeveloper() {
        super("Java Developer");
    }

    @Override
    public void calculateScore(Student s) {
        double total =
                s.getJavaSkill() * 0.40
              + s.getSqlSkill() * 0.20
              + s.getHtmlSkill() * 0.10
              + s.getProgrammingInterest() * 0.30;

        setScore((int) Math.round(total * 20));
    }
}

// Career 2
class QAEngineer extends Career {
    public QAEngineer() {
        super("QA Engineer");
    }

    @Override
    public void calculateScore(Student s) {
        double total =
                s.getJavaSkill() * 0.15
              + s.getSqlSkill() * 0.20
              + s.getHtmlSkill() * 0.10
              + s.getTestingInterest() * 0.55;

        setScore((int) Math.round(total * 20));
    }
}

// Career 3
class EmbeddedDeveloper extends Career {
    public EmbeddedDeveloper() {
        super("Embedded Developer");
    }

    @Override
    public void calculateScore(Student s) {
        double total =
                s.getElectronicsSkill() * 0.50
              + s.getProgrammingInterest() * 0.20
              + s.getEmbeddedInterest() * 0.30;

        setScore((int) Math.round(total * 20));
    }
}

// Main class
public class CareerRecommendationApp {
    static Scanner sc = new Scanner(System.in);

    public static int getRating(String message) {
        while (true) {
            System.out.print(message);

            if (sc.hasNextInt()) {
                int value = sc.nextInt();

                if (value >= 1 && value <= 5) {
                    return value;
                }
                System.out.println("Enter a rating from 1 to 5.");
            } else {
                System.out.println("Invalid input. Enter a number.");
                sc.next();
            }
        }
    }

    public static void main(String[] args) {
        String choice;

        do {
            System.out.println("\n===== CAREER RECOMMENDATION SYSTEM =====");

            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.println("\nRate your skills from 1 to 5:");
            int java = getRating("Java skill: ");
            int sql = getRating("SQL skill: ");
            int html = getRating("HTML skill: ");
            int electronics = getRating("Electronics knowledge: ");

            System.out.println("\nRate your interests from 1 to 5:");
            int programming = getRating("Programming interest: ");
            int testing = getRating("Software testing interest: ");
            int embedded = getRating("Embedded systems interest: ");

            Student student = new Student(
                    name, java, sql, html, electronics,
                    programming, testing, embedded
            );

            Career[] careers = {
                new JavaDeveloper(),
                new QAEngineer(),
                new EmbeddedDeveloper()
            };

            // Calculate scores using runtime polymorphism
            for (Career career : careers) {
                career.calculateScore(student);
            }

            // Sort highest score first
            Arrays.sort(careers,
                    Comparator.comparingInt(Career::getScore).reversed());

            System.out.println("\n===== RECOMMENDED CAREERS =====");
            System.out.println("Student Name: " + student.getName());

            for (int i = 0; i < careers.length; i++) {
                System.out.printf("%d. %-22s Score: %d%%%n",
                        i + 1,
                        careers[i].getCareerName(),
                        careers[i].getScore());
            }

            System.out.println("\nBest Career Match: "
                    + careers[0].getCareerName());

            System.out.print("\nAnalyze another student? (yes/no): ");
            choice = sc.nextLine().trim();

        } while (choice.equalsIgnoreCase("yes"));

        System.out.println("\nThank you for using the system!");
        sc.close();
    }
}


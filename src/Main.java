import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Task {
    String desc;
    Boolean isComplete;

    public Task(String desc) {
        this.desc = desc;
        this.isComplete = false;
    }
}

public class Main {
    static ArrayList<Task> tasks = new ArrayList<>();

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter task:");
        tasks.add(new Task(scanner.nextLine()));
        System.out.println("Task added!");
    }

    public static void viewTask() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        int i = 1;
        for (Task task : tasks) {
            String status = task.isComplete ? " (completed)" : " (not completed)";
            System.out.println(i + ". " + task.desc + status);
            i++;
        }
    }

    public static void markComplete() {
        Scanner scanner = new Scanner(System.in);
        viewTask();
        System.out.println("Enter task number to mark complete:");
        int num = scanner.nextInt();

        if (num <= 0 || num > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        tasks.get(num - 1).isComplete = true;
        System.out.println("Task marked as complete!");
    }

    public static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        viewTask();
        System.out.println("Enter task number to remove:");
        int num = scanner.nextInt();

        if (num <= 0 || num > tasks.size()) {
            System.out.println("Invalid task number.");
            return;
        }

        tasks.remove(num - 1);
        System.out.println("Task removed!");
    }

    public static void saveTask() throws IOException {
        try (FileWriter fw = new FileWriter("save.txt", false)) {
            for (Task task : tasks) {
                fw.write(task.desc + "," + task.isComplete + "\n");
            }
            System.out.println("Tasks saved to file.");
        }
    }

    public static void loadTask() throws IOException {
        tasks.clear(); // Clear current tasks to avoid duplicates

        try (BufferedReader br = new BufferedReader(new FileReader("save.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    Task t = new Task(parts[0].trim());
                    t.isComplete = Boolean.parseBoolean(parts[1].trim());
                    tasks.add(t);
                }
            }
            System.out.println("Tasks successfully loaded.");
        } catch (IOException e) {
            System.out.println("Could not load tasks. File may not exist yet.");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nChoose what task to do:");
            System.out.println("[1] Add task");
            System.out.println("[2] View tasks");
            System.out.println("[3] Mark task as complete");
            System.out.println("[4] Remove task");
            System.out.println("[5] Save tasks to file");
            System.out.println("[6] Load tasks from file");
            System.out.println("[7] Exit program");

            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // discard invalid input
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTask();
                case 3 -> markComplete();
                case 4 -> removeTask();
                case 5 -> saveTask();
                case 6 -> loadTask();
                case 7 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 7);
    }
}

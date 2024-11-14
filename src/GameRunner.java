import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GameRunner {
    private JFrame window;
    private JTextArea displayArea;
    private CharacterProfile character;

    public GameRunner() {
        setupWindow();
        chooseCharacter();
    }

    private void setupWindow() {
        window = new JFrame("Arcane Pathways");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(500, 300);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        window.setLayout(new BorderLayout());
        window.add(new JScrollPane(displayArea), BorderLayout.CENTER);
        window.setVisible(true);
        window.toFront();
        window.requestFocus();
    }

    private void chooseCharacter() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for archetype selection
        System.out.print("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM): ");
        displayArea.append("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM):\n");
        String archetype = scanner.nextLine();

        // Prompt for character name
        System.out.print("Enter your character name: ");
        displayArea.append("Enter your character name:\n");
        String name = scanner.nextLine();

        // Create CharacterProfile and display confirmation
        if (archetype != "random".toLowerCase() || archetype != "RANDOM".toUpperCase()) {
            character = new CharacterProfile(name, archetype, true);
            displayArea.append("You chose: " + archetype + "\nWelcome, " + name + " the " + archetype + "!\n");
            System.out.println("You chose: " + archetype);
            System.out.println("Welcome, " + name + " the " + archetype + "!");
        }
        else
        {
            int randomArchetype = (int) (Math.random() * 3);
            //character = new CharacterProfile(name, archetype[randomArchetype], false);
            displayArea.append("You chose: " + archetype + "\nWelcome, " + name + " the " + archetype + "!\n");
            System.out.println("You chose: " + archetype);
            System.out.println("Welcome, " + name + " the " + archetype + "!");
        }
    }

    public static void main(String[] args) {
        new GameRunner();
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class GameRunner {
    private JFrame window;
    private JTextArea displayArea;
    private Character character;
    private GameMap gameMap;

    // Screen Settings
    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    public GameRunner() {
        setupWindow();
        chooseCharacter();
        showGameMap();
        startGameLoop();
    }

    private void setupWindow() {
        window = new JFrame("Arcane Pathways");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(maxScreenCol * tileSize, maxScreenRow * tileSize);
        window.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        window.add(scrollPane, BorderLayout.NORTH);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private void chooseCharacter() {
        Scanner scanner = new Scanner(System.in);

        // Prompt for archetype selection
        System.out.print("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM): ");
        displayArea.append("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM):\n");
        String archetype = scanner.nextLine().trim().toLowerCase();

        // Prompt for character name
        System.out.print("Enter your character name: ");
        displayArea.append("Enter your character name:\n");
        String name = scanner.nextLine().trim();

        // Handle character creation
        switch (archetype) {
            case "wizard":
                character = new Wizard(name);
                break;
            case "knight":
                character = new Knight(name);
                break;
            case "deprived":
                character = new Deprived(name);
                break;
            case "random":
                int randomChoice = (int) (Math.random() * 3);
                switch (randomChoice) {
                    case 0 -> character = new Wizard(name);
                    case 1 -> character = new Knight(name);
                    case 2 -> character = new Deprived(name);
                }
                archetype = character.getArchetype(); // Update archetype to display the random choice
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Deprived.");
                displayArea.append("Invalid choice! Defaulting to Deprived.\n");
                character = new Deprived(name);
                archetype = "Deprived";
        }

        // Display character profile
        displayArea.append("\nYou chose: " + archetype + "\nWelcome, " + name + " the " + archetype + "!\n");
        System.out.println("\nYou chose: " + archetype);
        System.out.println("Welcome, " + name + " the " + archetype + "!");
        displayArea.append("\n" + character.getProfile() + "\n");
    }

    private void showGameMap() {
        gameMap = new GameMap();
        window.add(gameMap, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }

    private void startGameLoop() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Move your character with: W (up), S (down), A (left), D (right)");
        while (true) {
            System.out.print("Enter your move: ");
            String move = scanner.nextLine().trim().toUpperCase();

            if (!gameMap.handleMovement(move, character)) {
                System.out.println("Invalid move! Either blocked or invalid direction.");
            } else {
                // Update player attributes in the display area
                displayArea.setText(character.getProfile());
            }

            window.repaint();
        }
    }

    public static void main(String[] args) {
        new GameRunner();
    }
}

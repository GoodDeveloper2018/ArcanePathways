import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/**
 * The GameRunner class serves as the main entry point for the Arcane Pathways game.
 * It handles the game setup, character selection, map display, and game loop logic.
 */
public class GameRunner {

    private JFrame window;
    private JTextArea displayArea;
    private Character character;
    private GameMap gameMap;

    final int originalTileSize = 16;
    final int scale = 3;
    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    /**
     * Constructor for the GameRunner class. Initializes the game by setting up
     * the window, character selection, and game map, and starts the game loop.
     */
    public GameRunner() {
        setupWindow();
        chooseCharacter();
        showGameMap();
        startGameLoop();
    }

    /**
     * Sets up the game window and display area.
     */
    private void setupWindow() {
        window = new JFrame("Arcane Pathways");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(maxScreenCol * tileSize, maxScreenRow * tileSize + 150); // Increased height for displayArea
        window.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(displayArea);
        window.add(scrollPane, BorderLayout.NORTH);

        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    /**
     * Allows the user to choose a character archetype and name their character.
     */
    private void chooseCharacter() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM): ");
        displayArea.append("Choose your character archetype (Deprived, Knight, Wizard, or RANDOM):\n");
        String archetype = scanner.nextLine().trim().toLowerCase();

        System.out.print("Enter your character name: ");
        displayArea.append("Enter your character name:\n");
        String name = scanner.nextLine().trim();

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
                archetype = character.getArchetype();
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Deprived.");
                displayArea.append("Invalid choice! Defaulting to Deprived.\n");
                character = new Deprived(name);
                archetype = "Deprived";
        }

        String welcomeMessage = "\nYou chose: " + archetype + "\nWelcome, " + name + " the " + archetype + "!\n";
        displayArea.append(welcomeMessage);
        System.out.println(welcomeMessage); // Log to terminal
        displayArea.append("\n" + character.getProfile() + "\n");
    }

    /**
     * Displays the game map on the game window.
     */
    private void showGameMap() {
        gameMap = new GameMap();
        window.add(gameMap, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }

    /**
     * Starts the game loop, allowing the player to move their character on the map.
     */
    private void startGameLoop() {
        Scanner scanner = new Scanner(System.in);

        String instructions = "Move your character with: W (up), S (down), A (left), D (right)";
        System.out.println(instructions);
        displayArea.append(instructions + "\n");
        while (true) {
            System.out.print("Enter your move: ");
            displayArea.append("Enter your move: ");
            String move = scanner.nextLine().trim().toUpperCase();

            if (!gameMap.handleMovement(move, character)) {
                String invalidMoveMessage = "Invalid move! Either blocked or invalid direction.";
                System.out.println(invalidMoveMessage);
                displayArea.append(invalidMoveMessage + "\n");
            } else {
                String updatedProfile = character.getProfile();
                displayArea.setText(updatedProfile + "\n");
                System.out.println(updatedProfile); // Log updated profile to terminal
            }

            window.repaint();
        }
    }

    /**
     * The main method to launch the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new GameRunner();
    }
}

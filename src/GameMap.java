import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * The GameMap class represents the game's map, including player movement,
 * NPC interactions, static objects, and the game's end spots.
 */
public class GameMap extends JPanel {

    private final int rows = 12;
    private final int cols = 16;
    private final int tileSize = 48;
    private int playerX = 5;
    private int playerY = 5;
    private Point[] friendlyNPCs;
    private Point[] enemyNPCs;
    private boolean[][] staticObjects;
    private Point[] endSpots; // Array to hold end spots
    private String[] npcTexts = {
            "Greetings, traveler! Your health increases.",
            "Beware of the dangers ahead! Your strength grows.",
            "You feel magical power surging through you. Your magic improves."
    };

    private Random random = new Random();

    /**
     * Constructor for the GameMap class. Initializes the map with static objects,
     * friendly NPCs, enemy NPCs, and end spots.
     */
    public GameMap() {
        setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        setBackground(Color.BLACK);
        generateStaticObjects();
        generateEndSpots(); // Initialize end spots first
        generateFriendlyNPCs();
        generateEnemyNPCs();
    }

    /**
     * Generates static objects such as trees or bushes on the map.
     */
    private void generateStaticObjects() {
        staticObjects = new boolean[rows][cols];
        for (int i = 0; i < 20; i++) {
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            staticObjects[y][x] = true;
        }
    }

    /**
     * Generates friendly NPCs that can interact with the player.
     */
    private void generateFriendlyNPCs() {
        friendlyNPCs = new Point[5];
        for (int i = 0; i < friendlyNPCs.length; i++) {
            int x, y;
            do {
                x = random.nextInt(cols);
                y = random.nextInt(rows);
            } while (staticObjects[y][x] || (x == playerX && y == playerY) || isEndSpot(x, y));
            friendlyNPCs[i] = new Point(x, y);
        }
    }

    /**
     * Generates enemy NPCs that the player can encounter in duels.
     */
    private void generateEnemyNPCs() {
        enemyNPCs = new Point[5];
        for (int i = 0; i < enemyNPCs.length; i++) {
            int x, y;
            do {
                x = random.nextInt(cols);
                y = random.nextInt(rows);
            } while (staticObjects[y][x] || (x == playerX && y == playerY) || isEndSpot(x, y));
            enemyNPCs[i] = new Point(x, y);
        }
    }

    /**
     * Generates end spots located at the two right corners of the map.
     */
    private void generateEndSpots() {
        endSpots = new Point[2];
        endSpots[0] = new Point(cols - 1, 0);       // Top-right corner
        endSpots[1] = new Point(cols - 1, rows - 1); // Bottom-right corner
    }

    /**
     * Checks if the given coordinates correspond to an end spot.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     * @return true if the position is an end spot, false otherwise
     */
    private boolean isEndSpot(int x, int y) {
        for (Point endSpot : endSpots) {
            if (endSpot.x == x && endSpot.y == y) {
                return true;
            }
        }
        return false;
    }

    /**
     * Handles player movement on the map, checking for collisions, NPC interactions,
     * and reaching end spots.
     *
     * @param move   the player's move input (W, A, S, D)
     * @param player the player's character
     * @return true if the movement is valid, false otherwise
     */
    public boolean handleMovement(String move, Character player) {
        int newX = playerX;
        int newY = playerY;

        switch (move) {
            case "W":
                newY--;
                break;
            case "S":
                newY++;
                break;
            case "A":
                newX--;
                break;
            case "D":
                newX++;
                break;
            default:
                return false; // Invalid move
        }

        // Check boundaries and static objects
        if (newX < 0 || newX >= cols || newY < 0 || newY >= rows || staticObjects[newY][newX]) {
            return false; // Movement blocked
        }

        // Check if new position is an end spot
        if (isEndSpot(newX, newY)) {
            String victoryMessage = "Congratulations! You have defeated Arcane Pathways!";
            JOptionPane.showMessageDialog(this, victoryMessage, "Victory", JOptionPane.INFORMATION_MESSAGE);
            System.out.println(victoryMessage); // Log to terminal
            System.exit(0); // Terminate the game
        }

        // Check for friendly NPC interaction
        for (Point npc : friendlyNPCs) {
            if (npc != null && npc.x == newX && npc.y == newY) {
                interactWithFriendlyNPC(npc, player);
                // Remove NPC after interaction
                removeNPC(friendlyNPCs, npc);
                return true;
            }
        }

        // Check for enemy proximity (same tile or adjacent)
        for (Point npc : enemyNPCs) {
            if (npc != null && isAdjacent(newX, newY, npc.x, npc.y)) {
                String encounterMessage = "An enemy has appeared!";
                JOptionPane.showMessageDialog(this, encounterMessage, "Enemy Encounter", JOptionPane.WARNING_MESSAGE);
                System.out.println(encounterMessage); // Log to terminal

                boolean enemyFirst = random.nextBoolean();
                String turnMessage = enemyFirst ? "The enemy strikes first!" : "You strike first!";
                JOptionPane.showMessageDialog(this, turnMessage, enemyFirst ? "Enemy's Turn" : "Your Turn", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(turnMessage); // Log to terminal

                // Create a new Enemy instance for the duel
                Enemy enemy = new Enemy("Goblin");
                startDuel(player, enemy, enemyFirst);

                // Remove enemy NPC after the duel
                removeNPC(enemyNPCs, npc);

                return false; // Player does not move into enemy's space
            }
        }

        // Move player
        playerX = newX;
        playerY = newY;
        return true;
    }

    /**
     * Checks if two points are adjacent (one grid away) on the map.
     *
     * @param x1 the x-coordinate of the first point
     * @param y1 the y-coordinate of the first point
     * @param x2 the x-coordinate of the second point
     * @param y2 the y-coordinate of the second point
     * @return true if adjacent, false otherwise
     */
    private boolean isAdjacent(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) <= 0 && Math.abs(y1 - y2) <= 0;
    }

    /**
     * Handles interactions with friendly NPCs, providing random attribute boosts to the player.
     *
     * @param npc    the friendly NPC to interact with
     * @param player the player's character
     */
    private void interactWithFriendlyNPC(Point npc, Character player) {
        int choice = random.nextInt(3); // 0: Health, 1: Strength, 2: Magic
        String boostType;
        String boostMessage = "";
        switch (choice) {
            case 0:
                player.health += 10;
                boostType = "health";
                boostMessage = "+10 Health";
                break;
            case 1:
                player.strength += 2;
                boostType = "strength";
                boostMessage = "+2 Strength";
                break;
            case 2:
                player.magic += 2;
                boostType = "magic";
                boostMessage = "+2 Magic";
                break;
            default:
                boostType = "unknown";
        }
        String message = "Friendly NPC interaction! Your " + boostType + " has increased.";
        JOptionPane.showMessageDialog(this, message, "Friendly NPC", JOptionPane.INFORMATION_MESSAGE);
        System.out.println(message + " (" + boostMessage + ")"); // Log to terminal
    }

    /**
     * Initiates a duel between the player and an enemy NPC with interactive choices.
     *
     * @param player      the player's character
     * @param enemy       the enemy character
     * @param enemyFirst  true if the enemy attacks first, false if the player attacks first
     */
    private void startDuel(Character player, Enemy enemy, boolean enemyFirst) {
        boolean playerTurn = !enemyFirst;
        String duelStartMessage = "Duel started between " + player.getName() + " and " + enemy.getName() + "!";
        System.out.println(duelStartMessage); // Log to terminal
        while (player.getHealth() > 0 && enemy.getHealth() > 0) {
            if (playerTurn) {
                // Player's turn: present options based on archetype
                String[] options = getPlayerOptions(player);
                int choice = JOptionPane.showOptionDialog(this,
                        "Choose your action:",
                        "Your Turn",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == JOptionPane.CLOSED_OPTION) {
                    // Player closed the dialog; treat as forfeiting the duel
                    String forfeitMessage = "You have forfeited the duel!";
                    JOptionPane.showMessageDialog(this, forfeitMessage, "Forfeit", JOptionPane.WARNING_MESSAGE);
                    System.out.println(forfeitMessage); // Log to terminal
                    enemy.health = 0;
                    break;
                }

                String action = options[choice];
                String attackResult = executePlayerAction(player, enemy, action);
                JOptionPane.showMessageDialog(this, attackResult, "Player Action", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(attackResult); // Log to terminal

                if (enemy.getHealth() <= 0) {
                    String victoryMessage = "You defeated the enemy!";
                    JOptionPane.showMessageDialog(this, victoryMessage, "Victory", JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(victoryMessage); // Log to terminal
                    return;
                }
            } else {
                // Enemy's turn: enemy attacks
                String attackResult = enemy.attack(player);
                JOptionPane.showMessageDialog(this, attackResult, "Enemy Action", JOptionPane.INFORMATION_MESSAGE);
                System.out.println(attackResult); // Log to terminal

                if (player.getHealth() <= 0) {
                    String defeatMessage = "You were defeated by the enemy.";
                    JOptionPane.showMessageDialog(this, defeatMessage, "Defeat", JOptionPane.ERROR_MESSAGE);
                    System.out.println(defeatMessage); // Log to terminal
                    System.exit(0);
                }
            }
            // Toggle turn
            playerTurn = !playerTurn;
        }
    }

    /**
     * Provides attack options based on the player's archetype.
     *
     * @param player the player's character
     * @return an array of action options
     */
    private String[] getPlayerOptions(Character player) {
        switch (player.getArchetype()) {
            case "Knight":
                return new String[]{"Sword Slash", "Shield Block"};
            case "Wizard":
                return new String[]{"Fireball", "Magic Shield"};
            case "Deprived":
                return new String[]{"Basic Attack", "Magic Strike"};
            default:
                return new String[]{"Attack"};
        }
    }

    /**
     * Executes the player's chosen action and applies its effects.
     *
     * @param player the player's character
     * @param enemy  the enemy character
     * @param action the chosen action
     * @return a description of the action performed
     */
    private String executePlayerAction(Character player, Enemy enemy, String action) {
        switch (player.getArchetype()) {
            case "Knight":
                return executeKnightAction((Knight) player, enemy, action);
            case "Wizard":
                return executeWizardAction((Wizard) player, enemy, action);
            case "Deprived":
                return executeDeprivedAction((Deprived) player, enemy, action);
            default:
                return player.attack(enemy);
        }
    }

    /**
     * Executes a Knight's action.
     *
     * @param knight the Knight character
     * @param enemy  the enemy character
     * @param action the chosen action
     * @return a description of the action performed
     */
    private String executeKnightAction(Knight knight, Enemy enemy, String action) {
        switch (action) {
            case "Sword Slash":
                return knight.attack(enemy);
            case "Shield Block":
                // Shield Block could reduce incoming damage next turn
                // For simplicity, we'll implement it as a temporary strength boost
                knight.strength += 2; // Temporary boost
                String shieldMessage = knight.getName() + " uses Shield Block! Strength temporarily increased by 2.";
                System.out.println(shieldMessage); // Log to terminal
                return shieldMessage;
            default:
                return knight.attack(enemy);
        }
    }

    /**
     * Executes a Wizard's action.
     *
     * @param wizard the Wizard character
     * @param enemy  the enemy character
     * @param action the chosen action
     * @return a description of the action performed
     */
    private String executeWizardAction(Wizard wizard, Enemy enemy, String action) {
        switch (action) {
            case "Fireball":
                return wizard.attack(enemy);
            case "Magic Shield":
                // Magic Shield could reduce incoming damage next turn
                // For simplicity, we'll implement it as a temporary magic boost
                wizard.magic += 3; // Temporary boost
                String shieldMessage = wizard.getName() + " casts Magic Shield! Magic temporarily increased by 3.";
                System.out.println(shieldMessage); // Log to terminal
                return shieldMessage;
            default:
                return wizard.attack(enemy);
        }
    }

    /**
     * Executes a Deprived character's action.
     *
     * @param deprived the Deprived character
     * @param enemy    the enemy character
     * @param action   the chosen action
     * @return a description of the action performed
     */
    private String executeDeprivedAction(Deprived deprived, Enemy enemy, String action) {
        switch (action) {
            case "Basic Attack":
                return deprived.attack(enemy);
            case "Magic Strike":
                // For simplicity, using the same attack method with possibly different messaging
                return deprived.attack(enemy); // You can customize this further if desired
            default:
                return deprived.attack(enemy);
        }
    }

    /**
     * Optionally removes an NPC from the specified array after interaction.
     *
     * @param npcs the array of NPCs
     * @param npc  the NPC to remove
     */
    private void removeNPC(Point[] npcs, Point npc) {
        for (int i = 0; i < npcs.length; i++) {
            if (npcs[i] != null && npcs[i].equals(npc)) {
                npcs[i] = null;
                break;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.drawRect(col * tileSize, row * tileSize, tileSize, tileSize);
            }
        }

        // Draw static objects
        g.setColor(Color.GREEN);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (staticObjects[row][col]) {
                    g.fillRect(col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        }

        // Draw friendly NPCs
        g.setColor(Color.BLUE);
        for (Point npc : friendlyNPCs) {
            if (npc != null) {
                g.fillRect(npc.x * tileSize, npc.y * tileSize, tileSize, tileSize);
            }
        }

        // Draw end spots
        g.setColor(new Color(128, 0, 128)); // Purple color
        for (Point endSpot : endSpots) {
            g.fillOval(endSpot.x * tileSize, endSpot.y * tileSize, tileSize, tileSize);
        }

        // Draw enemy NPCs as hidden (e.g., with a different color or not at all)
        // Uncomment the following lines if you want to visualize enemy positions
        /*
        g.setColor(Color.MAGENTA);
        for (Point npc : enemyNPCs) {
            if (npc != null) {
                g.fillOval(npc.x * tileSize, npc.y * tileSize, tileSize, tileSize);
            }
        }
        */

        // Draw player
        g.setColor(Color.RED);
        g.fillOval(playerX * tileSize, playerY * tileSize, tileSize, tileSize);
    }
}
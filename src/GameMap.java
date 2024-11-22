import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameMap extends JPanel {
    private final int rows = 12;
    private final int cols = 16;
    private final int tileSize = 48;
    private int playerX = 5;
    private int playerY = 5;
    private Point[] friendlyNPCs;
    private Point[] enemyNPCs;
    private boolean[][] staticObjects;
    private String[] npcTexts = {
            "Greetings, traveler! Your health increases.",
            "Beware of the dangers ahead! Your strength grows.",
            "You feel magical power surging through you. Your magic improves."
    };

    public GameMap() {
        setPreferredSize(new Dimension(cols * tileSize, rows * tileSize));
        setBackground(Color.BLACK);

        generateStaticObjects();
        generateFriendlyNPCs();
        generateEnemyNPCs();
    }

    private void generateStaticObjects() {
        staticObjects = new boolean[rows][cols];
        Random random = new Random();

        for (int i = 0; i < 20; i++) { // Place static objects like trees/bushes
            int x = random.nextInt(cols);
            int y = random.nextInt(rows);
            staticObjects[y][x] = true;
        }
    }

    private void generateFriendlyNPCs() {
        friendlyNPCs = new Point[5];
        Random random = new Random();

        for (int i = 0; i < friendlyNPCs.length; i++) {
            int x, y;
            do {
                x = random.nextInt(cols);
                y = random.nextInt(rows);
            } while (staticObjects[y][x]); // Avoid static objects
            friendlyNPCs[i] = new Point(x, y);
        }
    }

    private void generateEnemyNPCs() {
        enemyNPCs = new Point[5];
        Random random = new Random();

        for (int i = 0; i < enemyNPCs.length; i++) {
            int x, y;
            do {
                x = random.nextInt(cols);
                y = random.nextInt(rows);
            } while (staticObjects[y][x]); // Avoid static objects
            enemyNPCs[i] = new Point(x, y);
        }
    }

    public boolean handleMovement(String move, Character player) {
        int newX = playerX;
        int newY = playerY;

        switch (move) {
            case "W" -> newY--;
            case "S" -> newY++;
            case "A" -> newX--;
            case "D" -> newX++;
            default -> {
                return false; // Invalid move
            }
        }

        if (newX < 0 || newX >= cols || newY < 0 || newY >= rows || staticObjects[newY][newX]) {
            return false; // Movement blocked
        }

        for (Point npc : friendlyNPCs) {
            if (npc.x == newX && npc.y == newY) {
                interactWithFriendlyNPC(npc, player);
                return true;
            }
        }

        for (Point npc : enemyNPCs) {
            if (Math.abs(npc.x - newX) <= 1 && Math.abs(npc.y - newY) <= 1) {
                startDuel(player, new Enemy("Hidden Enemy"));
                return false; // Player does not move into enemy
            }
        }

        playerX = newX;
        playerY = newY;
        return true;
    }

    private void interactWithFriendlyNPC(Point npc, Character player) {
        Random random = new Random();
        int choice = random.nextInt(3);
        switch (choice) {
            case 0 -> player.health += 10; // Increase health
            case 1 -> player.strength += 2; // Increase strength
            case 2 -> player.magic += 2; // Increase magic
        }
        JOptionPane.showMessageDialog(this, npcTexts[choice], "Friendly NPC Interaction", JOptionPane.INFORMATION_MESSAGE);
    }

    private void startDuel(Character player, Enemy enemy) {
        while (player.health > 0 && enemy.health > 0) {
            String playerAttackType = Math.random() > 0.5 ? "Physical" : "Magical";
            int playerDamage = playerAttackType.equals("Physical") ? player.strength : player.magic;
            enemy.health -= playerDamage;

            if (enemy.health <= 0) {
                JOptionPane.showMessageDialog(this, "You defeated the enemy!", "Victory", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String enemyAttackType = enemy.getAttackType();
            int enemyDamage = enemyAttackType.equals("Physical") ? enemy.strength : enemy.magic;
            player.health -= enemyDamage;

            if (player.health <= 0) {
                JOptionPane.showMessageDialog(this, "You were defeated by the enemy.", "Defeat", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
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

        // Draw player
        g.setColor(Color.RED);
        g.fillOval(playerX * tileSize, playerY * tileSize, tileSize, tileSize);

        // Draw friendly NPCs
        g.setColor(Color.BLUE);
        for (Point npc : friendlyNPCs) {
            g.fillRect(npc.x * tileSize, npc.y * tileSize, tileSize, tileSize);
        }

        // Enemy NPCs remain hidden
    }
}

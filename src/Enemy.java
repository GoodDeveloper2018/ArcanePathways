import java.util.Random;

/**
 * The Enemy class represents an enemy character in the game.
 */
public class Enemy extends Character {
    private Random random = new Random();

    /**
     * Constructor for the Enemy class.
     *
     * @param name the name of the enemy
     */
    public Enemy(String name) {
        super(name, "Enemy");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 100; // Example stats
        this.strength = 10;
        this.magic = 10;
        this.movementRange = 2; // Enemies can move 2 blocks per turn
    }

    @Override
    public String attack(Character target) {
        // Enemy randomly chooses between physical and magical attack
        boolean useMagic = random.nextBoolean();
        int damage;
        String attackType;
        if (useMagic) {
            damage = this.magic;
            attackType = "magical attack";
        } else {
            damage = this.strength;
            attackType = "physical attack";
        }
        target.health -= damage;
        return this.name + " uses a " + attackType + " on " + target.getName() + " for " + damage + " damage!";
    }
}

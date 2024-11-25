import java.util.Random;

/**
 * The Deprived class represents a character archetype with balanced and flexible attributes.
 */
public class Deprived extends Character {
    private Random random = new Random();

    /**
     * Constructor for the Deprived class.
     *
     * @param name the name of the deprived character
     */
    public Deprived(String name) {
        super(name, "Deprived");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 100;
        this.strength = 10;
        this.magic = 10;
        this.movementRange = 4; // Deprived characters can move 4 blocks per turn
    }

    @Override
    public String attack(Character target) {
        // Deprived can choose between a basic attack or a magic attack
        boolean useMagic = random.nextBoolean();
        int damage;
        String attackType;
        if (useMagic) {
            damage = this.magic;
            attackType = "magical attack";
        } else {
            damage = this.strength;
            attackType = "basic attack";
        }
        target.health -= damage;
        return this.name + " performs a " + attackType + " on " + target.getName() + " for " + damage + " damage!";
    }
}

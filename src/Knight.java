/**
 * The Knight class represents a character archetype with balanced physical attributes.
 */
public class Knight extends Character {
    /**
     * Constructor for the Knight class.
     *
     * @param name the name of the knight
     */
    public Knight(String name) {
        super(name, "Knight");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 120;
        this.strength = 15;
        this.magic = 5;
        this.movementRange = 2; // Knights can move 2 blocks per turn
    }

    @Override
    public String attack(Character target) {
        // Knight performs a sword slash
        int damage = this.strength;
        target.health -= damage;
        return this.name + " slashes " + target.getName() + " with a mighty sword for " + damage + " damage!";
    }
}

/**
 * The Wizard class represents a character archetype with high magical attributes.
 */
public class Wizard extends Character {
    /**
     * Constructor for the Wizard class.
     *
     * @param name the name of the wizard
     */
    public Wizard(String name) {
        super(name, "Wizard");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 80;
        this.strength = 5;
        this.magic = 20;
        this.movementRange = 3; // Wizards can move 3 blocks per turn
    }

    @Override
    public String attack(Character target) {
        // Wizard casts a fireball
        int damage = this.magic;
        target.health -= damage;
        return this.name + " casts a fireball at " + target.getName() + " for " + damage + " magical damage!";
    }
}

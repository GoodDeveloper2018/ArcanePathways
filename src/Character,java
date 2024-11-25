/**
 * The Character class serves as the base class for all character archetypes in the game.
 * It defines common attributes and abstract methods for characters.
 */
public abstract class Character {
    protected String name;
    protected int health;
    protected int strength;
    protected int magic;
    protected String archetype;
    protected int movementRange;

    /**
     * Constructor for the Character class.
     *
     * @param name      the name of the character
     * @param archetype the archetype of the character
     */
    public Character(String name, String archetype) {
        this.name = name;
        this.archetype = archetype;
        initializeAttributes();
    }

    /**
     * Initializes the character's attributes such as health, strength, and magic.
     * Must be implemented by subclasses.
     */
    protected abstract void initializeAttributes();

    /**
     * Performs an attack on the target character.
     *
     * @param target the character being attacked
     * @return the description of the attack performed
     */
    public abstract String attack(Character target);

    /**
     * Gets the name of the character.
     *
     * @return the character's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the archetype of the character.
     *
     * @return the character's archetype
     */
    public String getArchetype() {
        return archetype;
    }

    /**
     * Gets the current health of the character.
     *
     * @return the character's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the current strength of the character.
     *
     * @return the character's strength
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Gets the current magic of the character.
     *
     * @return the character's magic
     */
    public int getMagic() {
        return magic;
    }

    /**
     * Gets the movement range of the character.
     *
     * @return the character's movement range
     */
    public int getMovementRange() {
        return movementRange;
    }

    /**
     * Provides a profile summary of the character.
     *
     * @return a formatted string containing the character's profile
     */
    public String getProfile() {
        return "Name: " + name + "\nArchetype: " + archetype + "\nHealth: " + health +
                "\nStrength: " + strength + "\nMagic: " + magic +
                "\nMovement Range: " + movementRange;
    }
}

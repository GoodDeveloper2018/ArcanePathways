public class Wizard extends Character {
    public Wizard(String name) {
        super(name, "Wizard");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 50;
        this.strength = 3;
        this.magic = 15;
        this.movementRange = 3; // Wizards can move 3 blocks per turn
    }
}

public class Deprived extends Character {
    public Deprived(String name) {
        super(name, "Deprived");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 70;
        this.strength = 6;
        this.magic = 6;
        this.movementRange = 4; // Deprived characters can move 4 blocks per turn
    }
}

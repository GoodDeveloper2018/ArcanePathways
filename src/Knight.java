public class Knight extends Character {
    public Knight(String name) {
        super(name, "Knight");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 90;
        this.strength = 8;
        this.magic = 3;
        this.movementRange = 2; // Knights can move 2 blocks per turn
    }
}

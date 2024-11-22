public class Enemy extends Character {
    public Enemy(String name) {
        super(name, "Enemy");
    }

    @Override
    protected void initializeAttributes() {
        this.health = 60; // Example stats
        this.strength = 7;
        this.magic = 4;
    }

    public String getAttackType() {
        return Math.random() > 0.5 ? "Physical" : "Magical";
    }
}

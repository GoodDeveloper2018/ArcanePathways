import java.util.Scanner;

public class CharacterProfile {
    private String userName;
    private String characterArchetype;
    private int characterHealth;

    public CharacterProfile(String userName, String characterArchetype, boolean chosenArchetype) {
        this.userName = userName;
        this.characterArchetype = characterArchetype;
        setCharacterAttributes(characterArchetype);
    }

    private void setCharacterAttributes(String archetype) {
        switch (archetype.toLowerCase()) {
            case "deprived":
                characterHealth = 80;
                break;
            case "knight":
                characterHealth = 120;
                break;
            case "wizard":
                characterHealth = 60;
                break;
            default:
                characterHealth = 100; // Default for unknown types
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getCharacterArchetype() {
        return characterArchetype;
    }

    public int getCharacterHealth() {
        return characterHealth;
    }
}

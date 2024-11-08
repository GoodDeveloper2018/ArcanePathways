import java.util.Scanner;
import java.util.Dictionary;
import java.lang.*;

public class CharacterProfile {
    private String userName;
    private String characterArchetype;
    private boolean chosenArchetype;
    private int characterHealth;


    public CharacterProfile(String userName, String characterArchetype, boolean chosenArchetype) {
            this.userName = userName;
            this.characterArchetype = characterArchetype;
            this.chosenArchetype = chosenArchetype;

    }
    public String getCharacterProfile (String userName) {
        String CharacterAttributes [] = {};
        Scanner getter = new Scanner(System.in);
        userName = getter.nextLine();
        //CharacterAttributes.append(userName);
     return " ";
    }
    //return CharacterAttributes;
}

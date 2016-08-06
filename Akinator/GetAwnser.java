/**
 * Created by Frostbyte on 8/6/16.
 */
public class GetAwnser {
    public static int get(String input){
        switch (input.toLowerCase()) {
            case "yes":
                return 0;
            case "no":
                return 1;
            case "idk":
                return 2;
            case "p":
                return 3;
            case "pn":
                return 4;
            default:
                break;
        }
        return 6;
    }
}

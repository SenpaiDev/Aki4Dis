/**
 * Created by Frostbyte on 8/6/16.
 */
public class AkinatorBot {

    public static AkinatorBot instance;
    public BotSocket botSocket;

    public AkinatorBot(String token, String command){
        this.botSocket = new BotSocket(token);
    }
    public AkinatorBot(String email, String pass, String command){
        this(new UserLogin(email, pass).getToken(), command);
    }

}

class starter {
    //I heard ya'll like lazy code
    public static void main(String[] args){
        if (args[0].contains("::")){
            AkinatorBot.instance = new AkinatorBot(args[0].split("::")[0], args[0].split("::")[1], args[1]);
        } else
        AkinatorBot.instance = new AkinatorBot(args[0],args[1]);
    }
}

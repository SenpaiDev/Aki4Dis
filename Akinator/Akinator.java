import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Frostbyte on 8/6/16.
 */
public class Akinator {
    public String botdisc;
    public Map<String, UserProfile> userProfileMap = new HashMap<>();
    public Akinator(){
        try {
            botdisc = Unirest.get("https://discordapp.com/api/users/@me").header("Accept-Encoding","gzip").header("user-agent",UserLogin.LOGIN_Agent).header("Authorization", AkinatorBot.instance.botSocket.token).asJson().getBody().getObject().getString("discriminator");
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
    public UserProfile getProfile(String disc){
        if (userProfileMap.containsKey(disc))
            return userProfileMap.get(disc);
        else{
            UserProfile newp = new UserProfile(disc, disc.equalsIgnoreCase(botdisc));
            userProfileMap.put(disc, newp);
            return newp;
        }
    }
}

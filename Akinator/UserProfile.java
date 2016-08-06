import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * Created by Frostbyte on 8/6/16.
 */
public class UserProfile {

    String dis;
    String channel;
    String Ident;
    String Sig;
    int step;
    int awnser;
    boolean us;

    public UserProfile(String disc, boolean d){
        us = d;
        dis = disc;
        System.out.println(disc);
    }

    public void handlemessage(JSONObject message){
        if (!us){
            channel = message.getString("channel_id");

            awnser = GetAwnser.get(message.getString("content"));
            sendmessage(String.valueOf(awnser));
        }
    }
    public void sendmessage(String message){

        try {
            Unirest.post("https://discordapp.com/api/channels/"+channel+"/messages").header("Content-Type","application/json").header("Accept-Encoding", "gzip").header("user-agent",UserLogin.LOGIN_Agent).header("Authorization",AkinatorBot.instance.botSocket.token).body(new JSONObject().put("content",message)).asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}

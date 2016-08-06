import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;

/**
 * Created by Frostbyte on 8/6/16.
 */
public class UserLogin {

    public static String LOGIN_Agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) discord/0.0.19 Chrome/49.0.2623.75 Discord PTB/0.37.6 Safari/537.36";
    String email;
    String pass;
    String token;

    public UserLogin(String email, String pass){
        this.email = email;
        this.pass = pass;
    }


    public String getToken(){
        try {
            return new JSONObject(Unirest.post("https://discordapp.com/api/auth/login")
                    .header("Content-Type","application/json")
                    .header("user-agent", LOGIN_Agent)
                    .body(new JSONObject()
                    .put("email",email)
                    .put("password",pass).toString())
                    .asString().getBody()).getString("token");
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        return null;
    }

}

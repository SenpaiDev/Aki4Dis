import com.neovisionaries.ws.client.*;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * Created by Frostbyte on 8/6/16.
 */
public class BotSocket extends WebSocketAdapter implements WebSocketListener{

    public String token;
    WebSocket webSocket;
    volatile Thread keepalive;

    public static boolean online;

    public BotSocket(String token){

        this.token = token;
        try {
            webSocket = new WebSocketFactory().createSocket(new URI("wss://gateway.discord.gg?encoding=json&v=5")).addListener(this).connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (WebSocketException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
        online = true;
        websocket.sendText(new JSONObject().put("op",2).put("d", new JSONObject().put("token",token).put("properties",new JSONObject().put("$os","Linux").put("$browser","Akinator").put("$device","")
        .put("$referring_domain","http://senpaidev.github.io/Aki4Dis/").put("$referrer","")).put("v",5).put("large_threshold",250).put("compress",true)).toString());
        akinator = new Akinator();

    }
    int reps;
    public void sentKeepalive(){
        if (ping >= 3){
            webSocket.sendClose();
        }
        ping++;

        System.out.println("ping");
        System.out.println(new JSONObject().put("op",1).put("d",reps).toString());
        webSocket.sendText(new JSONObject().put("op",1).put("d",reps).toString());
    }
    Akinator akinator;
    int ping;
    @Override
    public void onTextMessage(WebSocket websocket, String text) throws Exception {
        JSONObject cont = new JSONObject(text);
        if (cont.has("s") && !cont.isNull("s"))
            reps = cont.getInt("s");

        switch (cont.getInt("op")) {
            case 0:

                if (!cont.getString("t").equalsIgnoreCase("message_create"))
                    break;
                else{
                    akinator.getProfile(cont.getJSONObject("d").getJSONObject("author").getString("discriminator")).handlemessage(cont.getJSONObject("d"));
                    break;}
            case 10:
                    Long sleep = cont.getJSONObject("d").getLong("heartbeat_interval");

                keepalive = new Thread(()->{
                        while (online){
                                sentKeepalive();
                            try {
                                Thread.sleep(sleep);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                keepalive.setPriority(Thread.MAX_PRIORITY);
                keepalive.setDaemon(true);
                keepalive.setName("Keep alive Thread");
                keepalive.start();
                break;
            case 11:
                ping = 0;
                break;
            default:
                System.out.println(cont);
                break;
        }
    }
}

package frc.robot.components;

import com.google.gson.Gson;
import com.team1091.shared.control.ImageInfo;
import com.team1091.shared.system.ITargetingSystem;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

public class TargetingSystem implements ITargetingSystem {

    private static final Gson gson = new Gson();
    private URL visionURL;
    private ImageInfo imageInfo = new ImageInfo();

    private final Runnable visionUpdater = () -> {
        while (true) {
            try (
                    InputStreamReader isr = new InputStreamReader(visionURL.openStream());
                    BufferedReader in = new BufferedReader(isr)
            ) {
                imageInfo = gson.fromJson(in.readLine(), ImageInfo.class);
//                System.out.println("Vision: " + imageInfo.center);
                Thread.sleep(100);
            } catch (ConnectException e) {
                System.out.println("No connection");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Thread thread = new Thread(visionUpdater);

    public TargetingSystem() {
        try {
            visionURL = new URL("http://169.254.152.146:4567/center");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    @Override
    public ImageInfo getCenter() {
        return imageInfo;
    }

    @Override
    public void start() {
      //  thread.start();
    }

}

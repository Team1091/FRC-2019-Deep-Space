package frc.robot.components;

import com.google.gson.Gson;
import com.team1091.shared.system.ITargetingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

public class TargetingSystem implements ITargetingSystem {

    private static Gson gson = new Gson();
    private URL visionURL;
    private ImageInfo imageInfo = new ImageInfo();

    private Runnable visionUpdater = () -> {
        while (true) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(visionURL.openStream()))) {
                imageInfo = gson.fromJson(in.readLine(), ImageInfo.class);
                Thread.sleep(100);
            } catch (ConnectException e) {
                System.out.println("No connection");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Thread thread = new Thread(visionUpdater);

    public TargetingSystem() {
        try {
            visionURL = new URL("http://laptop-1091.local:4567/center");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double getCenter() {
        return imageInfo.center;
    }

    @Override
    public void start() {
        thread.start();
    }

    class ImageInfo {
        boolean seen = false;
        double center = 0.0;
        double distance = Double.MAX_VALUE;
    }

}

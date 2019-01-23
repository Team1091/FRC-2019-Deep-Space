package frc.robot.components;

import com.google.gson.Gson;
import com.team1091.shared.system.ITargetingSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;

public class TargetingSystem implements ITargetingSystem {

    private static Gson gson = new Gson();
    private URL visionURL;
    private ImageInfo imageInfo;

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

    public TargetingSystem() throws MalformedURLException {
        visionURL = new URL("http://localhost:4567/center");
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
        boolean seen;
        double center;
        double distance;
    }

}

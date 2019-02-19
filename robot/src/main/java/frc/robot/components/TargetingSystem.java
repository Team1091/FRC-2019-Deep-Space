package frc.robot.components;

import com.team1091.shared.control.ImageInfo;
import com.team1091.shared.system.ITargetingSystem;
import org.jetbrains.annotations.NotNull;

import static spark.Spark.*;

public class TargetingSystem implements ITargetingSystem {

    //  private static final Gson gson = new Gson();
    //    private URL visionURL;
    private ImageInfo imageInfo = new ImageInfo();

//    private final Runnable visionUpdater = () -> {
//        while (true) {
//            try (
//                    InputStreamReader isr = new InputStreamReader(visionURL.openStream());
//                    BufferedReader in = new BufferedReader(isr)
//            ) {
//                imageInfo = gson.fromJson(in.readLine(), ImageInfo.class);
////                System.out.println("Vision: " + imageInfo.center);
//                Thread.sleep(100);
//            } catch (ConnectException e) {
//                System.out.println("No connection");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    };
//
//    private Thread thread = new Thread(visionUpdater);

//    public TargetingSystem() {
//        try {
//            visionURL = new URL("http://169.254.152.146:4567/center");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @NotNull
    @Override
    public ImageInfo getCenter() {
        return imageInfo;
    }

    @Override
    public void start() {
        //  thread.start();
        port(5801);
        exception(Exception.class, (exception, request, response) -> exception.printStackTrace());

        get("/center", (req, res) -> {
            System.out.println("Got Here");
//            if (req == null) {
//                System.out.println("Its null");
//            }
//            if (req.params() == null) {
//                System.out.println("Params null");
//            }
//            for (String o : req.params().values()) {
//                System.out.print(o);
//            }

            imageInfo = new ImageInfo(
                    Boolean.parseBoolean(req.queryParams("seen")),
                    Double.parseDouble(req.queryParams("center")),
                    Double.parseDouble(req.queryParams("distance"))
            );
            return "ok " + imageInfo;
        });

//        exception(Exception::class.java) { exception, request, response -> exception.printStackTrace() }

        // a little webserver.  Go to http://localhost:4567/center
//        get("/center") { _, _ -> gson.toJson(imageInfo) }
    }

}

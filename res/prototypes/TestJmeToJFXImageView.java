package prototypes;

import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3x.jfx.injfx.JmeToJFXApplication;
import com.jme3x.jfx.injfx.JmeToJFXIntegrator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * The test to show how to integrate jME to ImageView.
 *
 * @author JavaSaBr4
 */
public class TestJmeToJFXImageView extends Application {

    public static void main(@NotNull final String[] args) {
        launch(args);
    }

    @Override
    public void start(@NotNull final Stage stage) {

        final ImageView imageView = new ImageView();
        imageView.setFocusTraversable(true);
        imageView.setOnMouseClicked(event -> imageView.requestFocus());

        final Button button = new Button("BUTTON");
        final StackPane stackPane = new StackPane(imageView, button);
        final Scene scene = new Scene(stackPane, 600, 600);

        imageView.fitWidthProperty().bind(stackPane.widthProperty());
        imageView.fitHeightProperty().bind(stackPane.heightProperty());

        stage.setTitle("Test");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> System.exit(0));

        // creates jME application
        final JmeToJFXApplication application = makeJmeApplication();

        // integrate jME application with ImageView
        JmeToJFXIntegrator.startAndBindMainViewPort(application, imageView, Thread::new);
    }

    private static @NotNull JmeToJFXApplication makeJmeApplication() {

        final AppSettings settings = JmeToJFXIntegrator.prepareSettings(new AppSettings(true), 60);
        final JmeToJFXApplication application = new hey();
        application.setSettings(settings);
        application.setShowSettings(false);
        return application;
    }
}

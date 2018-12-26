package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.pacman.scenes.EditorScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main
        extends Application {
    public static Scene    scene;
    public static Renderer renderer;

    public static void main(String[] args) {
        launch(args);
    }

    // TODO: Map WH: 21

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        scene = new Scene(root, 800, 800);
        primaryStage.setTitle("PacMan");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        renderer = Renderer.instantiate(primaryStage);

        Renderer.instance.addGameScene(new EditorScene());
        Renderer.instance.setGameScene(EditorScene.class);
    }
}

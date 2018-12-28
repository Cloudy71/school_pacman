package cz.cloudy.pacman;

import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.core.Sound;
import cz.cloudy.fxengine.types.Int2;
import cz.cloudy.fxengine.types.Tileset;
import cz.cloudy.pacman.scenes.EditorScene;
import cz.cloudy.pacman.scenes.GameScene;
import cz.cloudy.pacman.scenes.MenuScene;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main
        extends Application {
    public static Scene    scene;
    public static Renderer renderer;

    public static Sound   SND_MENU;
    public static Sound   SND_CHOMP;
    public static int     coinTime = 0;
    public static int     ghost    = 0;
    public static Tileset tileset;

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

        SND_MENU = new Sound(Main.class.getResource("./sounds/pacman_beginning.wav"));
        SND_CHOMP = new Sound(Main.class.getResource("./sounds/pacman_chomp.wav"));

        tileset = new Tileset(Main.class.getResourceAsStream("./tiles.png"), new Int2(16, 16));

        renderer = Renderer.instantiate(primaryStage);

        Renderer.instance.addGameScene(MenuScene.class);
        Renderer.instance.addGameScene(GameScene.class);
        Renderer.instance.addGameScene(EditorScene.class);

        Renderer.instance.setGameScene(MenuScene.class);
//        Renderer.instance.setGameScene(EditorScene.class);
    }
}

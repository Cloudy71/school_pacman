/*
  User: Cloudy
  Date: 17-Nov-18
  Time: 20:55
*/

package cz.cloudy.pacman.io;

import cz.cloudy.pacman.core.Renderer;
import javafx.scene.input.KeyCode;

public class KeyboardController {
    public enum KeyboardKeyType {
        PRESSED, RELEASED, PRESS
    }

    public static void addKey(KeyboardKeyType keyboardKeyType, KeyCode keyCode) {
        if (!CodeAccessProtector.isAccessedFromClass(Renderer.class)) {
            return;
        }
        Keyboard.addKey(keyboardKeyType, keyCode);
    }
}
package cz.cloudy.fxengine.core;

import cz.cloudy.fxengine.annotations.Deserialization;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class GameObjectFactory {
    private static GameObjectCollector gameObjectCollector;
    private static GameObjectHelper    gameObjectHelper;

    protected GameObjectFactory(GameObjectCollector gameObjectCollector, GameObjectHelper gameObjectHelper) {
        GameObjectFactory.gameObjectCollector = gameObjectCollector;
        GameObjectFactory.gameObjectHelper = gameObjectHelper;
    }

    public static <T extends GameObject> T createObject(Class<? extends GameObject> clazz) {
        try {
            gameObjectCollector.requestNewId();
            T t = (T) clazz.getDeclaredConstructor()
                           .newInstance();
            addObject(t, true);
            return t;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void registerExistingObject(GameObject gameObject) {
        for (GameObject object : gameObjectCollector.getGameObjects()) {
            if (object.equals(gameObject)) {
                return;
            }
        }
        addObject(gameObject, false);
    }

    /**
     * Adds new GameObject into {@link GameObjectCollector}
     *
     * @param gameObject GameObject which should be added
     */
    private static void addObject(GameObject gameObject, boolean initialCreate) {
        gameObjectCollector.addGameObject(gameObject);
        if (initialCreate) {
            gameObject.create();
        } else {
            Method[] methods = gameObject.getClass()
                                         .getDeclaredMethods();
            for (Method method : methods) {
                if (method.getDeclaredAnnotation(Deserialization.class) != null) {
                    try {
                        method.setAccessible(true);
                        method.invoke(gameObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Disposes specified GameObject.
     *
     * @param gameObject GameObject which should be disposed
     */
    public static void disposeObject(GameObject gameObject) {
        gameObjectCollector.removeGameObject(gameObject);
    }
}

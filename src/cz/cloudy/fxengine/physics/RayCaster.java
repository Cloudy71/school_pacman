/*
  User: Cloudy
  Date: 29-Nov-18
  Time: 20:59
*/

package cz.cloudy.fxengine.physics;

import cz.cloudy.fxengine.core.GameObject;
import cz.cloudy.fxengine.core.Renderer;
import cz.cloudy.fxengine.types.Vector2;

import java.util.LinkedList;
import java.util.List;

public class RayCaster {
    public enum RayCastCheck {
        ALL, SOLIDS, TRIGGERS
    }

    private static final int pixelPrecision = 1;

    private static RayCastCheck check = RayCastCheck.ALL;

    public static void checkFor(RayCastCheck rayCastCheck) {
        check = rayCastCheck;
    }

    private static boolean isCheckEnabled(RayCastCheck rayCastCheck) {
        return check == rayCastCheck || check == RayCastCheck.ALL;
    }

    /**
     * Checks whether line intersects any GameObject's physics data on scene.
     * Checking is made by brute-force per pixel checking. Can be performance demanding.
     *
     * @param result Result class - GameObject, GameObject[]
     * @param start  Starting vector
     * @param end    Ending vector
     * @param <T>    The result type
     * @return single object nearest to start or an array of objects which intersect.
     */
    // TODO: Make it also for HitPoint and HitPoint[].
    public static <T> T castLine(Class<T> result, Vector2 start, Vector2 end) {
        if (result == GameObject.class) { // Single object, the nearest to start vector.
            return (T) (calculateLine(start, end, true)[0]);
        } else if (result == GameObject[].class) { // All objects in line.
            return (T) calculateLine(start, end, false);
        }
        // TODO: Make line intersection.
        return null;
    }

    private static GameObject[] calculateLine(Vector2 start, Vector2 end, boolean killOnFirst) {
        float xDiff = end.x - start.x;
        float yDiff = end.y - start.y;

        int checks = (int) (xDiff > yDiff ? xDiff / pixelPrecision : yDiff / pixelPrecision);
        float xAdd = xDiff / checks;
        float yAdd = yDiff / checks;

        List<GameObject> objects = new LinkedList<>();

        for (int i = 0; i < checks; i++) {
            float x = start.x + checks * xAdd;
            float y = start.y + checks * yAdd;

            for (GameObject gameObject : Renderer.instance.getGameObjectCollector()
                                                          .getGameObjects()) {
                if (gameObject.getPhysicsData() == null) continue;
                if ((gameObject.getPhysicsData()
                               .isHitOnly(new Vector2(x, y)) && isCheckEnabled(RayCastCheck.SOLIDS)) ||
                    (gameObject.getPhysicsData()
                               .isTriggerOnly(new Vector2(x, y)) && isCheckEnabled(RayCastCheck.TRIGGERS))) {
                    if (!objects.contains(gameObject)) {
                        if (killOnFirst) return new GameObject[] {gameObject};
                        objects.add(gameObject);
                    }
                }
            }
        }

        if (objects.size() == 0 && killOnFirst) {
            return new GameObject[] {null};
        }

        return objects.toArray(new GameObject[0]);
    }

    public static <T> T castPoint(Class<T> result, Vector2 position) {
        return (T) calculatePoint(position, result);
    }

    private static Object calculatePoint(Vector2 position, Class<?> result) {
        List<GameObject> objects = new LinkedList<>();
        boolean killOnFirst = result == GameObject.class || result == HitPoint.class;
        List<HitPoint> hitPoints = new LinkedList<>();

        for (GameObject gameObject : Renderer.instance.getGameObjectCollector()
                                                      .getGameObjects()) {
            if (gameObject.getPhysicsData() == null) continue;
            HitPoint hitPoint;
            if (((hitPoint = gameObject.getPhysicsData()
                                       .isHit(position)) != null && isCheckEnabled(RayCastCheck.SOLIDS)) ||
                ((hitPoint = gameObject.getPhysicsData()
                                       .isTrigger(position)) != null && isCheckEnabled(RayCastCheck.TRIGGERS))) {
                if (!objects.contains(gameObject)) {
                    objects.add(gameObject);
                    hitPoints.add(hitPoint);
                    if (killOnFirst) break;
                }
            }
        }

        if (objects.size() == 0 && killOnFirst) {
            return null;
        }

        if (result == GameObject.class) {
            return objects.get(0);
        } else if (result == GameObject[].class) {
            return objects.toArray(new GameObject[0]);
        } else if (result == HitPoint.class) {
            return hitPoints.get(0);
        } else if (result == HitPoint[].class) {
            return hitPoints.toArray(new HitPoint[0]);
        }
        return null;
    }
}
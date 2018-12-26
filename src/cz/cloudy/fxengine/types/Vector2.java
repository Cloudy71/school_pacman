package cz.cloudy.fxengine.types;

import cz.cloudy.fxengine.interfaces.IMeasurable;

import java.io.Serializable;

public class Vector2
        implements IMeasurable, Serializable {
    public static final Vector2 ZERO() {
        return new Vector2(0f, 0f);
    }

    public static final Vector2 SCALE() {
        return new Vector2(1f, 1f);
    }

    public float x;
    public float y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double x, double y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector2(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector2 scale(Vector2 scaler) {
        this.x *= scaler.x;
        this.y *= scaler.y;
        return this;
    }

    public Vector2 move(Vector2 offset) {
        this.x += offset.x;
        this.y += offset.y;
        return this;
    }

    public Vector2 add(Vector2 offset) {
        return move(offset);
    }

    public Vector2 subtract(Vector2 offset) {
        return move(new Vector2(-offset.x, -offset.y));
    }

    public float distance(Vector2 point) {
        return (float) Math.sqrt(1); // TODO: Finish
    }

    public Vector2 copy() {
        return new Vector2(this.x, this.y);
    }

    public float length() {
        return (float) Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0));
    }

    @Override
    public String toString() {
        return "Vector2[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector2) {
            if (((Vector2) obj).x == x && ((Vector2) obj).y == y) return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (int) ((x + y) * (y + x));
    }
}

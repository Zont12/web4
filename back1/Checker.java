package org.example.back1;

public class Checker {
    public Checker() {}

    public static boolean check(float x, float y, float r) {

        if (x >= 0 && y >= 0 && x <= r / 2 && y <= r) {
            return true;
        }

        if (x >= 0 && y <= 0 && x <= r / 2 && y >= -r / 2 && y >= -x) {
            return true;
        }

        if (x <= 0 && y <= 0 && (x * x + y * y) <= (r / 2) * (r / 2)) {
            return true;
        }
        return false;
    }

    public static boolean validate(float x, float y, float r) {
        return (x >= -2 && x <= 2) && (y >= -3 && y <= 5) && (r >= 0.5 && r <= 2);
    }
}

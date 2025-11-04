package utils;

public final class TextFormat {

    private TextFormat() {
    }

    public static String bold(String val) {
        return "\033[1m" + val + "\033[0m";
    }
}

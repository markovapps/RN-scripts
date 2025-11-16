package utils;

public final class TextFormat {

    private TextFormat() {
    }

    private static final String RESET = "\033[0m";

    // Styles
    public static String bold(String val)       { return "\033[1m" + val + RESET; }
    public static String faint(String val)      { return "\033[2m" + val + RESET; }
    public static String italic(String val)     { return "\033[3m" + val + RESET; }
    public static String underline(String val)  { return "\033[4m" + val + RESET; }
    public static String blink(String val)      { return "\033[5m" + val + RESET; }
    public static String inverse(String val)    { return "\033[7m" + val + RESET; }

    // Colors (foreground)
    public static String red(String val)        { return "\033[31m" + val + RESET; }
    public static String green(String val)      { return "\033[32m" + val + RESET; }
    public static String yellow(String val)     { return "\033[33m" + val + RESET; }
    public static String blue(String val)       { return "\033[34m" + val + RESET; }
    public static String magenta(String val)    { return "\033[35m" + val + RESET; }
    public static String cyan(String val)       { return "\033[36m" + val + RESET; }
    public static String white(String val)      { return "\033[37m" + val + RESET; }

    // Background colors
    public static String bgRed(String val)      { return "\033[41m" + val + RESET; }
    public static String bgGreen(String val)    { return "\033[42m" + val + RESET; }
    public static String bgYellow(String val)   { return "\033[43m" + val + RESET; }
    public static String bgBlue(String val)     { return "\033[44m" + val + RESET; }
    public static String bgMagenta(String val)  { return "\033[45m" + val + RESET; }
    public static String bgCyan(String val)     { return "\033[46m" + val + RESET; }
    public static String bgWhite(String val)    { return "\033[47m" + val + RESET; }
}

package org.projectsw.View;

public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN = "\033[0;36m";
    public static final String WHITE = "\033[0;37m";
    public static final String ORANGE = "\033[38;5;206m";
    public static final String MAGENTA = "\033[38;5;198m";
    public static final String GREY = "\033[38;5;248m";

    // Bold Colors
    public static final String BLUE_BOLD = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";

    // Tiles
    public static final String TROPHIES = ConsoleColors.CYAN + "TROPHIES" + ConsoleColors.RESET;
    public static final String GAMES = ConsoleColors.ORANGE + "GAMES" + ConsoleColors.RESET;
    public static final String PLANTS = ConsoleColors.MAGENTA + "PLANTS" + ConsoleColors.RESET;
    public static final String CATS = ConsoleColors.GREEN + "CATS" + ConsoleColors.RESET;
    public static final String BOOKS = ConsoleColors.YELLOW + "BOOKS" + ConsoleColors.RESET;
    public static final String FRAMES = ConsoleColors.BLUE + "FRAMES" + ConsoleColors.RESET;
    public static final String EMPTY = ConsoleColors.GREY + "EMPTY" + ConsoleColors.RESET;

}

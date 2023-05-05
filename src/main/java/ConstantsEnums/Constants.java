package ConstantsEnums;

public class Constants {
    public static final int FieldPixelsNum = 21;
    public static final double WindowWidth = 1200;
    public static final double WindowHeight = 600;
    public static final String[] GhostSource = new String[] {"ghost_red.png", "ghost_yellow.png", "ghost_pink.png", "ghost_blue.png"};
    public static final String[] PacmanSource = new String[] {
            "pacman_up_closed.png", "pacman_down_closed.png", "pacman_left_closed.png", "pacman_right_closed.png",
            "pacman_up_opened.png", "pacman_down_opened.png", "pacman_left_opened.png", "pacman_right_opened.png"};
    public static final String KeySource = "key.png";
    public static final String HomeSource = "home.png";
    public static final String TargetSource = "target.png";
    public static final double PacmanMoveDelay = 0.15;
    public static final double GhostMoveDelay = 0.8;

    //zkusit log vzdy 2x rychleji nez je rychlost pacmana
    public static int LoggerSpeed = (int) (1000 * PacmanMoveDelay / 2);
}

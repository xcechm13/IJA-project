package Constants;

public class Constants {
    /**
     * Field pixel size
     */
    public static final int FieldPixelsNum = 21;
    public static final double WindowWidth = 1200;
    public static final double WindowHeight = 600;
    /**
     * different colors of ghost
     */
    public static final String[] GhostSource = new String[] {"ghost_red.png", "ghost_yellow.png", "ghost_pink.png", "ghost_blue.png"};
    /**
     * Pacman different movement appearance
     */
    public static final String[] PacmanSource = new String[] {
            "pacman_up_closed.png", "pacman_down_closed.png", "pacman_left_closed.png", "pacman_right_closed.png",
            "pacman_up_opened.png", "pacman_down_opened.png", "pacman_left_opened.png", "pacman_right_opened.png"};
    public static final String KeySource = "key.png";
    public static final String HomeSource = "home.png";
    public static final String TargetSource = "target.png";
    /**
     * speed of pacman ( X seconds to move across one field)
     */
    public static final double PacmanMoveDelay = 0.15;
    /**
     * speed of ghost ( X seconds to move across one field)
     */
    public static final double GhostMoveDelay = 0.8;

    /**
     * Log the game (2x faster than pacman moving)
     */
    public static int LoggerSpeed = (int) (1000 * PacmanMoveDelay / 2);
}

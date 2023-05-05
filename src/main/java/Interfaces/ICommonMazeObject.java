package Interfaces;

/**
 * interface for GhostObject,HomeObject,KeyObject,PacmanObject,TargetObject
 */
public interface ICommonMazeObject {

    /**
     * check if object is Pacman
     * @return true if pacman
     */
    public boolean IsPacman();

    /**
     * check if object is Ghost
     * @return true if ghost
     */
    public boolean IsGhost();

    /**
     * check if object is Key
     * @return true if key
     */
    public boolean IsKey();

    /**
     * check if object is Target
     * @return true if target
     */
    public boolean IsTarget();

    /**
     * check if object is Home
     * @return true if home
     */
    public boolean IsHome();

    /**
     * returns field on which is object right now
     * @return actual field/error
     */
    public ICommonField GetField();

    /**
     * Change dimentions of object
     * @param height height of maze
     * @param width width of maze
     */
    public void SetFieldSize(double height, double width);
}

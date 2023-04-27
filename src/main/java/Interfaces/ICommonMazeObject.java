package Interfaces;

public interface ICommonMazeObject {

    public boolean IsPacman();

    public boolean IsGhost();

    public boolean IsKey();

    public boolean IsGoal();

    public ICommonField GetField();
}

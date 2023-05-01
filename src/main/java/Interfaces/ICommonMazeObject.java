package Interfaces;

public interface ICommonMazeObject {

    public boolean IsPacman();

    public boolean IsGhost();

    public boolean IsKey();

    public boolean IsTarget();

    public ICommonField GetField();

    public void SetFieldSize(double height, double width);
}

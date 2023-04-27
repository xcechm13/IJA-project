package Interfaces;

import ConstantsEnums.Direction;

public interface ICommonField {

    public boolean IsEmpty();

    public ICommonMazeObject[] GetMazeObjects();

    public boolean CanMove();

    public void Put (ICommonMazeObject object);

    public void Remove (ICommonMazeObject object);

    public boolean IsPathField();

    public ICommonField NextField(Direction direction);
}

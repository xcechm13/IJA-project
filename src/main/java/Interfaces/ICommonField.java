package Interfaces;

import ConstantsEnums.Direction;

import java.util.List;

public interface ICommonField {

    public boolean IsEmpty();

    public List<ICommonMazeObject> GetMazeObjects();

    public void Put (ICommonMazeObject object);

    public void Remove (ICommonMazeObject object);

    public boolean IsPathField();

    public boolean Contains(ICommonMazeObject object);

    public ICommonField NextField(Direction direction);
}

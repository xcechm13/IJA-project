package Interfaces;

import Enums.Direction;

import java.util.List;

/**
 * Interface for PathField and WallField
 */
public interface ICommonField {

    /**
     * check if there are any MazeObjects in this Field
     * @return true if empty
     */
    public boolean IsEmpty();

    /**
     * Get List of MazeObjects of this field
     * @return List of Objects
     */
    public List<ICommonMazeObject> GetMazeObjects();

    /**
     * Put MazeObject inside this Field
     * @param object that you want to put inside the field
     */
    public void Put (ICommonMazeObject object);

    /**
     * Remove MazeObject from this field
     * @param object that you want to remove from this field
     */
    public void Remove (ICommonMazeObject object);

    /**
     * Check if Field is PathField or WallField
     * @return true if field is PathField
     */
    public boolean IsPathField();

    /**
     * Check if MazeObject is inside this field
     * @param object searched MazeObject
     * @return true if the object is inside this field
     */
    public boolean Contains(ICommonMazeObject object);

    /**
     * Get Field that is next to this field in direction given
     * @param direction field on which side
     * @return Field in chosen direction
     */
    public ICommonField NextField(Direction direction);
}

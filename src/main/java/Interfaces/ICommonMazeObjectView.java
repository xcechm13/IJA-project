package Interfaces;

import javafx.scene.image.ImageView;

/**
 * Interface for GhostView, HomeView, KeyView, PacmanView, TargetView
 */
public interface ICommonMazeObjectView {

    /**
     * change object dimensions
     * @param height maze height
     * @param width maze width
     */
    public void SetFieldSize(double height, double width);

    /**
     * helps with creating view of object
     * @return ImageView of object
     */
    public ImageView CreateView();

    /**
     * remove view of object from map
     */
    public void RemoveView();
}

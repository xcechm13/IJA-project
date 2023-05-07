package Game;

import Game.Objects.GhostObject;
import Game.Objects.PacmanObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that takes care of threads
 */
public class ProcessRunner extends Thread{

    /**
     * all ghosts that are moving as threads
     */
    private List<Thread> ghostThreads;
    /**
     * all ghosts that are moving
     */
    private List<GhostObject> ghostObjects;
    /**
     * thread of pacman
     */
    private Thread pacmanThread;
    private PacmanObject pacmanObject;
    /**
     * thread that saves logs
     */
    private Thread recorderThread;
    private LoggerRecorder loggerRecorder;

    /**
     * Constructor
     */
    public ProcessRunner()
    {
        ghostThreads = new ArrayList<>();
        ghostObjects = new ArrayList<>();
    }

    public void addRecorder(Thread t, LoggerRecorder r)
    {
        recorderThread = t;
        loggerRecorder = r;
    }

    public void addGhost(Thread t, GhostObject o)
    {
        ghostThreads.add(t);
        ghostObjects.add(o);
    }

    public void setPacman(Thread t, PacmanObject o)
    {
        pacmanThread = t;
        pacmanObject = o;
    }

    /**
     * set all threads to null
     */
    public void Clear()
    {
        pacmanThread = null;
        recorderThread = null;
        ghostThreads = new ArrayList<>();
    }

    /**
     * starts all ghost movement one by one (delay in case of falling)
     */
    public void runGhosts(){
        for (Thread t : ghostThreads) {
            try {
                sleep(75);
                t.start();
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * starts pacman movement
     */
    public void runPacman()
    {
        try {
            sleep(75);
            pacmanThread.start();
        } catch (InterruptedException e) { }
    }

    /**
     * starts saving logs
     */
    public void runRecorder()
    {
        try {
            sleep(75);
            recorderThread.start();
        } catch (InterruptedException e) { }
    }

    /**
     * stop logging
     */
    public void stopRecorder()
    {
        loggerRecorder.stop();
    }

    /**
     * stop pacman and ghosts
     */
    public void stopAllObjects()
    {
        for(GhostObject t : ghostObjects)
        {
            t.stop();
        }
        pacmanObject.stop();
    }

    /**
     * start ghosts and pacman
     */
    public void runAllObjects()
    {
        for(GhostObject t : ghostObjects)
        {
            t.run();
        }
        pacmanObject.run();
    }

}

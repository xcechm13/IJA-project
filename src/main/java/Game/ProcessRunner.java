package Game;

import Game.Objects.GhostObject;
import Game.Objects.PacmanObject;

import java.util.ArrayList;
import java.util.List;

public class ProcessRunner extends Thread{

    private List<Thread> ghostThreads;
    private List<GhostObject> ghostObjects;
    private Thread pacmanThread;
    private PacmanObject pacmanObject;
    private Thread recorderThread;
    private LoggerRecorder loggerRecorder;

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

    public void Clear()
    {
        pacmanThread = null;
        recorderThread = null;
        ghostThreads = new ArrayList<>();
    }

    public void runGhosts(){
        for (Thread t : ghostThreads) {
            try {
                sleep(75);
                t.start();
            } catch (InterruptedException e) {
            }
        }
    }

    public void runPacman()
    {
        try {
            sleep(75);
            pacmanThread.start();
        } catch (InterruptedException e) { }
    }

    public void runRecorder()
    {
        try {
            sleep(75);
            recorderThread.start();
        } catch (InterruptedException e) { }
    }

    public void stopRecorder()
    {
        loggerRecorder.stop();
    }

    public void stopAllObjects()
    {
        for(GhostObject t : ghostObjects)
        {
            t.stop();
        }
        pacmanObject.stop();
    }

    public void runAllObjects()
    {
        for(GhostObject t : ghostObjects)
        {
            t.run();
        }
        pacmanObject.run();
    }

}

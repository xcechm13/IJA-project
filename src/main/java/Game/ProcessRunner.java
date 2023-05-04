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

    public ProcessRunner()
    {
        ghostThreads = new ArrayList<>();
        ghostObjects = new ArrayList<>();
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

    public void runPacman()
    {
        try {
            sleep(75);
            pacmanThread.start();
        } catch (InterruptedException e) { }
    }

    public void Clear()
    {
        pacmanThread = null;
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

        /*
        while(true)
        {
            try {
                sleep(1000);
            } catch (InterruptedException e) {}

            for(int i=0; i<objects.size(); i++)
            {
                objects.get(i).stop();
            }

            try {
                sleep(1000);
            } catch (InterruptedException e) {}

            for(int i=0; i<objects.size(); i++)
            {
                objects.get(i).run();
            }

        }*/

    }

    public void stopAll()
    {
        for(GhostObject t : ghostObjects)
        {
            t.stop();
        }
        pacmanObject.stop();
    }

    public void runAll()
    {
        for(GhostObject t : ghostObjects)
        {
            t.run();
        }
        pacmanObject.run();
    }

}

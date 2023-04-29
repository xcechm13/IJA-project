package Game;

import Game.Objects.GhostObject;
import java.util.ArrayList;
import java.util.List;

public class ProcessRunner extends Thread{

    private List<Thread> threads;

    public ProcessRunner()
    {
        threads = new ArrayList<>();
    }

    public void addProcess(GhostObject o)
    {
        threads.add(o);
    }

    public void run(){
        for(int i=0; i<threads.size(); i++)
        {
            try {
                sleep(50);
                threads.get(i).start();
            } catch (InterruptedException e) {}
        }
    }

}

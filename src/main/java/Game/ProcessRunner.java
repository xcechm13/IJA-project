package Game;

import Game.Objects.GhostObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProcessRunner extends Thread{

    private List<Thread> threads;
    //private List<GhostObject> objects;

    public ProcessRunner()
    {
        threads = new ArrayList<>();
        //objects = new ArrayList<>();
    }

    //public void addProcess(Thread t, GhostObject o)
    //{
    //    threads.add(t);
    //    objects.add(o);
    //}

    public void addProcess(Thread t)
    {
        threads.add(t);
    }

    public void run(){
        for (Thread t : threads) {
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

}

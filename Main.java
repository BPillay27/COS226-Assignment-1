import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    enum Contention {
        LOW(thinkMs(150), work(2000)),
        MEDIUM(thinkMs(50), work(8000)),
        HIGH(thinkMs(0), work(25000));

        final int thinkMs;
        final int csWorkIters;

        Contention(int thinkMs, int csWorkIters) {
            this.thinkMs = thinkMs;
            this.csWorkIters = csWorkIters;
        }

        static int thinkMs(int v) {
            return v;
        }

        static int work(int v) {
            return v;
        }

        int getThink() {
            return thinkMs;
        }

        int getWork() {
            return csWorkIters;
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            Contention level = Contention.LOW;
            int players = 2;
            Lock _lock=new TTASLock();

            if (args.length > 3) {
                throw new IllegalArgumentException("Too many arguments. At most 2!");
            }

            if (args.length == 1) {
                level = Contention.valueOf(args[0].toUpperCase());
            } else if (args.length == 2) {
                level = Contention.valueOf(args[0].toUpperCase());
                players = Math.abs(Integer.parseInt(args[1]));
            } else if (args.length==3){
                level = Contention.valueOf(args[0].toUpperCase());
                players = Math.abs(Integer.parseInt(args[1]));

                if(args[2].equals("CLH")){
                    _lock=new CLHLock();
                }
            }

            System.out.println("RUNNING with " + args[0].toUpperCase() + " contention and " + args[1] + " players");
            TreasureChest chest=new TreasureChest("Chest", 2000,_lock);
            List<Player> playerList=new ArrayList<>();
            for(int i;i<players;i++){
                Player temp=new Player(String.valueOf(i),chest,level.thinkMs, level.csWorkIters);
                playerList.add(new Player(String.valueOf(i),));
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Arguement exception: " + e);
        }

    }
}

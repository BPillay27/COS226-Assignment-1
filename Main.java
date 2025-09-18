import java.util.*;

public class Main {

    enum Contention {
        LOW(thinkMs(100), work(1000)),
        MEDIUM(thinkMs(30), work(5000)),
        HIGH(thinkMs(5), work(10000));

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
    }

    public static void main(String[] args) throws Exception {
        try {
            if (args.length != 4) {
                throw new IllegalArgumentException(
                        "Too few/many Arguments. Must be [Contention] [lock] [numPlayers] [numChests]");
            }

            String contention = validateContentionType(args[0]);
            if (contention.equals("")) {
                throw new IllegalArgumentException("Contention type must be LOW, MEDIUM or HIGH.");
            }

            String lockType = validateLock(args[1]);
            if (lockType.equals("")) {
                throw new IllegalArgumentException("Lock must be TTAS or CLH");
            }

            int numPlayers = isInteger(args[2]);
            if (numPlayers == -1) {
                throw new IllegalArgumentException("numPlayers must be a number >=1");
            }

            int numChests = isInteger(args[3]);
            if (numChests == -1) {
                throw new IllegalArgumentException("numChests must be a number >=1");
            }

            playGame(contention, lockType, numPlayers, numChests);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Argument: " + e.getMessage());
            return;
        }
    }

    public static String validateContentionType(String input) {
        if (input.equals("LOW") || input.equals("MEDIUM") || input.equals("HIGH")) {
            return input;
        } else {
            return "";
        }
    }

    public static String validateLock(String input) {
        if (input.equals("CLH") || input.equals("TTAS")) {
            return input;
        } else {
            return "";
        }
    }

    public static int isInteger(String input) {
        if (input == null)
            return -1;

        try {
            int temp = Integer.parseInt(input);

            if (temp < 1) {
                return -1;
            }
            return temp;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static void playGame(String contention, String lockType, int numPlayers, int numChests) {
        Contention level = Contention.valueOf(contention);

        List<TreasureChest> chests = new ArrayList<>();
        for (int i = 0; i < numChests; i++) {
            Lock tempLock = (lockType.equals("CLH")) ? new CLHLock() : new TTASLock();
            chests.add(new TreasureChest("Chest " + i, 2000, tempLock));
        }

        List<Player> players=new ArrayList<>();
        for(int i=0;i<numPlayers;i++){
            players.add(new Player("Player "+i,chests,level.thinkMs,level.csWorkIters));
        }

        try{
            long startTime=System.currentTimeMillis();
            for(Player p:players) p.start();
            for(Player p:players) p.join();
            long duration=System.currentTimeMillis()-startTime;
            System.out.println("Duration for " + contention +" Contention, "+ lockType+ " lock, " +numPlayers+" players and "+ numChests+ " chests = "+duration+ " (ms)");
        } catch(InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("Game interrupted and ended early!");
            return;
        }
        
    }
}

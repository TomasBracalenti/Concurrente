package tp.listas;

import tp.listas.scenarios.Scenario;

public class Main {
    public static void main(String[] args) {

        //OptimisticListScenarios();
        //NonBlockingListScenarios();
        //FineGrainedListScenarios();
        //NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        //System.out.println("ALGOOOO");
        Test();
        //nonBlockingList.add(1);
        //System.out.println("Luego de agregar 1:");
        //nonBlockingList.print();
        //nonBlockingList.print();
        //nonBlockingList.add(2);
        //System.out.println("Luego de agregar 2:");
        //nonBlockingList.print();
        //nonBlockingList.remove(1);
        //System.out.println("Elimino 1:");
        //nonBlockingList.print();
        //nonBlockingList.remove(1);
        //System.out.println("Elimino 1:");
        //nonBlockingList.print();
        //FineGrainedList<Integer> list = new FineGrainedList<>();
        //list.remove(Integer.valueOf(0));
        //list.remove(Integer.valueOf(0));
    }
   
    public static void Test(){
        System.out.println("Trash test...");
        NoAddingScenarios(); OnlyAddingScenarios(); BalancedScenarios();
        float times[][][] = new float[3][3][3];
        for(int i=0; i<3; i++){
            System.out.println("Testing...");
            times[i][0] = NoAddingScenarios();
            times[i][1] = OnlyAddingScenarios();
            times[i][2] = BalancedScenarios();
        }

        float firstTime = times[0][0][0], secondTime = times[1][0][0], thirdTime = times[2][0][0];
        System.out.println("Scenario: fine grained list, no adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][1][0]; secondTime = times[1][1][0]; thirdTime = times[2][1][0];
        System.out.println("Scenario: fine grained list, only adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][2][0]; secondTime = times[1][2][0]; thirdTime = times[2][2][0];
        System.out.println("Scenario: fine grained list, balanced, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][0][1]; secondTime = times[1][0][1]; thirdTime = times[2][0][1];
        System.out.println("Scenario: optimistic list, no adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][1][1]; secondTime = times[1][1][1]; thirdTime = times[2][1][1];
        System.out.println("Scenario: optimistic list, only adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][2][1]; secondTime = times[1][2][1]; thirdTime = times[2][2][1];
        System.out.println("Scenario: optimistic list, balanced, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][0][2]; secondTime = times[1][0][2]; thirdTime = times[2][0][2];
        System.out.println("Scenario: non blocking list, no adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][1][2]; secondTime = times[1][1][2]; thirdTime = times[2][1][2];
        System.out.println("Scenario: non blocking list, only adding, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        firstTime = times[0][2][2]; secondTime = times[1][2][2]; thirdTime = times[2][2][2];
        System.out.println("Scenario: non blocking list, balanced, 15000 threads, 5 ops/thread");
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

    }

    public static float[] NoAddingScenarios(){
        float times[] = new float[3];
        FineGrainedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario noAdding = new Scenario(15000, 5, 0, fineGrainedList);
        times[0] = noAdding.run();
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        noAdding = new Scenario(15000, 5, 0, optimisticList);
        times[1] = noAdding.run();
        NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        noAdding = new Scenario(15000, 5, 0, nonBlockingList);
        times[2] = noAdding.run();
        return times;
    }
//
    public static float[] OnlyAddingScenarios(){
        float times[] = new float[3];
        FineGrainedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario onlyAdding = new Scenario(15000, 5, 100, fineGrainedList);
        times[0] = onlyAdding.run();
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        onlyAdding = new Scenario(15000, 5, 100, optimisticList);
        times[1] = onlyAdding.run();
        NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        onlyAdding = new Scenario(15000, 5, 100, nonBlockingList);
        times[2] = onlyAdding.run();
        return times;
    }

    public static float[] BalancedScenarios(){
        float times[] = new float[3];
        FineGrainedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario balanced = new Scenario(15000, 5, 50, fineGrainedList);
        times[0] = balanced.run();
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        balanced = new Scenario(15000, 5, 50, optimisticList);
        times[1] = balanced.run();
        NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        balanced = new Scenario(15000, 5, 50, nonBlockingList);
        times[2] = balanced.run();
        return times;
    }

    public static void FineGrainedListScenarios(){

        System.out.println("Scenario: fine grained list, no adding, 15000 threads, 5 ops/thread");
        FineGrainedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario noAdding = new Scenario(15000, 5, 0, fineGrainedList);
        float firstTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        fineGrainedList = new FineGrainedList<>();
        noAdding = new Scenario(15000, 5, 0, fineGrainedList);
        float secondTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        fineGrainedList = new FineGrainedList<>();
        noAdding = new Scenario(15000, 5, 0, fineGrainedList);
        float thirdTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: fine grained list, only adding, 15000 threads, 5 ops/thread");
        fineGrainedList = new FineGrainedList<>();
        Scenario onlyAdding = new Scenario(15000, 5, 100, fineGrainedList);
        firstTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        fineGrainedList = new FineGrainedList<>();
        onlyAdding = new Scenario(15000, 5, 100, fineGrainedList);
        secondTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        fineGrainedList = new FineGrainedList<>();
        onlyAdding = new Scenario(15000, 5, 100, fineGrainedList);
        thirdTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: fine grained list, balanced, 15000 threads, 5 ops/thread");
        fineGrainedList = new FineGrainedList<>();
        Scenario balanced = new Scenario(15000, 5, 50, fineGrainedList);
        firstTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        fineGrainedList = new FineGrainedList<>();
        balanced = new Scenario(15000, 5, 50, fineGrainedList);
        secondTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        fineGrainedList = new FineGrainedList<>();
        balanced = new Scenario(15000, 5, 50, fineGrainedList);
        thirdTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
    }

    public static void OptimisticListScenarios(){

        System.out.println("Scenario: optimistic list, no adding, 15000 threads, 5 ops/thread");
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        Scenario noAdding = new Scenario(15000, 5, 0, optimisticList);
        float firstTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        noAdding = new Scenario(15000, 5, 0, optimisticList);
        float secondTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        noAdding = new Scenario(15000, 5, 0, optimisticList);
        float thirdTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: optimistic list, only adding, 15000 threads, 5 ops/thread");
        optimisticList = new OptimisticList<>();
        Scenario onlyAdding = new Scenario(15000, 5, 100, optimisticList);
        firstTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        onlyAdding = new Scenario(15000, 5, 100, optimisticList);
        secondTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        onlyAdding = new Scenario(15000, 5, 100, optimisticList);
        thirdTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: optimistic list, balanced, 15000 threads, 5 ops/thread");
        optimisticList = new OptimisticList<>();
        Scenario balanced = new Scenario(15000, 5, 50, optimisticList);
        firstTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        balanced = new Scenario(15000, 5, 50, optimisticList);
        secondTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        balanced = new Scenario(15000, 5, 50, optimisticList);
        thirdTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
    }

    public static void NonBlockingListScenarios(){

        System.out.println("Scenario: non blocking list, no adding, 15000 threads, 5 ops/thread");
        NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        Scenario noAdding = new Scenario(15000, 5, 0, nonBlockingList);
        float firstTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        nonBlockingList = new NonBlockingList<>();
        noAdding = new Scenario(15000, 5, 0, nonBlockingList);
        float secondTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        nonBlockingList = new NonBlockingList<>();
        noAdding = new Scenario(15000, 5, 0, nonBlockingList);
        float thirdTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: non blocking list, only adding, 15000 threads, 5 ops/thread");
        nonBlockingList = new NonBlockingList<>();
        Scenario onlyAdding = new Scenario(15000, 5, 100, nonBlockingList);
        firstTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        nonBlockingList = new NonBlockingList<>();
        onlyAdding = new Scenario(15000, 5, 100, nonBlockingList);
        secondTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        nonBlockingList = new NonBlockingList<>();
        onlyAdding = new Scenario(15000, 5, 100, nonBlockingList);
        thirdTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Scenario: non blocking list, balanced, 15000 threads, 5 ops/thread");
        nonBlockingList = new NonBlockingList<>();
        Scenario balanced = new Scenario(15000, 5, 50, nonBlockingList);
        firstTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        nonBlockingList = new NonBlockingList<>();
        balanced = new Scenario(15000, 5, 50, nonBlockingList);
        secondTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        nonBlockingList = new NonBlockingList<>();
        balanced = new Scenario(15000, 5, 50, nonBlockingList);
        thirdTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
    }
}
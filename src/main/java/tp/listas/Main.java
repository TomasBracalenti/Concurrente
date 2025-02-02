package tp.listas;

import tp.listas.scenarios.Scenario;

public class Main {
    public static void main(String[] args) {

        System.out.println("First scenario: optimistic list, no adding, 5000 threads, 5 ops/thread");
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        Scenario noAdding = new Scenario(5000, 5, 0, optimisticList);
        float firstTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        noAdding = new Scenario(5000, 5, 0, optimisticList);
        float secondTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        noAdding = new Scenario(5000, 5, 0, optimisticList);
        float thirdTime = noAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");
        
        
        System.out.println("Second scenario: optimistic list, only adding, 5000 threads, 5 ops/thread");
        optimisticList = new OptimisticList<>();
        Scenario onlyAdding = new Scenario(5000, 5, 100, optimisticList);
        firstTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        onlyAdding = new Scenario(5000, 5, 100, optimisticList);
        secondTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        onlyAdding = new Scenario(5000, 5, 100, optimisticList);
        thirdTime = onlyAdding.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

        
        System.out.println("Third scenario: optimistic list, balanced, 5000 threads, 5 ops/thread");
        optimisticList = new OptimisticList<>();
        Scenario balanced = new Scenario(5000, 5, 50, optimisticList);
        firstTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","First test:", firstTime, " seconds");

        optimisticList = new OptimisticList<>();
        balanced = new Scenario(5000, 5, 50, optimisticList);
        secondTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Second test:", secondTime, " seconds");
        
        optimisticList = new OptimisticList<>();
        balanced = new Scenario(5000, 5, 50, optimisticList);
        thirdTime = balanced.run();
        System.out.printf("%15s%10.4f%s\n","Third test:", thirdTime, " seconds");

        System.out.printf("%15s%10.4f%s\n","Avg:", (firstTime+secondTime+thirdTime)/3, " seconds");

    }
}
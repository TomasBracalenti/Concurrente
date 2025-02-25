package tp.listas;

import java.io.FileWriter;
import java.io.IOException;

import tp.listas.scenarios.Scenario;

public class Main {

    public enum ListType {

        FINE_GRAINED(0),
        OPTIMISTIC(1),
        NON_BLOCKING(2);

        private final int value;

        ListType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static ListType fromValue(int value) {
            for (ListType type : ListType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("Invalid value: " + value);
        }
    }

    public static void main(String[] args) {
        Test();
    }

    public static void Test() {

        // Una dimension para variar la lista, en el arreglo dado por mkln (0, 1, 2, 3, 4, 5)
        // 0 -> FineGrainedList
        // 1 -> OptimisticList
        // 2 -> NonBlockingList
        // 3 -> ModifiedFineGrainedList
        // 4 -> ModifiedOptimisticList
        // 5 -> ModifiedNonBlockingList
        // Una dimension para variar la probabilidad de agregar (m)
        // Una dimension para variar la cantidad de threads (k)
        // Una dimension para variar la cantidad de operaciones por thread (l)
        // Una dimension para variar la cantidad de tests (n)

        int probabilities[] = {0, 25, 50, 75, 100};
        int threads[] = {100, 1000, 10000};
        int operationsPerThread[] = {10, 100, 1000};
        int TESTS = 30;
        float times[][][][][] = new float[threads.length][operationsPerThread.length][probabilities.length][TESTS][6];

        for (int n = 0; n < TESTS; n++) {
            for (int k = 0; k < threads.length; k++) {
                for (int l = 0; l < operationsPerThread.length; l++) {
                    for (int m = 0; m < probabilities.length; m++) {
                        var currentThread = threads[k];
                        var currentOperationsPerThread = operationsPerThread[l];
                        var currentProbabilityAdd = probabilities[m];
                        times[k][l][m][n] = RunScenarios(currentThread, currentOperationsPerThread, currentProbabilityAdd);
                        System.out.println("Finish test with threads: " + threads[k] + ", operations per thread: " + operationsPerThread[l] + ", probability add: " + probabilities[m]);
                    }
                }
            }
        }

        try {
            FileWriter modifiedCsvWriter = new FileWriter("original_lists_test_results.csv");
            FileWriter originalCsvWriter = new FileWriter("modified_lists_test_results.csv");
            originalCsvWriter.append("Test,Threads,Operations per Thread,Probability Add,Avg\n");
            modifiedCsvWriter.append("Test,Threads,Operations per Thread,Probability Add,Avg\n");

            for (int i = 0; i < 6; i++) {
                for (int k = 0; k < threads.length; k++) {
                    for (int l = 0; l < operationsPerThread.length; l++) {
                        for (int m = 0; m < probabilities.length; m++) {
                            var currentThread = threads[k];
                            var currentOperationsPerThread = operationsPerThread[l];
                            var currentProbabilityAdd = probabilities[m];
                            var csvWriter = originalCsvWriter;
                            if (i>2) csvWriter = modifiedCsvWriter;
                            float avgTime = 0;
                            for (int n = 0; n < TESTS; n++) {
                                avgTime += times[k][l][m][n][i];
                            }
                            avgTime /= TESTS;
                            csvWriter.append(ListType.fromValue(i).name())
                                    .append(",")
                                    .append(String.valueOf(currentThread))
                                    .append(",")
                                    .append(String.valueOf(currentOperationsPerThread))
                                    .append(",")
                                    .append(String.valueOf(currentProbabilityAdd))
                                    .append(",")
                                    .append(String.valueOf(avgTime))
                                    .append("\n");
                            csvWriter.flush();
                        }
                    }
                }
            }
            originalCsvWriter.close();
            modifiedCsvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static float[] RunScenarios(int thCount, int opsPerThread, int probAdd) {
        float times[] = new float[6];
        SynchronizedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario scenario = new Scenario(thCount, opsPerThread, probAdd, fineGrainedList);
        times[0] = scenario.run();
        SynchronizedList<Integer> optimisticList = new OptimisticList<>();
        scenario = new Scenario(thCount, opsPerThread, probAdd, optimisticList);
        times[1] = scenario.run();
        SynchronizedList<Integer> nonBlockingList = new NonBlockingList<>();
        scenario = new Scenario(thCount, opsPerThread, probAdd, nonBlockingList);
        times[2] = scenario.run();
        SynchronizedList<Integer> modifiedFineGrainedList = new ModifiedFineGrainedList<>();
        scenario = new Scenario(thCount, opsPerThread, probAdd, modifiedFineGrainedList);
        times[3] = scenario.run();
        SynchronizedList<Integer> modifiedOptimisticList = new ModifiedOptimisticList<>();
        scenario = new Scenario(thCount, opsPerThread, probAdd, modifiedOptimisticList);
        times[4] = scenario.run();
        SynchronizedList<Integer> modifiedNonBlockingList = new ModifiedNonBlockingList<>();
        scenario = new Scenario(thCount, opsPerThread, probAdd, modifiedNonBlockingList);
        times[5] = scenario.run();
        return times;
    }
}
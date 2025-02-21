package tp.listas;

import java.io.FileWriter;
import java.io.IOException;

import tp.listas.scenarios.Scenario;

import java.util.List;

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

        // Una dimension para variar la lista, en el arreglo dado por mkln (0, 1, 2)
        // 0 -> FineGrainedList
        // 1 -> OptimisticList
        // 2 -> NonBlockingList
        // Una dimension para variar la probabilidad de agregar (m)
        // Una dimension para variar la cantidad de threads (k)
        // Una dimension para variar la cantidad de operaciones por thread (l)
        // Una dimension para variar la cantidad de tests (n)

        int probabilities[] = {0, 25, 50, 75, 100};
        int threads[] = {7500, 15000, 30000};
        int operationsPerThread[] = {5, 10, 20};
        int TESTS = 1;
        float times[][][][][] = new float[threads.length][operationsPerThread.length][probabilities.length][TESTS][3];

        try (FileWriter csvWriter = new FileWriter("test_results.csv")) {
            csvWriter.append("Test,Threads,Operations per Thread,Probability Add,Avg\n");

            for (int k = 0; k < threads.length; k++) {
                for (int l = 0; l < operationsPerThread.length; l++) {
                    for (int m = 0; m < probabilities.length; m++) {
                        var currentThread = threads[k];
                        var currentOperationsPerThread = operationsPerThread[l];
                        var currentProbabilityAdd = probabilities[m];
                        for (int n = 0; n < TESTS; n++) {
                            times[k][l][m][n] = RunScenarios(currentThread, currentOperationsPerThread, currentProbabilityAdd);
                        }
                        for (int i = 0; i < 3; i++) {
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
                            System.out.println("Finish test " + ListType.fromValue(i) + " with threads: " + threads[k] + ", operations per thread: " + operationsPerThread[l] + ", probability add: " + probabilities[m]);
                            csvWriter.flush();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Printing results...");
        for (int k = 0; k < threads.length; k++) {
            for (int l = 0; l < operationsPerThread.length; l++) {
                for (int m = 0; m < probabilities.length; m++) {
                    for (int i = 0; i < 3; i++) {
                        System.out.println("Results for test " + ListType.fromValue(i) + " with threads: " + threads[k] + ", operations per thread: " + operationsPerThread[l] + ", probability add: " + probabilities[m]);
                        printTestResults(times[k][l][m][0][i], times[k][l][m][1][i], times[k][l][m][2][i]);
                    }
                }
            }
        }


    }

    public static void printTestResults(float firstTime, float secondTime, float thirdTime) {
        System.out.printf("%15s%10.4f%s\n", "First test:", firstTime, " seconds");
        System.out.printf("%15s%10.4f%s\n", "Second test:", secondTime, " seconds");
        System.out.printf("%15s%10.4f%s\n", "Third test:", thirdTime, " seconds");
        System.out.printf("%15s%10.4f%s\n", "Avg:", (firstTime + secondTime + thirdTime) / 3, " seconds");
    }

    public static float[] RunScenarios(int thCount, int opsPerThread, int probAdd) {
        float times[] = new float[3];
        FineGrainedList<Integer> fineGrainedList = new FineGrainedList<>();
        Scenario noAdding = new Scenario(thCount, opsPerThread, probAdd, fineGrainedList);
        times[0] = noAdding.run();
        OptimisticList<Integer> optimisticList = new OptimisticList<>();
        noAdding = new Scenario(thCount, opsPerThread, probAdd, optimisticList);
        times[1] = noAdding.run();
        NonBlockingList<Integer> nonBlockingList = new NonBlockingList<>();
        noAdding = new Scenario(thCount, opsPerThread, probAdd, nonBlockingList);
        times[2] = noAdding.run();
        return times;
    }


}
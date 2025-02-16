package tp.listas;

import tp.listas.scenarios.Scenario;

import java.util.List;

public class Main {
    static int operationsPerThread = 15;
    static int threads = 15000;
    static int probabilityAdd = 50;

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

    public static void Test() {

        // Una dimension para variar la lista, en el arreglo dado por mkln (0, 1, 2)
        // 0 -> FineGrainedList
        // 1 -> OptimisticList
        // 2 -> NonBlockingList
        // Una dimension para variar la probabilidad de agregar (m)
        // Una dimension para variar la cantidad de threads (k)
        // Una dimension para variar la cantidad de operaciones por thread (l)
        // Una dimension para variar la cantidad de tests (n)

        int probabilities[] = {0, 50, 100};
        int threads[] = {7500, 15000, 30000};
        int operationsPerThread[] = {5, 10, 20};
        float times[][][][][] = new float[3][3][3][3][3];
        System.out.println("Trash test...");
        RunScenarios(threads[0], operationsPerThread[0], probabilities[0]);
        System.out.println("Starting tests....");
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                for (int m = 0; m < 3; m++) {
                    var currentThread = threads[k];
                    var currentOperationsPerThread = operationsPerThread[l];
                    var currentProbabilityAdd = probabilities[m];
                    for (int n = 0; n < 3; n++) {
                        times[k][l][m][n] = RunScenarios(currentThread, currentOperationsPerThread, currentProbabilityAdd);
                        ;
                    }
                    System.out.println("Finished tests with threads: " + currentThread + ", operations per thread: " + currentOperationsPerThread + ", probability add: " + currentProbabilityAdd);
                }
            }
        }
        System.out.println("Printing results...");
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                for (int m = 0; m < 3; m++) {
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
import java.util.*;

public class Knapsack {

    // 1) Greedy Fractional Knapsack Algorithm
    public static int fractionalKnapsack(int W, List<int[]> items) {
        List<Pair<Double, int[]>> ratios = new ArrayList<>();
        int steps = 0;
        
        // Calculate ratios and store items
        for (int i = 0; i < items.size(); i++) {
            int weight = items.get(i)[0];
            int value = items.get(i)[1];
            double ratio = (double) value / weight;
            ratios.add(new Pair<>(ratio, items.get(i)));
            steps++;
        }

        // Sort items by ratio (value/weight)
        ratios.sort((a, b) -> Double.compare(b.getKey(), a.getKey()));

        double totalValue = 0.0;
        
        // Greedy approach to take items
        for (Pair<Double, int[]> pair : ratios) {
            int weight = pair.getValue()[0];
            int value = pair.getValue()[1];
            steps++;
            if (W >= weight) {
                W -= weight;
                totalValue += value;
            } else {
                totalValue += pair.getKey() * W;
                break;
            }
        }

        System.out.println("Total value=" + totalValue);
        return steps;
    }

    // 2) 0/1 Knapsack using Dynamic Programming
    public static int knapsack01(int W, List<int[]> items) {
        int n = items.size();
        int[][] dp = new int[n + 1][W + 1];
        int steps = 0;

        // DP approach to 0/1 Knapsack
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= W; w++) {
                steps++;
                if (items.get(i - 1)[0] <= w) {
                    dp[i][w] = Math.max(dp[i - 1][w], dp[i - 1][w - items.get(i - 1)[0]] + items.get(i - 1)[1]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        System.out.println("Total value=" + dp[n][W]);
        return steps;
    }

    public static void analyzeKnapsack() {
        List<Integer> capacities = Arrays.asList(10, 20, 30, 40);
        List<int[]> items = Arrays.asList(new int[]{2, 10}, new int[]{3, 20}, new int[]{5, 30}, new int[]{7, 40}, new int[]{1, 5});

        for (int W : capacities) {
            System.out.println("=================== Capacity: " + W + " ===================");
            System.out.println("Greedy Knapsack(Fractional): " + fractionalKnapsack(W, items) + " steps");
            System.out.println("0/1 Knapsack(DP): " + knapsack01(W, items) + " steps");
            System.out.println("========================================================\n");
        }
    }

    public static void main(String[] args) {
        analyzeKnapsack();
    }
    
    // Pair class to store ratio and item data
    public static class Pair<K, V> {
        private K key;
        private V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}

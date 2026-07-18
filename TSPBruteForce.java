import java.util.*;

public class TSPBruteForce {
    static String[] cities = {"Cairo", "Giza", "Alexandria", "Port Said", "Damietta"};
    static Map<String, Integer> distanceMap = new HashMap<>();

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        initDistances();

        List<List<String>> allRoutes = new ArrayList<>();
        permute(new ArrayList<>(Arrays.asList(cities)), 0, allRoutes);

        int minDistance = Integer.MAX_VALUE;
        List<List<String>> shortestRoutes = new ArrayList<>();

        // Calculate all distances and track the shortest ones
        for (List<String> route : allRoutes) {
            int totalDistance = calculateRouteDistance(route);
            if (totalDistance < minDistance) {
                minDistance = totalDistance;
                shortestRoutes.clear();
                shortestRoutes.add(route);
            } else if (totalDistance == minDistance) {
                shortestRoutes.add(route);
            }
        }

        // Print all routes, highlight the shortest ones
        int count = 1;
        for (List<String> route : allRoutes) {
            int totalDistance = calculateRouteDistance(route);
            if (shortestRoutes.contains(route)) {
                System.out.printf("%3d: %-70s ✅ SHORTEST! Total Distance: %4d km%n", count++, route, totalDistance);
            } else {
                System.out.printf("%3d: %-70s Total Distance: %4d km%n", count++, route, totalDistance);
            }
        }

        // Optionally print summary
        System.out.printf("%nFound %d shortest routes with a distance of %d km.%n", shortestRoutes.size(), minDistance);
        long endTime = System.nanoTime();
        System.out.println("Execution time was " + (endTime - startTime) + " ns.");
    }

    static void permute(List<String> arr, int k, List<List<String>> result) {
        if (k == arr.size()) {
            result.add(new ArrayList<>(arr));
        } else {
            for (int i = k; i < arr.size(); i++) {
                Collections.swap(arr, i, k);
                permute(arr, k + 1, result);
                Collections.swap(arr, k, i);
            }
        }
    }

    static int calculateRouteDistance(List<String> route) {
        int total = 0;
        for (int i = 0; i < route.size() - 1; i++) {
            total += getDistance(route.get(i), route.get(i + 1));
        }
        total += getDistance(route.get(route.size() - 1), route.get(0)); // return to start
        return total;
    }

    static int getDistance(String from, String to) {
        String key1 = from + "-" + to;
        String key2 = to + "-" + from;
        return distanceMap.getOrDefault(key1, distanceMap.getOrDefault(key2, Integer.MAX_VALUE));
    }

    static void initDistances() {
        distanceMap.put("Cairo-Giza", 8);
        distanceMap.put("Cairo-Alexandria", 220);
        distanceMap.put("Cairo-Port Said", 195);
        distanceMap.put("Cairo-Damietta", 246);
        distanceMap.put("Giza-Alexandria", 220);
        distanceMap.put("Giza-Port Said", 204);
        distanceMap.put("Giza-Damietta", 267);
        distanceMap.put("Alexandria-Port Said", 253);
        distanceMap.put("Alexandria-Damietta", 202);
        distanceMap.put("Port Said-Damietta", 52);
    }
}

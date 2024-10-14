import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    
    private static final HashMap<String, List<String>> locations = new HashMap<>(
            Map.of(
                    "Noida", new ArrayList<>(
                            List.of("Delhi", "Gurugram", "Faridabad")),
                    "Delhi", new ArrayList<>(
                            List.of("Noida", "Sonipat", "Gurugram", "Faridabad")),
                    "Sonipat", new ArrayList<>(
                            List.of("Delhi", "Gurugram", "Panipat")),
                    "Gurugram", new ArrayList<>(
                            List.of("Noida", "Delhi", "Sonipat", "Panipat", "Faridabad")),
                    "Panipat", new ArrayList<>(
                            List.of("Sonipat", "Gurugram")),
                    "Faridabad", new ArrayList<>(
                            List.of("Noida", "Delhi", "Gurugram"))
            )
    );
    
    private static final HashMap<String, List<Integer>> holidays = new HashMap<>(
            Map.of(
                    "Noida", new ArrayList<>(
                            List.of(1, 3, 4, 2, 1, 5, 6, 5, 1, 7, 2, 1)),
                    "Delhi", new ArrayList<>(
                            List.of(5, 1, 8, 2, 1, 7, 2, 6, 2, 8, 2, 6)),
                    "Sonipat", new ArrayList<>(
                            List.of(2, 5, 8, 2, 1, 6, 9, 3, 2, 1, 5, 7)),
                    "Gurugram", new ArrayList<>(
                            List.of(6, 4, 1, 6, 3, 4, 7, 3, 2, 5, 7, 8)),
                    "Panipat", new ArrayList<>(
                            List.of(2, 4, 3, 1, 7, 2, 6, 8, 2, 1, 4, 6)),
                    "Faridabad", new ArrayList<>(
                            List.of(2, 4, 6, 7, 2, 1, 3, 6, 3, 1, 6, 8))
            )
    );
    
    public static void main(String[] args) {
        
        maximizeHolidays();
    
    }
    
    
    
    private static void maximizeHolidays() {
        
        HashMap<String, List<List<Integer>>> maximumQuarterlyHolidaysPlans =
                maximumQuarterlyHolidaysPlans();
        
    }
    
    
    
    private static HashMap<String, List<List<Integer>>> maximumQuarterlyHolidaysPlans() {
        
        HashMap<String, HashMap<String, List<Integer>>> holidaysPerQuarter =
                holidaysPerQuarter();
        
        HashMap<String, HashMap<String, List<Integer>>> bestHolidayPlansPerQuarter =
                bestHolidayPlansPerQuarter(holidaysPerQuarter);
        
        // ---------------------------------- TO BE CONTINUED ----------------------------------
        
        return null;
        
    }
    
    
    
    private static HashMap<String, HashMap<String, List<Integer>>> bestHolidayPlansPerQuarter(
            HashMap<String, HashMap<String, List<Integer>>> holidaysPerQuarter
    ) {
        HashMap<String, HashMap<String, List<Integer>>> result = new HashMap<>();
        
        HashMap<String, HashMap<String, Integer>> maximumStartingHolidayPerQuarter =
                maximumStartingHolidayPerQuarter(holidaysPerQuarter);
        
        List<String> possibleLocations = possibleLocations(
                maximumStartingHolidayPerQuarter
                        .entrySet().iterator().next().getValue()
                        .entrySet().iterator().next().getKey()
        );
        
        System.out.println(possibleLocations);
        
        // -------------------------------------- I AM HERE --------------------------------------
        
        return result;
    }
    
    
    private static List<String> possibleLocations(String key) {
        return locations.get(key).stream().toList();
    }
    
    
    private static HashMap<String, HashMap<String, Integer>> maximumStartingHolidayPerQuarter(
            HashMap<String, HashMap<String, List<Integer>>> holidaysPerQuarter
    ) {
        HashMap<String, HashMap<String, Integer>> result = new HashMap<>();
        
        holidaysPerQuarter.entrySet().iterator().forEachRemaining(
                (entry) -> result.put(entry.getKey(), maximumOfAll(entry.getValue()))
        );
        
        return result;
    }
    
    
    
    private static HashMap<String, Integer> maximumOfAll(
            HashMap<String, List<Integer>> entryValue
    ) {
        HashMap<String, Integer> result = new HashMap<>();
        int[] max = {0};
        
        // creating a new HasMap
        entryValue.forEach(
                (key, value) -> result.put(key, value.getFirst())
        );
        
        // Finding the maximum
        result.entrySet().iterator().forEachRemaining(
                (entry) -> max[0] = Math.max(max[0], entry.getValue())
        );
        
        // Removing less than maximum
        entryValue.entrySet().iterator().forEachRemaining(
                (entry) -> {
                    if (entry.getValue().getFirst() < max[0]) {
                        result.remove(entry.getKey());
                    }
                }
        );
        
        return result;
    }
    
    
    private static HashMap<String, HashMap<String, List<Integer>>> holidaysPerQuarter() {
        HashMap<String, HashMap<String, List<Integer>>> result = new HashMap<>();
        
        HashMap<String, List<Integer>> firstQuarterHolidays = new HashMap<>();
        HashMap<String, List<Integer>> secondQuarterHolidays = new HashMap<>();
        HashMap<String, List<Integer>> thirdQuarterHolidays = new HashMap<>();
        HashMap<String, List<Integer>> fourthQuarterHolidays = new HashMap<>();
        
        for (Map.Entry<String, List<Integer>> entry: holidays.entrySet()) {
            firstQuarterHolidays.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(0, 3)));
            secondQuarterHolidays.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(3, 6)));
            thirdQuarterHolidays.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(6, 9)));
            fourthQuarterHolidays.put(entry.getKey(), new ArrayList<>(entry.getValue().subList(9, 12)));
        }
        
        result.put("First", firstQuarterHolidays);
        result.put("Second", secondQuarterHolidays);
        result.put("Third", thirdQuarterHolidays);
        result.put("Fourth", fourthQuarterHolidays);
        
        return result;
    }
    
}

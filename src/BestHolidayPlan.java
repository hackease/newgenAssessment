import java.time.Month;
import java.util.*;

public class BestHolidayPlan {
    
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
        
        HashMap<Integer, HashMap<String, String>> bestHolidaysPlan = bestHolidaysPlan();
        
        HashMap<String, String> theHolidaysPlan = bestHolidaysPlan.values().iterator().next();
        int totalHolidaysEnjoyed = bestHolidaysPlan.keySet().iterator().next();

        printOutput(theHolidaysPlan, totalHolidaysEnjoyed);
    }
    
    private static HashMap<Integer, HashMap<String, String>> bestHolidaysPlan() {
        HashMap<Integer, HashMap<String, String>> result = new HashMap<>();
        
        List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> allHolidayPlansPerQuarter
                = allHolidayPlansPerQuarter();
        
        List<HashMap<Integer, List<HashMap<String, Integer>>>> bestHolidaysPlans
                = bestHolidaysPlans(allHolidayPlansPerQuarter);
        
        bestHolidaysPlans.sort((c1, c2) -> {
            Integer value1 = c1.keySet().iterator().next();
            Integer value2 = c2.keySet().iterator().next();
            return value1.compareTo(value2);
        });
        
        List<HashMap<String, Integer>> bestHolidaysPlan = bestHolidaysPlans.getFirst().values().iterator().next();
        
        HashMap<String, String> thePlan = new HashMap<>();
        Integer totalHolidays = totalHolidays(bestHolidaysPlan);
        
        for (int i=1; i<=bestHolidaysPlan.size(); i++) {
            String monthName = Month.of(i).name();
            String formattedMonthName = monthName.charAt(0) + monthName.substring(1).toLowerCase();
            thePlan.put(formattedMonthName, bestHolidaysPlan.get(i-1).keySet().iterator().next());
        }
        
        result.put(totalHolidays, thePlan);
        
        return result;
    }
    
    private static int totalHolidays(List<HashMap<String, Integer>> bestHolidaysPlan) {
        final int[] totalHolidays = {0};
        bestHolidaysPlan.forEach((action) -> totalHolidays[0] += action.values().iterator().next());
        return totalHolidays[0];
    }
    
    private static List<HashMap<Integer, List<HashMap<String, Integer>>>> bestHolidaysPlans(
            List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> allHolidayPlansPerQuarter
    ) {
        List<HashMap<Integer, List<HashMap<String, Integer>>>> result = new ArrayList<>();
        List<HashMap<Integer, List<HashMap<String, Integer>>>> bestHolidaysPlans = new ArrayList<>();
        
        var firstQuarterLists = allHolidayPlansPerQuarter.getFirst();
        var secondQuarterLists = allHolidayPlansPerQuarter.get(1);
        var thirdQuarterLists = allHolidayPlansPerQuarter.get(2);
        var fourthQuarterLists = allHolidayPlansPerQuarter.get(3);
        
        int maxHolidays = 0;
        
        for (var first: firstQuarterLists) {
            for (var second: secondQuarterLists) {
                for (var third: thirdQuarterLists) {
                    for (var fourth: fourthQuarterLists) {
                        Integer firstRelocationCounts = first.keySet().iterator().next();
                        Integer secondRelocationCounts = second.keySet().iterator().next();
                        Integer thirdRelocationCounts = third.keySet().iterator().next();
                        Integer fourthRelocationCounts = fourth.keySet().iterator().next();
                        
                        String firstEndLocation = first.values().iterator().next().get(2).keySet().iterator().next();
                        String secondEndLocation = second.values().iterator().next().get(2).keySet().iterator().next();
                        String thirdEndLocation = third.values().iterator().next().get(2).keySet().iterator().next();
                        
                        String secondStartLocation = second.values().iterator().next().getFirst().keySet().iterator().next();
                        String thirdStartLocation = third.values().iterator().next().getFirst().keySet().iterator().next();
                        String fourthStartLocation = fourth.values().iterator().next().getFirst().keySet().iterator().next();
                        
                        var tempBestHolidaysPlan = new ArrayList<>(first.values().iterator().next());
                        
                        var possibleSecondStartLocations = possibleLocations(firstEndLocation);
                        possibleSecondStartLocations.addFirst(firstEndLocation);
                        
                        if (possibleSecondStartLocations.contains(secondStartLocation)) {
                            if (firstEndLocation.equals(secondStartLocation))
                                tempBestHolidaysPlan.addAll(second.values().iterator().next());
                            else {
                                if (secondRelocationCounts == 1) {
                                    tempBestHolidaysPlan.addAll(second.values().iterator().next());
                                    secondRelocationCounts++;
                                }
                            }
                        }
                        
                        possibleSecondStartLocations.clear();
                        
                        var possibleThirdStartLocations = possibleLocations(secondEndLocation);
                        possibleThirdStartLocations.addFirst(secondEndLocation);
                        
                        if (possibleThirdStartLocations.contains(thirdStartLocation)) {
                            if (secondEndLocation.equals(thirdStartLocation))
                                tempBestHolidaysPlan.addAll(third.values().iterator().next());
                            else {
                                if (thirdRelocationCounts == 1) {
                                    tempBestHolidaysPlan.addAll(third.values().iterator().next());
                                    thirdRelocationCounts++;
                                }
                            }
                        }
                        
                        possibleThirdStartLocations.clear();
                        
                        var possibleFourthStartLocations = possibleLocations(thirdEndLocation);
                        possibleFourthStartLocations.addFirst(thirdEndLocation);
                        
                        if (possibleFourthStartLocations.contains(fourthStartLocation)) {
                            if (thirdEndLocation.equals(fourthStartLocation))
                                tempBestHolidaysPlan.addAll(fourth.values().iterator().next());
                            else {
                                if (fourthRelocationCounts == 1) {
                                    tempBestHolidaysPlan.addAll(fourth.values().iterator().next());
                                    fourthRelocationCounts++;
                                }
                            }
                        }
                        
                        possibleFourthStartLocations.clear();
                        
                        if (tempBestHolidaysPlan.size() == 12) {
                            final int[] totalHolidays = {0};
                            tempBestHolidaysPlan.forEach(
                                    (element) -> totalHolidays[0] += element.values().iterator().next()
                            );
                            maxHolidays = Math.max(maxHolidays, totalHolidays[0]);
                            
                            if (totalHolidays[0] == maxHolidays) {
                                Integer relocationCounts = firstRelocationCounts+
                                                                   secondRelocationCounts+
                                                                   thirdRelocationCounts+
                                                                   fourthRelocationCounts;
                                bestHolidaysPlans.addFirst(new HashMap<>(Map.of(
                                        relocationCounts, tempBestHolidaysPlan
                                )));
                            }
                        }
                    }
                }
            }
        }
        
        int[] maximumHolidays = {0};
        bestHolidaysPlans.forEach(
                (action) -> {
                    Integer actionKey = action.entrySet().iterator().next().getKey();
                    var actionValue = action.entrySet().iterator().next().getValue();
                    int[] totalHolidays = {0};
                    actionValue.forEach(
                            (tempMap) -> {
                                totalHolidays[0] += tempMap.values().iterator().next();
                                maximumHolidays[0] = Math.max(maximumHolidays[0], totalHolidays[0]);
                            }
                    );
                    
                    if (totalHolidays[0] == maximumHolidays[0])
                        result.addFirst(new HashMap<>(Map.of(actionKey, actionValue)));
                }
        );
        
        return result;
    }
    
    private static List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> allHolidayPlansPerQuarter() {
        
        List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> result = new ArrayList<>();
        
        var holidaysPerQuarter = holidaysPerQuarter();
        var sortedHolidaysPerQuarter = sortedHolidaysPerQuarter(holidaysPerQuarter);
        
        for (var sortedHolidaysPerMonth: sortedHolidaysPerQuarter) {
            var firstMonthHolidays = sortedHolidaysPerMonth.getFirst();
            var secondMonthHolidays = sortedHolidaysPerMonth.get(1);
            var thirdMonthHolidays = sortedHolidaysPerMonth.get(2);
            
            List<HashMap<Integer, List<HashMap<String, Integer>>>> tempBestQuarterlyHolidays = new ArrayList<>();
            for (var startPoint: firstMonthHolidays) {
                for (var midPoint: secondMonthHolidays) {
                    for (var endPoint: thirdMonthHolidays) {
                        String startPointKey = startPoint.keySet().iterator().next();
                        String midPointKey = midPoint.keySet().iterator().next();
                        String endPointKey = endPoint.keySet().iterator().next();
                        
                        int relocationCount = 0;
                        
                        List<HashMap<String, Integer>> tempMonthHolidays = new ArrayList<>();
                        tempMonthHolidays.add(startPoint);
                        
                        var possibleMidPointLocations = possibleLocations(startPointKey);
                        possibleMidPointLocations.addFirst(startPointKey);
                        
                        if (possibleMidPointLocations.contains(midPointKey)) {
                            if (!midPointKey.equals(startPointKey)) relocationCount++;
                            tempMonthHolidays.add(midPoint);
                        }
                        
                        possibleMidPointLocations.clear();
                        
                        var possibleEndPointLocations = possibleLocations(midPointKey);
                        possibleEndPointLocations.addFirst(midPointKey);
                        
                        if (possibleEndPointLocations.contains(endPointKey)) {
                            if (!endPointKey.equals(midPointKey)) relocationCount++;
                            tempMonthHolidays.add(endPoint);
                        }
                        
                        possibleEndPointLocations.clear();
                        
                        if (tempMonthHolidays.size() == 3) {
                            tempBestQuarterlyHolidays.add(new HashMap<>(Map.of(
                                    relocationCount,
                                    tempMonthHolidays
                            )));
                        }
                    }
                }
            }
            
            result.add(tempBestQuarterlyHolidays);
        }
        
        return result;
    }
    
    private static List<String> possibleLocations(String key) {
        return new ArrayList<>(locations.get(key));
    }
    
    private static List<List<List<HashMap<String, Integer>>>> sortedHolidaysPerQuarter(
            List<List<List<HashMap<String, Integer>>>> holidaysPerQuarter
    ) {
        holidaysPerQuarter.forEach(
                (quarters) -> quarters.forEach(
                        (months) -> months.sort((map1Entry, map2Entry) -> {
                                    Integer value1 = map1Entry.values().iterator().next();
                                    Integer value2 = map2Entry.values().iterator().next();
                                    return value2.compareTo(value1);
                                })
                )
        );
        
        return holidaysPerQuarter;
    }
    
    private static List<List<List<HashMap<String, Integer>>>> holidaysPerQuarter() {
        
        List<List<List<HashMap<String, Integer>>>> result = new ArrayList<>();
        
        var holidaysPerMonth = getLists();
        
        List<List<HashMap<String, Integer>>> firstQuarterHolidays = new ArrayList<>();
        List<List<HashMap<String, Integer>>> secondQuarterHolidays = new ArrayList<>();
        List<List<HashMap<String, Integer>>> thirdQuarterHolidays = new ArrayList<>();
        List<List<HashMap<String, Integer>>> fourthQuarterHolidays = new ArrayList<>();
        
        for (Map.Entry<String, List<Integer>> entry: holidays.entrySet()) {
            for (int i=0; i<entry.getValue().size(); i++) {
                HashMap<String, Integer> tempHashMap = new HashMap<>(
                        Map.of(entry.getKey(), entry.getValue().get(i))
                );
                
                holidaysPerMonth.get(i).add(tempHashMap);
            }
        }
        
        for (int i=0; i<holidaysPerMonth.size(); i++) {
            if (i<3) firstQuarterHolidays.add(holidaysPerMonth.get(i));
            else if (i<6) secondQuarterHolidays.add(holidaysPerMonth.get(i));
            else if (i<9) thirdQuarterHolidays.add(holidaysPerMonth.get(i));
            else if (i<12) fourthQuarterHolidays.add(holidaysPerMonth.get(i));
        }
        
        result.add(firstQuarterHolidays);
        result.add(secondQuarterHolidays);
        result.add(thirdQuarterHolidays);
        result.add(fourthQuarterHolidays);
        
        return result;
    }
    
    private static List<List<HashMap<String, Integer>>> getLists() {
        return new ArrayList<>(
                List.of(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>())
        );
    }
    
    private static void printOutput(HashMap<String, String> theHolidaysPlan, int totalHolidaysEnjoyed) {
        System.out.println("January:\t" + theHolidaysPlan.get("January"));
        System.out.println("February:\t" + theHolidaysPlan.get("February"));
        System.out.println("March:\t\t" + theHolidaysPlan.get("March"));
        System.out.println("April:\t\t" + theHolidaysPlan.get("April"));
        System.out.println("May:\t\t" + theHolidaysPlan.get("May"));
        System.out.println("June:\t\t" + theHolidaysPlan.get("June"));
        System.out.println("July:\t\t" + theHolidaysPlan.get("July"));
        System.out.println("August:\t\t" + theHolidaysPlan.get("August"));
        System.out.println("September:\t" + theHolidaysPlan.get("September"));
        System.out.println("October:\t" + theHolidaysPlan.get("October"));
        System.out.println("November:\t" + theHolidaysPlan.get("November"));
        System.out.println("December:\t" + theHolidaysPlan.get("December"));
        System.out.println();
        System.out.println("Total Holidays Enjoyed: " + totalHolidaysEnjoyed);
    }
    
}

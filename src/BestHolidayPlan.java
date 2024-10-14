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
        
        List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> bestHolidayPlansPerQuarter
                = bestHolidayPlansPerQuarter();
        
        List<HashMap<Integer, List<HashMap<String, Integer>>>> bestHolidaysPlans
                = bestHolidaysPlans(bestHolidayPlansPerQuarter);
        
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
            List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> bestHolidayPlansPerQuarter
    ) {
        List<HashMap<Integer, List<HashMap<String, Integer>>>> result = new ArrayList<>();
        
        List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> newBestHolidayPlansPerQuarter = new ArrayList<>();
        List<HashMap<String, Integer>> maxQuarterlyStartingHolidays = new ArrayList<>();
        
        List<HashMap<Integer, List<HashMap<String, Integer>>>> newFirstQuarter = new ArrayList<>();
        HashMap<String, Integer> firstQuarterMaxStartingHolidays = bestHolidayPlansPerQuarter.getFirst().getFirst().values().iterator().next().getFirst();

        List<HashMap<Integer, List<HashMap<String, Integer>>>> newSecondQuarter = new ArrayList<>();
        HashMap<String, Integer> secondQuarterMaxStartingHolidays = bestHolidayPlansPerQuarter.get(1).getFirst().values().iterator().next().getFirst();

        List<HashMap<Integer, List<HashMap<String, Integer>>>> newThirdQuarter = new ArrayList<>();
        HashMap<String, Integer> thirdQuarterMaxStartingHolidays = bestHolidayPlansPerQuarter.get(2).getFirst().values().iterator().next().getFirst();

        List<HashMap<Integer, List<HashMap<String, Integer>>>> newFourthQuarter = new ArrayList<>();
        HashMap<String, Integer> fourthQuarterMaxStartingHolidays = bestHolidayPlansPerQuarter.get(3).getFirst().values().iterator().next().getFirst();

        maxQuarterlyStartingHolidays.add(firstQuarterMaxStartingHolidays);
        maxQuarterlyStartingHolidays.add(secondQuarterMaxStartingHolidays);
        maxQuarterlyStartingHolidays.add(thirdQuarterMaxStartingHolidays);
        maxQuarterlyStartingHolidays.add(fourthQuarterMaxStartingHolidays);

        newBestHolidayPlansPerQuarter.add(newFirstQuarter);
        newBestHolidayPlansPerQuarter.add(newSecondQuarter);
        newBestHolidayPlansPerQuarter.add(newThirdQuarter);
        newBestHolidayPlansPerQuarter.add(newFourthQuarter);
        
        for (int i=0; i<bestHolidayPlansPerQuarter.size()-1; i++) {
            for (int j=0; j<bestHolidayPlansPerQuarter.get(i).size(); j++) {
                Iterator<Map.Entry<Integer, List<HashMap<String, Integer>>>> quarterIterator = bestHolidayPlansPerQuarter.get(i).get(j).entrySet().iterator();

                while (quarterIterator.hasNext()) {
                    Map.Entry<Integer, List<HashMap<String, Integer>>> entry = quarterIterator.next();

                    HashMap<String, Integer> tempMap = entry.getValue().get(2);
                    List<String> possibleLocations = possibleLocations(tempMap.keySet().iterator().next());
                    possibleLocations.addFirst(tempMap.keySet().iterator().next());

                    if (possibleLocations.contains(maxQuarterlyStartingHolidays.get(i+1).keySet().iterator().next())) {
                        newBestHolidayPlansPerQuarter.get(i).add(new HashMap<>(Map.of(
                                bestHolidayPlansPerQuarter.get(i).get(j).keySet().iterator().next(),
                                bestHolidayPlansPerQuarter.get(i).get(j).values().iterator().next()
                        )));
                    }

                    quarterIterator.remove();
                }
            }
        }

        newBestHolidayPlansPerQuarter.remove(3);
        newBestHolidayPlansPerQuarter.add(bestHolidayPlansPerQuarter.get(3));
        
        for (List<HashMap<Integer, List<HashMap<String, Integer>>>> hashMaps:
                newBestHolidayPlansPerQuarter) {
            hashMaps.sort(
                    (entry1, entry2) -> {
                        Integer value1 = entry1.values().iterator().next()
                                                 .get(2).values().iterator().next();
                        Integer value2 = entry2.values().iterator().next()
                                                 .get(2).values().iterator().next();
                        return value2.compareTo(value1);
                    }
            );
        }
        
        List<HashMap<Integer, List<HashMap<String, Integer>>>> firstQuarterLists = newBestHolidayPlansPerQuarter.getFirst();
        List<HashMap<Integer, List<HashMap<String, Integer>>>> secondQuarterLists = newBestHolidayPlansPerQuarter.get(1);
        List<HashMap<Integer, List<HashMap<String, Integer>>>> thirdQuarterLists = newBestHolidayPlansPerQuarter.get(2);
        List<HashMap<Integer, List<HashMap<String, Integer>>>> fourthQuarterLists = newBestHolidayPlansPerQuarter.get(3);
        
        int maxHolidays = 0;
        
        for (HashMap<Integer, List<HashMap<String, Integer>>> first: firstQuarterLists) {
            for (HashMap<Integer, List<HashMap<String, Integer>>> second: secondQuarterLists) {
                for (HashMap<Integer, List<HashMap<String, Integer>>> third: thirdQuarterLists) {
                    for (HashMap<Integer, List<HashMap<String, Integer>>> fourth: fourthQuarterLists) {
                        
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
                        
                        List<HashMap<String, Integer>> tempBestHolidaysPlan = new ArrayList<>(first.values().iterator().next());
                        
                        if (firstEndLocation.equals(secondStartLocation))
                            tempBestHolidaysPlan.addAll(second.values().iterator().next());
                        else {
                            if (secondRelocationCounts == 1) {
                                tempBestHolidaysPlan.addAll(second.values().iterator().next());
                                secondRelocationCounts++;
                            }
                        }
                        
                        if (secondEndLocation.equals(thirdStartLocation))
                            tempBestHolidaysPlan.addAll(third.values().iterator().next());
                        else {
                            if (thirdRelocationCounts == 1) {
                                tempBestHolidaysPlan.addAll(third.values().iterator().next());
                                thirdRelocationCounts++;
                            }
                        }
                        
                        if (thirdEndLocation.equals(fourthStartLocation))
                            tempBestHolidaysPlan.addAll(fourth.values().iterator().next());
                        else {
                            if (fourthRelocationCounts == 1) {
                                tempBestHolidaysPlan.addAll(fourth.values().iterator().next());
                                fourthRelocationCounts++;
                            }
                        }
                        
                        if (tempBestHolidaysPlan.size() == 12) {
                            final int[] totalHolidays = {0};
                            tempBestHolidaysPlan.forEach((element) -> totalHolidays[0] += element.values().iterator().next());
                            maxHolidays = Math.max(maxHolidays, totalHolidays[0]);
                            
                            if (totalHolidays[0] == maxHolidays) {
                                Integer relocationCounts = firstRelocationCounts + secondRelocationCounts + thirdRelocationCounts + fourthRelocationCounts;
                                result.add(new HashMap<>(Map.of(relocationCounts, tempBestHolidaysPlan)));
                            }
                        }
                    }
                }
            }
        }
        
        return result;
    }
    
    private static List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> bestHolidayPlansPerQuarter() {
        
        List<List<HashMap<Integer, List<HashMap<String, Integer>>>>> result = new ArrayList<>();
        
        List<List<List<HashMap<String, Integer>>>> holidaysPerQuarter
                = holidaysPerQuarter();
        
        List<List<List<HashMap<String, Integer>>>> sortedHolidaysPerQuarter
                = sortedHolidaysPerQuarter(holidaysPerQuarter);

        List<HashMap<String, Integer>> maximumStartingHolidayPerQuarter =
                maximumStartingHolidayPerQuarter(sortedHolidaysPerQuarter);
        
        int i=0;
        while (i<4) {
            List<HashMap<String, Integer>> firstMonthHolidays = sortedHolidaysPerQuarter.get(i).getFirst();
            List<HashMap<String, Integer>> secondMonthHolidays = sortedHolidaysPerQuarter.get(i).get(1);
            List<HashMap<String, Integer>> thirdMonthHolidays = sortedHolidaysPerQuarter.get(i).get(2);
            
            List<HashMap<Integer, List<HashMap<String, Integer>>>> tempBestQuarterlyHolidays = new ArrayList<>();
            HashMap<String, Integer> startPoint = maximumStartingHolidayPerQuarter.get(i);
            
            int relocationCount = 0;
            
            if (firstMonthHolidays.getFirst().equals(startPoint)) {
                List<String> possibleLocations = possibleLocations(
                        startPoint.keySet().iterator().next()
                );
                possibleLocations.addFirst(startPoint.keySet().iterator().next());
                int locationChecks = possibleLocations.size();
                int j = 0;
                while (locationChecks > 0) {
                    String tempKey = secondMonthHolidays.get(j).keySet().iterator().next();
                    Integer tempValue = secondMonthHolidays.get(j).values().iterator().next();
                    
                    if (possibleLocations.contains(tempKey)) {
                        if (!tempKey.equals(startPoint.keySet().iterator().next())) relocationCount++;
                        
                        List<String> possibleLocations1 = possibleLocations(tempKey);
                        possibleLocations1.addFirst(tempKey);
                        
                        int locationChecks1 = possibleLocations1.size();
                        int k = 0;
                        while (locationChecks1 > 0) {
                            String tempKey1 = thirdMonthHolidays.get(k).keySet().iterator().next();
                            Integer tempValue1 = thirdMonthHolidays.get(k).values().iterator().next();
                            
                            if (possibleLocations1.contains(tempKey1)) {
                                if (!tempKey1.equals(tempKey)) relocationCount++;
                                
                                tempBestQuarterlyHolidays.add(new HashMap<>(Map.of(relocationCount, new ArrayList<>(
                                        List.of(
                                                startPoint,
                                                new HashMap<>(Map.of(tempKey, tempValue)),
                                                new HashMap<>(Map.of(tempKey1, tempValue1))
                                        )
                                ))));
                                
                                if (!tempKey1.equals(tempKey)) relocationCount--;
                                locationChecks1--;
                            }
                            
                            k++;
                        }
                        
                        if (!tempKey.equals(startPoint.keySet().iterator().next())) relocationCount--;
                        locationChecks--;
                    }
                    
                    j++;
                }
                
            }
            
            result.add(tempBestQuarterlyHolidays);
            i++;
        }
        
        return result;
    }
    
    private static List<String> possibleLocations(String key) {
        return new ArrayList<>(locations.get(key));
    }
    
    private static List<HashMap<String, Integer>> maximumStartingHolidayPerQuarter(
            List<List<List<HashMap<String, Integer>>>> sortedHolidaysPerQuarter
    ) {
        List<HashMap<String, Integer>> result = new ArrayList<>();
        
        result.add(
                new HashMap<>(
                        Map.of(
                                sortedHolidaysPerQuarter.getFirst().getFirst().getFirst()
                                        .keySet().iterator().next(),
                                sortedHolidaysPerQuarter.getFirst().getFirst().getFirst()
                                        .values().iterator().next()
                        )
                )
        );
        result.add(
                new HashMap<>(
                        Map.of(
                                sortedHolidaysPerQuarter.get(1).getFirst().getFirst()
                                        .keySet().iterator().next(),
                                sortedHolidaysPerQuarter.get(1).getFirst().getFirst()
                                        .values().iterator().next()
                        )
                )
        );
        result.add(
                new HashMap<>(
                        Map.of(
                                sortedHolidaysPerQuarter.get(2).getFirst().getFirst()
                                        .keySet().iterator().next(),
                                sortedHolidaysPerQuarter.get(2).getFirst().getFirst()
                                        .values().iterator().next()
                        )
                )
        );
        result.add(
                new HashMap<>(
                        Map.of(
                                sortedHolidaysPerQuarter.get(3).getFirst().getFirst()
                                        .keySet().iterator().next(),
                                sortedHolidaysPerQuarter.get(3).getFirst().getFirst()
                                        .values().iterator().next()
                        )
                )
        );
        
        return result;
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
        
        List<List<HashMap<String, Integer>>> holidaysPerMonth = getLists();
        
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
            if (i<3) {
                firstQuarterHolidays.add(holidaysPerMonth.get(i));
            } else if (i<6) {
                secondQuarterHolidays.add(holidaysPerMonth.get(i));
            } else if (i<9) {
                thirdQuarterHolidays.add(holidaysPerMonth.get(i));
            } else if (i<12) {
                fourthQuarterHolidays.add(holidaysPerMonth.get(i));
            }
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

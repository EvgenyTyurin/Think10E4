import java.util.HashMap;
import java.util.Scanner;

/*
    Bruce Eckel "Think in Java": Chapter 10 Exercise 4
    Vampire numbers search (6 * 21 = 126, 35 * 41 = 1435 ...)
*/

public class Think10E4 {

    // Returns map (digit : number of occurrences)
    private static HashMap<Character,Integer> getDigitMap(String str) {
        HashMap<Character,Integer> charMap = new HashMap<>();
        for (int charCount = 0; charCount < str.length(); charCount++) {
            char digit = str.charAt(charCount);
            if (charMap.containsKey(digit))
                charMap.put(digit, charMap.get(digit) + 1);
            else
                charMap.put(digit, 1);
        }
        return charMap;
    }

    // Add one map of digit occurrences to another
    private static HashMap<Character,Integer> mapsAdd(HashMap<Character,Integer> map1,
            HashMap<Character,Integer> map2) {
        // Put both maps to one
        HashMap<Character,Integer> resultMap = new HashMap<>();
        resultMap.putAll(map1);
        resultMap.putAll(map2);
        // Entries from map2 may erase values from map1 - we must save them
        for (char letter : resultMap.keySet()) {
            if (map1.containsKey(letter) && map2.containsKey(letter)) {
                resultMap.put(letter, map1.get(letter) + resultMap.get(letter));
            }
        }
        return  resultMap;
    }

    // Run point
    public static void main(String[] args) {
        String userAnswer;
        do {
            // Ask user for search range
            int maxInt = 0;
            int minInt = 0;
            Scanner con = new Scanner(System.in);
            try {
                do {
                    System.out.print("Enter start number of vampire search: ");
                    minInt = con.nextInt();
                } while (minInt < 0);
                do {
                    System.out.print("Enter end number of vampire search  : ");
                    maxInt = con.nextInt();
                } while (maxInt < minInt);
            } catch (Exception e) {
                System.out.println("Input error!");
            }
            // Search for vampire numbers
            for (int mult1 = minInt; mult1 <= maxInt; mult1++) {
                String sMult1 = String.valueOf(mult1);
                HashMap<Character, Integer> mapMult1 = getDigitMap(sMult1);
                for (int mult2 = mult1; mult2 <= maxInt; mult2++) {
                    String sMult2 = String.valueOf(mult2);
                    int targetLength = sMult1.length() + sMult2.length();
                    HashMap<Character, Integer> mapMult2 = getDigitMap(sMult2);
                    int result = mult1 * mult2;
                    String sResult = String.valueOf(result);
                    if (sResult.length() != targetLength)
                        continue;
                    HashMap<Character, Integer> mapResult = getDigitMap(sResult);
                    HashMap<Character, Integer> mapMulties = mapsAdd(mapMult1, mapMult2);
                    // Numbers of digits in multipliers and in result equal - vampire!
                    if (mapMulties.equals(mapResult))
                        System.out.println(sMult1 + " * " + sMult2 + " = " + sResult);
                }
            }
            // Ask user for another search
            System.out.print("Enter 'y' to start another search: ");
            userAnswer = con.next();
        } while (userAnswer.equals("y"));
    }

}

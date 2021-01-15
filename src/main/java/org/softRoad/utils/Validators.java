package org.softRoad.utils;

import java.util.ArrayList;
import java.util.List;

public class Validators {
    public static boolean nationalIDValidator(String nationalID) {
        if (nationalID.length() != 10)
            return false;
        List<Integer> separatedNationalID = new ArrayList<>();
        for (int i = 9; i >= 0; i--)
            separatedNationalID.add(Character.getNumericValue(nationalID.charAt(i)));
        int sum = 0;
        for (int i = 9; i > 0; i -= 1)
            sum += separatedNationalID.get(i) * (i + 1);
        int temp = sum % 11;
        if (temp < 2)
            return separatedNationalID.get(0) == temp;
        else
            return separatedNationalID.get(0) == 11 - temp;
    }
}

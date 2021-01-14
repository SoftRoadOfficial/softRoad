package org.softRoad.utils;

public class Validators {
    public static boolean nationalIDValidator(String nationalID){
        if (nationalID.length() != 10)
            return false;
        int[] separatedNationalID = new int[10];
        for (int i = 0; i < 10 ; i += 1) {
            separatedNationalID[i] = Character.getNumericValue(nationalID.charAt(i));
        }
        int sum = 0;
        for (int i = 9; i > 0 ; i -= 1)
            sum += separatedNationalID[i] * (i+1);
        int temp = sum % 11;
        if (temp < 2)
            return separatedNationalID[0] == temp;
        else
            return separatedNationalID[0] == 11 - temp;
    }
}

package com.company;

import java.math.BigInteger;

public class Converter {

    public static String HexToBinary(String h){
        return new BigInteger(h, 16).toString(2);
    }

    public static String HexToBinary8Bits(String h){

        String hex = HexToBinary(h);
        for (int i = hex.length(); i < 8; i++) {
            hex = "0" + hex;
        }

        return hex;
    }
    public static String HexToBinary12Bits(String h){

        String hex = HexToBinary(h);
        for (int i = hex.length(); i < 12; i++) {
            hex = "0" + hex;
        }

        return hex;
    }

    public static int binaryToDecimal(String b){
        return Integer.parseInt(b,2);
    }

    public static String BinaryToHex(String b){
        int decimal = Integer.parseInt(b,2);
        return Integer.toString(decimal,16);
    }

}

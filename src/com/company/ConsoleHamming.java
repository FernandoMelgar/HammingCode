package com.company;

import java.util.Scanner;

public class ConsoleHamming {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    String hex;
    Hamming hm;

    while (true){
      showMenu();
      String option = sc.nextLine();
      switch (option){
        case  "1":
          System.out.println("write the data in Hexadecimal:");
          hex = sc.nextLine();
          hm = new Hamming(hex);
          System.out.println("The Encoding is: " + hm.getHammingCode());
          break;
        case  "2":
          System.out.println("Write the encoding in hexadecimal");
          hex = sc.nextLine();
          System.out.println("Write the General Parity (GP)");
          String gp =sc.nextLine();
          hm = new Hamming(hex, gp.charAt(0));
          switch (hm.status) {
            case "OK":
              System.out.println("There were no errors, Your data is: " + hm.getData());
              break;
            case "SEC":
              System.out.println("There was an SEC on the bit [" + hm.syndrome + "]," +
                      " your data is:" + hm.getData());
              break;
            case "DED":
              System.out.println("There was an DED, we can't solve the problem. " +
                      "Please ask for a retransmission");
              break;
          }
          break;
        default:
          System.out.println("Sorry we don't understand your option");
          break;
      }
      System.out.println("Do you wan't to try again Hamming Hamming Encoder/Decoder (y/n)");
      String toContinue = sc.nextLine();
      if (!toContinue.equalsIgnoreCase("y")) break;
    }
  }

  private static void showMenu() {
    System.out.println("Welcome to Hamming Encoder/Decoder");
    System.out.println("Please choose an option: ");
    System.out.println("1) Encode to Hamming");
    System.out.println("2) Decode from hamming");
  }
}

package com.company;

class Hamming {
  private String data;
  private String hammingCode;
  char generalParity;
  int syndrome;
  String status;


  public Hamming(String hex){
    this.data = Converter.HexToBinary8Bits(hex);
    this.hammingCode = binaryToHamming(this.data);
    calculateGeneralParity();
  }

  public Hamming(String hex, char external_gp){
    this.hammingCode = Converter.HexToBinary12Bits(hex);
    this.generalParity = calculateGeneralParity(this.hammingCode);
    this.syndrome = errorBetweenParities();

    ErrorInHamming error = new ErrorInHamming(this, external_gp);
    this.status = error.status;

    switch (status) {
      case "OK":
        this.data = hammingToBinary();
        break;
      case "SEC":
        changeHammingBitAt(this.syndrome);
        this.data = hammingToBinary();
        break;
      case "DED":
        this.data ="0";
        break;
    }
  }


  private String binaryToHamming(String b){
    String[] parityPrime = new ParityPrime(b).parities;

    return b.substring(0,4)
            + parityPrime[3]
            + b.substring(4,7)
            + parityPrime[2]
            + b.charAt(7)
            + parityPrime[1]
            + parityPrime[0];
  }

  private String hammingToBinary(){
    return onlyDataBits();

  }

  private int errorBetweenParities(){
    String[] parityPrime = new ParityPrime(onlyDataBits()).parities;
    String[] parityBiPrime = new ParityBiPrime(this.hammingCode).parities;
    return Converter.binaryToDecimal(XOR_Parities(parityPrime, parityBiPrime));
  }

  private String onlyDataBits() {
    String hamming = this.hammingCode;
    return hamming.substring(0,4) + hamming.substring(5,8) + hamming.charAt(9);
  }

  private static String XOR_Parities(String[] parityPrime, String[] parityBiPrime) {
    StringBuilder res = new StringBuilder();
    for (int i = 3; i >= 0; i--){
      res.append(XOR(parityPrime[i], parityBiPrime[i]) ? "1" : "0");
    }
    return res.toString();
  }

  private void changeHammingBitAt(int at) {
    int index =  at - 1;

    String reverseHam = new StringBuilder(this.hammingCode).reverse().toString();
    char charToReverse = reverseHam.charAt(index);

    try {
      StringBuilder changedHamming = new StringBuilder(reverseHam);
      changedHamming.setCharAt(index,reverseChar(charToReverse));
      this.data = changedHamming.reverse().toString();

    }catch (IndexOutOfBoundsException e){
      System.out.println("There was an error while changing bits");
    }
  }

  private static char reverseChar(char c){
    return (c == '1') ? '0' : '1';
  }

  private static boolean XOR(String a, String b){
    return a.equals("1")^b.equals("1");
  }


  private void calculateGeneralParity(){
    this.generalParity = calculateGeneralParity(this.hammingCode);
  }

  private char calculateGeneralParity(String bin){
    int n = 0;
    char[] chars = bin.toCharArray();
    for (char c : chars) {
      if (c == '1') n++;
    }
    return  (n % 2 == 0) ? '0' : '1';
  }


  private class ParityPrime {

    String[] parities;

    public ParityPrime(String bin) {
      this.parities = new String[]{
              parityOne(bin),
              parityTwo(bin),
              parityThree(bin),
              parityFour(bin)
      };
    }

    private String parityOne(String b){
      Character[] p1 = {b.charAt(1),b.charAt(3),b.charAt(4),b.charAt(6),b.charAt(7)};
      return XOR(p1) ? "1": "0";
    }

    private String parityTwo(String b){
      Character[] p2 = {b.charAt(1),b.charAt(2),b.charAt(4),b.charAt(5),b.charAt(7)};
      return XOR(p2) ? "1": "0";
    }
    private String parityThree(String b){
      Character[] p3 = {b.charAt(0),b.charAt(4),b.charAt(5),b.charAt(6)};
      return XOR(p3) ? "1": "0";
    }
    private String parityFour(String b){
      Character[] p4 = {b.charAt(0),b.charAt(1),b.charAt(2),b.charAt(3)};
      return XOR(p4) ? "1": "0";
    }

    private boolean XOR(Character[] arr){

      boolean result = false;
      for (char b : arr) {
        result = result^(b=='1');
      }
      return result;
    }


  }

  public String getData(){
    return Converter.BinaryToHex(this.data).toUpperCase();
  }
  public String getHammingCode(){
    return Converter.BinaryToHex(this.hammingCode).toUpperCase();
  }

  private class ParityBiPrime {
    String[] parities;

    public ParityBiPrime(String ham) {
      this.parities = new String[]{
              Character.toString(ham.charAt(11)),
              Character.toString(ham.charAt(10)),
              Character.toString(ham.charAt(8)),
              Character.toString(ham.charAt(4)),
      };
    }
  }

  private class ErrorInHamming{
    String status;

    private ErrorInHamming(Hamming ham, char gp) {
      if (ham.syndrome == 0) this.status = "OK";
      else if (ham.generalParity != gp) this.status = "SEC";
      else this.status = "DED";
    }
  }
}


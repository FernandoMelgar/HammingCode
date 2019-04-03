package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class HammingTest {


  @Test
  public void testHammingConverterRounder(){
      assertEquals("00000001",Converter.HexToBinary8Bits("1"));
  }


  @Test
  public void testHammingEncoder01(){
    String bin = "52";
    Hamming hamming = new Hamming(bin);
    assertEquals("51B", hamming.getHammingCode());
  }
  @Test
  public void testHammingEncoder02(){
    String bin = "F9";
    Hamming hamming = new Hamming(bin);
    assertEquals("F44", hamming.getHammingCode());
  }
  @Test
  public void testHammingEncoder03(){
    String bin = "75";
    Hamming hamming = new Hamming(bin);
    assertEquals("7AD", hamming.getHammingCode());
  }

  @Test
  public void testHammingDecoderNoErrors(){
    String ham = "51B";
    Hamming hamming = new Hamming(ham,'0');
    assertEquals("52", hamming.getData());
  }

  @Test
  public void testHammingDecoderWithSingleError_checkOnlyError(){
    String ham = "51A";
    Hamming hamming = new Hamming(ham,'0');
    assertEquals("SEC", hamming.status);
  }

  @Test
  public void testHammingDecoderWithSingleError_checkDecoding(){
    String ham = "51A";
    Hamming hamming = new Hamming(ham,'0');
    assertEquals("52", hamming.getData());
  }

  @Test
  public void testHammingDecoderWithDoubleError_checkOnlyError(){
    String ham = "41A";
    Hamming hamming = new Hamming(ham,'0');
    assertEquals("DED",hamming.status);
  }
  @Test
  public void testHammingDecoderWithDoubleError_checkDecoding(){
    String ham = "41A";
    Hamming hamming = new Hamming(ham,'0');
    assertEquals("0",hamming.getData());
  }



}
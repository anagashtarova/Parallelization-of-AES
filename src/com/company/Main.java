package com.company;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.IntStream;

class AES {

    public byte[][] divideArray(byte[] source) {


        byte[][] ret = new byte[(int) Math.ceil(source.length / (double) 16)][16];

        int start = 0;

        for (int i = 0; i < ret.length; i++) {
            if (start + 16 > source.length) {
                System.arraycopy(source, start, ret[i], 0, source.length - start);
            } else {
                System.arraycopy(source, start, ret[i], 0, 16);
            }
            start += 16;
        }

        return ret;
    }

}

public class Main {

    public static void main(String[] argv) throws Exception {
        AES d=new AES();
        String k="thisismykey!!!!!";

        String path = "C://Users//private//Desktop//dokument8.txt";
        String content = Files.readString(Paths.get(path));


        byte[][] podatoci = d.divideArray(content.getBytes());
        byte[][] finalna = new byte[podatoci.length][16];
        byte[][] dekriptirana = new byte[podatoci.length][16];

        byte[] enc = new byte[16];

        CryptoTool cryptoTool=new CryptoTool(k);

//        ArrayList<Integer> list=new ArrayList<Integer>(podatoci.length);
//        for(int i=0;i<podatoci.length;i++){
//            list.add(i);
//        }

        long startTime = System.currentTimeMillis();


        IntStream.range(0, podatoci.length).parallel().forEach(i -> {
            try {
//                System.out.println(i+" "+Thread.currentThread().getName());
                finalna[i] = cryptoTool.crypt(podatoci[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        long stopTime = System.currentTimeMillis();
        System.out.println("Time : "+(stopTime-startTime));


        for (int i = 0; i < finalna.length; i++) {

            enc = cryptoTool.decrypt(finalna[i]);
            dekriptirana[i]=enc;

        }

//        System.out.println("Original String : "+content);

        StringBuilder enkriptiran = new StringBuilder();
        for(int i=0;i<finalna.length;i++)
            enkriptiran.append(new String(finalna[i], StandardCharsets.UTF_8));

//        System.out.println("Encrypted String :");

    //    System.out.println(enkriptiran);

        StringBuilder dektiptiran = new StringBuilder();
        for(int i=0;i<dekriptirana.length;i++)
            dektiptiran.append(new String(dekriptirana[i], StandardCharsets.UTF_8));

  //      System.out.println("Decrypted String :"+dektiptiran);


    }
}

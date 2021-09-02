package com.company;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main2 {

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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < podatoci.length; i++) {

            enc = cryptoTool.crypt(podatoci[i]);

            finalna[i]=enc;

        }
        long stopTime = System.currentTimeMillis();
        System.out.println("Execution Time : "+(stopTime-startTime));


        for (int i = 0; i < finalna.length; i++) {

            enc = cryptoTool.decrypt(finalna[i]);

            dekriptirana[i]=enc;

        }


       // System.out.println("Original String : "+content);


        StringBuilder enkriptiran = new StringBuilder();
        for(int i=0;i<finalna.length;i++)
            enkriptiran.append(new String(finalna[i], StandardCharsets.UTF_8));

        System.out.println("Encrypted String :");

        System.out.println(enkriptiran);

        StringBuilder dektiptiran = new StringBuilder();
        for(int i=0;i<dekriptirana.length;i++)
            dektiptiran.append(new String(dekriptirana[i], StandardCharsets.UTF_8));

        System.out.println("Decrypted String :"+dektiptiran);


    }
}

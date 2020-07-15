package com.zx.app.study_notes.javaBasis.io;

import android.app.Activity;

import com.zx.app.study_notes.kotlin.KotlinBasis;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * author Afton
 * date 2020/6/2
 * 内存的读入写出 站在内存角度
 * 装饰者模式
 */
public class javaIo {
    private static final byte[] byteArray = {
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69, 0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F,
            0x70, 0x71, 0x72, 0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };

    public static void main(String[] args) throws IOException {
        /*---------DataStreamTest start-----------*/
//        testDataOutPutStream();//写
//        testDataInPutStream();//读
        /*---------DataStreamTest end-----------*/

        /*---------BufferedStreamTest start-----------*/
//        testBufferedOutputStream();//写
//        testBufferedInputStream();//读
        /*---------BufferedStreamTest end-----------*/

        /*---------StreamTest start-----------*/
//        testOutputStreamWriter();//写
//        testInputStreamReader();//读
        /*---------StreamTest end-----------*/
        KotlinBasis kotlinBasis=new KotlinBasis();
        System.out.println(kotlinBasis.add(1,3));
    }

    /*---------字节流 start-----------*/
    private static void testDataOutPutStream() throws FileNotFoundException {
        File file = new File("testtxt/dataStreamTest.txt");
        DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(file)));

        try {
            out.writeBoolean(true);
            out.writeByte((byte) 0x41);
            out.writeInt(0x12345678);
            out.writeChar((char) 0x4243);
            out.writeShort((short) 0x2465);
            out.writeLong(0x14786525521L);

            out.writeUTF("afton test 研究一下11");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void testDataInPutStream() throws FileNotFoundException {
        File file = new File("testtxt/dataStreamTest.txt");
        DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(file)));

        try {
            System.out.println(in.readBoolean());
            System.out.println(byteToHexString(in.readByte()));
            System.out.println(in.readInt());
            System.out.println(charToHexString(in.readChar()));
            System.out.println(shortToHexString(in.readShort()));
            System.out.println(Long.toHexString(in.readLong()));
            System.out.println(in.readUTF());

            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void testBufferedOutputStream() {

        try {
            File file = new File("testtxt/bufferedStreamTest.txt");
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));

            out.write(byteArray[0]);
            out.write(byteArray, 0, byteArray.length);
//            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void testBufferedInputStream() {
        try {
            File file = new File("testtxt/bufferedStreamTest.txt");
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

            for (int i = 0; i < 10; i++) {
                if (in.available() >= 0) {
                    System.out.println(byteToString((byte) in.read()));
                }
            }

            in.mark(6);
            in.skip(10);

            byte[] b = new byte[1024];
            int n1 = in.read(b, 0, b.length);
            System.out.println("有效字节数：" + n1);
            printByteValue(b);

            in.reset();

            int n2 = in.read(b, 0, b.length);
            System.out.println("有效字节数：" + n2);
            printByteValue(b);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*---------字节流 end-----------*/

    /*---------字符流 start-----------*/
    private final static String STRING = "I like AFTON";

    private static void testOutputStreamWriter() {
        try {
            File file = new File("testtxt/OutputStreamWriter.txt");
            // true, 设置内容可以追加
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);

            bw.write(STRING);
            bw.newLine();
            bw.flush();
            System.out.println(" osw encoding " + osw.getEncoding());

            OutputStreamWriter oswGBK = new OutputStreamWriter(fos, "GBK");
            BufferedWriter bwGBK = new BufferedWriter(oswGBK);

            bwGBK.write(STRING + " GBK");
            bw.newLine();
            bwGBK.flush();
            System.out.println(" osw encoding " + oswGBK.getEncoding());


            bw.close();
            bwGBK.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void testInputStreamReader() {

    }

    /*---------字符流 end-----------*/


    /*byte对应的16进制的字符串*/
    private static String byteToHexString(byte val) {
        return Integer.toHexString(val & 0xff);
    }

    private static String byteToString(byte val) {
        byte[] arr = {val};
        return new String(arr);
    }

    private static void printByteValue(byte[] arr) {
        System.out.println("b的长度:" + arr.length);
        for (byte b : arr) {
            if (b != 0) {
                System.out.print(byteToString(b) + " ");
            }
        }
    }

    /*char对应的16进制的字符串*/
    private static String charToHexString(char val) {
        return Integer.toHexString(val);
    }

    /*short对应的16进制的字符串*/
    private static String shortToHexString(short val) {
        return Integer.toHexString(val & 0xffff);
    }


}

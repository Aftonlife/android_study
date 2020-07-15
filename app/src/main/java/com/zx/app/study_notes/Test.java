package com.zx.app.study_notes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author Afton
 * date 2020/6/19
 */
public class Test {
    public static void main(String[] args) {
        String str[] = {"A", "B", "C"};

       /* int nCnt = str.length;

        int nBit = (0xFFFFFFFF >>> (32 - nCnt));

        int count=0;
        for (int i = 1; i <= nBit; i++) {
            for (int j = 0; j < nCnt; j++) {
                if ((i << (31 - j)) >> 31 == -1) {
                    System.out.print(str[j]);
                }
            }
            System.out.println("");
        }
        System.out.println(count);*/

        char[] arrays = {'a', 'b', 'c', 'd'};

//        List<Character> list= new ArrayList();
//        for(int number=2;number<=arrays.length;number++){
//            p(arrays, 0,number,list);
//        }


        String[] m = {"A", "B", "C","D","E"};
        Set<Set<String>> combinationAll = combination(m);
        System.out.println(combinationAll);
        System.out.println(combinationAll.size());
    }

    //排列
    public static void p(char[] array, int index) {
        char temp;
        if (index == array.length) {
            System.out.println(array);
            return;
        }
        if (array.length == 0 || index < 0 || index > array.length) {
            return;
        }
        for (int j = index; j < array.length; j++) {
            temp = array[j];
            array[j] = array[index];
            array[index] = temp;
            p(array, index + 1);
            temp = array[j];
            array[j] = array[index];
            array[index] = temp;
        }

    }

    //组合
    public static void p(char[] arrays, int begin, int number, List<Character> list) {

        if (number == 0) {
            System.out.println(list.toString());
            return;
        }
        if (begin == arrays.length) {
            return;
        }
        list.add(arrays[begin]);
        p(arrays, begin + 1, number - 1, list);
        list.remove((Character) arrays[begin]);
        p(arrays, begin + 1, number, list);
    }

    //组合位运算
    private static Set<Set<String>> combination(String[] m) {
        Set<Set<String>> result = new HashSet<>();
        for (int i = 1; i < Math.pow(2, m.length); i++) {
            Set<String> eligibleCollections = new HashSet<>();
            // 依次将数字 i 与 2^n 按位与，判断第 n 位是否为 1
            for (int j = 0; j < m.length; j++) {
                if ((i & (int) Math.pow(2, j)) == Math.pow(2, j)) {
                    eligibleCollections.add(m[j]);
                }

            }
            if (eligibleCollections.size() > 1 && eligibleCollections.size() >= m.length - 4)
                result.add(eligibleCollections);

        }
        return result;
    }
}

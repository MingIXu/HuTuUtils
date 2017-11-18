package com.xiaoleilu.hutool.extra;

public class test {
    public static void main(String[] args)
    {
        tohex_l(60);
    }

    public static void tohex_l(int num){
        char[] chs = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] arr = new char[8];
        int ops = arr.length-1;
        while (num != 0) {
            int temp = num & 15;
            arr[ops--] = chs[temp];
            num = num >>> 4;
        }
        for (int x = ops; x < arr.length; x++) {
            System.out.println(arr[x]);
        }
    }
}

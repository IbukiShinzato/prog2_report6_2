package jp.ac.uryukyu.ie.e235718;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// import org.checkerframework.checker.calledmethods.qual.EnsuresCalledMethods.List;


public class Main {
    public static void main(String[] args) {
        String moji = "H 13";
        int number = Integer.parseInt(moji.split(" ")[1]);
        System.out.println(number);
        String type = moji.split(" ")[0];
        System.out.println(type);
    }
}


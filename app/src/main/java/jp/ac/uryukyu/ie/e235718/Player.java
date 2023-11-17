package jp.ac.uryukyu.ie.e235718;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Strings;

import java.util.Iterator;

// import org.checkerframework.checker.index.qual.SubstringIndexBottom;

public class Player {
    private List<String> names;
    private List<Card> hand;

    // List<String> have_cards = new ArrayList<>();

    Player(){
        List<String> names = new ArrayList<String>(Arrays.asList("空条承太郎", "東方仗助", "吉良吉影", "DIO"));
    }

    void sayName(){
        int num = 0;
        for (int i = 0; i < 3; i++){
            System.out.printf("プレイヤー%dの名前は%sです。",num,names[num]);
            num += 1;
        }
    }

    public void getNames(){
        System.out.println(names);
    }

    public void setName(String _name){
        if (_name.length() == 0){
            System.out.println("名前を入力してください。");
        }else{
            this.names = _name;
        }
    }
}

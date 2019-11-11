package com.mut_jaeryo.givmkeyword.keyword;

import java.util.ArrayList;
import java.util.Random;

public class objects {

    //이 클래스는 사물의 단어들을 모아놓은 클래스 입니다


    //2019-06-27

    public static ArrayList<String> keyword_objects =new ArrayList<>();

    public objects(){
        keyword_objects.add("의자");
        keyword_objects.add("핸드폰");
        keyword_objects.add("민들레");
        keyword_objects.add("붓");
        keyword_objects.add("시계");
        keyword_objects.add("액자");
        keyword_objects.add("노트북");
        keyword_objects.add("군인");
        keyword_objects.add("의사");
        keyword_objects.add("코딩");
        keyword_objects.add("지하철");
        keyword_objects.add("공원 의자");
        keyword_objects.add("만화책");
        keyword_objects.add("책가방");
        keyword_objects.add("토끼");
        keyword_objects.add("학생");
        keyword_objects.add("지각");
        keyword_objects.add("게임");
        keyword_objects.add("로봇");
        keyword_objects.add("여우");
        keyword_objects.add("악마");
        keyword_objects.add("수영복");
        keyword_objects.add("우산");
        keyword_objects.add("헤드셋");
        keyword_objects.add("불");
        keyword_objects.add("농구공");
        keyword_objects.add("커피");
        keyword_objects.add("후드티");
        keyword_objects.add("눈물");
        keyword_objects.add("토");
        keyword_objects.add("만취");
        keyword_objects.add("보드카");
        keyword_objects.add("총");
        keyword_objects.add("피");
        keyword_objects.add("따듯함");
        keyword_objects.add("눈");
        keyword_objects.add("도깨비");
        keyword_objects.add("하늘");
    }

    public ArrayList<String> GetKeyword()
    {
        ArrayList<String> keywords=new ArrayList<>();

        Random random=new Random();
        for(int i=0;i<3;) {
        int n=random.nextInt(keyword_objects.size());

        if(!keywords.contains(keyword_objects.get(n)))
        {
            keywords.add(keyword_objects.get(n));
            i++;
        }
       }

        return keywords;
    }
}

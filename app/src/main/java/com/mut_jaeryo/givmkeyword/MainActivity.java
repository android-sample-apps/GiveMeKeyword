package com.mut_jaeryo.givmkeyword;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.mut_jaeryo.givmkeyword.keyword.objects;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        objects Keyword=new objects();
        Log.d("keyword", Arrays.toString(Keyword.GetKeyword().toArray()));
    }
}

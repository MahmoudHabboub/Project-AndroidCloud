package com.example.coronavirusproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    List<Fragment> fragmentList;
    List<String> fragmentListTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        fragmentList=new ArrayList<>();
        fragmentListTitle=new ArrayList<>();

        fragmentListTitle.add("إحصائيات");
        fragmentListTitle.add("عن الفيروس");
        fragmentListTitle.add("الوقاية");
        fragmentListTitle.add("أسئلة شائعة");
        fragmentListTitle.add("دردشة عامة");

        GeneralFragment generalFragment=new GeneralFragment();
        fragmentList.add(generalFragment);

        InformationFragment informationFragment=new InformationFragment();
        fragmentList.add(informationFragment);

        ProtectionFragment protectionFragment=new ProtectionFragment();
        fragmentList.add(protectionFragment);

        QuestionsFragment questionsFragment =new QuestionsFragment();
        fragmentList.add(questionsFragment);


        ListUsersFragment listUsersFragment=new ListUsersFragment();
        fragmentList.add(listUsersFragment);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),0,fragmentList,fragmentListTitle);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_data_usage_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_info_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_security_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_question_answer_black_24dp);
        tabLayout.getTabAt(4).setIcon(R.drawable.ic_chat_black_24dp);

    }
}

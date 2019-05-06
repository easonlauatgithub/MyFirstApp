package com.easontesting.myfirstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleAdapterListViewActivity extends AppCompatActivity {
    int p1 = 11;
    int[] p2 = new int[]{R.drawable.lv_michael_jordan,R.drawable.lv_russel_westbrook,R.drawable.lv_jeremy_lin,
            R.drawable.lv_yao_ming,R.drawable.lv_irving,R.drawable.lv_dwight_howard,R.drawable.lv_kelvin_durant,
            R.drawable.lv_kobe_byrant, R.drawable.lv_leborn_james,R.drawable.lv_paul_george,R.drawable.lv_stephen_curry};
    String[] p3 = new String[]{"MJ","WestBrook","Linsanity","YM","Irving","DHoward","KD","Kobe","LBJ","PG","SteCurry"};
    String[] p4 = new String[]{"Michael Jeffrey Jordan (born February 17, 1963), also known by his initials, MJ, is an American former professional basketball player. He played 15 seasons in the National Basketball Association (NBA) for the Chicago Bulls and Washington Wizards.",
            "Russell Westbrook III (born November 12, 1988) is an American professional basketball player for the Oklahoma City Thunder of the National Basketball Association (NBA).",
            "Jeremy Shu-How Lin (born August 23, 1988)[1][2] is an American professional basketball player for the Brooklyn Nets of the National Basketball Association (NBA). He unexpectedly led a winning turnaround with the New York Knicks in 2012, which generated a global craze known as 'Linsanity'",
            "Yao Ming (Chinese: 姚明; born September 12, 1980) is a Chinese retired professional basketball player who played for the Shanghai Sharks of the Chinese Basketball Association (CBA) and the Houston Rockets of the National Basketball Association (NBA). ",
            "Kyrie Andrew Irving (born March 23, 1992) is an American professional basketball player for the Boston Celtics of the National Basketball Association (NBA). He was named NBA Rookie of the Year after being selected by the Cleveland Cavaliers with the first overall pick in the 2011 NBA draft.",
            "Dwight David Howard (born December 8, 1985)[1] is an American professional basketball player for the Charlotte Hornets of the National Basketball Association (NBA). ",
            "Kevin Wayne Durant (born September 29, 1988) is an American professional basketball player for the Golden State Warriors of the National Basketball Association (NBA).",
            "Kobe Bean Bryant (born August 23, 1978) is an American former professional basketball player. He played his entire 20-year career with the Los Angeles Lakers of the National Basketball Association (NBA).",
            "LeBron Raymone James Sr. (/ləˈbrɒn/; born December 30, 1984) is an American professional basketball player for the Cleveland Cavaliers of the National Basketball Association (NBA).",
            "Paul Cliftonantho George[1] (born May 2, 1990) is an American professional basketball player for the Oklahoma City Thunder of the National Basketball Association (NBA).",
            "Wardell Stephen Curry II (/ˈstɛfən/ STEF-ən; born March 14, 1988) is an American professional basketball player for the Golden State Warriors of the National Basketball Association (NBA)."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_adapter_list_view);

        List<Map<String, Object>> listOfMapItems = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < p2.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", p2[i]);
            item.put("text", p3[i]);
            item.put("desc", p4[i]);
            listOfMapItems.add(item);
        }
        String[] from = new String[]{"image","text","desc"};
        int[] to = new int[]{R.id.iv1, R.id.tv1, R.id.tv2};
        SimpleAdapter adapter1 = new SimpleAdapter(this, listOfMapItems, R.layout.base_simple_adapter_list_item, from, to);
        ListView mListView = (ListView) findViewById(R.id.list);
        mListView.setAdapter(adapter1);

    }
}

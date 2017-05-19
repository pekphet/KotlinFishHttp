package cn.fish.ukt.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import cn.fish.ukt.R;
import cn.fish.ukt.entity.Person;

/**
 * Created by fish on 17-5-19.
 */

public class ContractActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.tv);
        Person personData = (Person) getIntent().getSerializableExtra(MainActivity.Companion.getKEY_PERSON());
        String content = "name:" + personData.getName() + "age:" + personData.getAge();
        tv.setText(content);
    }
}

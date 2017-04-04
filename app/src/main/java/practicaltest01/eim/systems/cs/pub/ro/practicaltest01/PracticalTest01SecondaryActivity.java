package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Adelina on 4/4/2017.
 */

public class PracticalTest01SecondaryActivity extends AppCompatActivity {

    ButtonHandler btH = new ButtonHandler();


    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.okButton:
                    setResult(RESULT_OK, null);
                    break;
                case R.id.cancelButton:
                    setResult(RESULT_CANCELED, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);

        TextView text = (TextView) findViewById(R.id.showNrOfClicks);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("numberOfClicks")) {
            int numberOfClicks = intent.getIntExtra("numberOfClicks", -1);
            text.setText(String.valueOf(numberOfClicks));
        }


        Button okButton = (Button) findViewById(R.id.okButton);
        Button cancelButton = (Button) findViewById(R.id.cancelButton);

        okButton.setOnClickListener(btH);
        cancelButton.setOnClickListener(btH);
    }
}

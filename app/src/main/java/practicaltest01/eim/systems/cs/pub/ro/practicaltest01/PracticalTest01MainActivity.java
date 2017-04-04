package practicaltest01.eim.systems.cs.pub.ro.practicaltest01;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {

    ButtonHandler buttonHandler = new ButtonHandler();
    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;
    int count = 0;
    private class ButtonHandler implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button1:
                    EditText left_edit = (EditText) findViewById(R.id.left_edit_text);
                    int currentNr_left = Integer.parseInt(left_edit.getText().toString());
                    left_edit.setText(String.valueOf(currentNr_left+1));
                    count++;
                    break;
                case R.id.button2:
                    EditText right_edit = (EditText) findViewById(R.id.right_edit_text);
                    int currentNr_Right = Integer.parseInt(right_edit.getText().toString());
                    right_edit.setText(String.valueOf(currentNr_Right+1));
                    count++;
                    break;
                case R.id.goTo:
                    Intent goToOtherPage = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    goToOtherPage.putExtra("numberOfClicks", count);
                    startActivityForResult(goToOtherPage, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;
            }

            if (count > Constants.NUMBER_OF_CLICKS_THRESHOLD) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", ((EditText) findViewById(R.id.left_edit_text)).getText().toString());
                intent.putExtra("secondNumber", ((EditText) findViewById(R.id.right_edit_text)).getText().toString());
                getApplicationContext().startService(intent);

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        Button button1 = (Button) findViewById(R.id.button1);
        Button button2 = (Button) findViewById(R.id.button2);
        Button goToButton = (Button) findViewById(R.id.goTo);

        button1.setOnClickListener(buttonHandler);
        button2.setOnClickListener(buttonHandler);
        goToButton.setOnClickListener(buttonHandler);

        EditText left_edit = (EditText) findViewById(R.id.left_edit_text);
        EditText right_edit = (EditText) findViewById(R.id.right_edit_text);

        left_edit.setText(String.valueOf(0));
        right_edit.setText(String.valueOf(0));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_practical_test01_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle onSavedInstance){

        EditText left_edit = (EditText) findViewById(R.id.left_edit_text);
        EditText right_edit = (EditText) findViewById(R.id.right_edit_text);

        onSavedInstance.putString("left_edit", left_edit.getText().toString());
        onSavedInstance.putString("right_edit", right_edit.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle onSavedInstance){

        EditText left_edit = (EditText) findViewById(R.id.left_edit_text);
        EditText right_edit = (EditText) findViewById(R.id.right_edit_text);

        if(onSavedInstance.containsKey("left_edit")) {
            String left_edit_text = onSavedInstance.getString("left_edit");
            left_edit.setText(left_edit_text);
        }
        else{
            left_edit.setText("0");
        }
        if(onSavedInstance.containsKey("right_edit")) {
            String right_edit_text = onSavedInstance.getString("right_edit");
            right_edit.setText(right_edit_text);
        }
        else{
            right_edit.setText("0");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }
}

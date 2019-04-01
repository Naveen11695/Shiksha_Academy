package development.assistant.project.s_assistant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class make_schedule extends AppCompatActivity {
    int hour;
    String mins;
    Spinner classSelect,daySelect;
    ArrayAdapter adapterSpinner, days;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_create);

        classSelect = (Spinner)findViewById(R.id.classSelector);
        daySelect = (Spinner)findViewById(R.id.daySelector);

        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, AppBase.divisions);
        assert classSelect != null;
        classSelect.setAdapter(adapterSpinner);

        ArrayList<String> weekdays = new ArrayList<>();
        weekdays.add("MONDAY");
        weekdays.add("TUESDAY");
        weekdays.add("WEDNESDAY");
        weekdays.add("THURSDAY");
        weekdays.add("FRIDAY");
        weekdays.add("SATURDAY");
        weekdays.add("SUNDAY");
        days = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, weekdays);
        assert classSelect != null;
        daySelect.setAdapter(days);

        Button btn = (Button)findViewById(R.id.saveBUTTON_SCHEDULE);
        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSchedule(v);
            }
        });

    }


    private void saveSchedule(View v) {
        String daySelected = daySelect.getSelectedItem().toString();
        String classSelected = classSelect.getSelectedItem().toString();
        EditText editText = (EditText)findViewById(R.id.teachername);
        EditText editText2 = (EditText)findViewById(R.id.teachername);
        String subject = editText.getText().toString();
        String Teacher= editText2.getText().toString();
        if(subject.length()<2)
        {
            Toast.makeText(getBaseContext(),"Enter Valid Subject Name",Toast.LENGTH_SHORT).show();
            return;
        }
        TimePicker timePicker = (TimePicker)findViewById(R.id.timePicker);


        int h = timePicker.getCurrentHour();
        if(h==0)
        {
            h=12;
        }
        int min = timePicker.getCurrentMinute();
        int min_temp=min;
        String AM_PM;
        if(h < 12) {
            AM_PM = "AM";
        } else {
            AM_PM = "PM";
        }
        if(min==0)
        {
             mins=new Integer(min).toString();
             mins=mins+"0";
        }
        else if(min==1)
        {
            mins=new Integer(min).toString();
            mins="01";
        }
        else if(min==2)
        {
            mins=new Integer(min).toString();
            mins="02";
        }else if(min==3)
        {
            mins=new Integer(min).toString();
            mins="03";
        }else if(min==4)
        {
            mins=new Integer(min).toString();
            mins="04";
        }else if(min==5) {
            mins = new Integer(min).toString();
            mins = "05";
        }else if(min==6)
        {
            mins=new Integer(min).toString();
            mins="06";
        }else if(min==1)
        {
            mins=new Integer(min).toString();
            mins="07";
        }else if(min==8)
        {
            mins=new Integer(min).toString();
            mins="08";
        }else if(min==9)
        {
            mins=new Integer(min).toString();
            mins="09";
        }
        else
        {
            mins=new Integer(min_temp).toString();
        }
        //firebase data transfer
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReferenceFromUrl("https://college-portal-e71bb.firebaseio.com/");
        databaseReference.push().setValue(Teacher+" sir, has a class of "+subject+" in "+classSelected+" at "+h+":"+mins+AM_PM+" on "+daySelected);
        //firebase entries finish..

        String sql = "INSERT INTO SCHEDULE VALUES('" + classSelected +"'," +
                "'" + subject + "'," +
                "'" + Teacher + "'," +
                "'" + hour +":"+min +" pm " + " " + daySelected + "');";
        Log.d("Schedule", sql);
        if(AppBase.handler.execAction(sql))
        {
            Toast.makeText(getBaseContext(),"Scheduling Done", Toast.LENGTH_LONG).show();
            this.finish();
        }else
            Toast.makeText(getBaseContext(),"Failed To Schedule", Toast.LENGTH_LONG).show();

    }
}

package it.uniba.di.sss1415.schedastudenti_menga_606362;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;


public class Data_view extends Activity {

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTHDAY = "birthday";
    private static final String SEX = "sex";
    private static final String EXAM = "exam";
    private static final String RATING = "rating";
    private static final String PHONES = "phones";

    private AutoCompleteTextView textView;
    private Button addStudentButton;
    private TextView data;
    private TextView name;
    private TextView exam;
    private TextView rating;
    private TextView number;
    private TextView years;


    private String[] students;
    private Resources res;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_view);
        res = getResources();
        students = getStudents();

        findViewsById();

        addStudentButton.setOnClickListener(addStudentButtonListener);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_student,
                students
        );
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                hideKeyboard();
                SharedPreferences savedStudent = getSharedPreferences((String) adapterView.getAdapter().getItem(i), Context.MODE_PRIVATE);
                if(savedStudent.getString(SEX, "").equals("Femmina")) {
                   data.setText(R.string.dataTextViewFemale);
                } else {
                    data.setText(R.string.dataTextViewMale);
                }
                name.setText(savedStudent.getString(NAME, "") + " " + savedStudent.getString(SURNAME, ""));
                String quantityString = res.getQuantityString(R.plurals.years,Integer.parseInt(calcYear(savedStudent.getString(BIRTHDAY, ""))),Integer.parseInt(calcYear(savedStudent.getString(BIRTHDAY, ""))));
                years.setText(quantityString);
                quantityString = res.getQuantityString(R.plurals.exam, Integer.parseInt(savedStudent.getString(EXAM, "")), Integer.parseInt(savedStudent.getString(EXAM,"")));
                exam.setText(quantityString);
                rating.setText("Valutazione: " + savedStudent.getString(RATING, ""));
                if(savedStudent.getStringSet(PHONES, null).toArray(new String[0]).length == 1) {
                    quantityString = res.getQuantityString(R.plurals.number,1,1);
                    number.setText(quantityString);
                } else {
                    quantityString = res.getQuantityString(R.plurals.number,5,5);
                    number.setText(quantityString);
                }
                ArrayAdapter<String> numbers = new ArrayAdapter<String>(
                        getApplicationContext(),
                        R.layout.list_view,
                        savedStudent.getStringSet(PHONES, null).toArray(new String[0]));
                listView.setAdapter(numbers);
            }
        });
    }

    public View.OnClickListener addStudentButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent add = new Intent(Data_view.this, AddStudent.class);
            startActivity(add);
        }
    };

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void findViewsById() {

        years = (TextView) findViewById(R.id.yearsTextView);
        addStudentButton = (Button) findViewById(R.id.addStudentButton);
        textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        data = (TextView) findViewById(R.id.dataTextView);
        name = (TextView) findViewById(R.id.nameTextView);
        exam = (TextView) findViewById(R.id.examTextView);
        rating = (TextView) findViewById(R.id.ratingTextView);
        number = (TextView) findViewById(R.id.numberTextView);
        listView = (ListView) findViewById(R.id.listView);

    }

    private String calcYear(String date) {
        Calendar dob = Calendar.getInstance();
        Date Birth = null;
        try {
            Birth = new SimpleDateFormat("dd/MM/yyy").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dob.setTime(Birth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if(today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH) && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)){
            age--;
        }
        return String.valueOf(age);
    }


    @Override
    protected void onResume() {
        super.onResume();
        students = getStudents();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_student,
                students
        );
        textView.setAdapter(adapter);
    }

    private String[] getStudents() {
        SharedPreferences savedSurnames = getSharedPreferences("surnames", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = savedSurnames.getAll();
        String[] students = new String[allEntries.size()];
        int i = 0;
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            students[i] =(String) entry.getValue();
            i++;
        }
        return students;
    }
}

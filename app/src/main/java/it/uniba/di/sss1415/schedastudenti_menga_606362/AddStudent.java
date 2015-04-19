package it.uniba.di.sss1415.schedastudenti_menga_606362;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


public class AddStudent extends Activity {

    private SharedPreferences savedStudent;
    private SharedPreferences savedSurnames;

    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String BIRTHDAY = "birthday";
    private static final String SEX = "sex";
    private static final String EXAM = "exam";
    private static final String RATING = "rating";
    private static final String PHONES = "phones";

    private EditText name;
    private EditText surname;
    private TextView birthday;
    private ToggleButton sex;
    private EditText exam;
    private RatingBar rating;
    private EditText number;
    private Button addNumber;
    private TextView phone1;
    private TextView phone2;
    private TextView phone3;
    private Button removeNumber1;
    private Button removeNumber2;
    private Button removeNumber3;
    private Button okButton;
    private Button cancelButton;

    private DatePickerDialog date;
    private SimpleDateFormat dateFormatter;
    private Calendar newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        findViewsById();

        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        birthday.setText(dateFormatter.format(newCalendar.getTime()));
        date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year,monthOfYear,dayOfMonth);
                birthday.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                date.show();
            }
        });

        addNumber.setOnClickListener(addNumberButtonListener);
        removeNumber1.setOnClickListener(removeNumber1Listener);
        removeNumber2.setOnClickListener(removeNumber2Listener);
        removeNumber3.setOnClickListener(removeNumber3Listener);
        okButton.setOnClickListener(okButtonListener);
        cancelButton.setOnClickListener(cancelButtonListener);


    }

    private void findViewsById() {

        name = (EditText) findViewById(R.id.nameEditText);
        surname = (EditText) findViewById(R.id.surnameEditText);
        birthday = (TextView) findViewById(R.id.dateTextView);
        sex = (ToggleButton) findViewById(R.id.toggleButton);
        exam = (EditText) findViewById(R.id.examEditText);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        number = (EditText) findViewById(R.id.numberEditText);
        addNumber = (Button) findViewById(R.id.addNumberButton);
        phone1 = (TextView) findViewById(R.id.numberTextView1);
        phone2 = (TextView) findViewById(R.id.numberTextView2);
        phone3 = (TextView) findViewById(R.id.numberTextView3);
        removeNumber1 = (Button) findViewById(R.id.removeNumberButton1);
        removeNumber2 = (Button) findViewById(R.id.removeNumberButton2);
        removeNumber3 = (Button) findViewById(R.id.removeNumberButton3);
        okButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

    }

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public View.OnClickListener okButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            savedStudent = getSharedPreferences(surname.getText().toString(), Context.MODE_PRIVATE);
            savedSurnames = getSharedPreferences("surnames", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = savedStudent.edit();
            SharedPreferences.Editor editor2 = savedSurnames.edit();
            if (!name.getText().equals("") && !surname.getText().equals("") && !birthday.getText().equals("") && !exam.getText().equals("") && !phone1.getText().equals("")) {
                editor2.putString(surname.getText().toString(), surname.getText().toString());
                editor.putString(NAME, name.getText().toString());
                editor.putString(SURNAME, surname.getText().toString());
                editor.putString(BIRTHDAY, birthday.getText().toString());
                editor.putString(SEX, sex.getText().toString());
                editor.putString(EXAM, exam.getText().toString());
                editor.putString(RATING, "" +rating.getNumStars());
                Set<String> p = new HashSet<String>();
                p.add(phone1.getText().toString());
                if (!phone2.getText().equals("")) {
                    p.add(phone2.getText().toString());
                }
                if (!phone3.getText().equals("")) {
                    p.add(phone3.getText().toString());
                }
                editor.putStringSet(PHONES, p);
                editor2.commit();
                editor.commit();
                finish();
            } else {
                Toast.makeText(AddStudent.this, R.string.emptyField, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public View.OnClickListener cancelButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    public View.OnClickListener addNumberButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(number.getText().length() == 0) {
                Toast.makeText(AddStudent.this, R.string.emptyNumberEditText,Toast.LENGTH_SHORT).show();
            } else {
                if(phone1.getText().length() == 0) {
                    phone1.setText(number.getText());
                    phone1.setVisibility(View.VISIBLE);
                    removeNumber1.setVisibility(View.VISIBLE);
                    number.setText("");
                } else if(phone2.getText().length() == 0) {
                    phone2.setText(number.getText());
                    phone2.setVisibility(View.VISIBLE);
                    removeNumber2.setVisibility(View.VISIBLE);
                    number.setText("");
                } else if(phone3.getText().length() == 0) {
                    phone3.setText(number.getText());
                    phone3.setVisibility(View.VISIBLE);
                    removeNumber3.setVisibility(View.VISIBLE);
                    number.setText("");
                }
            }
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    };

    public View.OnClickListener removeNumber3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            phone3.setText("");
            phone3.setVisibility(View.INVISIBLE);
            removeNumber3.setVisibility(View.INVISIBLE);
        }
    };

    public View.OnClickListener removeNumber2Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(phone3.getText().length() == 0) {
                phone2.setText("");
                phone2.setVisibility(View.INVISIBLE);
                removeNumber2.setVisibility(View.INVISIBLE);
            } else {
                phone2.setText(phone3.getText());
                phone3.setText("");
                phone3.setVisibility(View.INVISIBLE);
                removeNumber3.setVisibility(View.INVISIBLE);
            }
        }
    };

    public View.OnClickListener removeNumber1Listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(phone2.getText().length() == 0) {
                if(phone3.getText().length() == 0) {
                    phone1.setText("");
                    removeNumber1.setVisibility(View.INVISIBLE);
                    phone1.setVisibility(View.INVISIBLE);
                }
            } else {
                if(phone3.getText().length() == 0) {
                    phone1.setText(phone2.getText());
                    phone2.setText("");
                    phone2.setVisibility(View.INVISIBLE);
                    removeNumber2.setVisibility(View.INVISIBLE);
                } else {
                    phone1.setText(phone2.getText());
                    phone2.setText(phone3.getText());
                    phone3.setText("");
                    phone3.setVisibility(View.INVISIBLE);
                    removeNumber3.setVisibility(View.INVISIBLE);
                }
            }
        }
    };
}

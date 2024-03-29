package com.maciejlesniak;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    private Button mRegister;
    private EditText mEmail, mPassword, mName;

    private RadioGroup mRadioGroup;

    private DatePicker mDatePicker;
    /*      COMMENTARY    */
    /* DECLARATION OF INSTANCE FirebaseAuth */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    private LocalDate birthDate, today;
    private int month, age;
    private Period period;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        /* INICIALIZATION OF INSTANCE FirebaseAuth */
        mAuth = FirebaseAuth.getInstance();

        /* INSTANCE STATE LISTENER OF FirebaseAuth */
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                /* CHECKING IF USER IS ACTUALLY LOGGED IN, IF HE/SHE IS GO TO MAINACTIVITY */
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mRegister = (Button) findViewById(R.id.register);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mName = (EditText) findViewById(R.id.name);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mDatePicker = (DatePicker) findViewById(R.id.datePicker1);

        /*  */
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                month = mDatePicker.getMonth()+1;
                birthDate = new LocalDate(mDatePicker.getYear(), month, mDatePicker.getDayOfMonth());
                today = new LocalDate();
                period = new Period(birthDate, today, PeriodType.yearMonthDay());
                age = period.getYears();

                int selectId = mRadioGroup.getCheckedRadioButtonId();
                final RadioButton radioButton = (RadioButton) findViewById(selectId);


                if (radioButton.getText() == null) {
                    return;
                }

                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();
                final String name = mName.getText().toString();
                if (radioButton.getText().toString().equals("Adoptujący") && age < 18) {
                    Toast.makeText(RegistrationActivity.this, "Musisz mieć ukończone 18 lat", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegistrationActivity.this, "Rejestracja nie powiodła się", Toast.LENGTH_SHORT).show();
                            } else {
                                String userId = mAuth.getCurrentUser().getUid();
                                //reference to database
                                DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                                //ADDING DEFAULT IMAGE TO DATABASE
                                Map userInfo = new HashMap<>();
                                userInfo.put("nazwa", name);
                                userInfo.put("rodzaj użytkownika", radioButton.getText().toString());
                                userInfo.put("profileImageUrl", "default");
                                userInfo.put("Data Urodzenia", birthDate.toString());
                                userInfo.put("Wiek", age);
                                currentUserDb.updateChildren(userInfo);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }

}

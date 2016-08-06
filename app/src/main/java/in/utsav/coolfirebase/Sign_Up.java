package in.utsav.coolfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {

    EditText email;
    EditText pass;
    EditText confPass;
    Button signUp;
    TextView AlreadyHaveanAccount;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        getAllIds();
        checkSignUp();
    }

    private void checkSignUp() {

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailText = email.getText().toString().toLowerCase().trim();
                String passwdText = pass.getText().toString();
                String confirmPasswd = confPass.getText().toString();

                if (TextUtils.isEmpty(emailText)) {
                    MainActivity.showToast(Sign_Up.this, "Enter your Email Address");
                    return;
                } else if (!emailText.contains("@")) {
                    MainActivity.showToast(Sign_Up.this, "Enter Valid Email Address");
                    return;
                } else if (TextUtils.isEmpty(passwdText)) {
                    MainActivity.showToast(Sign_Up.this, "Enter Your Password");
                    return;
                } else if (TextUtils.isEmpty(confirmPasswd)) {
                    MainActivity.showToast(Sign_Up.this, "Enter Your Confirm Password");
                    return;
                } else if (passwdText.length()<4) {
                    MainActivity.showToast(Sign_Up.this, "Password Should Be Atleast 4 character");
                    return;
                } else if (!passwdText.equals(confirmPasswd)) {
                    MainActivity.showToast(Sign_Up.this, "Both Password Should Match");
                    return;

                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(emailText,passwdText).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        MainActivity.showToast(Sign_Up.this, "User Sucessfully Created");
                        Intent intent = new Intent(Sign_Up.this, MainActivity.class);
                        startActivity(intent);
                        if (!task.isSuccessful()) {
                            MainActivity.showToast(Sign_Up.this, "Something Error Occured");
                        }
                    }
                });
            }
        });

    }

    private void getAllIds() {
        firebaseAuth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.username);
        pass = (EditText) findViewById(R.id.password);
        confPass = (EditText) findViewById(R.id.confPassword);
        signUp = (Button) findViewById(R.id.signUpBtn);
        AlreadyHaveanAccount = (TextView) findViewById(R.id.loginTextinSignUp);
        progressBar = (ProgressBar) findViewById(R.id.signUpProgressBar);
    }
}

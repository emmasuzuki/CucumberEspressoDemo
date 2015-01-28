package com.emmasuzuki.cucumberespressodemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Demo Login activity class
 */
public class LoginActivity extends Activity {

    private static final String DEMO_EMAIL = "espresso@spoon.com";
    private static final String DEMO_PASSWORD = "lemoncake";

    private EditText mEmail, mPassword;
    private View mError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        View submitButton = findViewById(R.id.submit);
        submitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                validateFields();

                if (mEmail.getError() == null && mPassword.getError() == null) {
                    validateAccount();
                }
            }
        });
    }

    private void validateFields() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail.getText().toString()).matches()) {
            mEmail.setError(getString(R.string.msg_email_error));
        } else {
            mEmail.setError(null);
        }

        if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError(getString(R.string.msg_password_error));
        } else {
            mPassword.setError(null);
        }
    }

    private void validateAccount() {
        if (mError == null) {
            mError = findViewById(R.id.error);
        }

        if (!mEmail.getText().toString().equals(DEMO_EMAIL) || !mPassword.getText().toString().equals(DEMO_PASSWORD)) {
            mError.setVisibility(View.VISIBLE);
        } else {
            mError.setVisibility(View.GONE);
        }
    }
}

/*
 * Copyright (C) 2015 emmasuzuki <emma11suzuki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 * THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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

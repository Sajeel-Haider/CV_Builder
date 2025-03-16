package com.example.cv_builder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalDetailsActivity extends AppCompatActivity {
    private EditText etName, etEmail, etPhone;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.personalDetailsActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        loadSavedData();
    }

    void init() {
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Trim input values
                String name = etName.getText().toString().trim();
                String email = etEmail.getText().toString().trim();
                String phone = etPhone.getText().toString().trim();

                // Validate Name
                if (name.length() < 2) {
                    etName.setError("Name must be at least 2 characters");
                    return;
                }

                // Validate Email: must contain '@' and end with '.com'
                if (!isValidEmail(email)) {
                    etEmail.setError("Invalid email address");
                    return;
                }

                // Validate Phone: at least 7 digits, only digits (ignoring a possible '+' at start)
                if (!isValidPhone(phone)) {
                    etPhone.setError("Invalid phone number");
                    return;
                }

                // Save the entered personal details to CVData if validations pass
                CVData.userName = name;
                CVData.userEmail = email;
                CVData.userPhone = phone;
                CVData.personalDetails = "Name: " + name +
                        "\nEmail: " + email +
                        "\nPhone: " + phone;
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Discard any changes by clearing the saved personal details data
                CVData.userName = "";
                CVData.userEmail = "";
                CVData.userPhone = "";
                CVData.personalDetails = "";
                finish();
            }
        });
    }

    // Load previously saved personal details into the EditTexts
    void loadSavedData() {
        if (CVData.userName != null && !CVData.userName.isEmpty()) {
            etName.setText(CVData.userName);
        }
        if (CVData.userEmail != null && !CVData.userEmail.isEmpty()) {
            etEmail.setText(CVData.userEmail);
        }
        if (CVData.userPhone != null && !CVData.userPhone.isEmpty()) {
            etPhone.setText(CVData.userPhone);
        }
    }

    // Email validation: must contain '@' and end with ".com"
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.endsWith(".com");
    }

    // Phone validation: can start with '+'; must have at least 7 digits and only digits otherwise
    private boolean isValidPhone(String phone) {
        if (phone.isEmpty()) {
            return false;
        }
        String phoneWithoutPlus = phone.startsWith("+") ? phone.substring(1) : phone;
        if (phoneWithoutPlus.length() < 7) {
            return false;
        }
        return phoneWithoutPlus.matches("\\d+");
    }
}

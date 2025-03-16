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

public class CertificationsActivity extends AppCompatActivity {
    private EditText etCertifications;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.certificationsActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        loadSavedData();
    }

    void init(){
        etCertifications = findViewById(R.id.etCertifications);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                CVData.certifications = etCertifications.getText().toString();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Discard changes by clearing saved certifications data
                CVData.certifications = "";
                finish();
            }
        });
    }

    // Load previously saved certifications data into the EditText
    void loadSavedData() {
        if (CVData.certifications != null && !CVData.certifications.isEmpty()) {
            etCertifications.setText(CVData.certifications);
        }
    }
}

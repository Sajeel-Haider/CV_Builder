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

public class EducationActivity extends AppCompatActivity {
    private EditText etInstitution, etDegree, etEducationDetails;
    private Button btnSave, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_education);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.educationActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
        loadSavedData();
    }

    void init(){
        etInstitution = findViewById(R.id.etInstitution);
        etDegree = findViewById(R.id.etDegree);
        etEducationDetails = findViewById(R.id.etEducationDetails);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Build the formatted education string.
                String institution = etInstitution.getText().toString().trim();
                String degree = etDegree.getText().toString().trim();
                String details = etEducationDetails.getText().toString().trim();

                // Here you can optionally add validations if required

                StringBuilder educationBuilder = new StringBuilder();
                if (!institution.isEmpty()) {
                    educationBuilder.append("Institution: ").append(institution).append("\n");
                }
                if (!degree.isEmpty()) {
                    educationBuilder.append("Degree: ").append(degree).append("\n");
                }
                if (!details.isEmpty()) {
                    educationBuilder.append("Details: ").append(details).append("\n");
                }
                CVData.education = educationBuilder.toString();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Discard any changes by clearing the saved education data.
                CVData.education = "";
                finish();
            }
        });
    }

    // Load previously saved education details into the EditTexts
    void loadSavedData() {
        // If you saved data in a structured way, you can split it back.
        // For simplicity, we'll just load the whole education string into etEducationDetails.
        // Alternatively, you could store each field in separate variables in CVData.
        if (CVData.education != null && !CVData.education.isEmpty()) {
            // A simple approach: you might assume that if education was saved, the first two lines are institution and degree.
            String[] lines = CVData.education.split("\n");
            if (lines.length > 0 && lines[0].startsWith("Institution: ")) {
                etInstitution.setText(lines[0].replace("Institution: ", ""));
            }
            if (lines.length > 1 && lines[1].startsWith("Degree: ")) {
                etDegree.setText(lines[1].replace("Degree: ", ""));
            }
            if (lines.length > 2 && lines[2].startsWith("Details: ")) {
                // In case there are additional details, we combine the rest.
                StringBuilder details = new StringBuilder();
                for (int i = 2; i < lines.length; i++) {
                    details.append(lines[i].replace("Details: ", "")).append("\n");
                }
                etEducationDetails.setText(details.toString().trim());
            }
        }
    }
}

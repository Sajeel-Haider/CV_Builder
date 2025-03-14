package com.example.cv_builder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalActivity extends AppCompatActivity {
    private ImageView ivProfilePicture;
    private TextView tvCVPreview;
    private Button btnShare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.finalActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    void init(){
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvCVPreview = findViewById(R.id.tvCVPreview);
        btnShare = findViewById(R.id.btnShare);

        // Load profile picture if available
        if (CVData.profilePictureUri != null) {
            ivProfilePicture.setImageURI(Uri.parse(CVData.profilePictureUri));
        }

        // Build the CV preview text from the saved data
        StringBuilder cvText = new StringBuilder();
        if (CVData.personalDetails != null) {
            cvText.append("Personal Details:\n").append(CVData.personalDetails).append("\n\n");
        }
        if (CVData.summary != null) {
            cvText.append("Summary:\n").append(CVData.summary).append("\n\n");
        }
        if (CVData.education != null) {
            cvText.append("Education:\n").append(CVData.education).append("\n\n");
        }
        if (CVData.experience != null) {
            cvText.append("Experience:\n").append(CVData.experience).append("\n\n");
        }
        if (CVData.certifications != null) {
            cvText.append("Certifications:\n").append(CVData.certifications).append("\n\n");
        }
        if (CVData.references != null) {
            cvText.append("References:\n").append(CVData.references).append("\n\n");
        }

        tvCVPreview.setText(cvText.toString());

        btnShare.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Share the CV text using an implicit intent
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CV");
                shareIntent.putExtra(Intent.EXTRA_TEXT, cvText.toString());
                startActivity(Intent.createChooser(shareIntent, "Share CV using"));
            }
        });
    }
}
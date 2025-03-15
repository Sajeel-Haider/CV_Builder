package com.example.cv_builder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FinalActivity extends AppCompatActivity {

    private ImageView ivProfilePicture;
    private TextView tvName, tvEmail, tvPhone;
    // For each included layout:
    private TextView tvSummaryTitle, tvSummaryContent;
    private TextView tvEducationTitle, tvEducationContent;
    private TextView tvExperienceTitle, tvExperienceContent;
    private TextView tvCertificationsTitle, tvCertificationsContent;
    private TextView tvReferencesTitle, tvReferencesContent;

    private Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        // Profile Section
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        // Summary Section
        View summaryView = findViewById(R.id.includeSummary);
        tvSummaryTitle = summaryView.findViewById(R.id.tvSectionTitle);
        tvSummaryContent = summaryView.findViewById(R.id.tvSectionContent);

        // Education Section
        View educationView = findViewById(R.id.includeEducation);
        tvEducationTitle = educationView.findViewById(R.id.tvSectionTitle);
        tvEducationContent = educationView.findViewById(R.id.tvSectionContent);

        // Experience Section
        View experienceView = findViewById(R.id.includeExperience);
        tvExperienceTitle = experienceView.findViewById(R.id.tvSectionTitle);
        tvExperienceContent = experienceView.findViewById(R.id.tvSectionContent);

        // Certifications Section
        View certsView = findViewById(R.id.includeCertifications);
        tvCertificationsTitle = certsView.findViewById(R.id.tvSectionTitle);
        tvCertificationsContent = certsView.findViewById(R.id.tvSectionContent);

        // References Section
        View refsView = findViewById(R.id.includeReferences);
        tvReferencesTitle = refsView.findViewById(R.id.tvSectionTitle);
        tvReferencesContent = refsView.findViewById(R.id.tvSectionContent);

        // Share Button
        btnShare = findViewById(R.id.btnShare);

        // Load data from CVData (or wherever you store user input)
        populateUI();
    }

    private void populateUI() {
        // Profile Picture
        if (CVData.profilePictureUri != null && !CVData.profilePictureUri.isEmpty()) {
            ivProfilePicture.setImageURI(Uri.parse(CVData.profilePictureUri));
        }

        // Name, Email, Phone
        tvName.setText(CVData.userName);
        tvEmail.setText(CVData.userEmail);
        tvPhone.setText(CVData.userPhone);

        // SUMMARY
        tvSummaryTitle.setText("Summary");
        tvSummaryContent.setText(CVData.summary);

        // EDUCATION
        tvEducationTitle.setText("Education");
        tvEducationContent.setText(CVData.education);

        // EXPERIENCE
        tvExperienceTitle.setText("Work Experience");
        tvExperienceContent.setText(CVData.experience);

        // CERTIFICATIONS
        tvCertificationsTitle.setText("Certifications");
        tvCertificationsContent.setText(CVData.certifications);

        // REFERENCES
        tvReferencesTitle.setText("References");
        tvReferencesContent.setText(CVData.references);

        // Setup share button
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cvText = buildCVText();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My CV");
                shareIntent.putExtra(Intent.EXTRA_TEXT, cvText);
                startActivity(Intent.createChooser(shareIntent, "Share CV via"));
            }
        });
    }

    private String buildCVText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(CVData.userName).append("\n")
                .append("Email: ").append(CVData.userEmail).append("\n")
                .append("Phone: ").append(CVData.userPhone).append("\n\n")
                .append("Summary:\n").append(CVData.summary).append("\n\n")
                .append("Education:\n").append(CVData.education).append("\n\n")
                .append("Experience:\n").append(CVData.experience).append("\n\n")
                .append("Certifications:\n").append(CVData.certifications).append("\n\n")
                .append("References:\n").append(CVData.references).append("\n\n");
        return sb.toString();
    }
}

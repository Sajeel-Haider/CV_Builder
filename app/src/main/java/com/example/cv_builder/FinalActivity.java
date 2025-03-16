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

    // Profile Section Views
    private ImageView ivProfilePicture;
    private TextView tvName, tvEmail, tvPhone;

    // Included card Views (retrieved from include layouts)
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

        // Bind profile section views
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);

        // Bind the include layouts and retrieve their inner TextViews
        View summaryInclude = findViewById(R.id.includeSummary);
        tvSummaryTitle = summaryInclude.findViewById(R.id.tvSectionTitle);
        tvSummaryContent = summaryInclude.findViewById(R.id.tvSectionContent);

        View educationInclude = findViewById(R.id.includeEducation);
        tvEducationTitle = educationInclude.findViewById(R.id.tvSectionTitle);
        tvEducationContent = educationInclude.findViewById(R.id.tvSectionContent);

        View experienceInclude = findViewById(R.id.includeExperience);
        tvExperienceTitle = experienceInclude.findViewById(R.id.tvSectionTitle);
        tvExperienceContent = experienceInclude.findViewById(R.id.tvSectionContent);

        View certificationsInclude = findViewById(R.id.includeCertifications);
        tvCertificationsTitle = certificationsInclude.findViewById(R.id.tvSectionTitle);
        tvCertificationsContent = certificationsInclude.findViewById(R.id.tvSectionContent);

        View referencesInclude = findViewById(R.id.includeReferences);
        tvReferencesTitle = referencesInclude.findViewById(R.id.tvSectionTitle);
        tvReferencesContent = referencesInclude.findViewById(R.id.tvSectionContent);

        btnShare = findViewById(R.id.btnShare);

        populateUI();
    }

    private void populateUI() {
        // Set profile picture if provided
        if (CVData.profilePicturePath != null && !CVData.profilePicturePath.isEmpty()) {
            ivProfilePicture.setImageURI(Uri.parse(CVData.profilePicturePath));
        } else {
            // Optionally set a default image if desired:
            // ivProfilePicture.setImageResource(R.drawable.default_profile);
        }

        // Set profile information with default text if empty or null
        tvName.setText(getSafeString(CVData.userName, "Name not provided"));
        tvEmail.setText(getSafeString(CVData.userEmail, "Email not provided"));
        tvPhone.setText(getSafeString(CVData.userPhone, "Phone not provided"));

        // Set the headings and content for each section using CVData
        tvSummaryTitle.setText("Summary");
        tvSummaryContent.setText(getSafeString(CVData.summary, "No summary provided"));

        tvEducationTitle.setText("Education");
        tvEducationContent.setText(getSafeString(CVData.education, "No education details provided"));

        tvExperienceTitle.setText("Experience");
        tvExperienceContent.setText(getSafeString(CVData.experience, "No experience details provided"));

        tvCertificationsTitle.setText("Certifications");
        tvCertificationsContent.setText(getSafeString(CVData.certifications, "No certifications provided"));

        tvReferencesTitle.setText("References");
        tvReferencesContent.setText(getSafeString(CVData.references, "No references provided"));

        // Setup share button to share a formatted CV text
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
        sb.append("Name: ").append(getSafeString(CVData.userName, "Name not provided")).append("\n")
                .append("Email: ").append(getSafeString(CVData.userEmail, "Email not provided")).append("\n")
                .append("Phone: ").append(getSafeString(CVData.userPhone, "Phone not provided")).append("\n\n")
                .append("Summary:\n").append(getSafeString(CVData.summary, "No summary provided")).append("\n\n")
                .append("Education:\n").append(getSafeString(CVData.education, "No education details provided")).append("\n\n")
                .append("Experience:\n").append(getSafeString(CVData.experience, "No experience details provided")).append("\n\n")
                .append("Certifications:\n").append(getSafeString(CVData.certifications, "No certifications provided")).append("\n\n")
                .append("References:\n").append(getSafeString(CVData.references, "No references provided")).append("\n\n");
        return sb.toString();
    }

    // Utility method to safely retrieve a string or a default value if it's null or empty
    private String getSafeString(String input, String defaultValue) {
        if (input == null || input.isEmpty()) {
            return defaultValue;
        }
        return input;
    }
}

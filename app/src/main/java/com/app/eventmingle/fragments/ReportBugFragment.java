package com.app.eventmingle.fragments;

import static android.widget.Toast.makeText;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.eventmingle.R;

public class ReportBugFragment extends Fragment {

    public ReportBugFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_bug, container, false);

        EditText etBug = view.findViewById(R.id.etBugDescription);
        Button btnSubmit = view.findViewById(R.id.btnSubmitBug);

        btnSubmit.setOnClickListener(v -> {
            String description = etBug.getText().toString().trim();

            if (description.isEmpty()) {
                etBug.setError("Please describe the issue.");
                return;
            }

            sendEmailToDeveloper(description);
        });

        return view;
    }

    private void sendEmailToDeveloper(String bugDescription) {
        String developerEmail = "sania.nisar01@gmail.com";
        String subject = "Bug Report: EventMingle App";
        String body = "Dear Developer,\n\n"
                + "The following bug was reported by a user:\n\n"
                + bugDescription + "\n\n"
                + "Regards,\nEventMingle App";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");  // Forces only email apps to respond
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sania.nisar01@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivity(Intent.createChooser(intent, "Send Email via..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email app found.", Toast.LENGTH_SHORT).show();
        }
    }
}

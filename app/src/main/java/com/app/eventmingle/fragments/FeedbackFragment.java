package com.app.eventmingle.fragments;

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

public class FeedbackFragment extends Fragment {

    public FeedbackFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        EditText etFeedback = view.findViewById(R.id.etFeedback);
        Button btnSend = view.findViewById(R.id.btnSendFeedback);

        btnSend.setOnClickListener(v -> {
            String feedback = etFeedback.getText().toString().trim();

            if (feedback.isEmpty()) {
                etFeedback.setError("Please write your feedback");
                return;
            }

            sendEmail(feedback);
        });

        return view;
    }

    private void sendEmail(String messageBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");  // Forces only email apps to respond
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sania.nisar01@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack - EventMingle App");
        intent.putExtra(Intent.EXTRA_TEXT, messageBody);

        try {
            startActivity(Intent.createChooser(intent, "Send Email via..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email app found.", Toast.LENGTH_SHORT).show();
        }
    }

}

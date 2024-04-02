package com.example.digifarm.Fragment;



import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.digifarm.R;

import com.google.ai.client.generativeai.BuildConfig;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;


import static android.app.Activity.RESULT_OK;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CaretakerFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewSelectedPhoto;

    ProgressBar pb;
    TextView genText;
    private Button buttonSelectPhoto, buttonSeeDiagnosis;


    private View rootView;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_caretaker, container, false);

        imageViewSelectedPhoto = rootView.findViewById(R.id.imageViewSelectedPhoto);
        buttonSelectPhoto = rootView.findViewById(R.id.buttonTakePhoto);
        buttonSeeDiagnosis = rootView.findViewById(R.id.buttonSeeDiagnosis);
        pb=rootView.findViewById(R.id.genrate);
        genText=rootView.findViewById(R.id.genratedText);


        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });


//
        buttonSeeDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                if (imageViewSelectedPhoto.getDrawable() == null) {
                    Toast.makeText(getContext(), "Please select an image first", Toast.LENGTH_SHORT).show();
                    return;
                }

                GenerativeModel gm = new GenerativeModel("gemini-pro-vision","AIzaSyBlE5FUZ-JAOOgTOotGdPHKF9zMni0kh1U");
                GenerativeModelFutures model = GenerativeModelFutures.from(gm);

                Bitmap img = ((BitmapDrawable)imageViewSelectedPhoto.getDrawable()).getBitmap();

                Content content = new Content.Builder()
                        .addImage(img)
                        .addText("What's the name of Picture")
                        .build();

                Executor executor = Executors.newSingleThreadExecutor();

                ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
                Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        String resultText = result.getText();
                        // Update UI with the generated text
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                genText.setText(resultText);
                                pb.setVisibility(View.GONE);
                            }
                        });
                        Log.d("Diagnosis Result", resultText); // Logging the result
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                }, executor);
            }
        });






        return rootView;
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                imageViewSelectedPhoto.setImageBitmap(bitmap);
                imageViewSelectedPhoto.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.example.digifarm.Fragment;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import com.example.digifarm.R;






import static android.app.Activity.RESULT_OK;

import java.util.concurrent.Executor;

public class CaretakerFragment extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageViewSelectedPhoto;
    private Button buttonSelectPhoto, buttonSeeDiagnosis;


    private View rootView;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_caretaker, container, false);

        imageViewSelectedPhoto = rootView.findViewById(R.id.imageViewSelectedPhoto);
        buttonSelectPhoto = rootView.findViewById(R.id.buttonTakePhoto);
        buttonSeeDiagnosis = rootView.findViewById(R.id.buttonSeeDiagnosis);


        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        buttonSeeDiagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

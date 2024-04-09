package com.crtv.creativetechnocollege;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.creativetechnocollege.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.OutputStream;

public class ImageDetailFragment extends Fragment {

    private static final int REQUEST_PERMISSION_CODE = 123;
    private PhotoView imageView;
    private ImageView save;

    public ImageDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
        imageView = view.findViewById(R.id.imageViewDetail);
        save = view.findViewById(R.id.download);

        // Get the clicked image bitmap from arguments
        Bundle bundle = getArguments();
        if (bundle != null) {
            Bitmap clickedImage = bundle.getParcelable("clicked_image");
            if (clickedImage != null) {
                imageView.setImageBitmap(clickedImage);
            }
        }

        // Set click listener for the "Download" button
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check and request storage permission before saving the image
                if (checkStoragePermission()) {
                    saveImageToGallery();
                } else {
                    requestStoragePermission();
                }
            }
        });

        return view;
    }

    private boolean checkStoragePermission() {
        // Check if the WRITE_EXTERNAL_STORAGE permission is granted
        return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        // Request WRITE_EXTERNAL_STORAGE permission from the user
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            // Check if the permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, save the image
                saveImageToGallery();
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(requireContext(), "Permission denied. Cannot save image.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveImageToGallery() {
        Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        if (image != null) {
            try {
                ContentResolver resolver = requireActivity().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "your_image_name.png");
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.Images.Media.IS_PENDING, 1);
                }

                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                OutputStream outputStream = resolver.openOutputStream(imageUri);
                image.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.put(MediaStore.Images.Media.IS_PENDING, 0);
                    resolver.update(imageUri, values, null, null);
                }

                Toast.makeText(requireContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(requireContext(), "Image not available", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.example.coindownloader;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.coindownloader.databinding.ActivityMainBinding;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ExecutorService executor;
    private ActivityMainBinding binding;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Downloading images...");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String[] coinUrls = {
    "https://upload.wikimedia.org/wikipedia/commons/c/c6/1879S_Morgan_Dollar_NGC_MS67plus_Obverse.png",
    "https://upload.wikimedia.org/wikipedia/commons/b/b9/1974S_Eisenhower_Obverse.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/f/f0/1976S_Type1_Eisenhower_Reverse.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/2006_AESilver_Proof_Obv.png/1920px-2006_AESilver_Proof_Obv.png",
    "https://upload.wikimedia.org/wikipedia/en/f/fe/Sacagawea_dollar_obverse.png",
    "https://upload.wikimedia.org/wikipedia/commons/5/54/2003_Sacagawea_Rev.png",
    "https://upload.wikimedia.org/wikipedia/commons/d/d7/2009NativeAmericanRev.jpg",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/0/0a/George_Washington_Presidential_%241_Coin_obverse.png/1920px-George_Washington_Presidential_%241_Coin_obverse.png",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fa/Presidential_dollar_coin_reverse.png/1920px-Presidential_dollar_coin_reverse.png",
    "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9d/NNC-US-1921-1%24-Peace_dollar.jpg/2880px-NNC-US-1921-1%24-Peace_dollar.jpg"
    };

        if (id == R.id.resetdownloading) {
            toast("Resetting all images");
            for (int i = 1; i <= coinUrls.length; i++) {
                ImageView imageView = findViewById(getResources().getIdentifier("imageView" + i, "id", getPackageName()));
                if (imageView != null) {
                    imageView.setImageResource(android.R.color.transparent);
                }
            }
        }

        if (id == R.id.DownloadParallel) {
            toast("Downloading images in parallel");
            executor = Executors.newFixedThreadPool(coinUrls.length);
            progressDialog.show(); // Show the progress dialog
            for (int i = 1; i <= coinUrls.length; i++) {
                int finalI = i;
                executor.execute(() -> {
                    Bitmap bitmap = downloadImage(coinUrls[finalI - 1]);
                    runOnUiThread(() -> {
                        ImageView imageView = findViewById(getResources().getIdentifier("imageView" + finalI, "id", getPackageName()));
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                        if (finalI == coinUrls.length) {
                            progressDialog.dismiss(); // Dismiss the progress dialog when all images are downloaded
                        }
                    });
                });
            }
        }

        if (id == R.id.Downloadserial) {
            toast("Downloading images sequentially");
            executor = Executors.newSingleThreadExecutor();
            progressDialog.show(); // Show the progress dialog
            for (int i = 1; i <= coinUrls.length; i++) {
                int finalI = i;
                executor.execute(() -> {
                    Bitmap bitmap = downloadImage(coinUrls[finalI - 1]);
                    runOnUiThread(() -> {
                        ImageView imageView = findViewById(getResources().getIdentifier("imageView" + finalI, "id", getPackageName()));
                        if (imageView != null) {
                            imageView.setImageBitmap(bitmap);
                        }
                        if (finalI == coinUrls.length) {
                            progressDialog.dismiss(); // Dismiss the progress dialog when all images are downloaded
                        }
                    });
                });
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }

    private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            // If an error occurs while downloading the image, download a default error image
            try {
                InputStream in = new java.net.URL("https://upload.wikimedia.org/wikipedia/commons/thumb/f/f0/Error.svg/994px-Error.svg.png").openStream();
                bitmap = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bitmap;
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}


package cs160.final_proj_drawer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mStorageRef = FirebaseStorage.getInstance().getReference();
        //context = getApplicationContext();

    }

    public void saveText (View view) {
        EditText username = findViewById(R.id.editText);
        String user = username.getText().toString();


        //Snackbar.make(view, user, Snackbar.LENGTH_LONG)
        //        .setAction("Action", null).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}

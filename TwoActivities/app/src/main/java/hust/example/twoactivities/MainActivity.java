package hust.example.twoactivities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Two Activities";
    Button btnSend = null;
    public static final String EXTRA_MESSAGE =
            "hust.example.twoactivities.extra.MESSAGE";
    private EditText mMessageEditText;
    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;
    ActivityResultLauncher<Intent> mReply = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    //Log.d(TAG,"onActivityResult: ");
                    if (result.getResultCode() == RESULT_OK) {
                        Intent replyIntent = result.getData();
                        String reply = null;
                        if (replyIntent != null) {
                            reply = replyIntent.getStringExtra(SecondActivity.EXTRA_REPLY);
                            mReplyHeadTextView.setVisibility(View.VISIBLE);
                            mReplyTextView.setText(reply);
                            mReplyTextView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.button_main);
        mMessageEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                String message = mMessageEditText.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                mReply.launch(intent);
            }
        });

    }
}
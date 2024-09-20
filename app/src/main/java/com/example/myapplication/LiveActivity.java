package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingConfig;
import com.zegocloud.uikit.prebuilt.livestreaming.ZegoUIKitPrebuiltLiveStreamingFragment;

public class LiveActivity extends AppCompatActivity {

    String userId, name, liveId;
    boolean isHost;
    TextView liveidText;
    ImageView share_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_live);

        liveidText = findViewById(R.id.live_id_text);
        share_btn = findViewById(R.id.share_btn);

        userId = getIntent().getStringExtra("user_id");
        name = getIntent().getStringExtra("name");
        liveId = getIntent().getStringExtra("live_id");
        isHost = getIntent().getBooleanExtra("host", false);

        liveidText.setText(liveId);

        addFragment();

        share_btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,"Join my live \n Live ID: "+liveId);
            startActivity(Intent.createChooser(intent, "share"));
        });
    }

    void addFragment(){
        ZegoUIKitPrebuiltLiveStreamingConfig config;
        if (isHost){
            config = ZegoUIKitPrebuiltLiveStreamingConfig.host();
        }else{
            config = ZegoUIKitPrebuiltLiveStreamingConfig.audience();
        }

        ZegoUIKitPrebuiltLiveStreamingFragment fragment = ZegoUIKitPrebuiltLiveStreamingFragment.newInstance(
                AppContent.AppId,AppContent.AppSign,userId,name,liveId,config
        );
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.Fragment_container,fragment)
                .commitNow();
    }
}
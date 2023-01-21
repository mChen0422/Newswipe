package com.meitong.newswipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.meitong.newswipe.database.newswipeDatabase;
import com.meitong.newswipe.model.NewsResponse;
import com.meitong.newswipe.network.NewsApi;
import com.meitong.newswipe.network.RetrofitClient;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

     NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LinearLayout linearLayout = new LinearLayout(this);
//        TextView textView = new TextView(this);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        textView.setText("hello world");
//        linearLayout.addView(textView);
//        LayoutInflater.from(this).inflate(R.layout.fragment_search, linearLayout, true);
//        setContentView(linearLayout);
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);
//
//        // sample on using retrofit
//        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
//        // endpoint: baseUrl/top-headlines/?q=tesla&pageSize=10
//        Call<NewsResponse> responseCall = api.getTopHeadlines("tesla", 10);


//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });
        // new a task, make the call<NewsResponse>
        // add task to queue
        // while(true) { retrofit keep check the queue }
        // if queue has `task`, retrofit do task: call endpoint, parse json , etc
        // once retrofit finish the task
        // callback.onRsponse(response)
        // if (task success) newsResponseCallback.onResponse()
        // else newsResponseCallback.onFailure()
//        Callback<NewsResponse> newsResponseCallback = new Callback<NewsResponse>() {
//            @Override
//            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
//                if (response.isSuccessful()) {
//                    NewsResponse news = response.body();
//                    Log.d("getTopHeadlines", news.toString());
//                } else {
//                    Log.d("getTopHeadlines", response.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<NewsResponse> call, Throwable t) {
//                Log.d("getTopHeadlines", t.toString());
//            }
//        };
//
//        responseCall.enqueue(newsResponseCallback);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}
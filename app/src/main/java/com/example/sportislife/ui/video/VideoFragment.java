package com.example.sportislife.ui.video;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportislife.Config;
import com.example.sportislife.R;
import com.example.sportislife.YouTubePlayer;
import com.example.sportislife.adapter.ListVideoAdapter;
import com.example.sportislife.adapter.WorkoutAdapter;
import com.example.sportislife.databinding.FragmentVideoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends Fragment {

    private Application application;
    private VideoViewModel viewModel;
    private FragmentVideoBinding binding;

    private RecyclerView recyclerView;
    private ListVideoAdapter listVideoAdapter;
    private ArrayList<VideoModel> itemVideoInformation = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        application = this.requireActivity().getApplication();
        VideoFactory factory = new VideoFactory(application);

        viewModel = new ViewModelProvider(this, factory).get(VideoViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_video, container, false);
        binding.setLifecycleOwner(this);

        recyclerView = binding.recyclerView;

        getVideoInfo();

        return binding.getRoot();
    }

    private void getVideoInfo() {
        String url = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=25&q="+"Войтенко тренировки"+"&type=video&key="+Config.YOUTUBE_API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(application.getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ArrayList<VideoModel> items = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("items");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                JSONObject jsonId = obj.getJSONObject("id");
                                String idbj = (String) jsonId.getString("videoId");
                                JSONObject jsonSnippet = obj.getJSONObject("snippet");
                                String titleobj = (String) jsonSnippet.getString("title");
                                String discobj = (String) jsonSnippet.getString("channelTitle");
                                JSONObject imageObject = jsonSnippet.getJSONObject("thumbnails").getJSONObject("default");
                                String imageobj = (String) imageObject.getString("url");
                                items.add(new VideoModel(idbj, titleobj,discobj,imageobj));
                                processRequest(items);
                            }
                        } catch (JSONException e) {
                            Log.d("Error",e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error",error.toString());
                error.printStackTrace();
            }
        });

        requestQueue.add(stringRequest);
    }

    private void processRequest(ArrayList<VideoModel> items) {
        listVideoAdapter = new ListVideoAdapter(application.getApplicationContext(),items);
        recyclerView.setAdapter(listVideoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(application.getApplicationContext()));

        listVideoAdapter.setOnClickListener(new ListVideoAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String title = items.get(position).getTitle();
                String id = items.get(position).getId();
                Context context = application.getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, title, duration);
                toast.show();

                Intent intent = new Intent(context, YouTubePlayer.class);
                intent.putExtra("VIDEO_ID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

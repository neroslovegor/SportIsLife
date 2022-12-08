package com.example.sportislife.ui.video;

import android.app.Application;
import android.app.DownloadManager;
import android.media.Image;
import android.media.VolumeAutomation;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sportislife.Config;
import com.example.sportislife.adapter.ListVideoAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VideoViewModel extends ViewModel {

    private Application application;

    public VideoViewModel(Application application) {
        this.application = application;
    }

    private String getStringRequest(String videoId) {
        return "https://www.googleapis.com/youtube/v3/videos?id=" + videoId + "&key=" + Config.YOUTUBE_API_KEY + "&part=snippet,statistics";
    }

    private String getYouTubeId(String youTubeURL) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeURL);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
}

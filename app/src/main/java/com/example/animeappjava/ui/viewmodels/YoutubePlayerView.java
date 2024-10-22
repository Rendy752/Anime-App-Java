package com.example.animeappjava.ui.viewmodels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressLint("SetJavaScriptEnabled")
public class YoutubePlayerView extends WebView {

    public YoutubePlayerView(Context context) {
        super(context);
        init();
    }

    public YoutubePlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public YoutubePlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        getSettings().setJavaScriptEnabled(true);
    }

    public void playVideo(String embedUrl) {
        String videoId = extractVideoId(embedUrl);
        if (videoId != null && !videoId.isEmpty()) {
            String html = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <style>\n" +
                    "        body { margin: 0; }\n" +
                    "        iframe { display: block; width: 100%; height: 100%; border: none; }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <iframe src=\"https://www.youtube.com/embed/" + videoId + "?enablejsapi=1\" frameborder=\"0\" allowfullscreen></iframe>\n" +
                    "</body>\n" +
                    "</html>";

            loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
        }
    }

    private String extractVideoId(String embedUrl) {
        Pattern pattern = Pattern.compile("(?<=embed/)([a-zA-Z0-9_-]+)");
        Matcher matcher = pattern.matcher(embedUrl);
        return matcher.find() ? matcher.group(1) : null;
    }
}
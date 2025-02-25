//package com.example.mealplannerapp.meal.view;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ImageView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import com.bumptech.glide.Glide;
//import com.example.mealplannerapp.R;
//import java.util.List;
//
//public class MediaPagerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//    private final List<String> mediaUrls;
//
//    public MediaPagerAdapter(List<String> mediaUrls) {
//        this.mediaUrls = mediaUrls;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return mediaUrls.get(position).contains("youtube.com") ? 1 : 0;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        if (viewType == 0) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
//            return new ImageViewHolder(view);
//        } else {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
//            return new VideoViewHolder(view);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        String mediaUrl = mediaUrls.get(position);
//
//        if (holder instanceof ImageViewHolder) {
//            Glide.with(holder.itemView.getContext())
//                    .load(mediaUrl)
//                    .placeholder(R.drawable.background)
//                    .into(((ImageViewHolder) holder).mealImage);
//        } else {
//            VideoViewHolder videoHolder = (VideoViewHolder) holder;
//            videoHolder.youtubeWebView.getSettings().setJavaScriptEnabled(true);
//            videoHolder.youtubeWebView.setWebViewClient(new WebViewClient());
//            videoHolder.youtubeWebView.loadUrl(mediaUrl);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mediaUrls.size();
//    }
//
//    static class ImageViewHolder extends RecyclerView.ViewHolder {
//        ImageView mealImage;
//        ImageViewHolder(@NonNull View itemView) {
//            super(itemView);
//            mealImage = itemView.findViewById(R.id.mealImage);
//        }
//    }
//
//    static class VideoViewHolder extends RecyclerView.ViewHolder {
//        WebView youtubeWebView;
//        VideoViewHolder(@NonNull View itemView) {
//            super(itemView);
//            youtubeWebView = itemView.findViewById(R.id.youtubeWebView);
//        }
//    }
//}

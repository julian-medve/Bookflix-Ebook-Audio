package com.fixnowitdeveloper.bookflix.AdapterUtil;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.devbrackets.android.exomedia.ui.widget.VideoView;
import com.fixnowitdeveloper.bookflix.CustomUtil.GlideApp;
import com.fixnowitdeveloper.bookflix.ObjectUtil.EmptyObject;
import com.fixnowitdeveloper.bookflix.ObjectUtil.ProgressObject;
import com.fixnowitdeveloper.bookflix.R;

import net.bohush.geometricprogressview.GeometricProgressView;

import java.util.ArrayList;


/**
 * Created by hp on 5/5/2018.
 */

public abstract class StatusAdapter extends RecyclerView.Adapter {
    private int NO_DATA_VIEW = 1;
    private int WALLPAPER_VIEW = 2;
    private int PROGRESS_VIEW = 3;
    private int EMPTY_VIEW = 4;
    private Context context;
    private String postType;
    private ArrayList<Object> wallpaperArray = new ArrayList<>();


    public StatusAdapter(Context context, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.wallpaperArray = wallpaperArray;
    }

    public StatusAdapter(Context context, String postType, ArrayList<Object> wallpaperArray) {
        this.context = context;
        this.postType = postType;
        this.wallpaperArray = wallpaperArray;
    }

    @Override
    public int getItemViewType(int position) {


        if (wallpaperArray.get(position) instanceof EmptyObject) {
            return NO_DATA_VIEW;
        } else if (wallpaperArray.get(position) instanceof String) {
            return WALLPAPER_VIEW;
        } else if (wallpaperArray.get(position) instanceof ProgressObject) {
            return PROGRESS_VIEW;
        } else if (wallpaperArray.get(position) instanceof EmptyObject) {
            return EMPTY_VIEW;
        }

        return NO_DATA_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == NO_DATA_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        } else if (viewType == WALLPAPER_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.status_item_layout, parent, false);
            viewHolder = new WallpaperHolder(view);

        } else if (viewType == PROGRESS_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item_layout, parent, false);
            viewHolder = new ProgressHolder(view);

        } else if (viewType == EMPTY_VIEW) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.empty_item_layout, parent, false);
            viewHolder = new EmptyHolder(view);

        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof ProgressHolder) {

            //LookUpObject lookUpObject = (LookUpObject) wallpaperArray.get(position);
            ProgressHolder lookUpHolder = (ProgressHolder) holder;


        } else if (holder instanceof EmptyHolder) {

            EmptyHolder emptyHolder = (EmptyHolder) holder;
            EmptyObject emptyObject = (EmptyObject) wallpaperArray.get(position);

            emptyHolder.imageIcon.setImageResource(emptyObject.getPlaceHolderIcon());
            emptyHolder.txtTitle.setText(emptyObject.getTitle());
            emptyHolder.txtDescription.setText(emptyObject.getDescription());


        } else if (holder instanceof WallpaperHolder) {

            String file = (String) wallpaperArray.get(position);
            final WallpaperHolder wallpaperHolder = (WallpaperHolder) holder;

            wallpaperHolder.layoutCategory.setTag(position);
            wallpaperHolder.layoutCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) wallpaperHolder.layoutCategory.getTag();
                    selectWallpaper(pos);
                }
            });

            if (postType.equalsIgnoreCase("image")) {

                wallpaperHolder.imageWallpaper.setVisibility(View.VISIBLE);
                wallpaperHolder.videoStatus.setVisibility(View.GONE);
                GlideApp.with(context).load(file)
                        .into(wallpaperHolder.imageWallpaper);

            } else if (postType.equalsIgnoreCase("video")) {

                wallpaperHolder.imageWallpaper.setVisibility(View.GONE);
                wallpaperHolder.videoStatus.setVisibility(View.VISIBLE);

                wallpaperHolder.videoStatus.setVideoPath(file);
                wallpaperHolder.videoStatus.pause();

            }


        }


    }

    @Override
    public int getItemCount() {
        return wallpaperArray.size();

    }

    public abstract void selectWallpaper(int position);

    protected class EmptyHolder extends RecyclerView.ViewHolder {
        private ImageView imageIcon;
        private TextView txtTitle;
        private TextView txtDescription;

        public EmptyHolder(View view) {
            super(view);

            imageIcon = (ImageView) view.findViewById(R.id.image_icon);
            txtTitle = (TextView) view.findViewById(R.id.txt_title);
            txtDescription = (TextView) view.findViewById(R.id.txt_description);
        }
    }

    protected class WallpaperHolder extends RecyclerView.ViewHolder {
        private ImageView imageWallpaper;
        private RelativeLayout layoutCategory;
        private VideoView videoStatus;

        public WallpaperHolder(View view) {
            super(view);
            imageWallpaper = (ImageView) view.findViewById(R.id.image_wallpaper);
            layoutCategory = view.findViewById(R.id.layout_category);
            videoStatus = view.findViewById(R.id.video_status);
        }

    }

    protected class ProgressHolder extends RecyclerView.ViewHolder {
        private GeometricProgressView progressView;

        public ProgressHolder(View view) {
            super(view);
            progressView = (GeometricProgressView) view.findViewById(R.id.progressView);
        }

    }


}

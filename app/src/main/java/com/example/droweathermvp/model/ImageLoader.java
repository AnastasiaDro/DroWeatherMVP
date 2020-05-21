package com.example.droweathermvp.model;

import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;

public class ImageLoader {

        private Uri uri;
        private String uriString;
        String imgId;

        public ImageLoader(){
            uriString ="https://openweathermap.org/img/wn/99999@2x.png";
        }

        public void loadDraweeImage (SimpleDraweeView draweeView, String imgId) {
            uriString = uriString.replace("99999", imgId);
            uri = Uri.parse(uriString);
            uriString = "https://openweathermap.org/img/wn/99999@2x.png";
            draweeView.setImageURI(uri);
        }
}

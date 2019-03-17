package com.test.ishanishah.user.utils;

import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.test.ishanishah.R;
import com.test.ishanishah.user.model.UserData;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;

public class BinderUtils {
    @BindingAdapter("imageUrl")
    public static void bindImages(AppCompatImageView imageView, String url) {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new CircleCrop());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

        Glide.with(imageView.getContext()).setDefaultRequestOptions(requestOptions).load(url).into(imageView);

    }

    @BindingAdapter(value = {"checkBoxChangeListener", "userData"}, requireAll = false)
    public static void bindCheckBox(CheckBox view, final ObservableBoolean checked, final UserData userData) {
        if (view.getTag(R.id.cbUser) == null) {
            //Here you are setting the attributes to your *view* and
            //decouple it from your article. It does not reference it,
            //the properties (isChecked) isnow on the view.
            //So when your view gets recycled when you scroll,
            //it still has the property you set the last time -
            //and not from your current article, which is displayed now in the view.

            view.setTag(R.id.cbUser, true);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    userData.setChecked(isChecked);
                }
            });
        }
    }


}

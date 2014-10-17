package de.handler.mobile.android.shopprototype.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.ShopApp_;
import de.handler.mobile.android.shopprototype.utils.CustomNetworkImageView;
import de.handler.mobile.android.shopprototype.utils.RoundNetworkImageView;


/**
 * Image Adapter used in GridView Fragments
 */
public class ImageAdapter extends BaseAdapter {

    private final int mLayoutFile;
    private final ArrayList<Product> mProducts;
    private Context mContext;


    public ImageAdapter(Context context, ArrayList<Product> products, int layoutFile) {
        mContext = context;
        mProducts = products;
        mLayoutFile = layoutFile;
    }

    public int getCount() {
        return mProducts.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //if (view == null) {
            // Get layout
            // Changed this from recycled view pattern to actual pattern
            // as it showed the tags in unpredictable order, sometimes twice, sometimes false
            view = inflater.inflate(mLayoutFile, null);

        //} else {
            // Create custom typeface
            Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");

            RoundNetworkImageView imageViewProduct = (RoundNetworkImageView) view.findViewById(R.id.image_adapter_image);
            TextView textViewDescription = (TextView) view.findViewById(R.id.image_adapter_text);
            textViewDescription.setTypeface(myTypeface);

            if (mProducts.size() < 1) {
                textViewDescription.setVisibility(View.GONE);
                imageViewProduct.setVisibility(View.GONE);
            }

            String description = mProducts.get(position).getDescription();
            String url = mProducts.get(position).getImageUrl();

            ShopApp_ app = (ShopApp_) mContext.getApplicationContext();

            ImageLoader imageLoader = app.getImageLoader();
            imageViewProduct.setImageUrl(url, imageLoader);
            imageViewProduct.setErrorImageResId(R.drawable.watermark);

            // Set text
            textViewDescription.setText(description);
        //}
        return view;
    }
}

package de.handler.mobile.android.fairmondo.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.util.RoundNetworkImageView;


/**
 * Image Adapter used in GridView Fragments
 */
public class ImageAdapter extends BaseAdapter {

    private final int mLayoutFile;
    private final ArrayList<Article> mProducts;
    private Context mContext;


    public ImageAdapter(Context context, ArrayList<Article> products, int layoutFile) {
        mContext = context;

        if (products != null) {
            mProducts = products;
        } else {
            mProducts = new ArrayList<Article>();
        }

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

        // Recycled view pattern
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayoutFile, parent, false);
        }

        // Create custom typeface
        Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");

        RoundNetworkImageView imageViewProduct = (RoundNetworkImageView) view.findViewById(R.id.image_adapter_image);
        TextView textViewDescription = (TextView) view.findViewById(R.id.image_adapter_text);
        textViewDescription.setTypeface(myTypeface);

        if (mProducts.size() < 1) {
            textViewDescription.setVisibility(View.GONE);
            imageViewProduct.setVisibility(View.GONE);
        }

        String description = mProducts.get(position).getTitle();

        String url = mProducts.get(position).getTitle_image_url();
        if (mProducts.get(position).getTitle_image() != null) {
            url = mProducts.get(position).getTitle_image().getThumb_url();
        }

        FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();

        ImageLoader imageLoader = app.getImageLoader();
        imageViewProduct.setImageUrl(url, imageLoader);
        imageViewProduct.setErrorImageResId(R.drawable.watermark);

        // Set text
        textViewDescription.setText(description);

        return view;
    }
}

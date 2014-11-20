package de.handler.mobile.android.fairmondo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final ArrayList<Article> mProducts;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected RoundNetworkImageView imageView;
        protected TextView textView;

        public ViewHolder(View v) {
            super(v);
            this.imageView = (RoundNetworkImageView) v.findViewById(R.id.image_adapter_image);
            this.textView = (TextView) v.findViewById(R.id.image_adapter_text);
        }
    }



    public ImageAdapter(Context context, ArrayList<Article> products) {
        mContext = context;

        if (products != null) {
            mProducts = products;
        } else {
            mProducts = new ArrayList<Article>();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_image_grid_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if (mProducts.size() < 1) {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textView.setVisibility(View.GONE);
        }

        // Create custom typeface
        //Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");

        String description = mProducts.get(position).getTitle();

        String url = mProducts.get(position).getTitle_image_url();
        if (mProducts.get(position).getTitle_image() != null) {
            url = mProducts.get(position).getTitle_image().getThumb_url();
        }

        FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();

        ImageLoader imageLoader = app.getImageLoader();
        viewHolder.imageView.setImageUrl(url, imageLoader);
        viewHolder.imageView.setErrorImageResId(R.drawable.watermark);

        // Set text
        viewHolder.textView.setText(description);
    }

    public long getItemId(int position) {
        return mProducts.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

}

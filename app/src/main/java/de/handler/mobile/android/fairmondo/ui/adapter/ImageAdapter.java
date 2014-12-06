package de.handler.mobile.android.fairmondo.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.rest.json.Article;
import de.handler.mobile.android.fairmondo.ui.views.CustomNetworkImageView;


/**
 * Image Adapter used in GridView Fragments
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final ArrayList<Article> mProducts;
    private Context mContext;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected CustomNetworkImageView imageView;
        protected TextView textView;
        protected CardView cardView;
        protected ProgressBar progressBar;

        public ViewHolder(View v) {
            super(v);

            this.imageView = (CustomNetworkImageView) v.findViewById(R.id.image_adapter_image);
            this.textView = (TextView) v.findViewById(R.id.image_adapter_text);
            this.cardView = (CardView) v.findViewById(R.id.image_adapter_card_view);
            this.progressBar = (ProgressBar) v.findViewById(R.id.image_adapter_progress_bar);
        }
    }

    public ImageAdapter(Context context, ArrayList<Article> products) {
        mContext = context;

        if (products != null) {
            mProducts = products;
        } else {
            mProducts = new ArrayList<>();
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_image_grid_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        if (mProducts.size() > 0) {

            Article product = mProducts.get(position);
            if (product != null) {
                // Create custom typeface
                Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");

                // Set image
                String url = product.getTitle_image_url();
                if (product.getTitle_image() != null) {
                    url = product.getTitle_image().getThumb_url();
                }

                FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();

                if (url != null) {
                    app.getImageLoader().get(url, new ImageLoader.ImageListener() {

                        @Override
                        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                            if (response.getBitmap() != null) {
                                setImage(viewHolder, response.getBitmap());
                            }
                        }

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            setImage(viewHolder, BitmapFactory
                                    .decodeResource(mContext.getResources(),
                                            R.drawable.fairmondo_small));
                        }
                    });
                }

                // Set text
                String description = product.getTitle();
                viewHolder.textView.setText(description);
                viewHolder.textView.setTypeface(myTypeface);

            } else {
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.textView.setVisibility(View.GONE);
                viewHolder.cardView.setVisibility(View.GONE);
                viewHolder.progressBar.setVisibility(View.GONE);
            }
        }
    }

    public void setImage(ViewHolder viewHolder, Bitmap bitmap) {
        viewHolder.imageView.setLocalImageBitmap(bitmap);
        viewHolder.progressBar.setVisibility(View.GONE);
    }

    public long getItemId(int position) {
        return mProducts.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

}

package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;


/**
 * Image Adapter used in GridView Fragments.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final List<Product> mProducts;
    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final CustomNetworkImageView imageView;
        protected final TextView textView;
        protected final CardView cardView;

        public ViewHolder(final View v) {
            super(v);
            this.imageView = (CustomNetworkImageView) v.findViewById(R.id.image_adapter_image);
            this.textView = (TextView) v.findViewById(R.id.image_adapter_text);
            this.cardView = (CardView) v.findViewById(R.id.image_adapter_card_view);
        }
    }

    public ImageAdapter(final Context context, final List<Product> products) {
        mContext = context;
        if (products != null) {
            mProducts = products;
        } else {
            mProducts = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_image_grid_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        if (mProducts.size() > 0) {
            final Product product = mProducts.get(position);
            if (product != null) {
                // Create custom typeface
                Typeface myTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");

                // Set image
                FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();
                String url = product.getTitleImageUrl();
                if (product.getTitleImage() != null && !product.getTitleImage().getOriginalUrl().equals("")) {
                    url = product.getTitleImage().getOriginalUrl();
                }

                if (url != null) {
                    app.getImageLoader().get(url, new ImageLoader.ImageListener() {
                        @Override
                        public void onResponse(final ImageLoader.ImageContainer response, final boolean isImmediate) {
                            if (response.getBitmap() != null) {
                                setImage(viewHolder, response.getBitmap());
                            }
                        }

                        @Override
                        public void onErrorResponse(final VolleyError error) {
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
            }
        }
    }

    public void setImage(final ViewHolder viewHolder, final Bitmap bitmap) {
        viewHolder.imageView.setLocalImageBitmap(bitmap);
    }

    @Override
    public long getItemId(final int position) {
        return Long.getLong(mProducts.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }
}

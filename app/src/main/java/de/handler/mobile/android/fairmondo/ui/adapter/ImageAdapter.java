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
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datalayer.businessobject.Product;
import de.handler.mobile.android.fairmondo.ui.views.CustomNetworkImageView;


/**
 * Image Adapter used in GridView Fragments
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final ArrayList<Product> mProducts;
    private final Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected final CustomNetworkImageView imageView;
        protected final TextView textView;
        protected final CardView cardView;

        public ViewHolder(View v) {
            super(v);
            this.imageView = (CustomNetworkImageView) v.findViewById(R.id.image_adapter_image);
            this.textView = (TextView) v.findViewById(R.id.image_adapter_text);
            this.cardView = (CardView) v.findViewById(R.id.image_adapter_card_view);
        }
    }

    public ImageAdapter(Context context, ArrayList<Product> products) {
        mContext = context;
        if (products != null) {
            mProducts = products;
        } else {
            mProducts = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_image_grid_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
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
            }
        }
    }

    public void setImage(ViewHolder viewHolder, Bitmap bitmap) {
        viewHolder.imageView.setLocalImageBitmap(bitmap);
    }

    public long getItemId(int position) {
        return mProducts.get(position).getId().intValue();
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

}

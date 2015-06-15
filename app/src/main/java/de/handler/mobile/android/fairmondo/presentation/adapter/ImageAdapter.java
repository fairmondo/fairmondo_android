package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.activities.ProductGalleryActivity_;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;


/**
 * Image Adapter used in GridView Fragments.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final List<Product> mProducts;
    private final Context mContext;

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
        return new ViewHolder(v, mContext, mProducts);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bindPosition(position);
        if (mProducts.size() > 0) {
            final Product product = mProducts.get(position);
            if (product != null) {
                // Set image
                FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();
                String url = product.getTitleImageUrl();
                if (product.getTitleImage() != null && !product.getTitleImage().getOriginalUrl().equals("")) {
                    url = product.getTitleImage().getOriginalUrl();
                }

                this.loadImage(app, viewHolder, url);

                // Set text
                String description = product.getTitle();
                viewHolder.textView.setText(description);

            } else {
                viewHolder.imageView.setVisibility(View.GONE);
                viewHolder.textView.setVisibility(View.GONE);
                viewHolder.cardView.setVisibility(View.GONE);
            }
        }
    }

    private void loadImage(final FairmondoApp app, final ImageAdapter.ViewHolder viewHolder, final String url) {
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


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected final CustomNetworkImageView imageView;
        protected final TextView textView;
        protected final CardView cardView;

        private final Context mContext;
        private final List<Product> mProducts;
        private int mPosition;

        public ViewHolder(final View view, final Context context, final List<Product> products) {
            super(view);
            this.imageView = (CustomNetworkImageView) view.findViewById(R.id.image_adapter_image);
            this.textView = (TextView) view.findViewById(R.id.image_adapter_text);
            this.cardView = (CardView) view.findViewById(R.id.image_adapter_card_view);
            this.cardView.setOnClickListener(this);
            this.mContext = context;
            this.mProducts = products;
        }

        protected void bindPosition(final int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View v) {
            this.startProductGallery(mPosition);
        }

        private void startProductGallery(final int position) {
            ProductGalleryActivity_.intent(mContext).mProductsParcelable(Parcels.wrap(List.class, mProducts)).mPosition(position).start();
        }
    }
}

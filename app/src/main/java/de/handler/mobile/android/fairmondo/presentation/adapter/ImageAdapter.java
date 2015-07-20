package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

import org.parceler.Parcels;

import java.util.List;

import de.handler.mobile.android.fairmondo.FairmondoApp;
import de.handler.mobile.android.fairmondo.FairmondoApp_;
import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.data.businessobject.Product;
import de.handler.mobile.android.fairmondo.presentation.FormatHelper;
import de.handler.mobile.android.fairmondo.presentation.activities.ProductGalleryActivity_;
import de.handler.mobile.android.fairmondo.presentation.views.CustomNetworkImageView;


/**
 * Image Adapter used in GridView Fragments.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private final Context mContext;
    private List<Product> mProducts;

    /**
     * Creates an instance of the ImageAdapter.
     */
    public ImageAdapter(@NonNull final Context context, @NonNull final List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int viewType) {
        final View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_image_list_item, viewGroup, false);
        return new ViewHolder(view, mContext, mProducts);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bindPosition(position);
        if (mProducts.size() > 0) {
            final Product product = mProducts.get(position);
            if (product != null) {

                // Set image
                String url = product.getTitleImageUrl();
                if (product.getTitleImage() != null && !product.getTitleImage().getOriginalUrl().equals("")) {
                    url = product.getTitleImage().getOriginalUrl();
                }

                final FairmondoApp_ app = (FairmondoApp_) mContext.getApplicationContext();
                this.loadImage(app, viewHolder, url);

                // Set price
                final String price = FormatHelper.formatPrice(product.getPriceCents());
                viewHolder.mTextViewPrice.setText(price + " €");

                // Set text
                final String description = product.getTitle();
                viewHolder.mTextViewTitle.setText(description);

            } else {
                viewHolder.mListItem.setVisibility(View.GONE);
            }
        }
    }

    private void loadImage(@NonNull final FairmondoApp app, @NonNull final ImageAdapter.ViewHolder viewHolder, @Nullable final String url) {
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

    public void setImage(@NonNull final ViewHolder viewHolder, @NonNull final Bitmap bitmap) {
        viewHolder.mImageView.setLocalImageBitmap(bitmap);
    }

    @Override
    public long getItemId(final int position) {
        return Long.getLong(mProducts.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }


    protected static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final CustomNetworkImageView mImageView;
        private final TextView mTextViewTitle;
        private final TextView mTextViewPrice;
        private final LinearLayout mListItem;

        private final Context mContext;
        private final List<Product> mProducts;
        private int mPosition;

        public ViewHolder(@NonNull final View view, @NonNull final Context context, @NonNull final List<Product> products) {
            super(view);
            mImageView = (CustomNetworkImageView) view.findViewById(R.id.image_adapter_image);
            mTextViewTitle = (TextView) view.findViewById(R.id.image_adapter_text);
            mTextViewPrice = (TextView) view.findViewById(R.id.image_adapter_price);
            mListItem = (LinearLayout) view.findViewById(R.id.image_adapter_list_item);
            mListItem.setOnClickListener(this);
            mContext = context;
            mProducts = products;
        }

        protected void bindPosition(final int position) {
            mPosition = position;
        }

        @Override
        public void onClick(final View view) {
            this.startProductGallery(mPosition);
        }

        private void startProductGallery(final int position) {
            ProductGalleryActivity_.intent(mContext).mProductsParcelable(Parcels.wrap(List.class, mProducts)).mPosition(position).start();
        }
    }
}

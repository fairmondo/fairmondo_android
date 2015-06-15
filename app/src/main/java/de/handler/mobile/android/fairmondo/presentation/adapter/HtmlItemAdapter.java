package de.handler.mobile.android.fairmondo.presentation.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.handler.mobile.android.fairmondo.R;


/**
 * Image Adapter used in GridView Fragments.
 */
//TODO: make adapter work
public class HtmlItemAdapter extends RecyclerView.Adapter<HtmlItemAdapter.ViewHolder> {
    private final List<String> mTitles;
    private final List<String> mContents;

    public HtmlItemAdapter(final List<String> titles, final List<String> contents) {
        mTitles = titles;
        mContents = contents;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_html_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.bindItem(mTitles.get(position), mContents.get(position));
    }

    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final LinearLayout linearLayoutContainer;
        protected final TextView textViewTitle;
        protected final WebView webViewContent;
        private boolean mCollapsed;

        public ViewHolder(final View view) {
            super(view);
            this.linearLayoutContainer = (LinearLayout) view.findViewById(R.id.adapter_html_item_container);
            this.textViewTitle = (TextView) view.findViewById(R.id.adapter_html_title);
            this.webViewContent = (WebView) view.findViewById(R.id.adapter_html_content);

            this.linearLayoutContainer.setOnClickListener(this);
            this.webViewContent.setVisibility(View.GONE);
            mCollapsed = true;
        }

        protected void bindItem(final String title, final String content) {
            this.textViewTitle.setText(title);
            this.webViewContent.loadData(content, "text/html; charset=UTF-8", null);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.adapter_html_item_container) {
                if (mCollapsed) {
                    this.webViewContent.setVisibility(View.VISIBLE);
                } else {
                    this.webViewContent.setVisibility(View.GONE);
                }
            }
        }
    }
}

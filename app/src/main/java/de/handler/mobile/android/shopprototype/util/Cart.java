package de.handler.mobile.android.shopprototype.util;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;

import java.util.HashMap;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.rest.json.Article;

/**
 * Contains a list of products the user wants to buy
 */
@EBean(scope = EBean.Scope.Singleton)
public class Cart {

    private Context mContext;
    private HashMap<String, Article> articles;


    public Cart(Context context) {
        this.mContext = context;
        this.articles = new HashMap<String, Article>();
    }

    public HashMap<String, Article> getArticles() {
        return articles;
    }

    public void addArticle(String key, Article article) {
        this.articles.put(key, article);
    }

    public void removeArticle(String key) {
        this.articles.remove(key);
    }

    public void send() {
        Toast.makeText(mContext, mContext.getString(R.string.question_buy_products), Toast.LENGTH_SHORT).show();
    }
}

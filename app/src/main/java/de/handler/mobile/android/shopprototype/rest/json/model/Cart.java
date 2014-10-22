package de.handler.mobile.android.shopprototype.rest.json.model;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;

import de.handler.mobile.android.shopprototype.R;
import de.handler.mobile.android.shopprototype.rest.RestController;
import de.handler.mobile.android.shopprototype.rest.json.Article;

/**
 * Contains a list of products the user wants to buy
 */
@EBean(scope = EBean.Scope.Singleton)
public class Cart {

    @Bean
    RestController restController;

    private Context mContext;

    public Cart(Context context) {
        this.mContext = context;
        this.articles = new ArrayList<Article>();
    }

    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void addArticle(Article article) {
        this.articles.add(article);
    }

    public void removeArticle(Article article) {
        this.articles.remove(article);
    }

    public void send() {
        Toast.makeText(mContext, mContext.getString(R.string.question_buy_products), Toast.LENGTH_SHORT).show();
    }
}

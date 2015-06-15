package de.handler.mobile.android.fairmondo.data.datasource;

import android.content.SearchRecentSuggestionsProvider;

/**
 * A Content provider which provides the recent searches of the customer.
 */
public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    public final static String AUTHORITY = "de.handler.mobile.android.fairmondo.searchsuggestionprovider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}

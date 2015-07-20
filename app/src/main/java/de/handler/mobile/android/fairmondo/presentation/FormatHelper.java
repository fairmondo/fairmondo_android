package de.handler.mobile.android.fairmondo.presentation;

import android.support.annotation.NonNull;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Contains methods to format strings.
 */
public final class FormatHelper {
    private FormatHelper() {
        // prevents instantiation
    }

    public static String formatPrice(@NonNull final Integer priceInCents) {
        final double priceValue = (priceInCents / 100.00);
        // Localized price value (e.g. instead of '.' use ',' in German) and corresponding currency
        final NumberFormat format = NumberFormat.getInstance(Locale.getDefault());
        format.setMinimumFractionDigits(2);
        return format.format(priceValue);
    }
}

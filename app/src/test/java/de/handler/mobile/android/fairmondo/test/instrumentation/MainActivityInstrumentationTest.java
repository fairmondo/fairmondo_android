package de.handler.mobile.android.fairmondo.test.instrumentation;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.view.KeyEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import de.handler.mobile.android.fairmondo.R;
import de.handler.mobile.android.fairmondo.datasource.database.Category;
import de.handler.mobile.android.fairmondo.datasource.database.Product;
import de.handler.mobile.android.fairmondo.ui.activities.MainActivity_;

/**
 * Instrumentation Test - invoke android system callbacks like onCreate(), ...
 */
public class MainActivityInstrumentationTest extends ActivityInstrumentationTestCase2<MainActivity_> {


    private Activity mActivity;
    private Activity mParentActivity;
    private Spinner mSpinner;
    private SpinnerAdapter mSpinnerAdapter;
    private ProgressBar mProgressBar;
    private LinearLayout mTitleContainer;
    private LinearLayout mProductsContainer;

    private int mSpinnerObjectCount;

    private ArrayList<Category> categories;
    private ArrayList<Product> products;

    public MainActivityInstrumentationTest(Class<MainActivity_> activityClass) {
        super(activityClass);
    }


    @Override
    public void setUp() throws Exception {
        super.setUp();

        mActivity = getActivity();
        mParentActivity = mActivity.getParent();

        mSpinner = (Spinner) mActivity.findViewById(R.id.main_category_spinner);
        mSpinnerAdapter = mSpinner.getAdapter();
        mSpinnerObjectCount = mSpinnerAdapter.getCount();

        mProgressBar = (ProgressBar) mActivity.findViewById(R.id.main_progress_bar);
        mTitleContainer = (LinearLayout) mActivity.findViewById(R.id.main_title_container);
        mProductsContainer = (LinearLayout) mActivity.findViewById(R.id.main_products_container);

        // Disable TouchMode initially as otherwise touch events will be ignored
        setActivityInitialTouchMode(false);
    }


    public void testPreConditions() {
        assertTrue(mParentActivity != null);
        assertTrue(mSpinner.getOnItemSelectedListener() != null);
        assertTrue(mSpinnerAdapter != null);
        assertTrue(mProgressBar != null);
        assertTrue(mTitleContainer != null);
        assertTrue(mProductsContainer != null);
        assertTrue(mSpinnerObjectCount > 0);
    }


    public void testNetworkConnectionTests() {

    }


    public void testOrientationChange() {

    }

    public void testSpinner() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Set focus to spinner equals navigating over the spinner
                mSpinner.requestFocus();
                mSpinner.setSelection(0);
            }
        });

        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
        for (int i = 1; i <= mSpinnerObjectCount; i++) {
            this.sendKeys(KeyEvent.KEYCODE_DPAD_DOWN);
        }
        this.sendKeys(KeyEvent.KEYCODE_DPAD_CENTER);
    }
}

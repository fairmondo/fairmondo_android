package de.handler.mobile.android.fairmondo.test;

import android.support.v7.app.ActionBarActivity;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import de.handler.mobile.android.fairmondo.ui.activities.MainActivity_;


@RunWith(RobolectricGradleTestRunner.class)
public class MainActivityTest {

    @Test
    public void testOnActivityLifecycle() throws Exception {
        ActionBarActivity activity = Robolectric.buildActivity(MainActivity_.class)
                .create()
                .start()
                .resume()
                .visible()
                .get();
        Assert.assertNotNull(activity);
    }

    @Test
    public void testOnProductsSearchResponse() throws Exception {

    }

    @Test
    public void testOnCategoriesResponse() throws Exception {

    }

    @Test
    public void testInitSpinner() throws Exception {

    }

    @Test
    public void testOnItemSelected() throws Exception {

    }

    @Test
    public void testOnSubCategoriesResponse() throws Exception {

    }

    @Test
    public void testOnBackPressed() throws Exception {

    }
}

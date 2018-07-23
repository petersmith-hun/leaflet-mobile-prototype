package hu.psprog.leaflet.mobile.view.helper;

import android.view.View;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 * @author Peter Smith
 */
@Singleton
public class ViewHelper {

    @Inject
    public ViewHelper() {
    }

    public void show(View... views) {
        Arrays.stream(views).forEach(view -> changeVisibility(view, true));
    }

    public void hide(View... views) {
        Arrays.stream(views).forEach(view -> changeVisibility(view, false));
    }

    private void changeVisibility(View view, boolean visible) {
        view.setVisibility(visible
                ? View.VISIBLE
                : View.INVISIBLE);
    }
}

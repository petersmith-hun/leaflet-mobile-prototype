package hu.psprog.leaflet.mobile.config.dagger.module;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hu.psprog.leaflet.mobile.EntryDetailsActivity;
import hu.psprog.leaflet.mobile.MainActivity;

/**
 * @author Peter Smith
 */
@Module
public abstract class LeafletMobileApplicationModule {

    @ContributesAndroidInjector
    abstract MainActivity contributeActivityInjector();

    @ContributesAndroidInjector
    abstract EntryDetailsActivity contributeEntryDetailsActivity();
}

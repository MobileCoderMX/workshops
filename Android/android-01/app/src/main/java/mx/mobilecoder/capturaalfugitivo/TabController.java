package mx.mobilecoder.capturaalfugitivo;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import mx.mobilecoder.capturaalfugitivo.adapters.FugitiveAdapter;
import mx.mobilecoder.capturaalfugitivo.model.Fugitive;


public class TabController extends Activity implements ActionBar.TabListener {

    private static ArrayList<Fugitive> mFugitives = new ArrayList<Fugitive>();
    private static int mCurrentPage = 0;
    private static int mSelectedItem;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    static ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_controller);

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    public void reloadData() {
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(mCurrentPage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_controller, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mCurrentPage = tab.getPosition();
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public void addFugitiveMenuSelected(MenuItem item) {
        Intent addFugitiveIntent = new Intent(this, AddFugitiveActivity.class);
        startActivityForResult(addFugitiveIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == 1) {
            String newFugitiveName = data.getStringExtra(AddFugitiveActivity.FUGITIVE_NAME_KEY);
            Fugitive newFugitive = new Fugitive(newFugitiveName);
            mFugitives.add(newFugitive);
        }

        reloadData();
    }

    /**
     * Este adaptador retornara un fragmento dependiendo la posición en la que se encuentre la vista actual
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 2:
                    return AboutFragment.newInstance();

                default:
                    return FugitiveListFragment.newInstance(position + 1);
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_fugitives).toUpperCase(l);
                case 1:
                    return getString(R.string.title_captured).toUpperCase(l);
                case 2:
                    return getString(R.string.title_about).toUpperCase(l);
            }
            return null;
        }
    }


    public static class FugitiveListFragment extends Fragment {
        private ListView mFugitiveListView;
        private int mSectionNumber;

        public static FugitiveListFragment newInstance(int sectionNumber) {
            FugitiveListFragment fragment = new FugitiveListFragment();
            fragment.mSectionNumber = sectionNumber;

            return fragment;
        }

        public FugitiveListFragment() {
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 200 && resultCode == 1) {
                Fugitive updatedFugitive = data.getParcelableExtra(FugitiveDetailActivity.FUGITIVE_KEY);
                mFugitives.remove(mSelectedItem);
                mFugitives.add(mSelectedItem, updatedFugitive);

                mViewPager.setAdapter(mSectionsPagerAdapter);
                mViewPager.setCurrentItem(mCurrentPage);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View rootView = inflater.inflate(R.layout.fragment_fugitive_list, container, false);
            mFugitiveListView = (ListView) rootView.findViewById(R.id.fugitiveListView);

            ArrayList<Fugitive> fugitives = new ArrayList<Fugitive>();

            for (int i = 0; i < mFugitives.size(); i++) {
                Fugitive f = mFugitives.get(i);
                if (mSectionNumber == 1) {
                    if (f.mCaptured == 0) {
                        fugitives.add(f);
                    }
                }else{
                    if (f.mCaptured == 1) {
                        fugitives.add(f);
                    }
                }
            }

            FugitiveAdapter adapter = new FugitiveAdapter(getActivity(), android.R.layout.simple_list_item_1, fugitives.toArray(new Fugitive[fugitives.size()]));
            mFugitiveListView.setAdapter(adapter);

            mFugitiveListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mSelectedItem = position;

                    Fugitive fugitive = mFugitives.get(position);

                    Intent fugitiveDetailIntent = new Intent(getActivity(), FugitiveDetailActivity.class);
                    fugitiveDetailIntent.putExtra(FugitiveDetailActivity.FUGITIVE_KEY, fugitive);
                    startActivityForResult(fugitiveDetailIntent, 200);
                }
            });

            return rootView;
        }
    }


    // Fragmento para mostrar About
    public static class AboutFragment extends Fragment {

        /**
         * Fragmento que muestra información sobre la app.
         */
        public static AboutFragment newInstance() {
            AboutFragment fragment = new AboutFragment();

            return fragment;
        }

        public AboutFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
            View rootView = inflater.inflate(R.layout.fragment_about, container, false);

            return rootView;
        }
    }

}

package com.vsupport.npluslabs.vsupport;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.vsupport.npluslabs.vsupport.Adapters.NavAdapter;
import com.vsupport.npluslabs.vsupport.Fragments.DashboardFragment;
import com.vsupport.npluslabs.vsupport.Fragments.GameFragment;
import com.vsupport.npluslabs.vsupport.Fragments.ParticipantsList;
import com.vsupport.npluslabs.vsupport.Fragments.ShowsMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    Toolbar toolbar;
    private DrawerLayout drawer;
    private ExpandableListView drawerList;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        initDrawer();
        ShowsMenu showsMenu = new ShowsMenu();
        getSupportFragmentManager().beginTransaction().replace(R.id.container_body, showsMenu).commit();

    }

    private void initDrawer() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerList = (ExpandableListView) findViewById(R.id.left_drawer);

        // preparing list data
        prepareListData();

        drawerList.setAdapter(new NavAdapter(this, listDataHeader, listDataChild));

        drawerList.setOnChildClickListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.drawer_open , R.string.drawer_close ){

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we don't want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we don't want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();


        // Adding headers
        Resources res = getResources();
        String[] headers = res.getStringArray(R.array.nav_drawer_labels);
        listDataHeader = Arrays.asList(headers);

        //Adding child data

//        //Dynamic method
//        for (int i =0; i<listDataHeader.size(); i++){
//
//            //Save data in array
//            String[] childData = res.getStringArray(R.array.elements_home);
//
//            //Put data in List
//            List<String> listChild;
//            listChild = Arrays.asList(childData);
//
//            //Add to hashMap
//            listDataChild.put(listDataHeader.get(i),listChild);
//        }

        // Static method
        List<String> home,friends,notifs;
        String[] shome,sfriends,snotifs;

        shome = res.getStringArray(R.array.elements_home);
        home = Arrays.asList(shome);

        sfriends = res.getStringArray(R.array.elements_friends);
        friends = Arrays.asList(sfriends);

        snotifs = res.getStringArray(R.array.elements_notifs);
        notifs = Arrays.asList(snotifs);

        // Add to hashMap
        listDataChild.put(listDataHeader.get(0), home); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), friends);
//        listDataChild.put(listDataHeader.get(2), notifs);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {
        Toast.makeText(
                getApplicationContext(),
                listDataHeader.get(groupPosition)
                        + " : "
                        + listDataChild.get(
                        listDataHeader.get(groupPosition)).get(
                        childPosition), Toast.LENGTH_SHORT)
                .show();
        if(listDataChild.get(
                listDataHeader.get(groupPosition)).get(
                childPosition).equals("Dashboard")){
            DashboardFragment tamilShows = new DashboardFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.container_body,tamilShows).
                    addToBackStack(null)
                    .commit();
        }
        if(listDataChild.get(
                listDataHeader.get(groupPosition)).get(
                childPosition).equals("Shows")){
            ShowsMenu showsMenu = new ShowsMenu();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_body, showsMenu).addToBackStack(null).commit();
        }
//        if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 2k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }
//        if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 2k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }
//        if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 4k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }
//        if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 7k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 9k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 10k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }if(listDataChild.get(
//                listDataHeader.get(groupPosition)).get(
//                childPosition).equals("Virtual 15k Run")){
//            GameFragment gameFragment = new GameFragment();
//            getSupportFragmentManager().beginTransaction().replace(R.id.container_body,gameFragment).addToBackStack(null).commit();
//        }



        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed(){
        FragmentManager fm = getFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            super.onBackPressed();
        }
    }
}

package com.vsupport.npluslabs.vsupport.Fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vsupport.npluslabs.vsupport.Adapters.CartListAdapter;
import com.vsupport.npluslabs.vsupport.HelperClass.Item;
import com.vsupport.npluslabs.vsupport.MainActivity;
import com.vsupport.npluslabs.vsupport.MyApplication;
import com.vsupport.npluslabs.vsupport.R;
import com.vsupport.npluslabs.vsupport.HelperClass.RecyclerItemTouchHelper;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TamilShows extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    View view;
    public TamilShows() {
        // Required empty public constructor
    }
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<Item> cartList;
    private CartListAdapter mAdapter;
    private CoordinatorLayout coordinatorLayout;
    private static final String URL = "https://api.androidhive.info/json/menu.json";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_tamil_shows, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        coordinatorLayout = view.findViewById(R.id.coordinator_layout);
        cartList = new ArrayList<>();
        mAdapter = new CartListAdapter(getActivity(), cartList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);


        // making http call and fetching menu json
        prepareCart();
        return view;
    }

    private void prepareCart() {
       Item item1 = new Item();
       item1.setName("Geetha Madhuri");
       item1.setDescription("She is one of the contestants in Bigg Boss Telugu 2. A playback singer and dubbing artist in Tollywood.");
       item1.setId(1);
       item1.setPrice(312);
       item1.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2F555ac40d-1c97-43e6-a9e2-604925feec31%2Fgm.png?auto=format&q=35&fm=pjpeg");

       cartList.add(item1);

        Item item2 = new Item();
        item2.setName("Anchor Deepthi");
        item2.setDescription("A news anchor with a television channel, she's also a district level hockey player.");
        item2.setId(2);
        item2.setPrice(362);
        item2.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2F12cbf045-32cc-4281-a7eb-7d8252aa7f78%2F999.png?auto=format&q=35&fm=pjpeg");

        cartList.add(item2);

        Item item3 = new Item();
        item3.setName("Actress Tejaswi");
        item3.setDescription("She was seen in in films like Kerintha, Srimanthudu, Seethamma Vakitlo Sirimalle Chettu and Rojulu Marayi.");
        item3.setId(3);
        item3.setPrice(302);
        item3.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2F8fc8c4bd-0487-43c8-a619-48a22524a621%2Fta.png?auto=format&q=35&fm=pjpeg");


        cartList.add(item3);


        Item item4 = new Item();
        item4.setName("Hero Tanish");
        item4.setDescription("He essayed the role of a child artiste in Devullu and Manmadhudu which did good business at the box office.");
        item4.setId(4);
        item4.setPrice(412);
        item4.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2F85e7b750-e05c-4084-9ff5-1f3e93ab7518%2Ftaheru.jpg?auto=format&q=35&fm=pjpeg");


        cartList.add(item4);


        Item item5 = new Item();
        item5.setName("Actress Deepthi Sunaina");
        item5.setDescription("She is seen as a social media network sensation especially on Instagram and Youtube.");
        item5.setId(5);
        item5.setPrice(352);
        item5.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2Fb9c73fd9-c76d-4669-a159-c0ee779a5c72%2Fgasghdas.png?auto=format&q=35&fm=pjpeg");

        cartList.add(item5);

        Item item6 = new Item();
        item6.setName("Actor Samrat");
        item6.setDescription("He was last seen in Anushka's Panchakshari playing the role of a husband.");
        item6.setId(6);
        item6.setPrice(412);
        item6.setThumbnail("https://images.assettype.com/sakshipost%2F2018-06%2F62af40b6-a2cc-434c-89f9-b5ed8b065e20%2Ffasgas.jpg?auto=format&q=35&fm=pjpeg");

        cartList.add(item6);
        mAdapter.notifyDataSetChanged();
    }

//    private void prepareCart() {
//        JsonArrayRequest request = new JsonArrayRequest(URL,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        if (response == null) {
//                            Toast.makeText(getActivity(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
//                            return;
//                        }
//
//                        List<Item> items = new Gson().fromJson(response.toString(), new TypeToken<List<Item>>() {
//                        }.getType());
//
//                        // adding items to cart list
//                        cartList.clear();
//                        cartList.addAll(items);
//
//                        // refreshing recycler view
//                        mAdapter.notifyDataSetChanged();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // error in getting json
//                Log.d(TAG, "Error: " + error.getMessage());
//                Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        MyApplication.getInstance().addToRequestQueue(request);
//    }

    /**
     * callback when recycler view is swiped
     * item will be removed on swiped
     * undo option will be provided in snackbar to restore the item
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CartListAdapter.MyViewHolder) {
            // get the removed item name to display it in snack bar
            String name = cartList.get(viewHolder.getAdapterPosition()).getName();

            // backup of removed item for undo purpose
            final Item deletedItem = cartList.get(viewHolder.getAdapterPosition());
            final int deletedIndex = viewHolder.getAdapterPosition();

            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

            // showing snack bar with Undo option
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, name + " removed from cart!", Snackbar.LENGTH_LONG);
            snackbar.setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // undo is selected, restore the deleted item
                    mAdapter.restoreItem(deletedItem, deletedIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

}

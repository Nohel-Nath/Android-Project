package com.login_signup_screendesign_demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import model.Rent;
import model.owneradapter;


public class ownerpage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;



    private RecyclerView recyclerView;

    private RecyclerView.LayoutManager layoutManager;
    ArrayList<Rent> list;
    private owneradapter houseAdapter;


    FirebaseDatabase database;
    DatabaseReference myRef;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ownerpage);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(ownerpage.this);
        recyclerView.setLayoutManager(layoutManager);
        //navigation view

        drawerLayout = findViewById(R.id.drawer_layout_id);
        navigationView = findViewById(R.id.navigationViewId);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.app_name);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("House");

        list = new ArrayList<Rent>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Rent r = dataSnapshot1.getValue(Rent.class);
                    assert r != null;
                    if(r.getAvailable().equals("yes")){
                        list.add(r);
                    }
                }


                houseAdapter = new owneradapter(ownerpage.this, list);


                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(houseAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ownerpage.this, "Oops,Something is wrong............", Toast.LENGTH_SHORT).show();
            }

        })
        ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase.getInstance().getReference("owner").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Student owner = dataSnapshot.getValue(Student.class);
                View headerView = navigationView.getHeaderView(0);
                TextView navUsername = headerView.findViewById(R.id.header_fullName);
                TextView navUserEmail =  headerView.findViewById(R.id.header_gmail);
                navUsername.setText(owner.getFullname());
                navUserEmail.setText(owner.getEmailId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId()==R.id.House)
        {
            startActivity(new Intent(this,addhouse.class));
        }
        else if (menuItem.getItemId()==R.id.profile){
            startActivity(new Intent(this,profileforowner.class));
        }else if(menuItem.getItemId()==R.id.logout){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this,MainActivity.class));
        }else if(menuItem.getItemId() == R.id.rating){
            startActivity(new Intent(this,rating.class));
        }else if(menuItem.getItemId() == R.id.my_houses){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        Rent r = dataSnapshot1.getValue(Rent.class);
                        assert r != null;
                        if(r.getContactEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                            list.add(r);
                        }
                    }


                    houseAdapter = new owneradapter(ownerpage.this, list);


                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(houseAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ownerpage.this, "Oops,Something is wrong............", Toast.LENGTH_SHORT).show();
                }

            });
        }
        drawerLayout.closeDrawers();
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Search By Location....");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchView.getWindowToken(),0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                houseAdapter.getFilter().filter(newText);
//                Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return true;

    }


}



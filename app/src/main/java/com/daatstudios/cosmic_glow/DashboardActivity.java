package com.daatstudios.cosmic_glow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView logo, name, email;
    public static DrawerLayout drawer;
    FirebaseAuth firebaseAuth;
    public static Activity mainactivity;
    TextView cartCount;
    MenuItem cartItem;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard2);
        Toolbar toolbar = findViewById(R.id.carttoolbar);


        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(new Intent(DashboardActivity.this, RegisterActivity.class));
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        logo = findViewById(R.id.logoTxtVIEW);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        bottomNavigationView = findViewById(R.id.bottomNavigationView);



//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.getMenu().getItem(0).setChecked(true);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                // Handle navigation view item clicks here.
//                int id = item.getItemId();
//                drawer.closeDrawer(GravityCompat.START);
//                if (id == R.id.nav_home) {
//                } else if (id == R.id.nav_cart) {

//                } else if (id == R.id.nav_orders) {
//                    Intent intent = new Intent(DashboardActivity.this, Orders.class);
//                    startActivity(intent);
//                } else if (id == R.id.nav_wishlist) {
//                    Intent intent = new Intent(DashboardActivity.this, wishlist.class);
//                    startActivity(intent);
//                } else if (id == R.id.nav_account) {
//                    Intent intent = new Intent(DashboardActivity.this, MyAccount.class);
//                    startActivity(intent);
//                } else if (id == R.id.nav_privacy) {
//                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                } else if (id == R.id.nav_terms) {
//                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);;
//                } else if (id == R.id.nav_returns) {
//                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    startActivity(intent);
//                }
//                firebaseAuth = FirebaseAuth.getInstance();
//                return true;
//
//            }
//        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController);
//        NavigationUI.setupWithNavController(navigationView, navController);

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        cartItem = menu.findItem(R.id.main_cart);

        cartItem.setActionView(R.layout.badge_layout);
        ImageView badgeIcon = cartItem.getActionView().findViewById(R.id.badgeIcon);
        badgeIcon.setImageResource(R.drawable.cart);
        cartCount = cartItem.getActionView().findViewById(R.id.badgeCount);
        if (dbQuaries.cartlist.size() == 0) {
//            dbQuaries.loadCartList(DashboardActivity.this, new Dialog(DashboardActivity.this), false, cartCount, new TextView(DashboardActivity.this));
            cartCount.setVisibility(View.INVISIBLE);
        } else {
            cartCount.setVisibility(View.VISIBLE);
            if (dbQuaries.cartlist.size() < 99) {
                cartCount.setText(String.valueOf(dbQuaries.cartlist.size()));
            } else {
                cartCount.setText(("99+"));
            }
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();

    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//
//    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.main_search) {
            Intent intent = new Intent(DashboardActivity.this, searchActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_cart) {
            Intent intent = new Intent(DashboardActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_notifications) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void setfragment(Fragment fragment,int FRAGMENTNO)
//    {
//        CurrentFragment = FRAGMENTNO;
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.commit();
//    }

    private void Cart() {
        invalidateOptionsMenu();
    }
}
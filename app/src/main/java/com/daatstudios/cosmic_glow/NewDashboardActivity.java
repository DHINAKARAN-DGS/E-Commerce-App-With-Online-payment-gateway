package com.daatstudios.cosmic_glow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daatstudios.cosmic_glow.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class NewDashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    TextView cartCount;
    MenuItem cartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_dashboard);

        drawerLayout = findViewById(R.id.custom_drawer);
        navigationView = findViewById(R.id.custom_Nav);


        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

//        BottomNavigationView bottomNavigationView = findViewById(R.id.cbottomNavigationView);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.nav_account) {
                    Intent intent = new Intent(NewDashboardActivity.this, MyAccount.class);
                    startActivity(intent);
                } else if (id == R.id.nav_wishlist) {
                    Intent intent = new Intent(NewDashboardActivity.this, wishlist.class);
                    startActivity(intent);
                } else if (id == R.id.nav_orders) {
                    Intent intent = new Intent(NewDashboardActivity.this, Orders.class);
                    startActivity(intent);
                }else if (id == R.id.nav_privacy) {
                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else if (id == R.id.nav_terms) {
                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    ;
                } else if (id == R.id.nav_returns) {
                    Uri uri = Uri.parse("https://dgsofttech.blogspot.com/2021/06/privacy-policies-from-panda-ekart.html"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        dbQuaries.loadAddresses(this,new Dialog(this),false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
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

        dbQuaries.loadCartList(NewDashboardActivity.this, new Dialog(NewDashboardActivity.this), false, cartCount, new TextView(NewDashboardActivity.this));
        if (dbQuaries.cartlist.size()!=0){
            cartCount.setVisibility(View.VISIBLE);
            if (dbQuaries.cartlist.size() < 99) {
                cartCount.setText(String.valueOf(dbQuaries.cartlist.size()));
            } else {
                cartCount.setText(("99+"));
            }
        }else{
            cartCount.setVisibility(View.INVISIBLE);
        }
        cartItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewDashboardActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();
        if (id == R.id.main_search) {
            Intent intent = new Intent(NewDashboardActivity.this, searchActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_cart) {
            Intent intent = new Intent(NewDashboardActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.main_notifications) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        return true;
    }
}
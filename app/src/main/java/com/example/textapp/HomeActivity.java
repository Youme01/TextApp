package com.example.textapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private ProfileFragment profileFragment;
    private FriendsFragment friendsFragment;
    private ChatFragment chatFragment;
    private CallFragment callFragment;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();
        mTextMessage = findViewById(R.id.message);


        BottomNavigationView navView = findViewById(R.id.nav_view);
        BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        return true;
                    case R.id.navigation_friends:
                        setFragment(friendsFragment);
                        return true;
                    case R.id.navigation_chat:
                        setFragment(chatFragment);
                        return true;
                    case R.id.navigation_call:
                        setFragment(callFragment);
                        return true;
                }
                return false;
            }
        };

        profileFragment = new ProfileFragment();
        callFragment = new CallFragment();
        chatFragment = new ChatFragment();
        friendsFragment = new FriendsFragment();

        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void setFragment (Fragment fragment){

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainframe , fragment);
        fragmentTransaction.commit();
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem menuItem){

        int id = menuItem.getItemId();

        if(id == R.id.logout){
            Logout();
        }

        return super.onOptionsItemSelected(menuItem);

    }

    public void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

}

package com.example.bookexchange.ui.activites;


import static com.example.bookexchange.ui.fragments.PostsFragment.myadapter;
import static com.example.bookexchange.ui.fragments.PostsFragment.postList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bookexchange.R;
import com.example.bookexchange.dao.DAOProfileInfo;
import com.example.bookexchange.models.Post;
import com.example.bookexchange.models.Profile_info;
import com.example.bookexchange.ui.fragments.MyPostsFragment;
import com.example.bookexchange.ui.fragments.MyRequestFragment;
import com.example.bookexchange.ui.fragments.NotificationFragment;
import com.example.bookexchange.ui.fragments.PostsFragment;
import com.example.bookexchange.ui.fragments.ReceivedNotificationFragment;
import com.example.bookexchange.ui.fragments.RequestsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    public static FloatingActionButton fab;
    Toolbar toolbar;
    AlertDialog.Builder builder;


    private DrawerLayout drawerLayout;
    NavigationView nav_view;


    public static String username, username_Id;
    public FirebaseAuth auth;
    FirebaseUser currentUser;
    DAOProfileInfo daoProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent intent = getIntent();

        String Collages = intent.getStringExtra("Collages");
        Bundle bundle = new Bundle();
        bundle.putString("Collages", Collages);


        auth = FirebaseAuth.getInstance();
        daoProfile = new DAOProfileInfo();
        currentUser = auth.getCurrentUser();


        if (currentUser != null) {
            // User is logged in
            // Perform necessary actions
            username = currentUser.getDisplayName();
            username_Id = currentUser.getUid();

            drawerLayout = findViewById(R.id.drawerLayout);
            nav_view = findViewById(R.id.nav_view);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close);

            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();


            nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.nav_my_profile:
                            Intent intent = new Intent(HomeActivity.this, Profile.class);
                            startActivity(intent);
                            break;
                        case R.id.nav_my_posts:
                            replaceFragment(new MyPostsFragment());
                            break;
                        case R.id.nav_my_requests:
                            replaceFragment(new MyRequestFragment());
                            break;
                        case R.id.nav_my_editable_profile:
                            startActivity(new Intent(HomeActivity.this, EditableProfile.class));
                            finish();
                            break;
                        case R.id.nav_logout:
                            FirebaseAuth.getInstance().signOut();
                            Intent intent2 = new Intent(HomeActivity.this, LoginActivity.class);
                            drawerLayout.closeDrawer(GravityCompat.START);
                            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent2);
                            finish();
                            break;
                        case R.id.notification_fragment:
                            replaceFragment(new NotificationFragment());
                            break;
                        case R.id.received_notification_fragment:
                            replaceFragment(new ReceivedNotificationFragment());
                            break;
                        case R.id.home:
                            replaceFragment2(new PostsFragment(), bundle);
                            break;
                        case R.id.colleges:
                            startActivity(new Intent(getApplicationContext(), CollageActivity.class));
                            finish();
                            break;
                        default:
                            break;
                    }


//                drawerLayout.close();
                    return true;
                }
            });

            //here we how assign the name and the img of user that in nav header
            // and i do it in ( setDataToNavHeader ) method.


//        View navheader = nav_view.getHeaderView(0);
//
//        TextView usernameHeader = navheader.findViewById(R.id.username_nav_header);
//        usernameHeader.setText("Abd");


            //////////////////////////////////////////////////////


            fab = findViewById(R.id.fab);
            toolbar = findViewById(R.id.toolbar_custom);
            setSupportActionBar(toolbar);

            //add navigation button and open it
            toolbar.setNavigationIcon(R.drawable.ic_navigation);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });


            // * Inflate Components *
            bottomNavigationView = findViewById(R.id.bottomNavigationView);
            fab = findViewById(R.id.fab);


            replaceFragment2(new PostsFragment(), bundle);
            bottomNavigationView.setBackground(null);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.post:
                        replaceFragment2(new PostsFragment(), bundle);

                        break;
                    case R.id.request:
                        replaceFragment2(new RequestsFragment(), bundle);
                        break;


                }
                return true;
            });

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    showBottomDialog();


                }
            });


            setDataToNavHeader();

        } else {
            // User is not logged in
            // Redirect to login screen or show a login prompt
            //////////////////////////////////////////////////////

            fab = findViewById(R.id.fab);

            toolbar = findViewById(R.id.toolbar_custom);
            setSupportActionBar(toolbar);


            // * Inflate Components *
            bottomNavigationView = findViewById(R.id.bottomNavigationView);

            builder = new AlertDialog.Builder(this);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    View dialogView = getLayoutInflater().inflate(R.layout.dialog_login_signup, null);
                    builder.setView(dialogView);
                    AlertDialog dialog = builder.create();
                    dialogView.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);

                        }
                    });

                    dialogView.findViewById(R.id.signUp_btn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                            startActivity(intent);

                        }
                    });


                    if (dialog.getWindow() != null) {
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    }
                    dialog.show();


                }
            });


            replaceFragment2(new PostsFragment(), bundle);
            bottomNavigationView.setBackground(null);
            bottomNavigationView.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.post:
                        replaceFragment2(new PostsFragment(), bundle);

                        break;
                    case R.id.request:
                        replaceFragment2(new RequestsFragment(), bundle);
                        break;


                }
                return true;
            });


        }


    }


    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void replaceFragment2(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }


    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_sheet_layout);

        LinearLayout postLayout = dialog.findViewById(R.id.createPost);
        LinearLayout requestsLayout = dialog.findViewById(R.id.CreateRequest);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        postLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), CreatePostActivity.class));
                finish();

            }
        });

        requestsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                startActivity(new Intent(getApplicationContext(), CreateRequestActivity.class));
                finish();

            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);


    }

    private void filtertext(String text) {

        List<Post> filteredList = new ArrayList<>();

        for (Post post : postList) {

            if (post.getBookName().toLowerCase().contains(text.toLowerCase())) {

                filteredList.add(post);

            }

            if (filteredList.isEmpty()) {


            } else {

                myadapter.setfilteredlist(filteredList);

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    //Press on any place in screen to cancel the keyboard
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {

                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);


        MenuItem loginItem = menu.findItem(R.id.action_Login_signup);
        loginItem.setVisible(currentUser == null);




        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Here..");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtertext(newText);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_Login_signup) {
            // Handle login button click
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_login_signup, null);
            builder.setView(dialogView);
            AlertDialog dialog = builder.create();
            dialogView.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }
            });

            dialogView.findViewById(R.id.signUp_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);

                }
            });


            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            dialog.show();
            return true;
        } else {


            item.setVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDataToNavHeader() {


        View navheader = nav_view.getHeaderView(0);

        if(currentUser !=null){

            TextView usernameHeader = navheader.findViewById(R.id.username_nav_header);
            usernameHeader.setText(username);

            ImageView imageView = navheader.findViewById(R.id.img_nav_header);

            daoProfile.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot profilesnap : snapshot.getChildren()) {

                        Profile_info profile = profilesnap.getValue(Profile_info.class);


                        if (currentUser.getUid().equals(profile.getUserId())) {

                            if (profile.getImg() != null) {
                                Glide.with(HomeActivity.this).load(profile.getImg()).fitCenter().centerCrop().into(imageView);

                            } else {
                                Glide.with(HomeActivity.this).load(getDrawable(R.drawable.default_profile_img)).fitCenter().centerCrop().into(imageView);

                            }

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{

            TextView usernameHeader = navheader.findViewById(R.id.username_nav_header);
            usernameHeader.setText("");

            ImageView imageView = navheader.findViewById(R.id.img_nav_header);

            daoProfile.get().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot profilesnap : snapshot.getChildren()) {

                        Profile_info profile = profilesnap.getValue(Profile_info.class);


                        if (currentUser.getUid().equals(profile.getUserId())) {

                            if (profile.getImg() != null) {
                                Glide.with(HomeActivity.this).load(profile.getImg()).fitCenter().centerCrop().into(imageView);

                            } else {
                                Glide.with(HomeActivity.this).load(getDrawable(R.drawable.default_profile_img)).fitCenter().centerCrop().into(imageView);

                            }

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }




    }

    @Override
    public void onBackPressed() {
        // Call finish() to exit the app when back button is pressed
        finish();
    }

}


package com.example.petsandroid;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsandroid.Admin.AdminMaintainProductsActivity;
import com.example.petsandroid.Model.OrderHistory;
import com.example.petsandroid.Model.Products;
import com.example.petsandroid.Prevalent.Prevalent;
import com.example.petsandroid.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.livechatinc.inappchat.ChatWindowConfiguration;
import com.livechatinc.inappchat.ChatWindowErrorType;
import com.livechatinc.inappchat.ChatWindowView;
import com.livechatinc.inappchat.models.NewMessageModel;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import static android.view.View.GONE;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChatWindowView.ChatWindowEventsListener
        {
    private DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    private String type = "";

    public String productName,productPrice,productDes;


    private ChatWindowView chatWindow;
    private TextView chatBadgeTv;
    private int badgeCounter;
    public static final String LIVECHAT_SUPPORT_LICENCE_NR = "1520";

    ChatWindowConfiguration configuration = new ChatWindowConfiguration(LIVECHAT_SUPPORT_LICENCE_NR, "77", "", null, null);


            NavigationView navigationView;
            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        chatWindow = ChatWindowView.createAndAttachChatWindowInstance(HomeActivity.this);
        chatWindow.setUpWindow(configuration);
        chatWindow.setUpListener(this);
        chatWindow.initialize();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        chatBadgeTv = (TextView) findViewById(R.id.chat_badge);

        if (bundle != null)
        {
            type = getIntent().getExtras().get("Admin").toString();
        }



        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");


        Paper.init(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (!type.equals("Admin"))
                {
//                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
//                    startActivity(intent);

                    showChatWindow();

                }
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView userNameTextView = headerView.findViewById(R.id.user_profile_name);
        CircleImageView profileImageView = headerView.findViewById(R.id.user_profile_image);


                if (type.equals("Admin")) {
                    hideItem();
                }

        if (!type.equals("Admin"))
        {
            userNameTextView.setText(Prevalent.currentOnlineUser.getName());
            Picasso.get().load(Prevalent.currentOnlineUser.getImage()).placeholder(R.drawable.profile).into(profileImageView);

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users")
                    .child(Prevalent.currentOnlineUser.getPhone());

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // String mphone = dataSnapshot.child("phone").getValue().toString();


                        if (dataSnapshot.hasChild("Security Questions")) {


                        } else {

                            Toast.makeText(HomeActivity.this, "Please set Your security questions", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HomeActivity.this, ResetPasswordActivity.class);
                            intent.putExtra("check", "settings");
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "this phone number not exist", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        recyclerView = findViewById(R.id.recycler_menu);
       // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mGridLayoutManager);


    }

            private void hideItem() {



                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_settings).setVisible(false);
                nav_Menu.findItem(R.id.nav_wish).setVisible(false);
                nav_Menu.findItem(R.id.nav_cart).setVisible(false);
                nav_Menu.findItem(R.id.nav_search).setVisible(false);
                nav_Menu.findItem(R.id.nav_categories).setVisible(false);



            }


            @Override
    protected void onStart()
    {
        super.onStart();






        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
                .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName.setText(model.getPname());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price = " + model.getPrice() + "$");



                        Picasso.get().load(model.getImage()).into(holder.imageView);


                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                if (type.equals("Admin"))
                                {
                                    Intent intent = new Intent(HomeActivity.this, AdminMaintainProductsActivity.class);
                                    intent.putExtra("pid", model.getPid());
                                    startActivity(intent);
                                }
                                else
                                {



                                        CharSequence options[] = new CharSequence[]
                                                {
                                                        "View Product Details",
                                                        "Add to wish List"
                                                };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                        builder.setTitle("More Options:");

                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i)
                                            {
                                                if (i == 0)
                                                {
                                                    Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                                                    intent.putExtra("pid", model.getPid());
                                                    startActivity(intent);
                                                }
                                                if (i == 1)
                                                {
                                                    String pid=model.getPid();


                                                    final DatabaseReference wish;
                                                    wish = FirebaseDatabase.getInstance().getReference().child("Wish List");



                                                    wish.addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                        {
                                                            if (dataSnapshot.child("User View")
                                                                    .child(Prevalent.currentOnlineUser.getPhone())
                                                                    .child("Products")
                                                                    .child(model.getPid()).exists())
                                                            {

                                                                Toast.makeText(HomeActivity.this, " Already added to wishlist", Toast.LENGTH_SHORT).show();


                                                            }
                                                            else{


                                                                final DatabaseReference RootRef;
                                                                RootRef = FirebaseDatabase.getInstance().getReference();


                                                                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                    @SuppressLint("LongLogTag")
                                                                    @Override
                                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                                                                    {
                                                                        if (dataSnapshot.child("Products").child(pid).exists())
                                                                        {
                                                                            Products usersData = dataSnapshot.child("Products").child(pid).getValue(Products.class);
                                                                            productName=  usersData.getPname();

                                                                            productPrice=usersData.getPrice();
                                                                            productDes= usersData.getDescription();
                                                                            Log.d("product details from DB",productName+" "+productPrice+" "+productDes);
                                                                            addingToWishList(pid,productName,productPrice,productDes);

                                                                        }

                                                                    }

                                                                    @Override
                                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                        Toast.makeText(HomeActivity.this, " Error with your code", Toast.LENGTH_SHORT).show();

                                                                    }
                                                                });

                                                            }


                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            Toast.makeText(HomeActivity.this, " Error with your code", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });



                                                }
                                            }
                                        });
                                        builder.show();
                                    }




                            }
                        });
                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();




    }

    private void addingToWishList(String pid,String pname,String pprice,String pdes)
    {


        String saveCurrentTime, saveCurrentDate;

        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());
        Log.d("product ids in meth",pid);

        Log.d("product details method",pname+" "+pprice+" "+pdes);



        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Wish List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid",pid );
        cartMap.put("pname", pname);
        cartMap.put("price", pprice);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("descrip",pdes);
        cartMap.put("discount", "");


        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Products").child(pid)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Products").child(pid)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(HomeActivity.this, "Added to Cart List.", Toast.LENGTH_SHORT).show();


                                            }
                                        }
                                    });
                        }
                    }
                });
    }


            private void showChatWindow() {
                chatWindow.showChatWindow();
            }
            @Override
            public void onChatWindowVisibilityChanged(boolean visible) {
                if (visible) {
                    discardBadge();
                }
            }

            private void discardBadge() {
                badgeCounter = 0;
                chatBadgeTv.setVisibility(GONE);
                chatBadgeTv.setText("");
            }

            @Override
            public void onNewMessage(NewMessageModel message, boolean windowVisible) {
                if (!windowVisible) {
                    badgeCounter++;
                    chatBadgeTv.setVisibility(View.VISIBLE);
                    chatBadgeTv.setText(String.valueOf(badgeCounter));
                }
            }

            @Override
            public boolean handleUri(Uri uri) {
                return false;
            }

            @Override
            public void onStartFilePickerActivity(Intent intent, int requestCode) {
                startActivityForResult(intent, requestCode);
            }

            @Override
            public boolean onError(ChatWindowErrorType errorType, int errorCode, String errorDescription) {
                Toast.makeText(HomeActivity.this, errorDescription, Toast.LENGTH_SHORT).show();
                return false;
            }


            @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                chatWindow.onActivityResult(requestCode, resultCode, data);
            }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (!chatWindow.onBackPressed())
            super.onBackPressed();
       else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
//       else {
//            super.onBackPressed();
//        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_logout)
        {
            Paper.book().destroy();

            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        }

        return super.onOptionsItemSelected(item);
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_cart)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        }
        if (id == R.id.nav_wish)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, WishListActivity.class);
                startActivity(intent);
            }

        }

        else if (id == R.id.nav_search)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SearchProductsActivity.class);
                startActivity(intent);
            }
        }

        else if (id == R.id.nav_share)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, ShareActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_about)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, AboutUsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_contact)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, ContactUsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_settings)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, SettinsActivity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_send)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, Send.class);
                startActivity(intent);
            }
        }


        else if (id == R.id.nav_faq)
        {
            if (!type.equals("Admin"))
            {
                Intent intent = new Intent(HomeActivity.this, FAQ_Activity.class);
                startActivity(intent);
            }
        }
        else if (id == R.id.nav_categories)
        {
            if (!type.equals("Admin"))
            {
                Toast.makeText(HomeActivity.this, "clicked on orderhistory", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivity.this, OrderHistoryActivity.class);

                startActivity(intent);
            }
        }
        else if (id == R.id.nav_logout)
        {
//            if (!type.equals("Admin"))
//            {
                Paper.book().destroy();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
           // }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

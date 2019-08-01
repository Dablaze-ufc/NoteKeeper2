package com.dablaze.notekeeper;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private NoteRecyclerAdapter fNoteRecyclerAdapter;
    private RecyclerView fRecyclerViewItems;
    private LinearLayoutManager fNotesLayoutManager;
    private NoteCourseRecyclerAdapter fNoteCourseRecyclerAdapter;
    private GridLayoutManager fCoursesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  startActivity(new Intent(MainActivity.this, NoteActivity.class));


            }
        });


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



        initializeDisplayContent();

    }
    @Override
    protected void onResume() {
        super.onResume();
        fNoteRecyclerAdapter.notifyDataSetChanged();

    }

    private void initializeDisplayContent() {


        fRecyclerViewItems = findViewById(R.id.list_items);
        fNotesLayoutManager = new LinearLayoutManager(this);
        fCoursesLayoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.column_span));


        List<NoteInfo> notes =DataManager.getInstance().getNotes();
        fNoteRecyclerAdapter = new NoteRecyclerAdapter(this, notes);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        fNoteCourseRecyclerAdapter = new NoteCourseRecyclerAdapter(this, courses);

         displayNotes();


    }

    private void displayNotes() {
        fRecyclerViewItems.setLayoutManager(fNotesLayoutManager);
        fRecyclerViewItems.setAdapter(fNoteRecyclerAdapter);
        selectNaviagtionMenuItem(R.id.nav_notes);
    }

    private void selectNaviagtionMenuItem(int id) {
        NavigationView navigationView = findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(id).setChecked(true);
    }

    private void displayCourses(){
        fRecyclerViewItems.setLayoutManager(fCoursesLayoutManager);
        fRecyclerViewItems.setAdapter(fNoteCourseRecyclerAdapter);
        selectNaviagtionMenuItem(R.id.nav_courses);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_notes) {
            displayNotes();
        } else if (id == R.id.nav_courses) {
           displayCourses();

        } else if (id == R.id.nav_share) {
            handleSelection("Don't think you have shared this");

        } else if (id == R.id.nav_send) {
            handleSelection("Send");

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleSelection(String message) {
        View view = findViewById(R.id.list_items);
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();

    }
}

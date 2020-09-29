package ch.ost.rj.mge.v03.examples;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import ch.ost.rj.mge.v03.examples.adapter.UsersAdapter;
import ch.ost.rj.mge.v03.examples.adapter.UsersAdapter2;
import ch.ost.rj.mge.v03.examples.model.User;
import ch.ost.rj.mge.v03.examples.model.UserManager;

public class MainActivity extends AppCompatActivity {
    private boolean displaysFirstLayout = true;
    private boolean createMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpForPaddingAndMarginDemo();
        //setUpForLinearLayoutVerticalDemo();
        //setUpForLinearLayoutHorizontalDemo();
        //setUpForFrameLayoutDemo();
        //setUpForRelativeLayoutDemo();
        //setUpForConstraintLayoutDemo();
        //setUpAnimationForConstraintLayoutDemo();
        //setUpForWidgetDemo();
        //setUpForSystemWidgetsDemo();
        //setUpForMenuDemo();
        //setUpForListViewDemo1();
        //setUpForListViewDemo2();
        //setUpForListViewDemo3();
    }

    private void setUpForPaddingAndMarginDemo() {
        setContentView(R.layout.activity_padding_and_margin);
    }

    private void setUpForLinearLayoutVerticalDemo() {
        setContentView(R.layout.activity_linear_vertical);
    }

    private void setUpForLinearLayoutHorizontalDemo() {
        setContentView(R.layout.activity_linear_horizontal);
    }

    private void setUpForFrameLayoutDemo() {
        this.setContentView(R.layout.activity_frame);
    }

    private void setUpForRelativeLayoutDemo() {
        this.setContentView(R.layout.activity_relative);
    }

    private void setUpForConstraintLayoutDemo() {
        this.setContentView(R.layout.activity_constraint);
    }

    private void setUpAnimationForConstraintLayoutDemo() {
        setContentView(R.layout.activity_constraint);

        TextView textViewA = findViewById(R.id.txtA);
        ConstraintLayout constraintLayout = findViewById(R.id.constraint_layout);

        textViewA.setOnClickListener(v -> {
            int targetLayout = displaysFirstLayout ?
                    R.layout.activity_constraint_2 :
                    R.layout.activity_constraint;

            TransitionManager.beginDelayedTransition(constraintLayout);

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.load(this, targetLayout);
            constraintSet.applyTo(constraintLayout);

            displaysFirstLayout = !displaysFirstLayout;
        });
    }

    private void setUpForWidgetDemo() {
        setContentView(R.layout.activity_widgets);

        EditText passwordInput = findViewById(R.id.edit_password);
        passwordInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() < 8) {
                    passwordInput.setError("Passwort zu kurz.");
                }
            }
        });
    }

    private void setUpForSystemWidgetsDemo() {
        setContentView(R.layout.activity_widgets_system);

        // Toast
        Button toastButton = findViewById(R.id.button_toast);
        toastButton.setOnClickListener(v -> {
            Context context = this;
            String text = "MGE rocks!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        });

        // Snackbar
        Button snackbarButton = findViewById(R.id.button_snackbar);
        snackbarButton.setOnClickListener(b -> {
            ViewGroup parent = findViewById(R.id.layout_system_widgets);
            String text = "MGE rocks!";
            int duration = Snackbar.LENGTH_LONG;
            String action = "Schliessen";

            Snackbar snackbar = Snackbar.make(parent,text, duration);
            snackbar.setAction(action, v -> { snackbar.dismiss(); });
            snackbar.show();
        });

        // Dialog
        Button dialogButton = findViewById(R.id.button_dialog);
        dialogButton.setOnClickListener(b -> {
            AlertDialog dialog;

            dialog = new AlertDialog.Builder(this)
                    .setTitle("Beispiel")
                    .setMessage("Gutes Beispiel?")
                    .setCancelable(false)
                    .setPositiveButton("Ja", (d, id) -> dialogButton.setText("Dialog: ja"))
                    .setNegativeButton("Nein", (d, id) -> dialogButton.setText("Dialog: nein"))
                    .create();

            /*
            dialog = new AlertDialog.Builder(this)
                    .setTitle("Auswahl")
                    .setCancelable(false)
                    .setSingleChoiceItems(new String[]{ "Option 1", "Option 2", "Option 3" }, 0, (d, id) -> {})
                    //.setMultiChoiceItems(new String[]{ "Option 1", "Option 2", "Option 3" }, new boolean[] { false, false, true }, (d, id, x) -> {})
                    .setPositiveButton("OK", (d, id) -> {})
                    .create();
             */

            dialog.show();
        });

        // Notification
        final String CHANNEL_ID = "MGE_Channel";
        final String CHANNEL_NAME = "MGE Notifications";
        final String CHANNEL_DESCRIPTION = "Ein Channel für die MGE Demo";
        final int CHANNEL_IMPORTANCE = NotificationManager.IMPORTANCE_HIGH;
        final int[] notificationId = { 1 };

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, CHANNEL_IMPORTANCE);
            channel.setDescription(CHANNEL_DESCRIPTION);

            manager.createNotificationChannel(channel);
        }

        Button notificationButton = findViewById(R.id.button_notification);
        notificationButton.setOnClickListener(b -> {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_emoji)
                    .setContentTitle("MGE")
                    .setContentText("MGE rocks!")
                    .build();

            manager.notify(notificationId[0], notification);

            notificationId[0]++;
        });
    }

    private void setUpForMenuDemo() {
        setContentView(R.layout.activity_menus);
        this.createMenu = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (this.createMenu)
        {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_example, menu);
            return true;
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.createMenu)
        {
            int menuIndex = 0;
            switch (item.getItemId()) {
                case R.id.menu_1:
                    menuIndex = 1;
                    break;

                case R.id.menu_2:
                    menuIndex = 2;
                    break;

                case R.id.menu_3:
                    menuIndex = 3;
                    break;

                case R.id.menu_4:
                    menuIndex = 4;
                    break;
            }

            TextView outputText = findViewById(R.id.text_menu_selection);
            outputText.setText("Auswahl: " + menuIndex);
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpForListViewDemo1() {
        setContentView(R.layout.activity_listview);

        final int ITEMS = 100;
        String[] data = new String[ITEMS];
        for (int i = 1; i <= ITEMS; i++)
            data[i-1] = "Item " + i;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                data);

        ListView listView = findViewById(R.id.list_example);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "Item " + i, Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpForListViewDemo2() {
        setContentView(R.layout.activity_listview);

        ArrayList<User> data = UserManager.getUsers();
        UsersAdapter adapter = new UsersAdapter(this, data);

        ListView listView = findViewById(R.id.list_example);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, "Item " + i, Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpForListViewDemo3() {
        setContentView(R.layout.activity_recyclerview);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        RecyclerView.LayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<User> data = UserManager.getUsers();
        UsersAdapter2 adapter = new UsersAdapter2(data);
        recyclerView.setAdapter(adapter);
    }
}
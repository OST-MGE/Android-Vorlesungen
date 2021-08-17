package ch.ost.rj.mge.v05.examples.persistence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import ch.ost.rj.mge.v05.examples.R;
import ch.ost.rj.mge.v05.examples.persistence.database.ContentDbHelper;
import ch.ost.rj.mge.v05.examples.persistence.database.Entry;
import ch.ost.rj.mge.v05.examples.persistence.database.EntryDatabase;

public class PersistenceActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "MGE.V05";

    private static final String FILE_NAME = "my_file.txt";
    private static final String FILE_TYPE = "text/plain";

    private static final String PREFERENCES_FILE_NAME = "ch.ost.rj.mge.v05.myapplication.preferences";
    private static final String PREFERENCES_KEY = "my.key";

    private static final int CREATE_DOCUMENT_CODE = 1;
    private static final int OPEN_DOCUMENT_CODE = 2;

    private static final String SQLITE_DB = "sqlite.db";
    private static final String ROOM_DB = "room.db";

    private EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistence);

        // Shared
        inputText = findViewById(R.id.editext_input);

        // App-specific files
        findViewById(R.id.button_asf_write_internal).setOnClickListener(v -> writeAppSpecificInternalFile());
        findViewById(R.id.button_asf_read_internal).setOnClickListener(v -> readAppSpecificInternalFile());
        findViewById(R.id.button_asf_write_external).setOnClickListener(v -> writeAppSpecificExternalFile());
        findViewById(R.id.button_asf_read_external).setOnClickListener(v -> readAppSpecificExternalFile());

        // Preferences
        findViewById(R.id.button_preferences_write).setOnClickListener(v -> writePreferences());
        findViewById(R.id.button_preferences_read).setOnClickListener(v -> readPreferences());

        // Database
        findViewById(R.id.button_database_write).setOnClickListener(v -> writeDatabase());
        findViewById(R.id.button_database_read).setOnClickListener(v -> readDatabase());
        findViewById(R.id.button_room_write).setOnClickListener(v -> writeDatabaseWithRoom());
        findViewById(R.id.button_room_read).setOnClickListener(v -> readDatabaseWithRoom());

        // Media Store
        findViewById(R.id.button_media_write).setOnClickListener(v -> writeMedia());
        findViewById(R.id.button_media_read).setOnClickListener(v -> readMedia());

        // Documents
        findViewById(R.id.button_document_write).setOnClickListener(v -> writeDocument());
        findViewById(R.id.button_document_read).setOnClickListener(v -> readDocument());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);

        switch(requestCode) {
            case CREATE_DOCUMENT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = resultData.getData();
                    writeDocument(uri);
                }
                break;

            case OPEN_DOCUMENT_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    Uri uri = resultData.getData();
                    readDocument(uri);
                }
        }
    }

    private String getInputText() {
        return inputText.getText().toString();
    }

    private void setInputText(String text) {
        inputText.post(() -> inputText.setText(text));
    }

    private void writeAppSpecificInternalFile() {
        File parentFolder = getFilesDir();
        writeToFile(parentFolder);
    }

    private void readAppSpecificInternalFile() {
        File parentFolder = getFilesDir();
        listFilesInFolder(parentFolder);
        readFromFile(parentFolder);
    }

    private void writeAppSpecificExternalFile() {
        File parentFolder = getExternalFilesDir(null);
        writeToFile(parentFolder);
    }

    private void readAppSpecificExternalFile() {
        File parentFolder = getExternalFilesDir(null);
        listFilesInFolder(parentFolder);
        readFromFile(parentFolder);
    }

    private void writeToFile(File parentFolder) {
        File file = new File(parentFolder, FILE_NAME);
        String content = getInputText();

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream stream = new FileOutputStream(file);
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Could not write file.", e);
        }
    }

    private void readFromFile(File parentFolder) {
        File file = new File(parentFolder, FILE_NAME);

        try {
            int length = (int) file.length();
            byte[] bytes = new byte[length];

            FileInputStream stream = new FileInputStream(file);
            stream.read(bytes);
            stream.close();

            String content = new String(bytes);
            setInputText(content);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Could not write file.", e);
        }
    }

    private void listFilesInFolder(File parentFolder) {
        File[] files = parentFolder.listFiles();

        Log.d(DEBUG_TAG, "Folder: "+ parentFolder);
        Log.d(DEBUG_TAG, "Files: "+ files.length);

        for(File file : files) {
            Log.d(DEBUG_TAG, "File: " + file.getName());
        }
    }

    private void writePreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFERENCES_KEY, getInputText());
        editor.commit();
    }

    private void readPreferences() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        String value = preferences.getString(PREFERENCES_KEY, "default");
        setInputText(value);
    }

    private void writeDatabase() {
        String content = getInputText();

        ContentValues values = new ContentValues();
        values.put("content", content);

        ContentDbHelper dbHelper = new ContentDbHelper(this, SQLITE_DB);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("entry", null, values);
        db.close();
    }

    private void readDatabase() {
        ContentDbHelper dbHelper = new ContentDbHelper(this, SQLITE_DB);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "entry",
                new String[]{ "id", "content" },
                null,
                null,
                null,
                null,
                "id ASC");

        while(cursor.moveToNext()) {
            Log.d(DEBUG_TAG, "DB Entry | " + cursor.getInt(0) + " | " + cursor.getString(1));
            setInputText(cursor.getString(1));
        }

        cursor.close();
        db.close();
    }

    private void writeDatabaseWithRoom() {
        Runnable write = () -> {
            Entry entry = new Entry();
            entry.content = getInputText();

            EntryDatabase db = Room.databaseBuilder(this, EntryDatabase.class, ROOM_DB).build();
            db.entryDao().insert(entry);
            db.close();
        };

        new Thread(write).start();
    }

    private void readDatabaseWithRoom() {
        Runnable read = () -> {
            EntryDatabase db = Room.databaseBuilder(this, EntryDatabase.class, ROOM_DB).build();

            List<Entry> entries = db.entryDao().getEntries();
            for (Entry entry : entries) {
                Log.d(DEBUG_TAG, "DB Entry | " + entry.id + " | " + entry.content);
                setInputText(entry.content);
            }

            db.close();
        };

        new Thread(read).start();
    }

    private void writeMedia() {
        long millis = System.currentTimeMillis();
        byte[] textBytes = getInputText().getBytes();
        String fileName = "MGE Textfile " + millis + ".txt";

        ContentValues metaData = new ContentValues();
        metaData.put(MediaStore.Files.FileColumns.TITLE, fileName);
        metaData.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        metaData.put(MediaStore.Files.FileColumns.MIME_TYPE, FILE_TYPE);
        metaData.put(MediaStore.Files.FileColumns.DATE_ADDED, millis);
        metaData.put(MediaStore.Files.FileColumns.DATE_TAKEN, millis);

        ContentResolver resolver = getContentResolver();
        Uri filesDirectory = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Uri newFileUri = resolver.insert(filesDirectory, metaData);
        writeFile(resolver, newFileUri, textBytes);
    }

    private void readMedia() {
        String[] projection = new String[] {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.DATE_ADDED
        };

        String sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

        ContentResolver resolver = getContentResolver();
        Uri filesDirectory = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
        Cursor cursor = resolver.query(
                filesDirectory,
                projection,
                null,
                null,
                sortOrder
        );

        int idColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID);
        int titleColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE);
        int addedColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(idColumn);
            String title = cursor.getString(titleColumn);
            long added = cursor.getLong(addedColumn);

            Uri fileUri = ContentUris.withAppendedId(filesDirectory, id);

            Log.d(DEBUG_TAG, "Media Store | ID: " + id + " | Title: " + title + " | Added: " + added);
            Log.d(DEBUG_TAG, "Media Store | Uri: " + fileUri);
        }

        cursor.close();
    }

    private void writeFile(ContentResolver contentResolver, Uri targetUri, byte[] content) {
        // Based on https://gist.github.com/benny-shotvibe/1e0d745b7bc68a9c3256
        try {
            final int BUFFER_SIZE = 1024;

            ByteArrayInputStream inputStream = new ByteArrayInputStream(content);
            OutputStream outputStream = contentResolver.openOutputStream(targetUri);

            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                int numBytesRead = inputStream.read(buffer);
                if (numBytesRead <= 0) {
                    break;
                }
                outputStream.write(buffer, 0, numBytesRead);
            }

            outputStream.close();
            inputStream.close();

        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Could not write file.", e);
        }
    }

    private void writeDocument() {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FILE_TYPE);
        intent.putExtra(Intent.EXTRA_TITLE, FILE_NAME);

        startActivityForResult(intent, CREATE_DOCUMENT_CODE);
    }

    private void readDocument() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(FILE_TYPE);

        startActivityForResult(intent, OPEN_DOCUMENT_CODE);
    }

    private void writeDocument(Uri targetUri) {
        try {
            String content = getInputText();

            OutputStream stream = getContentResolver().openOutputStream(targetUri);
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Could not write document.", e);
        }
    }

    private void readDocument(Uri sourceUri) {
        try {
            String lineEnding = System.getProperty("line.separator");
            StringBuilder stringBuilder = new StringBuilder();

            InputStream inputStream = getContentResolver().openInputStream(sourceUri);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(lineEnding);
            }

            int end = stringBuilder.length();
            int start = end - lineEnding.length();
            stringBuilder.delete(start, end);

            String content = stringBuilder.toString();
            setInputText(content);
        } catch (Exception e) {
            Log.e(DEBUG_TAG, "Could not read document.", e);
        }
    }
}
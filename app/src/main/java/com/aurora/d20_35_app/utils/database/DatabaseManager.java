package com.aurora.d20_35_app.utils.database;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import com.aurora.d20_35_app.enums.DatabaseUsage;
import com.aurora.d20_35_app.enums.ItemType;
import com.aurora.d20_35_app.helper.BindingActivity;
import com.aurora.d20_35_app.models.userData.HeroPlayer;
import com.aurora.d20_35_app.utils.CommonUtils;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import lombok.NonNull;

import static com.aurora.d20_35_app.utils.ExternalStorageHandler.getPublicExternalStorageBaseDir;

public class DatabaseManager {

    private static String externalPathSeparator = "/Android/data/com.aurora.d20_3.5_app/";
    public static String path = getPublicExternalStorageBaseDir() + externalPathSeparator + "Data/";

    //String filename = "standardRulesData.xml";
    private static final String[] FILENAMES = {"test.xml", "translations_for_app.xml"}; //todo refactor

    private static ProgressBar progressBar;
    private static final int MAX_PROGRESS = 100;
    private static final int NUMBER_OF_PROGRESS_INCREASES = 10;


    public static void initialDatabasesResolver(BindingActivity activity) {
        initialPathSetup();
        increaseProgressBar();
        SharedPreferences sharedpreferences = activity.getApplicationContext().getSharedPreferences("AppPref", 0);
        if (!sharedpreferences.getBoolean("firstTimeOpened", false)) {
            onFirstTime(sharedpreferences, activity);
        } else {
            onAnotherTime(sharedpreferences, activity);
        }
    }

    private static void onFirstTime(SharedPreferences sharedpreferences, BindingActivity activity) {
        Log.i("Database", "First opening");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("firstTimeOpened", true);
        editor.putString("language", "en");
        editor.apply();
        copyAllFilesFromAssets(activity);
        increaseProgressBar();
        chooseDatabaseLoadingType(DatabaseHolder.getDatabaseHolder(activity.getApplicationContext()), DatabaseUsage.Startup, "en");
    }

    private static void onAnotherTime(SharedPreferences sharedpreferences, BindingActivity activity) {
        Log.i("Database", "Another opening");
        increaseProgressBar();
        chooseDatabaseLoadingType(DatabaseHolder.getDatabaseHolder(activity.getApplicationContext()), DatabaseUsage.ReloadFromDatabase, sharedpreferences.getString("language", "en")); // todo refactor/delete
    }

    public static void startProgressBar(@NonNull BindingActivity activity) {
        progressBar = activity.showLoading();
        if (progressBar != null) {
            progressBar.setMax(MAX_PROGRESS);
        }
    }

    private static void increaseProgressBar() {
        if (progressBar != null) {
            int increaseBy;
            if (progressBar.getProgress() == MAX_PROGRESS) {
                return;
            } else if (progressBar.getProgress() < MAX_PROGRESS && (progressBar.getProgress() > (MAX_PROGRESS - (MAX_PROGRESS / NUMBER_OF_PROGRESS_INCREASES)))) {
                increaseBy = MAX_PROGRESS - progressBar.getProgress();
            } else {
                increaseBy = MAX_PROGRESS / NUMBER_OF_PROGRESS_INCREASES;
            }
            progressBar.incrementProgressBy(increaseBy);
        }
    }

    public static void closeProgressBar(@NonNull BindingActivity activity) {
        if (progressBar != null) {
            if (progressBar.getProgress() != MAX_PROGRESS) {
                progressBar.incrementProgressBy(MAX_PROGRESS - progressBar.getProgress());
            }
            progressBar.setVisibility(View.GONE);
            progressBar = null;
            activity.hideLoading();
        }
    }

    private static void copyAllFilesFromAssets(Activity activity) {
        for (String filename : FILENAMES) {
            try (InputStream source = activity.getAssets().open(filename)) {
                Path destination = FileSystems.getDefault().getPath(path, filename);
                CommonUtils.copyFileFromAssets(source, destination); //todo test - refactor or delete
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void initialPathSetup() {
        Log.i("Database directory:", "checking if directory: " + path + " exist...");
        Path dir = FileSystems.getDefault().getPath(path);
        if (Files.notExists(dir)) {
            Log.i("Database directory:", "directory " + path + " doesn't exist, creating...");
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Files.exists(dir)) {
                Log.i("Database directory:", "created directory:" + path);
            } else {
                Log.e("Database directory:", "failed to create directory:" + path);
            }
        } else {
            Log.i("Database directory:", "directory " + path + " already exist.");
        }
    }

    public static void clearWholeDatabaseAndAllHolders(DatabaseHolder databaseHolder) {
        for (ItemType type : ItemType.values()) {
            type.deleteAll(databaseHolder);
        }
    }

    public static void clearWholeDatabase(DatabaseHolder databaseHolder) {
        for (ItemType type : ItemType.values()) {
            type.deleteAllFromDatabase(databaseHolder);
        }
    }

    public static void clearAllHolders(DatabaseHolder databaseHolder) {
        for (ItemType type : ItemType.values()) {
            type.deleteAllFromHolder(databaseHolder);
        }
    }

    public static void closeDatabase(DatabaseHolder databaseHolder) {
        databaseHolder.close();
        DatabaseHolder.destroyInstance();
    }

    private static void clear(DatabaseHolder databaseHolder, DatabaseUsage databaseUsage, ItemType itemType) {
        switch (databaseUsage) {
            case ClearDatabaseAndHolder:
                if (itemType != null) {
                    itemType.deleteAll(databaseHolder);
                } else {
                    clearWholeDatabaseAndAllHolders(databaseHolder);
                }
                break;
            case ClearHolder:
                if (itemType != null) {
                    itemType.deleteAllFromHolder(databaseHolder);
                } else {
                    clearAllHolders(databaseHolder);
                }
                break;
            case ClearDatabase:
                if (itemType != null) {
                    itemType.deleteAllFromDatabase(databaseHolder);
                } else {
                    clearWholeDatabase(databaseHolder);
                }
                break;
            default:
                break;
        }
    }

    private static void loadRulesFromFileToHolderAndDatabaseAndReloadHolder(DatabaseHolder databaseHolder, String[] filenames, ItemType itemType) {
        increaseProgressBar();
        for (String filename : filenames) {
            loadDataFromFileToHolder(databaseHolder, filename);
        }
        loadDataFromHolderToDatabase(databaseHolder, itemType);
    }

    private static void loadDataFromDatabaseToHolder(DatabaseHolder databaseHolder, ItemType itemType) {
        //todo check first if rules present?
        increaseProgressBar();
        if (itemType != null) {
            itemType.fromDatabaseToHolder(databaseHolder);
        } else {
            for (ItemType it : ItemType.values()) {
                it.fromDatabaseToHolder(databaseHolder);
            }
        }
        increaseProgressBar();
    }

    private static void loadDataFromHolderToDatabase(DatabaseHolder databaseHolder, ItemType itemType) {
        //todo check first if rules present?
        increaseProgressBar();
        if (itemType != null) {
            itemType.fromHolderToDatabase(databaseHolder);
        } else {
            for (ItemType it : ItemType.values()) {
                it.fromHolderToDatabase(databaseHolder);
            }
        }
        increaseProgressBar();
    }

    private static void loadDataFromFileToHolder(DatabaseHolder databaseHolder, String filename) {
        try {
            Path inputFile = FileSystems.getDefault().getPath(path, filename);
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XmlHandler xmlHandler = new XmlHandler(databaseHolder);
            saxParser.parse(inputFile.toFile(), xmlHandler);
        } catch (IOException | SAXException | ParserConfigurationException e) {
            e.printStackTrace();
        }
        increaseProgressBar();
    }

    public static void writeDataToFile(DatabaseHolder databaseHolder) {
        String filename = "testFile.xml";
        Path outPath = FileSystems.getDefault().getPath(path, filename);
        try {
            Path outFile = Files.createFile(outPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XmlSerializer xmlSerializer = Xml.newSerializer(); //todo continue
        //out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));
        //out.writeObject(myPersonObject);

    }

    private static void chooseDatabaseLoadingType(DatabaseHolder databaseHolder, DatabaseUsage usage, String language) {
        ItemType itemType = null;
        switch (usage) {
            case Startup:
                loadRulesFromFileToHolderAndDatabaseAndReloadHolder(databaseHolder, FILENAMES, itemType);
                clear(databaseHolder, DatabaseUsage.ClearHolder, itemType);
                loadDataFromDatabaseToHolder(databaseHolder, itemType);
                break;
            case ReloadFromFile:
                clear(databaseHolder, DatabaseUsage.ClearDatabaseAndHolder, itemType);
                loadRulesFromFileToHolderAndDatabaseAndReloadHolder(databaseHolder, FILENAMES, itemType);
                clear(databaseHolder, DatabaseUsage.ClearHolder, itemType);
                loadDataFromDatabaseToHolder(databaseHolder, itemType);
                break;
            case ReloadFromDatabase:
                clear(databaseHolder, DatabaseUsage.ClearHolder, itemType);
                loadDataFromDatabaseToHolder(databaseHolder, itemType);
                break;
            case ReloadFromHolder:
                clear(databaseHolder, DatabaseUsage.ClearDatabase, itemType);
                loadDataFromHolderToDatabase(databaseHolder, itemType);
                break;
            case Another:
                break;
        }
        TranslationsHolder.loadAllTranslationsForLanguage(databaseHolder, language);
    }

    public static void populateAsync(@NonNull final DatabaseHolder databaseHolder, DatabaseUsage usage, String language) {
        PopulateDbAsync task = new PopulateDbAsync(databaseHolder, usage, language);
        task.execute();
    }

    public static void dbCheckup() {
        System.out.println("db data errors, need fix");   //todo implement db checkup and fix
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final DatabaseHolder databaseHolder;
        private final DatabaseUsage usage;
        private final String language;

        PopulateDbAsync(DatabaseHolder databaseHolder, DatabaseUsage usage, String language) {
            this.databaseHolder = databaseHolder;
            this.usage = usage;
            this.language = language;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            chooseDatabaseLoadingType(databaseHolder, usage, language);
            return null;
        }

        @Override
        protected void onPostExecute(final Void vo) {
            super.onPostExecute(vo);
            increaseProgressBar();
        }
    }

    private static void chooseDatabaseActions(DatabaseHolder databaseHolder) {
        ItemType itemType = ItemType.HeroPlayer; //todo delete
        System.out.println("AllFromDb " + itemType.getAllFromDatabase(databaseHolder)); //todo delete
        System.out.println("DaoRaceFromName " + (itemType.getDAO(databaseHolder)).getItemWithName("Test_race_name1")); //todo delete

        System.out.println("FirstFromList " + (itemType.getDatabaseList(databaseHolder).get(0))); //todo delete
        System.out.println("Id\"1\" FromMap " + (itemType.getDatabaseMap(databaseHolder).get(1))); //todo delete

        System.out.println("DaoNames " + itemType.getDAO(databaseHolder).getNames()); //todo delete
        System.out.println("FirstNameFromList " + itemType.getDatabaseList(databaseHolder).get(0).getName()); //todo delete
        System.out.println("Id\"1\" NameFromMap " + itemType.getDatabaseMap(databaseHolder).get(1).getName()); //todo delete

        System.out.println("FirstDescrFromList(ToHero) " + ((HeroPlayer) itemType.getDatabaseList(databaseHolder).get(0)).getHeroDescription().getHeroPlayer()); //todo delete
        System.out.println("Id\"1\" DescrFromMap(ToHero) " + ((HeroPlayer) itemType.getDatabaseMap(databaseHolder).get(1)).getHeroDescription().getHeroPlayer()); //todo delete

        // todo delete
        System.out.println("why?");
        System.out.println("Hero " + (itemType.getDAO(databaseHolder)).getItems());
        System.out.println("ItemHero " + (itemType.getDAO(databaseHolder)).getItemsAsItem());
        //*/

        //closeDatabase(databaseHolder); //todo need to close(?)
    }

    public static class ConnectDbAsync extends AsyncTask<Void, Void, Void> {

        private final DatabaseHolder databaseHolder;

        public ConnectDbAsync(DatabaseHolder databaseHolder) {
            this.databaseHolder = databaseHolder;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            chooseDatabaseActions(databaseHolder);
            return null;
        }
    }

}
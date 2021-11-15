package com.example.garneau.demo_tp3.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.garneau.demo_tp3.model.Location;


@Database(entities = {Location.class}, version = 1, exportSchema = true)
public abstract class LocationRoomDatabase extends RoomDatabase {
    // abstract, ce n'est pas une classe d'implémentation
    // l'implémentation est fournie par le room

    // Singleton
    public static LocationRoomDatabase INSTANCE;

    // DAO
    public abstract LocationDao LocationDao();

    public static synchronized LocationRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // Crée la BDD
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    LocationRoomDatabase.class, "todo_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomDatabaseCallBack)
                    .build();
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallBack =
            new RoomDatabase.Callback() {
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final LocationDao locationDao;

        public PopulateDbAsync(LocationRoomDatabase db) {
            locationDao = db.LocationDao();
        }



        @Override
        protected Void doInBackground(Void... voids) {
            locationDao.insert(new Location("what","else", "schmut", 46.793028, -71.2646));
            locationDao.insert(new Location("whot","else", "schmit", 46.79, -71.264));
            locationDao.insert(new Location("garneau","ecole", " adress garneau", 46.792028, -71.264176));

            return null;
        }
    }
}

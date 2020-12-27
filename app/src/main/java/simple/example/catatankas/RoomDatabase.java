package simple.example.catatankas;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simple.example.catatankas.entity.transaksi.Transaksi;
import simple.example.catatankas.entity.transaksi.TransaksiDAO;

@Database(entities = {Transaksi.class},exportSchema = false,version = 1)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private final static String DB_NAME="tr_db";
    public abstract TransaksiDAO transaksiDAO();

    private static volatile RoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static RoomDatabase getDatabase(final Context context) {
        Log.d("DB","get database instance"+INSTANCE);
        if (INSTANCE == null) {
            Log.d("DB","get database instance, is NULL");
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    Log.d("DB","get database instance, is NULL on Synchronized, build instance");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                    Log.d("DB","DB "+INSTANCE);
                }
            }
        }
        return INSTANCE;
    }

    private static androidx.room.RoomDatabase.Callback sRoomDatabaseCallback = new androidx.room.RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            Log.d("DB","create callback called");
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                Log.d("DB","callback called");
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TransaksiDAO dao = INSTANCE.transaksiDAO();

                Transaksi tr = new Transaksi();
                tr.setJenis(Transaksi.KREDIT);
                tr.setNilai(175000);
                tr.setTanggal(new Date());
                tr.setDeskripsi("Saldo awal");
                dao.insertTransaksi(tr);
            });
        }
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d("DB","Open callback called");
        }
    };
}

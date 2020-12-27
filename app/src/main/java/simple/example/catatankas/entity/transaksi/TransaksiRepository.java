package simple.example.catatankas.entity.transaksi;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import simple.example.catatankas.RoomDatabase;

public class TransaksiRepository {
    private TransaksiDAO transaksiDAO;
    private LiveData<List<Transaksi>> transaksiList;

    public TransaksiRepository(Application application) {
        RoomDatabase db = RoomDatabase.getDatabase(application);
        Log.d("TR REPO","Get database "+db);
        transaksiDAO = db.transaksiDAO();
        transaksiList = transaksiDAO.getAll();
    }

    public LiveData<List<Transaksi>> getTransaksiList() {
        return transaksiList;
    }

    void insert(Transaksi transaksi) {
        RoomDatabase.databaseWriteExecutor.execute(() -> {
            transaksiDAO.insertTransaksi(transaksi);
        });
    }
}

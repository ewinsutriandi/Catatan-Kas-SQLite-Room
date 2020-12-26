package simple.example.catatankas.entity.transaksi;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TransaksiDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertTransaksi(Transaksi tr);

    @Update
    public void updateTransaksi(Transaksi tr);

    @Delete
    public void deleteTransaksi(Transaksi tr);

    @Query("SELECT * FROM Transaksi ORDER BY tanggal DESC")
    public LiveData<List<Transaksi>> getAll();
}

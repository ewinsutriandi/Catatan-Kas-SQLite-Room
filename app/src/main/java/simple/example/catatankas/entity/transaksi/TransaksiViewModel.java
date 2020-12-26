package simple.example.catatankas.entity.transaksi;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TransaksiViewModel extends AndroidViewModel {

    private TransaksiRepository transaksiRepository;
    private final LiveData<List<Transaksi>> transaksiList;

    public TransaksiViewModel(Application application) {
        super(application);
        transaksiRepository = new TransaksiRepository(application);
        transaksiList = transaksiRepository.getTransaksiList();
    }

    public LiveData<List<Transaksi>> getTransaksiList() {
        return transaksiList;
    }

    public void insert(Transaksi tr) {
        transaksiRepository.insert(tr);
    }
}

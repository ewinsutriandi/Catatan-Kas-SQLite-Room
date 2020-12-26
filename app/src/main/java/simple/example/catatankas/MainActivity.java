package simple.example.catatankas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import simple.example.catatankas.entity.transaksi.Transaksi;
import simple.example.catatankas.entity.transaksi.TransaksiViewModel;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton btnTambahTransaksi;
    ImageButton btnChangeUserName;
    ListView lvDaftarTransaksi;
    TextView txNoData, txUsername, txSaldo;
    DaftarTransaksiAdapter adapter;
    List<Transaksi> daftarTransaksi = new ArrayList<Transaksi>();
    TransaksiViewModel transaksiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inisialisasiView();
        setupViewModel();
        setupListview();
    }

    private void setupViewModel() {
        Log.d("MAIN","Setup view model");
        transaksiViewModel = new TransaksiViewModel(getApplication());
        // monitor perubahan data
        transaksiViewModel.getTransaksiList().observe(this,transaksiList -> {
            // update daftar dan saldo bila terjadi perubahan
            daftarTransaksi = transaksiList;
            updateDaftarTransaksi();
            updateSaldo();
        });
    }

    private void updateDaftarTransaksi() {
        adapter.clear();
        adapter.addAll(daftarTransaksi);
    }

    private void updateSaldo() {
        Log.d("MAIN","Update Saldo");
        double saldo = 0;
        if (daftarTransaksi.size()>0) {
            txNoData.setVisibility(View.GONE);
            // hitung saldo
            for (Transaksi tr:daftarTransaksi) {
                //Log.d("MAIN","TR:"+tr.toJSONObject().toString());
                if (tr.getJenis().equals(Transaksi.DEBIT)) {
                    saldo -= tr.getNilai();
                } else {
                    saldo += tr.getNilai();
                }
            }
        } else {
            txNoData.setVisibility(View.VISIBLE);
        }
        txSaldo.setText(GenericUtility.formatUang(saldo));
    }

    private void inisialisasiView() {
        btnTambahTransaksi = findViewById(R.id.btn_add_transaksi);
        btnTambahTransaksi.setOnClickListener(view -> bukaFormTambahTransaksi());
        btnChangeUserName = findViewById(R.id.btn_change_username);
        btnChangeUserName.setOnClickListener(view -> changeUserName());
        lvDaftarTransaksi = findViewById(R.id.lv_transaksi);
        txNoData = findViewById(R.id.tx_nodata);
        txUsername = findViewById(R.id.tx_user_name);
        txUsername.setText(SharedPreferenceUtility.getUserName(this));
        txSaldo = findViewById(R.id.tx_saldo);
    }

    private void setupListview() {
        adapter = new DaftarTransaksiAdapter(this, daftarTransaksi);
        lvDaftarTransaksi.setAdapter(adapter);
    }


    private void bukaFormTambahTransaksi() {
        Intent intent = new Intent(this, FormTransaksiActivity.class);
        startActivityForResult(intent,2);
    }

    private void changeUserName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ganti nama");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferenceUtility.saveUserName(getApplicationContext(),input.getText().toString());
                Toast.makeText(getApplicationContext(),"Nama user berhasil disimpan",Toast.LENGTH_SHORT).show();
                txUsername.setText(SharedPreferenceUtility.getUserName(getApplicationContext()));
            }
        });
        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}
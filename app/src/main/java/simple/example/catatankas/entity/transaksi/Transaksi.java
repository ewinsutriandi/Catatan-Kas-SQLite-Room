package simple.example.catatankas.entity.transaksi;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import simple.example.catatankas.TimeConverter;

@Entity
public class Transaksi implements Serializable {
    public static final String DEBIT="DEBIT";
    public static final String KREDIT="KREDIT";
    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters({TimeConverter.class})
    private Date tanggal;
    private String deskripsi;
    private double nilai;
    private String jenis;

    public Transaksi() {
        this.tanggal = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public double getNilai() {
        return nilai;
    }

    public void setNilai(double nilai) {
        this.nilai = nilai;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public static Transaksi fromJSONObject(JSONObject obj) {
        Transaksi tr = new Transaksi();
        try {
            tr.setId(obj.getInt("id"));
            tr.setTanggal(new Date(obj.getLong("tanggal")));
            tr.setDeskripsi(obj.getString("deskripsi"));
            tr.setNilai(obj.getDouble("nilai"));
            tr.setJenis(obj.getString("jenis"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tr;
    }

    public JSONObject toJSONObject() {
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("id",this.id);
            jsonObj.put("tanggal",this.tanggal.getTime());
            jsonObj.put("jenis",this.jenis);
            jsonObj.put("nilai",this.nilai);
            jsonObj.put("deskripsi",this.deskripsi);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}

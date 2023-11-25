package stu.edu.vn.lab5_th;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;
import stu.edu.vn.lab5_th.model.CongViec;
import stu.edu.vn.lab5_th.util.FormatUtil;

public class ThemSuaCongViecActivity extends AppCompatActivity {
    EditText txtTen;
    TextView txtNgay,txtGio;
    ImageButton btnDatePicker,btnTimePicker;
    Button btnLuu;
    Calendar calendar;
    CongViec chon;
    int resultCode = 115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_cong_viec);
        addControls();
        getIntentData();
        addEvents();
    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTen);
        txtNgay=findViewById(R.id.txtNgay);
        txtGio=findViewById(R.id.txtGio);
        btnDatePicker=findViewById(R.id.btnDate);
        btnTimePicker=findViewById(R.id.btnTime);
        btnLuu=findViewById(R.id.btnLuu);
        calendar = Calendar.getInstance();
        chon=null;
    }

    private void getIntentData() {
        Intent intent= getIntent();
        if(intent.hasExtra("Chon")){
            chon=(CongViec) intent.getSerializableExtra("Chon");
            if(chon!=null){
                txtTen.setText(chon.getTen());
                calendar.setTime(chon.getHan());
                txtNgay.setText(FormatUtil.formatDate(calendar.getTime()));
                txtGio.setText(FormatUtil.formatTime(calendar.getTime()));
            }
            else{
                resetView();
            }
        }
        else{
            resetView();
        }
    }
    public void resetView(){
        txtTen.setText("");
        txtNgay.setText("dd/MM/yyyy");
        txtGio.setText("hh:mm aa");
        calendar=Calendar.getInstance();
        chon=null;
    }

    private void addEvents() {
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonNgay();
            }
        });
        btnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyChonGio();
            }
        });
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xuLyLuu();
            }
        });

    }
    private void xuLyChonNgay(){
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,monthOfYear);
                calendar.set(Calendar.DATE,dayOfMonth);
                txtNgay.setText(FormatUtil.formatDate(
                        calendar.getTime()
                ));
            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                ThemSuaCongViecActivity.this,
                listener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DATE)
        );
        datePickerDialog.show();
    }
    private void xuLyChonGio(){
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                txtGio.setText(FormatUtil.formatTime(
                        calendar.getTime())
                );
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                ThemSuaCongViecActivity.this,
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),false
        );
        timePickerDialog.show();
    }

    private void xuLyLuu(){
        if(chon==null){
            chon = new CongViec();
        }
        chon.setTen(txtTen.getText().toString());
        chon.setHan(calendar.getTime());
        Intent intent = getIntent();
        intent.putExtra("TRA", chon);
        setResult(resultCode,intent);
        finish();
    }
}
package stu.edu.vn.lab5_th;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import stu.edu.vn.lab5_th.model.CongViec;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fabThem;
    ArrayAdapter<CongViec> adapter;
    ListView lvCongViec;
    CongViec chon;
    int requestCode =113,resultCode=115;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        fabThem = findViewById(R.id.fabThem);
        adapter = new ArrayAdapter<>(
                MainActivity.this,
                android.R.layout.simple_list_item_1
        );
        lvCongViec=findViewById(R.id.lvCongViec);
        lvCongViec.setAdapter(adapter);
        chon= null;
    }

    private void addEvents() {

        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        MainActivity.this,
                        ThemSuaCongViecActivity.class
                );
                startActivityForResult(intent,requestCode);

            }
        });
        lvCongViec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position>=0 && position<adapter.getCount()){
                    Intent intent = new Intent(
                            MainActivity.this,
                            ThemSuaCongViecActivity.class
                    );
                    chon = adapter.getItem(position);
                    intent.putExtra("Chon",chon);


                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Xác nhận sửa công việc");
                    builder.setMessage("Bạn có chắc muốn sửa công việc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivityForResult(intent,requestCode);
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        lvCongViec.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                if (position >= 0 && position < adapter.getCount()) {
                    chon = adapter.getItem(position);
                    builder.setTitle("Xác nhận xóa công việc");
                    builder.setMessage("Bạn có chắc muốn xóa công việc");
                    builder.setIcon(android.R.drawable.ic_dialog_alert);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            adapter.remove(chon);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                }
                return false;
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == this.requestCode) {
            if (resultCode == this.resultCode) {
                if (data.hasExtra("TRA")) {
                    CongViec tra = (CongViec) data.getSerializableExtra("TRA");
                    if (chon == null) {
                        adapter.add(tra);
                    } else {
                        chon.setTen(tra.getTen());
                        chon.setHan(tra.getHan());
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
        chon = null;
    }

}
package cn.zql.vgbill;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zql.vgbill.Tools.DBHelper;
import cn.zql.vgbill.Tools.Values;

public class Details extends AppCompatActivity {

    @BindView(R.id.et_details_name)
    EditText etDetailsName;
    @BindView(R.id.et_details_phone)
    EditText etDetailsPhone;
    @BindView(R.id.et_details_address)
    EditText etDetailsAddress;
    @BindView(R.id.et_details_size)
    EditText etDetailsSize;
    @BindView(R.id.et_details_Buydate)
    EditText etDetailsBuydate;
    @BindView(R.id.et_details_money)
    EditText etDetailsMoney;
    @BindView(R.id.et_details_maker)
    EditText etDetailsMaker;
    @BindView(R.id.et_details_makeDate)
    EditText etDetailsMakeDate;
    @BindView(R.id.et_details_payment)
    EditText etDetailsPayment;
    @BindView(R.id.et_details_service)
    EditText etDetailsService;
    @BindView(R.id.btn_details_ok)
    Button btnDetailsOk;
    @BindView(R.id.btn_details_cancel)
    Button btnDetailsCancel;

    DBHelper myDBHelper;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private Values value;
    private String[] sql;
    private String[] et;
    private Boolean similar = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        myDBHelper = new DBHelper(this);
        ButterKnife.bind(this);
        initView();
        initDate();
    }

    private void initDate() {
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                sql = new String[]{value.getCustomerName(), value.getCustomerPhone(), value.getCustomerAddress(),
                        value.getSize(), value.getBuyDate(), value.getMoney(), value.getMaker(),
                        value.getMakerDate(), value.getPayment(), value.getService()};

                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();

    }

    private void initView() {
        Intent intent = this.getIntent();
        if (intent != null) {
            value = new Values();

            value.setService(intent.getStringExtra(DBHelper.SERVICE));
            value.setPayment(intent.getStringExtra(DBHelper.PAYMENT));
            value.setSize(intent.getStringExtra(DBHelper.SIZE));
            value.setMoney(intent.getStringExtra(DBHelper.MONEY));
            value.setMaker(intent.getStringExtra(DBHelper.MAKER));
            value.setMakerDate(intent.getStringExtra(DBHelper.MAKERDATE));
            value.setBuyDate(intent.getStringExtra(DBHelper.BUYDATE));
            value.setCustomerAddress(intent.getStringExtra(DBHelper.CUSTOMERADDRESS));
            value.setCustomerPhone(intent.getStringExtra(DBHelper.CUSTOMERPHONE));
            value.setCustomerName(intent.getStringExtra(DBHelper.CUSTOMERNAME));
            value.setId(Integer.valueOf(intent.getStringExtra(DBHelper.ID)));
            //  System.out.println(intent.getStringExtra(DBHelper.ID));
            etDetailsAddress.setText(value.getCustomerAddress());
            etDetailsBuydate.setText(value.getBuyDate());
            etDetailsMakeDate.setText(value.getMakerDate());
            etDetailsMaker.setText(value.getMaker());
            etDetailsMoney.setText(value.getMoney());
            etDetailsName.setText(value.getCustomerName());
            etDetailsPayment.setText(value.getPayment());
            etDetailsPhone.setText(value.getCustomerPhone());
            etDetailsSize.setText(value.getSize());
            etDetailsService.setText(value.getService());
        }
    }

    @OnClick({R.id.btn_details_ok, R.id.btn_details_cancel, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_details_ok:
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                values.put(DBHelper.CUSTOMERNAME, etDetailsName.getText().toString());
                values.put(DBHelper.CUSTOMERPHONE, etDetailsPhone.getText().toString());
                values.put(DBHelper.CUSTOMERADDRESS, etDetailsAddress.getText().toString());
                values.put(DBHelper.SIZE, etDetailsSize.getText().toString());
                values.put(DBHelper.BUYDATE, etDetailsBuydate.getText().toString());
                values.put(DBHelper.MONEY, etDetailsMoney.getText().toString());
                values.put(DBHelper.MAKER, etDetailsMaker.getText().toString());
                values.put(DBHelper.MAKERDATE, etDetailsMakeDate.getText().toString());
                values.put(DBHelper.PAYMENT, etDetailsPayment.getText().toString());
                values.put(DBHelper.SERVICE, etDetailsService.getText().toString());

                if (etDetailsPhone.getText().length() != 11) {
                    Toast.makeText(this, "客户手机号码错误", Toast.LENGTH_LONG).show();
                } else {
                    db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                    Toast.makeText(this, "保存修改成功", Toast.LENGTH_LONG).show();
                    db.close();
                    finish();
                }
                break;
            case R.id.btn_details_cancel:
                et = new String[]{etDetailsName.getText().toString(), etDetailsPhone.getText().toString(), etDetailsAddress.getText().toString(),
                        etDetailsSize.getText().toString(), etDetailsBuydate.getText().toString(), etDetailsMoney.getText().toString(),
                        etDetailsMaker.getText().toString(), etDetailsMakeDate.getText().toString(), etDetailsPayment.getText().toString(),
                        etDetailsService.getText().toString()};
                //判断2个数组是否相等
                System.out.println(et[2]);
                System.out.println(sql[2]);
                System.out.println("similar =" + similar);
                for (int i = 0; i < 10; i++) {//循环遍历对比每个位置的元素
                    if (!sql[i].equals(et[i])) {//只要出现一次不相等，那么2个数组就不相等
                        similar = false;
                    }
                }
                System.out.println("similar=" + similar);
                if (similar.equals(true)) {
                    System.out.println("similar=" + similar);
                    System.out.println("结果一致");
                    finish();
                } else {
                      System.out.println("结果不一致");
                    new AlertDialog.Builder(Details.this)
                            .setTitle("提示框")
                            .setMessage("是否保存当前内容?")
                            .setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();

                                            values.put(DBHelper.CUSTOMERNAME, etDetailsName.getText().toString());
                                            values.put(DBHelper.CUSTOMERPHONE, etDetailsPhone.getText().toString());
                                            values.put(DBHelper.CUSTOMERADDRESS, etDetailsAddress.getText().toString());
                                            values.put(DBHelper.SIZE, etDetailsSize.getText().toString());
                                            values.put(DBHelper.BUYDATE, etDetailsBuydate.getText().toString());
                                            values.put(DBHelper.MONEY, etDetailsMoney.getText().toString());
                                            values.put(DBHelper.MAKER, etDetailsMaker.getText().toString());
                                            values.put(DBHelper.MAKERDATE, etDetailsMakeDate.getText().toString());
                                            values.put(DBHelper.PAYMENT, etDetailsPayment.getText().toString());
                                            values.put(DBHelper.SERVICE, etDetailsService.getText().toString());

                                            db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                                            Toast.makeText(Details.this, "保存修改成功", Toast.LENGTH_LONG).show();
                                            db.close();
                                            finish();
                                        }
                                    })
                            .setNegativeButton("no",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                }
             //   System.out.println("w zoudaozhe;l");
                break;
            case R.id.iv_back:
                et = new String[]{etDetailsName.getText().toString(), etDetailsPhone.getText().toString(), etDetailsAddress.getText().toString(),
                        etDetailsSize.getText().toString(), etDetailsBuydate.getText().toString(), etDetailsMoney.getText().toString(),
                        etDetailsMaker.getText().toString(), etDetailsMakeDate.getText().toString(), etDetailsPayment.getText().toString(),
                        etDetailsService.getText().toString()};
                //判断2个数组是否相等
                for (int i = 0; i < sql.length; i++) {//循环遍历对比每个位置的元素
                    if (!sql[i].equals(et[i])) {//只要出现一次不相等，那么2个数组就不相等
                        similar = false;
                    }
                }
                if (similar == false) {
                    new AlertDialog.Builder(Details.this)
                            .setTitle("提示框")
                            .setMessage("是否保存当前内容?")
                            .setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();

                                            values.put(DBHelper.CUSTOMERNAME, etDetailsName.getText().toString());
                                            values.put(DBHelper.CUSTOMERPHONE, etDetailsPhone.getText().toString());
                                            values.put(DBHelper.CUSTOMERADDRESS, etDetailsAddress.getText().toString());
                                            values.put(DBHelper.SIZE, etDetailsSize.getText().toString());
                                            values.put(DBHelper.BUYDATE, etDetailsBuydate.getText().toString());
                                            values.put(DBHelper.MONEY, etDetailsMoney.getText().toString());
                                            values.put(DBHelper.MAKER, etDetailsMaker.getText().toString());
                                            values.put(DBHelper.MAKERDATE, etDetailsMakeDate.getText().toString());
                                            values.put(DBHelper.PAYMENT, etDetailsPayment.getText().toString());
                                            values.put(DBHelper.SERVICE, etDetailsService.getText().toString());

                                            db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                                            Toast.makeText(Details.this, "保存修改成功", Toast.LENGTH_LONG).show();
                                            db.close();
                                            finish();
                                        }
                                    })
                            .setNegativeButton("no",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    }).show();
                }  else {
                    finish();
                }
                break;
        }
    }

    public void onBackPressed() {
        et = new String[]{etDetailsName.getText().toString(), etDetailsPhone.getText().toString(), etDetailsAddress.getText().toString(),
                etDetailsSize.getText().toString(), etDetailsBuydate.getText().toString(), etDetailsMoney.getText().toString(),
                etDetailsMaker.getText().toString(), etDetailsMakeDate.getText().toString(), etDetailsPayment.getText().toString(),
                etDetailsService.getText().toString()};
        //判断2个数组是否相等
        for (int i = 0; i < sql.length; i++) {//循环遍历对比每个位置的元素
            if (!sql[i].equals(et[i])) {//只要出现一次不相等，那么2个数组就不相等
                similar = false;
            }
        }
        if (similar == false) {
            new AlertDialog.Builder(Details.this)
                    .setTitle("提示框")
                    .setMessage("是否保存当前内容?")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();

                                    values.put(DBHelper.CUSTOMERNAME, etDetailsName.getText().toString());
                                    values.put(DBHelper.CUSTOMERPHONE, etDetailsPhone.getText().toString());
                                    values.put(DBHelper.CUSTOMERADDRESS, etDetailsAddress.getText().toString());
                                    values.put(DBHelper.SIZE, etDetailsSize.getText().toString());
                                    values.put(DBHelper.BUYDATE, etDetailsBuydate.getText().toString());
                                    values.put(DBHelper.MONEY, etDetailsMoney.getText().toString());
                                    values.put(DBHelper.MAKER, etDetailsMaker.getText().toString());
                                    values.put(DBHelper.MAKERDATE, etDetailsMakeDate.getText().toString());
                                    values.put(DBHelper.PAYMENT, etDetailsPayment.getText().toString());
                                    values.put(DBHelper.SERVICE, etDetailsService.getText().toString());

                                    db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                                    Toast.makeText(Details.this, "保存修改成功", Toast.LENGTH_LONG).show();
                                    db.close();
                                    finish();
                                }
                            })
                    .setNegativeButton("no",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
        }  else {
            finish();
        }
    }

}

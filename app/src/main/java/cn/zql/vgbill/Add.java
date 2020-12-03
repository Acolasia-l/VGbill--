package cn.zql.vgbill;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zql.vgbill.Tools.DBHelper;
import cn.zql.vgbill.Tools.Values;

public class Add extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_add_name)
    EditText etAddName;
    @BindView(R.id.et_add_phone)
    EditText etAddPhone;
    @BindView(R.id.et_add_address)
    EditText etAddAddress;
    @BindView(R.id.et_add_size)
    EditText etAddSize;
    @BindView(R.id.et_add_Buydate)
    EditText etAddBuydate;
    @BindView(R.id.et_add_money)
    EditText etAddMoney;
    @BindView(R.id.et_add_maker)
    EditText etAddMaker;
    @BindView(R.id.et_add_makeDate)
    EditText etAddMakeDate;
    @BindView(R.id.et_add_payment)
    EditText etAddPayment;
    @BindView(R.id.et_add_service)
    EditText etAddService;
    @BindView(R.id.btn_add_ok)
    Button btnAddOk;
    @BindView(R.id.btn_add_cancel)
    Button btnAddCancel;

    DBHelper myDBHelper;
    Values value;
    private String[] et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        ButterKnife.bind(this);
    }

    private void initView() {
        myDBHelper = new DBHelper(this);
        value = new Values();
        //SQLiteDatabase db = myDBHelper.getWritableDatabase();

    }

    @OnClick({R.id.iv_back, R.id.btn_add_ok, R.id.btn_add_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                et = new String[]{etAddName.getText().toString(), etAddPhone.getText().toString(), etAddAddress.getText().toString(),
                        etAddSize.getText().toString(), etAddBuydate.getText().toString(), etAddMoney.getText().toString(),
                        etAddMaker.getText().toString(), etAddMakeDate.getText().toString(), etAddPayment.getText().toString(),
                        etAddService.getText().toString()};
                if (et.equals("")) {
                    new AlertDialog.Builder(Add.this)
                            .setTitle("提示框")
                            .setMessage("是否保存当前内容?")
                            .setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();

                                            values.put(DBHelper.CUSTOMERNAME, etAddName.getText().toString());
                                            values.put(DBHelper.CUSTOMERPHONE, etAddPhone.getText().toString());
                                            values.put(DBHelper.CUSTOMERADDRESS, etAddAddress.getText().toString());
                                            values.put(DBHelper.SIZE, etAddSize.getText().toString());
                                            values.put(DBHelper.BUYDATE, etAddBuydate.getText().toString());
                                            values.put(DBHelper.MONEY, etAddMoney.getText().toString());
                                            values.put(DBHelper.MAKER, etAddMaker.getText().toString());
                                            values.put(DBHelper.MAKERDATE, etAddMakeDate.getText().toString());
                                            values.put(DBHelper.PAYMENT, etAddPayment.getText().toString());
                                            values.put(DBHelper.SERVICE, etAddService.getText().toString());

                                            db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                                            Toast.makeText(Add.this, "保存修改成功", Toast.LENGTH_LONG).show();
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
                } else {
                    finish();
                }
            case R.id.btn_add_cancel:
                et = new String[]{etAddName.getText().toString(), etAddPhone.getText().toString(), etAddAddress.getText().toString(),
                        etAddSize.getText().toString(), etAddBuydate.getText().toString(), etAddMoney.getText().toString(),
                        etAddMaker.getText().toString(), etAddMakeDate.getText().toString(), etAddPayment.getText().toString(),
                        etAddService.getText().toString()};
                if (et.equals("")) {
                    new AlertDialog.Builder(Add.this)
                            .setTitle("提示框")
                            .setMessage("是否保存当前内容?")
                            .setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                            ContentValues values = new ContentValues();

                                            values.put(DBHelper.CUSTOMERNAME, etAddName.getText().toString());
                                            values.put(DBHelper.CUSTOMERPHONE, etAddPhone.getText().toString());
                                            values.put(DBHelper.CUSTOMERADDRESS, etAddAddress.getText().toString());
                                            values.put(DBHelper.SIZE, etAddSize.getText().toString());
                                            values.put(DBHelper.BUYDATE, etAddBuydate.getText().toString());
                                            values.put(DBHelper.MONEY, etAddMoney.getText().toString());
                                            values.put(DBHelper.MAKER, etAddMaker.getText().toString());
                                            values.put(DBHelper.MAKERDATE, etAddMakeDate.getText().toString());
                                            values.put(DBHelper.PAYMENT, etAddPayment.getText().toString());
                                            values.put(DBHelper.SERVICE, etAddService.getText().toString());

                                            db.update(DBHelper.TABLE, values, DBHelper.ID + "=?", new String[]{value.getId().toString()});
                                            Toast.makeText(Add.this, "保存修改成功", Toast.LENGTH_LONG).show();
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
                } else {
                    finish();
                }
                break;
            case R.id.btn_add_ok:
                SQLiteDatabase db = myDBHelper.getWritableDatabase();
                ContentValues values = new ContentValues();

                if ("".equals(etAddName.getText().toString())) {
                    Toast.makeText(Add.this, "顾客姓名不能为空", Toast.LENGTH_LONG).show();
                    return;
                }
                values.put(DBHelper.CUSTOMERNAME, etAddName.getText().toString());
                values.put(DBHelper.CUSTOMERPHONE, etAddPhone.getText().toString());
                values.put(DBHelper.CUSTOMERADDRESS, etAddAddress.getText().toString());
                values.put(DBHelper.SIZE, etAddSize.getText().toString());
                values.put(DBHelper.BUYDATE, etAddBuydate.getText().toString());
                values.put(DBHelper.MONEY, etAddMoney.getText().toString());
                values.put(DBHelper.MAKER, etAddMaker.getText().toString());
                values.put(DBHelper.MAKERDATE, etAddMakeDate.getText().toString());
                values.put(DBHelper.PAYMENT, etAddPayment.getText().toString());
                values.put(DBHelper.SERVICE, etAddService.getText().toString());

                if (etAddPhone.getText().length() != 11) {
                    Toast.makeText(this, "客户手机号码为空或格式错误", Toast.LENGTH_LONG).show();
                } else {
                    db.insert(DBHelper.TABLE, null, values);
                    Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
                    System.out.println("数据库" + db);
                    db.close();
                    finish();
                }
                break;
        }
    }
    public void onBackPressed() {
       String []et = new String[]{etAddName.getText().toString(), etAddPhone.getText().toString(), etAddAddress.getText().toString(),
                etAddSize.getText().toString(), etAddBuydate.getText().toString(), etAddMoney.getText().toString(),
                etAddMaker.getText().toString(), etAddMakeDate.getText().toString(), etAddPayment.getText().toString(),
                etAddService.getText().toString()};
       Boolean blank = true;
       for (int i = 0;i<et.length;i++){
           if (!et[i].equals("")){
               blank = false;
           }
       }
        if (blank.equals(false)) {
            new AlertDialog.Builder(Add.this)
                    .setTitle("提示框")
                    .setMessage("是否保存当前内容?")
                    .setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SQLiteDatabase db = myDBHelper.getWritableDatabase();
                                    ContentValues values = new ContentValues();

                                    if ("".equals(etAddName.getText().toString())) {
                                        Toast.makeText(Add.this, "顾客姓名不能为空", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    values.put(DBHelper.CUSTOMERNAME, etAddName.getText().toString());
                                    values.put(DBHelper.CUSTOMERPHONE, etAddPhone.getText().toString());
                                    values.put(DBHelper.CUSTOMERADDRESS, etAddAddress.getText().toString());
                                    values.put(DBHelper.SIZE, etAddSize.getText().toString());
                                    values.put(DBHelper.BUYDATE, etAddBuydate.getText().toString());
                                    values.put(DBHelper.MONEY, etAddMoney.getText().toString());
                                    values.put(DBHelper.MAKER, etAddMaker.getText().toString());
                                    values.put(DBHelper.MAKERDATE, etAddMakeDate.getText().toString());
                                    values.put(DBHelper.PAYMENT, etAddPayment.getText().toString());
                                    values.put(DBHelper.SERVICE, etAddService.getText().toString());

                                    if (etAddPhone.getText().length() != 11) {
                                        Toast.makeText(Add.this, "客户手机号码为空或格式错误", Toast.LENGTH_LONG).show();
                                    } else {
                                        db.insert(DBHelper.TABLE, null, values);
                                        Toast.makeText(Add.this, "添加成功", Toast.LENGTH_LONG).show();
                                        System.out.println("数据库" + db);
                                        db.close();
                                        finish();
                                    }
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
    //获取当前时间
    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }
}

package cn.zql.vgbill;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zql.vgbill.Tools.DBHelper;
import cn.zql.vgbill.Tools.MyData;
import cn.zql.vgbill.Tools.Values;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.lv_bill)
    ListView lvBill;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_main_result)
    TextView tvMainResult;
    @BindView(R.id.tv_main_blank)
    TextView tvMainBlank;

    private List<Values> valuesList;
    DBHelper myDBHelper;
    private String[] search;
    Values value;

    /**
     * 上次点击返回键的时间
     */
    private long lastBackPressed;
    /**
     * 两次点击的间隔时间
     */
    private static final int QUIT_INTERVAL = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDBHelper = new DBHelper(this);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        valuesList = new ArrayList<>();
        if (MyData.blank == true){
            tvMainBlank.setVisibility(View.VISIBLE);
        }else {
            tvMainBlank.setVisibility(View.GONE);
        }
        //数据库
        SQLiteDatabase sqLiteDatabase = myDBHelper.getReadableDatabase();
        //查询数据库中的数据
        Cursor cursor = sqLiteDatabase.query(myDBHelper.TABLE, null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            Values values;
            while (!cursor.isAfterLast()) {
                //实例化values对象
                values = new Values();

                //把数据库中的一个表中的数据赋值给values
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(myDBHelper.ID))));
                values.setCustomerName(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERNAME)));
                values.setCustomerPhone(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERPHONE)));
                values.setCustomerAddress(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERADDRESS)));
                values.setBuyDate(cursor.getString(cursor.getColumnIndex(myDBHelper.BUYDATE)));
                values.setMaker(cursor.getString(cursor.getColumnIndex(myDBHelper.MAKER)));
                values.setMakerDate(cursor.getString(cursor.getColumnIndex(myDBHelper.MAKERDATE)));
                values.setMoney(cursor.getString(cursor.getColumnIndex(myDBHelper.MONEY)));
                values.setSize(cursor.getString(cursor.getColumnIndex(myDBHelper.SIZE)));
                values.setPayment(cursor.getString(cursor.getColumnIndex(myDBHelper.PAYMENT)));
                values.setService(cursor.getString(cursor.getColumnIndex(myDBHelper.SERVICE)));
                //将values对象存入list对象数组中
                valuesList.add(values);
                cursor.moveToNext();
            }
        }
        cursor.close();
        sqLiteDatabase.close();
        final MyBaseadpter myBaseadpter = new MyBaseadpter(valuesList, this, R.layout.item_bill);
        lvBill.setAdapter(myBaseadpter);
        //单击查看详情
        lvBill.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, Details.class);
            Values values = (Values) lvBill.getItemAtPosition(position);
            intent.putExtra(DBHelper.CUSTOMERNAME, values.getCustomerName().trim());
            intent.putExtra(DBHelper.CUSTOMERPHONE, values.getCustomerPhone().trim());
            intent.putExtra(DBHelper.CUSTOMERADDRESS, values.getCustomerAddress().trim());
            intent.putExtra(DBHelper.ID, values.getId().toString().trim());
            intent.putExtra(DBHelper.BUYDATE, values.getBuyDate().trim());
            intent.putExtra(DBHelper.MONEY, values.getMoney().trim());
            intent.putExtra(DBHelper.SIZE, values.getSize().trim());
            intent.putExtra(DBHelper.MAKER, values.getMaker().trim());
            intent.putExtra(DBHelper.MAKERDATE, values.getMakerDate().trim());
            intent.putExtra(DBHelper.PAYMENT, values.getPayment().trim());
            intent.putExtra(DBHelper.SERVICE, values.getService().trim());
            startActivity(intent);
        });
        lvBill.setOnItemLongClickListener((parent, view, position, id) -> {
            final Values values = (Values) lvBill.getItemAtPosition(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("提示框")
                    .setMessage("是否确定删除？")
                    .setPositiveButton("确定",
                            (dialog, which) -> {
                                SQLiteDatabase sqLiteDatabase1 = myDBHelper.getWritableDatabase();
                                sqLiteDatabase1.delete(DBHelper.TABLE, DBHelper.ID + "=?", new String[]{String.valueOf(values.getId())});
                                sqLiteDatabase1.close();
                                myBaseadpter.removeItem(position);
                                lvBill.post(() -> myBaseadpter.notifyDataSetChanged());
                                MainActivity.this.onResume();
                            })
                    .setNegativeButton("取消", null).show();
            return true;
        });
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!etSearch.getText().toString().equals("")) {
                    MyData.blank = false;
                    tvMainBlank.setVisibility(View.GONE);
                    value = new Values();
                    valuesList = new ArrayList<>();
                    final MyBaseadpter myBaseadpter = new MyBaseadpter(valuesList, getApplicationContext(), R.layout.item_bill);
                    lvBill.setAdapter(myBaseadpter);
                    Search();
                    myBaseadpter.notifyDataSetChanged();
                    if (MyData.result == true) {
                        tvMainResult.setVisibility(View.VISIBLE);
                    } else {
                        tvMainResult.setVisibility(View.GONE);
                    }
                } else {
                    MyData.blank = true;
                    if (MyData.blank){
                        tvMainBlank.setVisibility(View.VISIBLE);
                    }else {
                        tvMainBlank.setVisibility(View.GONE);
                    }
                    tvMainResult.setVisibility(View.GONE);
                }
            }
        });
        //回车 搜索键
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“搜索”键*/
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    String key = etSearch.getText().toString().trim();
                    //  这里记得一定要将键盘隐藏了
                    hideKeyBoard();
                    return true;
                }
                return false;
            }
        });
    }

    private void hideKeyBoard() {
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @OnClick({R.id.iv_search, R.id.iv_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                value = new Values();
                valuesList = new ArrayList<>();
                final MyBaseadpter myBaseadpter = new MyBaseadpter(valuesList, this, R.layout.item_bill);
                lvBill.setAdapter(myBaseadpter);
                if (MyData.search == true) {
                    etSearch.setVisibility(View.VISIBLE);
                    //获取焦点并弹出软键盘
                    etSearch.requestFocus();
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(etSearch,0);
                    MyData.search = false;
                } else if (!etSearch.getText().toString().equals("")) {
                    Search();
                    myBaseadpter.notifyDataSetChanged();
                    hideKeyBoard();
                    if (MyData.result == true) {
                        tvMainResult.setVisibility(View.VISIBLE);
                    } else {
                        tvMainResult.setVisibility(View.GONE);
                    }
                } else {
                    myBaseadpter.notifyDataSetChanged();
                }
                break;
            case R.id.iv_add:
                startActivity(new Intent(this, Add.class));
                break;

        }
    }

    class MyBaseadpter extends BaseAdapter {
        private List<Values> valuesList;
        private Context context;
        private int layoutId;

        public MyBaseadpter(List<Values> valuesList, Context context, int layoutId) {
            this.valuesList = valuesList;
            this.context = context;
            this.layoutId = layoutId;
        }

        @Override
        public int getCount() {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.get(position);
            else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_bill, parent, false);
                viewHolder.customerName = convertView.findViewById(R.id.tv_item_name);
                viewHolder.address = convertView.findViewById(R.id.tv_item_address);
                viewHolder.time = convertView.findViewById(R.id.tv_item_time);
                viewHolder.phone = convertView.findViewById(R.id.tv_item_phone);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String name = valuesList.get(position).getCustomerName();
            viewHolder.customerName.setText(name);
            viewHolder.address.setText(valuesList.get(position).getCustomerAddress());
            viewHolder.phone.setText(valuesList.get(position).getCustomerPhone());
            viewHolder.time.setText(valuesList.get(position).getMakerDate());
            //  System.out.println(valuesList);
            return convertView;
        }

        public void removeItem(int position) {
            this.valuesList.remove(position);
        }

    }

    class ViewHolder {
        TextView customerName;
        TextView address;
        TextView time;
        TextView phone;
    }

    /**
     * 重写onBackPressed()
     */
    @Override
    public void onBackPressed() {
        tvMainBlank.setVisibility(View.GONE);
        etSearch.setVisibility(View.GONE);
        tvMainResult.setVisibility(View.GONE);
        etSearch.setText("");
        MyData.search = true;
        MyData.blank = false;
        initView();
        long backPressed = System.currentTimeMillis();
        if (backPressed - lastBackPressed > QUIT_INTERVAL) {
            lastBackPressed = backPressed;
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG).show();
        } else {
            finish();
            System.exit(0);
        }
    }

    //返回时 刷新数据（部分ui）
    @Override
    protected void onResume() {
        super.onResume();
        initView();
        etSearch.setVisibility(View.GONE);
        tvMainResult.setVisibility(View.GONE);
        tvMainBlank.setVisibility(View.GONE);
        MyData.search = true;
    }

    //搜索功能
    public void Search() {
        //数据库
        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        //查询数据库中的数据
        //"select * from table_search where username like '%" + searchData + "%' or password like '%" + searchData + "%'", null)
        Cursor cursor = db.rawQuery(
                "select * from " + DBHelper.TABLE + " where " + DBHelper.CUSTOMERNAME + " like '%" + etSearch.getText().toString().trim() + "%'", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                //把数据库中的一个表中的数据赋值给values
                value.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(myDBHelper.ID))));
                value.setCustomerName(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERNAME)));
                value.setCustomerPhone(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERPHONE)));
                value.setCustomerAddress(cursor.getString(cursor.getColumnIndex(myDBHelper.CUSTOMERADDRESS)));
                value.setBuyDate(cursor.getString(cursor.getColumnIndex(myDBHelper.BUYDATE)));
                value.setMaker(cursor.getString(cursor.getColumnIndex(myDBHelper.MAKER)));
                value.setMakerDate(cursor.getString(cursor.getColumnIndex(myDBHelper.MAKERDATE)));
                value.setMoney(cursor.getString(cursor.getColumnIndex(myDBHelper.MONEY)));
                value.setSize(cursor.getString(cursor.getColumnIndex(myDBHelper.SIZE)));
                value.setPayment(cursor.getString(cursor.getColumnIndex(myDBHelper.PAYMENT)));
                value.setService(cursor.getString(cursor.getColumnIndex(myDBHelper.SERVICE)));
                //将values对象存入list对象数组中
                valuesList.add(value);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        if (valuesList != null && valuesList.size() > 0) {
            MyData.result = false;
        } else {
            MyData.result = true;
        }
    }
//    //返回时刷新ui
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(intent);
//    }

}

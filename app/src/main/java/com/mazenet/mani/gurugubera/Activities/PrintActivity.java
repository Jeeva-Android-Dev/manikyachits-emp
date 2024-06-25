package com.mazenet.mani.gurugubera.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.RT_Printer.BluetoothPrinter.BLUETOOTH.BluetoothPrintDriver;

import com.mazenet.mani.gurugubera.Model.successmsgmodel;
import com.mazenet.mani.gurugubera.R;
import com.mazenet.mani.gurugubera.Retrofit.ICallService;
import com.mazenet.mani.gurugubera.Retrofit.RetrofitBuilder;
import com.mazenet.mani.gurugubera.Utilities.BaseActivity;
import com.mazenet.mani.gurugubera.Utilities.BaseUtils;
import com.mazenet.mani.gurugubera.Utilities.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.ParseException;
import java.util.HashMap;


public class PrintActivity extends BaseActivity {


    // Debugging
    private static final String TAG = "BloothPrinterActivity";
    private static final boolean D = true;
    String recptdate = "", cmpny_street = "";

    String Cusname = "", receivedamount = "", Groupname = "", ReceiptAmount = "", ticketno = "", paymode = "", Receiptno = "", penaltyamnt = "", totaldue = "", bonusamnt = "", username = "", due = "", cheno = "", chedate = "", chebank = "", subid = "", mobile = "", recptno = "", chequebranch = "", isprinted = "";
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";

    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public static int revBytes = 0;
    public static boolean isHex = false;
    public static final int REFRESH = 8;

    byte[] cc = new byte[]{0x1B, 0x21, 0x00};  // 0- normal size text
    byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
    private Button mBtnConnetBluetoothDevice = null;
    private Button mBtnPrint = null;

    private String mConnectedDeviceName = null;
    // Local Bluetooth adapter
    private BluetoothAdapter mBluetoothAdapter = null;
    // Member object for the chat services
    private BluetoothPrintDriver mChatService = null;
    private String transactionno;
    private String transactiondate;

    public PrintActivity() throws ParseException {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void builddilogdecline() {

        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(
                PrintActivity.this, R.style.MyDialogTheme);
        alertDialog.setTitle("Information");
//        alertDialog.setIcon(R.drawable.ic_print_red);
        alertDialog
                .setMessage("Connect Device to Print the Receipt");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Continue",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        username = getPrefsString("username", "");
        if (D) Log.e(TAG, "+++ ON CREATE +++");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // �����ʼ��
        InitUIControl();
        //fininvoice = "vjv-8";
        try {
            Intent it = getIntent();
            Cusname = it.getStringExtra("Cusname");
            receivedamount = it.getStringExtra("receivedamount");
            Groupname = it.getStringExtra("groupname");
            ticketno = it.getStringExtra("ticketno");
            paymode = it.getStringExtra("paymentmode");
            penaltyamnt = it.getStringExtra("penalty");
            totaldue = it.getStringExtra("totaldue");
            bonusamnt = it.getStringExtra("bonus");
            due = it.getStringExtra("installmentno");
            cheno = it.getStringExtra("chequeno");
            chebank = it.getStringExtra("chequebank");
            chedate = it.getStringExtra("chequedate");
            chequebranch = it.getStringExtra("chequebranch");
            transactionno = it.getStringExtra("transactionno");
            transactiondate = it.getStringExtra("transactiondate");
            subid = it.getStringExtra("customerid");
            mobile = it.getStringExtra("customermobile");
            recptno = it.getStringExtra("recptno");
            recptdate = it.getStringExtra("recptdate");
            isprinted = it.getStringExtra("isprinted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void InitUIControl() {

        mBtnPrint = (Button) findViewById(R.id.btn_print);
        mBtnConnetBluetoothDevice = (Button) findViewById(R.id.btn_connect_bluetooth_device);
        mBtnConnetBluetoothDevice.setOnClickListener(mBtnConnetBluetoothDeviceOnClickListener);
        mBtnPrint.setOnClickListener(mBtnPrintOnClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (D) Log.e(TAG, "++ ON START ++");


        if (!mBluetoothAdapter.isEnabled()) {

            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            builddilogdecline();
            if (mChatService == null) setupChat();

        }
    }

    @Override
    public synchronized void onResume() {
        super.onResume();
        if (D) Log.e(TAG, "+ ON RESUME +");

        if (mChatService != null) {
            if (mChatService.getState() == BluetoothPrintDriver.STATE_NONE) {

                mChatService.start();
            }
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        mChatService = new BluetoothPrintDriver(this, mHandler);
    }

    @Override
    public synchronized void onPause() {
        super.onPause();
        if (D) Log.e(TAG, "- ON PAUSE -");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (D) Log.e(TAG, "-- ON STOP --");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stop the Bluetooth chat services
        if (mChatService != null) mChatService.stop();
        if (D) Log.e(TAG, "--- ON DESTROY ---");
    }

    @SuppressLint("NewApi")
    private void ensureDiscoverable() {
        if (D) Log.d(TAG, "ensure discoverable");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (mBluetoothAdapter.getScanMode() !=
                BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);
        }
    }


    // The Handler that gets information back from the BluetoothChatService
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    if (D) Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    switch (msg.arg1) {
                        case BluetoothPrintDriver.STATE_CONNECTED:

                            break;
                        case BluetoothPrintDriver.STATE_CONNECTING:

                            break;
                        case BluetoothPrintDriver.STATE_LISTEN:
                        case BluetoothPrintDriver.STATE_NONE:

                            break;
                    }
                    break;
                case MESSAGE_WRITE:
                    break;
                case MESSAGE_READ:
                    String ErrorMsg = null;
                    byte[] readBuf = (byte[]) msg.obj;
                    float Voltage = 0;
                    if (D)
                        Log.i(TAG, "readBuf[0]:" + readBuf[0] + "  readBuf[1]:" + readBuf[1] + "  readBuf[2]:" + readBuf[2]);
                    if (readBuf[2] == 0)
                        ErrorMsg = "NO ERROR!         ";
                    else {
                        if ((readBuf[2] & 0x02) != 0)
                            ErrorMsg = "ERROR: No printer connected!";
                        if ((readBuf[2] & 0x04) != 0)
                            ErrorMsg = "ERROR: No paper!  ";
                        if ((readBuf[2] & 0x08) != 0)
                            ErrorMsg = "ERROR: Voltage is too low!  ";
                        if ((readBuf[2] & 0x40) != 0)
                            ErrorMsg = "ERROR: Printer Over Heat!  ";
                    }
                    Voltage = (float) ((readBuf[0] * 256 + readBuf[1]) / 10.0);
                    //if(D) Log.i(TAG, "Voltage: "+Voltage);
                    DisplayToast(ErrorMsg + "                                        " + "Battery voltage��" + Voltage + " V");
                    break;
                case MESSAGE_DEVICE_NAME:
                    // save the connected device's name
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), "Connected to "
                            + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                    mBtnPrint.setVisibility(View.VISIBLE);
                    break;
                case MESSAGE_TOAST:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    //��ʾ��Ϣ
    public void showMessage(String str) {
        Toast.makeText(this, str, Toast.LENGTH_LONG).show();
    }//showMessage

    // ��ʾToast
    public void DisplayToast(String str) {
        Toast toast = Toast.makeText(this, str, Toast.LENGTH_SHORT);
        //����toast��ʾ��λ��
        toast.setGravity(Gravity.TOP, 0, 100);
        //��ʾ��Toast
        toast.show();
    }//DisplayToast

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (D) Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:

                System.out.println("cos");
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mChatService.connect(device);

                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                } else {
                    // User did not enable Bluetooth or an error occured
                    Log.d(TAG, "BT not enabled");
                    //Toast.makeText(this, R.string.bt_not_enabled_leaving, Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    OnClickListener mBtnQuitOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            // Stop the Bluetooth chat services
            if (mChatService != null) mChatService.stop();
            finish();
        }
    };

    OnClickListener mBtnConnetBluetoothDeviceOnClickListener = new OnClickListener() {
        Intent serverIntent = null;

        public void onClick(View arg0) {
            // Launch the DeviceListActivity to see devices and do scan
            serverIntent = new Intent(PrintActivity.this, DeviceListActivity.class);
            startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
        }
    };

    OnClickListener mBtnPrintOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (BluetoothPrintDriver.IsNoConnection()) {
                return;
            }
            BluetoothPrintDriver.Begin();

            if (isprinted.equalsIgnoreCase("no")) {

            } else {
                BluetoothPrintDriver.SetAlignMode((byte) 1);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("[ Duplicate Copy ]");
                BluetoothPrintDriver.LF();
            }

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Receipt No : " + recptno + "\n");
            BluetoothPrintDriver.LF();

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Date : " + recptdate + "\n");
            BluetoothPrintDriver.LF();

            BluetoothPrintDriver.SetAlignMode((byte) 1);
            BluetoothPrintDriver.SetFontEnlarge((byte) 0x01);
            BluetoothPrintDriver.SetBold((byte) 0x01);//����
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write(getPrefsString("teanant_name", "") + "\n");
            BluetoothPrintDriver.SetBold((byte) 0x00);//����
            BluetoothPrintDriver.SetFontEnlarge((byte) 0x00);
            BluetoothPrintDriver.LF();

            String address1 = getPrefsString("tenant_address1", "");
            String address2 = getPrefsString("tenant_address2", "");
            String tenant_state = getPrefsString("tenant_state", "");
            String tenant_phone = getPrefsString("tenant_phone", "");
            String tenant_mobile = getPrefsString("tenant_mobile", "");

            if (address1.isEmpty()) {
                if (address2.isEmpty()) {

                } else {
                    BluetoothPrintDriver.SetAlignMode((byte) 1);
                    BluetoothPrintDriver.BT_Write(cc);
                    BluetoothPrintDriver.BT_Write(address2 + ",\n");


                    BluetoothPrintDriver.LF();
                }
            } else {
                BluetoothPrintDriver.SetAlignMode((byte) 1);
                BluetoothPrintDriver.BT_Write(cc);

                BluetoothPrintDriver.BT_Write(address1 + ",\n");
                BluetoothPrintDriver.LF();
            }
            BluetoothPrintDriver.SetAlignMode((byte) 1);
            BluetoothPrintDriver.BT_Write(cc);

            BluetoothPrintDriver.BT_Write(tenant_state + ",\n");
            BluetoothPrintDriver.LF();
            if (tenant_phone.isEmpty()) {
                if (tenant_mobile.isEmpty()) {

                } else {
                    BluetoothPrintDriver.SetAlignMode((byte) 1);
                    BluetoothPrintDriver.BT_Write(cc);

                    BluetoothPrintDriver.BT_Write(tenant_mobile + ",\n");
                    BluetoothPrintDriver.LF();
                }
            } else {
                BluetoothPrintDriver.SetAlignMode((byte) 1);
                BluetoothPrintDriver.BT_Write(cc);

                BluetoothPrintDriver.BT_Write(tenant_phone + ",\n");
                BluetoothPrintDriver.LF();
            }
            BluetoothPrintDriver.SetAlignMode((byte) 1);
            BluetoothPrintDriver.BT_Write(cc);

            BluetoothPrintDriver.BT_Write("GST No: " + getPrefsString("tenant_gst", ""));
            BluetoothPrintDriver.LF();

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write("\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.SetBold((byte) 0x01);//����
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Name : " + Cusname + "\n");
            BluetoothPrintDriver.SetBold((byte) 0x00);//����

            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Customer Id : ");
            BluetoothPrintDriver.BT_Write(subid + "\n");
            BluetoothPrintDriver.LF();

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Mobile : ");
            BluetoothPrintDriver.BT_Write(mobile + "\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(String.format("--------------------------------\n"), true);


            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Group : ");
            BluetoothPrintDriver.BT_Write(Groupname +"\n");
            BluetoothPrintDriver.LF();BluetoothPrintDriver.SetAlignMode((byte) 0);

            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Ticket No : ");
            BluetoothPrintDriver.BT_Write( ticketno + "\n");
            BluetoothPrintDriver.LF();BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Due : ");
            BluetoothPrintDriver.BT_Write(due + "\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);
            BluetoothPrintDriver.BT_Write(bb2);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write("Penalty : ");
            BluetoothPrintDriver.BT_Write(penaltyamnt + "\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write("Bonus : ");
            BluetoothPrintDriver.BT_Write(bonusamnt + "\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write("Received Amount : ");
            BluetoothPrintDriver.BT_Write(receivedamount + "\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Payment Mode : ");
            BluetoothPrintDriver.BT_Write(paymode + "\n");
            BluetoothPrintDriver.LF();
            if (paymode.equalsIgnoreCase("Cheque")) {
                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Cheque No. : ");
                BluetoothPrintDriver.BT_Write(cheno + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Cheque Bank : ");
                BluetoothPrintDriver.BT_Write(chebank + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Cheque bank branch : ");
                BluetoothPrintDriver.BT_Write(chequebranch + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Cheque Date : ");
                BluetoothPrintDriver.BT_Write(chedate + "\n");
                BluetoothPrintDriver.LF();
            } else if (paymode.equalsIgnoreCase("D.D")) {
                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("D.D No. : ");
                BluetoothPrintDriver.BT_Write(cheno + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("D.D Bank : ");
                BluetoothPrintDriver.BT_Write(chebank + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("D.D bank branch : ");
                BluetoothPrintDriver.BT_Write(chequebranch + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("D.D Date : ");
                BluetoothPrintDriver.BT_Write(chedate + "\n");
                BluetoothPrintDriver.LF();
            } else if (paymode.equalsIgnoreCase("RTGS/NEFT") || paymode.equalsIgnoreCase("Card")) {
                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Transaction No. : ");
                BluetoothPrintDriver.BT_Write(transactionno + "\n");
                BluetoothPrintDriver.LF();

                BluetoothPrintDriver.SetAlignMode((byte) 0);
                BluetoothPrintDriver.BT_Write(bb2);

                BluetoothPrintDriver.BT_Write("Transaction Date : ");
                BluetoothPrintDriver.BT_Write(transactiondate + "\n");
                BluetoothPrintDriver.LF();
            } else {

            }

            BluetoothPrintDriver.SetAlignMode((byte) 0);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write(String.format("--------------------------------\n"), true);// ┏
            BluetoothPrintDriver.LF();

            BluetoothPrintDriver.BT_Write("*System generated Bill.\nNo signature Needed\n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);

            if (paymode.equalsIgnoreCase("cheque")) {
                BluetoothPrintDriver.BT_Write("*Subject to Realization");
                BluetoothPrintDriver.LF();
            }
            BluetoothPrintDriver.SetAlignMode((byte) 1);
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.BT_Write("Prepared by : " + username);
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetAlignMode((byte) 1);
            BluetoothPrintDriver.SetFontEnlarge((byte) 0x10);
            BluetoothPrintDriver.BT_Write("\nThank You \n");
            BluetoothPrintDriver.LF();
            BluetoothPrintDriver.BT_Write(bb2);

            BluetoothPrintDriver.SetFontEnlarge((byte) 0x00);
            BluetoothPrintDriver.BT_Write("\n\n");
            BluetoothPrintDriver.LF();
            if (getPrefsBoolean("isonline", true)) {
                update_printed(recptno);
            } else {
                BaseUtils.offlinedb.update_printed_receipts(recptno);
            }
            finish();
        }
    };


    OnClickListener mBtnTestOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (BluetoothPrintDriver.IsNoConnection()) {
                return;
            }
            BluetoothPrintDriver.Begin();
            BluetoothPrintDriver.SelftestPrint();    //��ӡ�Բ�ҳ
        }
    };
    OnClickListener mBtnInquiryOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.i(TAG, "inquiry btn");
            if (BluetoothPrintDriver.IsNoConnection()) {
                return;
            }
            BluetoothPrintDriver.Begin();
            BluetoothPrintDriver.StatusInquiry();    // ��ѯ����״̬���ص�ѹ

        }
    };

    private void update_printed(String recieptno) {
        ICallService service = RetrofitBuilder.INSTANCE.buildservice(ICallService.class);
        HashMap<String, String> params = new HashMap();
        params.put("tenant_id", getPrefsString(Constants.INSTANCE.getTenantid(), ""));
        params.put("user_id", getPrefsString(Constants.INSTANCE.getLoggeduser(), ""));
        params.put("receipt_no", recieptno);
        params.put("db",getPrefsString(Constants.INSTANCE.getDb(),""));
        Call<successmsgmodel> call = service.update_printed_status(params);
        call.enqueue(new Callback<successmsgmodel>() {
            @Override
            public void onResponse(Call<successmsgmodel> call, Response<successmsgmodel> response) {
                if (response.isSuccessful()) {
                    if (response.code() == (200)) {
                        if (response.body().getStatus().equalsIgnoreCase("Success")) {

                        } else {
                            toast(response.body().getMsg());
                        }
                    } else {
                        System.out.println("no show");
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<successmsgmodel> call, Throwable t) {

            }
        });
    }
}
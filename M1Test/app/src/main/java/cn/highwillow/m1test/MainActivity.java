package cn.highwillow.m1test;

import android.app.PendingIntent;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import cc.lotuscard.ILotusCallBack;
import cc.lotuscard.LotusCardDriver;
import cc.lotuscard.LotusCardParam;

import static cc.lotuscard.LotusCardDriver.m_InEndpoint;
import static cc.lotuscard.LotusCardDriver.m_OutEndpoint;
import static cc.lotuscard.LotusCardDriver.m_UsbDeviceConnection;

public class MainActivity extends AppCompatActivity implements ILotusCallBack {
    private EditText m_edtLog;
    private ImageView m_imgIdPhoto;

    private UsbManager m_UsbManager = null;
    private UsbDevice m_LotusCardDevice = null;
    private UsbInterface m_LotusCardInterface = null;
    private UsbDeviceConnection m_LotusCardDeviceConnection = null;
    private final int m_nVID = 1306;
    private final int m_nPID = 20763;
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";

    private Boolean m_bUseUsbHostApi = true;
    private Boolean m_bCanUseUsbHostApi = true;
    private String m_strDeviceNode;

    private long m_nDeviceHandle = -1;
    private Handler m_Handler = null;

    private LotusCardDriver mLotusCardDriver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_edtLog = (EditText) findViewById(R.id.edtLog);
        m_imgIdPhoto = (ImageView) findViewById(R.id.imgIdPhoto);
        // 设置USB读写回调 串口可以不用此操作
        m_bCanUseUsbHostApi = SetUsbCallBack();
        if (m_bCanUseUsbHostApi) {
            AddLog("Find LotusSmart IC Reader!");
            AddLog("Device Node:" + m_strDeviceNode);
        } else {
            AddLog("Not Find LotusSmart IC Reader!");
        }
        mLotusCardDriver = new LotusCardDriver();
        mLotusCardDriver.m_lotusCallBack = this;
    }
    public void OnClearLogListener(View arg0) {
        if (null != m_imgIdPhoto) {
            m_imgIdPhoto.setBackgroundColor(0);
        }
        if (null == m_edtLog)
            return;
        m_edtLog.setText("");

    }



    private Boolean SetUsbCallBack() {
        Boolean bResult = false;
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        m_UsbManager = (UsbManager) getSystemService(USB_SERVICE);
        if (null == m_UsbManager)
            return bResult;

        HashMap<String, UsbDevice> deviceList = m_UsbManager.getDeviceList();
        if (!deviceList.isEmpty()) {
            for (UsbDevice device : deviceList.values()) {
                if ((m_nVID == device.getVendorId())
                        && (m_nPID == device.getProductId())) {
                    m_LotusCardDevice = device;
                    m_strDeviceNode = m_LotusCardDevice.getDeviceName();
                    break;
                }
            }
        }
        if (null == m_LotusCardDevice)
            return bResult;
        m_LotusCardInterface = m_LotusCardDevice.getInterface(0);
        if (null == m_LotusCardInterface)
            return bResult;
        if (false == m_UsbManager.hasPermission(m_LotusCardDevice)) {
            m_UsbManager.requestPermission(m_LotusCardDevice, pendingIntent);
        }
        UsbDeviceConnection conn = null;
        if (m_UsbManager.hasPermission(m_LotusCardDevice)) {
            conn = m_UsbManager.openDevice(m_LotusCardDevice);
        }

        if (null == conn)
            return bResult;

        if (conn.claimInterface(m_LotusCardInterface, true)) {
            m_LotusCardDeviceConnection = conn;
        } else {
            conn.close();
        }
        if (null == m_LotusCardDeviceConnection)
            return bResult;
        // 把上面获取的对性设置到接口中用于回调操作
        m_UsbDeviceConnection = m_LotusCardDeviceConnection;
        if (m_LotusCardInterface.getEndpoint(1) != null) {
            m_OutEndpoint = m_LotusCardInterface.getEndpoint(1);
        }
        if (m_LotusCardInterface.getEndpoint(0) != null) {
            m_InEndpoint = m_LotusCardInterface.getEndpoint(0);
        }
        bResult = true;
        return bResult;
    }

    public void AddLog(String strLog) {
        //SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm:ss:SSS");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String strDate = formatter.format(curDate);
        if (null == m_edtLog)
            return;
        String strLogs = m_edtLog.getText().toString().trim();
        if (strLogs.equals("")) {
            strLogs = strDate + " " + strLog;
        } else {
            strLogs += "\r\n" + strDate + " " + strLog;
        }
        m_edtLog.setText(strLogs);
    }

    public void OnTestM1Listener(View arg0) {
        if (null == mLotusCardDriver)
            return;
        if (m_nDeviceHandle == -1) {
            m_nDeviceHandle = mLotusCardDriver.OpenDevice("", 0, 0, 0, 0,// 使用内部默认超时设置
                    true);
//            m_nDeviceHandle = mLotusCardDriver.OpenDevice("", 0, 0, 0, 0,// 使用内部默认超时设置
//                    false);

        }
        if (m_nDeviceHandle != -1) {
             AddLog("Open Device Success!");
            testIcCardReader(m_nDeviceHandle);
        } else {
            AddLog("Open Device False!");
        }
    }


    public long bytes2long(byte[] byteNum) {
        long num = 0;
        for (int ix = 3; ix >= 0; --ix) {
            num <<= 8;
            if (byteNum[ix] < 0) {
                num |= (256 + (byteNum[ix]) & 0xff);
            } else {
                num |= (byteNum[ix] & 0xff);
            }
        }
        return num;
    }

    public String leftString(String strText, int nLeftLength) {
        if (1 == strText.length())
            strText = "0" + strText;
        if (strText.length() <= nLeftLength)
            return strText;

        return strText.substring(strText.length() - nLeftLength,
                strText.length());
    }
    private void testIcCardReader(long nDeviceHandle) {
        boolean bResult = false;
        int nRequestType;
        long lCardNo = 0;
        int nErrorCode = 0;
        LotusCardParam tLotusCardParam1 = new LotusCardParam();
        bResult = mLotusCardDriver.Beep(nDeviceHandle, 10);
        // bResult = mLotusCardDriver.Beep(nDeviceHandle, 10);
        if (!bResult) {
            nErrorCode = mLotusCardDriver.GetErrorCode(nDeviceHandle);
            AddLog("Call Beep Error!"+ nErrorCode + ":"+ mLotusCardDriver.GetErrorInfo(nDeviceHandle, nErrorCode));
            return;
        }
        AddLog("Call Beep Ok!");
        nRequestType = LotusCardDriver.RT_ALL;//.RT_NOT_HALT;
        // 以下3个函数可以用GetCardNo替代
        // bResult = mLotusCardDriver.Request(nDeviceHandle, nRequestType,
        // tLotusCardParam1);
        // if (!bResult)
        // return;
        // bResult = mLotusCardDriver.Anticoll(nDeviceHandle, tLotusCardParam1);
        // if (!bResult)
        // return;
        // bResult = mLotusCardDriver.Select(nDeviceHandle, tLotusCardParam1);
        // if (!bResult)
        // return;
        bResult = mLotusCardDriver.GetCardNo(nDeviceHandle, nRequestType,
                tLotusCardParam1);
        if (!bResult) {
            nErrorCode = mLotusCardDriver.GetErrorCode(nDeviceHandle);
            AddLog("Call GetCardNo Error!" + nErrorCode + ":"+ mLotusCardDriver.GetErrorInfo(nDeviceHandle, nErrorCode));
            return;
        }
        lCardNo = bytes2long(tLotusCardParam1.arrCardNo);
        AddLog("Call GetCardNo Ok!");
        AddLog("CardNo(DEC):" + lCardNo);
        AddLog("CardNo(HEX):"
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrCardNo[3]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrCardNo[2]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrCardNo[1]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrCardNo[0]), 2)
                .toUpperCase());
        tLotusCardParam1.arrKeys[0] = (byte) 0xff;
        tLotusCardParam1.arrKeys[1] = (byte) 0xff;
        tLotusCardParam1.arrKeys[2] = (byte) 0xff;
        tLotusCardParam1.arrKeys[3] = (byte) 0xff;
        tLotusCardParam1.arrKeys[4] = (byte) 0xff;
        tLotusCardParam1.arrKeys[5] = (byte) 0xf1;
        tLotusCardParam1.nKeysSize = 6;
        boolean bUseLoadKey = false;
        if(true == bUseLoadKey) {
            bResult = mLotusCardDriver.LoadKey(nDeviceHandle, LotusCardDriver.AM_A,
                    0, tLotusCardParam1);
            if (!bResult) {
                AddLog("Call LoadKey Error!");
                return;
            }
            AddLog("Call LoadKey Ok!");
            bResult = mLotusCardDriver.Authentication(nDeviceHandle,
                    LotusCardDriver.AM_A, 0, tLotusCardParam1);
        }
        else {
            //直接使用参数里面的密码
            bResult = mLotusCardDriver.AuthenticationWithPassword(nDeviceHandle,
                    LotusCardDriver.AM_A, 0, tLotusCardParam1);
        }

        if (!bResult) {
            AddLog("Call Authentication(A) Error!");
            return;
        }
        AddLog("Call Authentication(A) Ok!");
        bResult = mLotusCardDriver.Read(nDeviceHandle, 1, tLotusCardParam1);
        if (!bResult) {
            AddLog("Call Read Error!");
            return;
        }
        AddLog("Call Read Ok!");
        AddLog("Buffer(HEX):"
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[1]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[2]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[3]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[4]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[5]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[6]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[7]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[8]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[9]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xa]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xb]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xc]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xd]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xe]), 2)
                .toUpperCase()
                + leftString(
                Integer.toHexString(tLotusCardParam1.arrBuffer[0xf]), 2)
                .toUpperCase());
        tLotusCardParam1.arrBuffer[0] = (byte) 0x10;
        tLotusCardParam1.arrBuffer[1] = (byte) 0x01;
        tLotusCardParam1.arrBuffer[2] = (byte) 0x02;
        tLotusCardParam1.arrBuffer[3] = (byte) 0x03;
        tLotusCardParam1.arrBuffer[4] = (byte) 0x04;
        tLotusCardParam1.arrBuffer[5] = (byte) 0x05;
        tLotusCardParam1.arrBuffer[6] = (byte) 0x06;
        tLotusCardParam1.arrBuffer[7] = (byte) 0x07;
        tLotusCardParam1.arrBuffer[8] = (byte) 0x08;
        tLotusCardParam1.arrBuffer[9] = (byte) 0x09;
        tLotusCardParam1.arrBuffer[10] = (byte) 0x0a;
        tLotusCardParam1.arrBuffer[11] = (byte) 0x0b;
        tLotusCardParam1.arrBuffer[12] = (byte) 0x0c;
        tLotusCardParam1.arrBuffer[13] = (byte) 0x0d;
        tLotusCardParam1.arrBuffer[14] = (byte) 0x0e;
        tLotusCardParam1.arrBuffer[15] = (byte) 0x0f;
        tLotusCardParam1.nBufferSize = 16;
        bResult = mLotusCardDriver.Write(nDeviceHandle, 1, tLotusCardParam1);
        if (!bResult) {
            AddLog("Call Write Error!");
            return;
        }
        AddLog("Call Write Ok!");
    }

    @Override
    public boolean callBackExtendIdDeviceProcess(Object objUser, byte[] arrBuffer) {
        return false;
    }

    @Override
    public boolean callBackReadWriteProcess(long nDeviceHandle, boolean bRead, byte[] arrBuffer) {

		int nResult = 0;
		boolean bResult = false;
		int nBufferLength = arrBuffer.length;
		int nWaitCount = 0;
		if (null == m_UsbDeviceConnection) {
            AddLog("null == m_UsbDeviceConnection");
            return false;
        }
		if (null == m_OutEndpoint) {
            AddLog("null == m_OutEndpoint");
            return false;
        }
		if (null == m_InEndpoint) {
            AddLog("null == m_InEndpoint");
            return false;
        }
        //AddLog("callBackReadWriteProcess nBufferLength:" + nBufferLength);
		if (nBufferLength < 65) {
            AddLog("nBufferLength < 65");
            return false;
        }
		if (true == bRead) {
			arrBuffer[0] = 0;
			while (true) {
				nResult = m_UsbDeviceConnection.bulkTransfer(m_InEndpoint,
						arrBuffer, 64, 3000);
				if (nResult <= 0) {
                    AddLog("nResult <= 0 is " + nResult);
                    break;
                }
				if (arrBuffer[0] != 0) {
					//此处调整一下
					System.arraycopy(arrBuffer, 0, arrBuffer, 1, nResult);
					arrBuffer[0] = (byte)nResult;
					break;
				}
				nWaitCount++;
				if (nWaitCount > 1000) {
                    AddLog("nWaitCount > 1000");
                    break;
                }
			}
            //AddLog("m_InEndpoint bulkTransfer Read:"+nResult);
			if (nResult == 64) {
				bResult = true;
			} else {
                AddLog("nResult != 64 is" +nResult);
				bResult = false;
			}
		} else {
			nResult = m_UsbDeviceConnection.bulkTransfer(m_OutEndpoint,
					arrBuffer, 64, 3000);
            //AddLog("m_OutEndpoint bulkTransfer Write:"+nResult);
			if (nResult == 64) {
				bResult = true;
                //AddLog("m_OutEndpoint bulkTransfer Write Ok!");
			} else {
                AddLog("m_OutEndpoint bulkTransfer Write error");
				bResult = false;
			}
		}
		return bResult;
    }
}

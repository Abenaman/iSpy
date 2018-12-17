package com.softs.aben.ispy.mailing;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BRecorder extends AppCompatActivity {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");

    String currentDateandTime = sdf.format(new Date());
    MediaRecorder recorder=null;
    private String ofile=null;
    int count=0;
    public BRecorder(){

    }
    private void prepare(){

        recorder = new MediaRecorder();
        File root = new File(Environment.getExternalStorageDirectory(), "iSpyRecord");
        if (!root.exists()) {
            root.mkdirs();
            ofile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/iSpyRecord/myrec"+currentDateandTime+".3gp";
            recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            recorder.setOutputFile(ofile);
        }
        else
            ofile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/iSpyRecord/myrec"+currentDateandTime+".3gp";
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setOutputFile(ofile);

    }
    public void start(){
        try {
            prepare();
            recorder.prepare();
            recorder.start();

            Toast.makeText(this, "Recording Started", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void stop(){

          try {
              recorder.stop();
              recorder.release();
              recorder= null;
              Toast.makeText(this, "Recording Stoped And Successfull", Toast.LENGTH_SHORT).show();
          }
          catch (Exception e){

              e.printStackTrace();
          }
    }
    public void play()throws IOException{
        MediaPlayer m=new MediaPlayer();
        m.setDataSource(ofile);
        m.prepare();
        m.start();
        Toast.makeText(this, "Playing Audio", Toast.LENGTH_SHORT).show();
    }

}

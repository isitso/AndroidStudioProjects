/*
http://developer.android.com/guide/topics/media/audio-capture.html
 */
package teamname.morselight;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.IOException;


public class AudioActivity extends ActionBarActivity {

    private String fileName;
    private Button recordButton, playButton;

    private MediaRecorder recorder;
    private MediaPlayer player;
    private boolean isRecording, isPlaying;


    private  void onRecord(){
        if (isRecording) {
            recordButton.setText("Start Recording");
            stopRecording();
        }else {
            recordButton.setText("Stop Recording");
            startRecording();
        }
        isRecording = !isRecording;
    }

    private void onPlaying(){
        if (isPlaying) {
            playButton.setText("Start Playing");
            stopPlaying();
        }else {
            playButton.setText("Stop Playing");
            startPlaying();
        }
        isPlaying = !isPlaying;
    }
    private void startRecording(){
        // create MediaRecorder to get the audio
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        }catch (IOException e){
            Log.e("", "recorder prepare failed");
        }
        recorder.start();
    }

    private void stopRecording(){
        if (!isRecording)
            return;
        recorder.stop();
        recorder.release();
        recorder = null;
    }


    private void startPlaying(){
        player = new MediaPlayer();
        try{
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        }catch (IOException e){
            Log.e("", "startPlaying error at setDataSource()");
        }
    }

    private void stopPlaying(){
        if (!isPlaying)
            return;
        player.release();
        player = null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audio.3gp";
        isPlaying = false;
        isRecording = false;
        // get buttons
        recordButton = (Button)findViewById(R.id.recordB);
        playButton = (Button)findViewById(R.id.playB);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecord();
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaying();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

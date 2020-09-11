package ch.ost.rj.mge.v06.myapplication;

import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;

public class SleepingAsyncTask extends AsyncTask<Integer, String, String> {

    private final Button startButton;
    private final TextView output;

    public SleepingAsyncTask(Button startButton, TextView output) {
        this.startButton = startButton;
        this.output = output;
    }

    @Override
    protected void onPreExecute() {
        startButton.setEnabled(false);
    }

    @Override
    protected String doInBackground(Integer... params) {
        for (int i = 1; i <= params[0]; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}

            publishProgress("Durchgang " + i);
        }

        return "Berechnung komplett";
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        output.setText(progress[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        startButton.setEnabled(true);
        output.setText(result);
    }
}
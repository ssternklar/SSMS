package io.github.ssternklar.ssms;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;


/**
 * A placeholder fragment containing a simple view.
 */
public class CreateProfileActivityFragment extends Fragment {

    String text = "Hello!";
    TextView textView;
    NameBoxFragment nameBoxFragment;

    public CreateProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_profile, container, false);

        //Get our nameBoxFragment
        nameBoxFragment = (NameBoxFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.name_box_fragment);
        //Get our button
        Button button = (Button)view.findViewById(R.id.generate_key_button);

        //Set up our event listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FetchRandomNumberTask task = new FetchRandomNumberTask();
                task.execute("Bob Smith", "https://www.random.org/sequences/?min=32&max=239&col=1&format=plain&rnd=new");
            }
        });

        textView = (TextView)view.findViewById(R.id.TEXT);

        return view;
    }

    public void processRandomTaskFinish(int[] str)
    {
        textView.setText(Arrays.toString(str));
    }

    class FetchRandomNumberTask extends AsyncTask<String,Void, int[]>
    {
        @Override
        protected int[] doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String randomNumberString = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL(params[1]);

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + " ");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                randomNumberString = buffer.toString();
            } catch (IOException e) {
                Log.e("MainActivityFragment", "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("MainActivityFragment", "Error closing stream", e);
                    }
                }
            }

            String[] str = randomNumberString.split("[ \n\r]");
            int[] intArr = new int[str.length];
            for(int i = 0; i < str.length; i++)
                intArr[i] = Integer.parseInt(str[i]);

            return intArr;
        }

        @Override
        public void onPostExecute(int[] result)
        {
            processRandomTaskFinish(result);
        }
    }

}

package io.github.ssternklar.ssms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
    EditText nameBox;

    public CreateProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_profile, container, false);

        //Get our nameBoxFragment
        nameBox = (EditText)view.findViewById(R.id.name_box);
        //Get our button
        Button button = (Button)view.findViewById(R.id.generate_key_button);

        //Set up our event listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = nameBox.getText().toString();
                final SetUpProfileTask task = new SetUpProfileTask();

                //Do a first pass check to make sure nothing will go wrong
                boolean continueForSure = true;
                if(!SSMSContentProviderHelper.getEncryptKey(getActivity(), text).equals("") || !SSMSContentProviderHelper.getPhoneNumber(getActivity(), text).equals(""))
                {
                    //Why can't I just make a new AlertDialog? Why do I have to go through the Builder class?
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm Overwrite")
                            .setMessage("There is already a profile with this name, do you wish to overwrite it? You will have to re-transmit the new key once created")
                            .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    task.execute(text, "https://www.random.org/sequences/?min=32&max=239&col=1&format=plain&rnd=new");
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
                else
                {
                    task.execute(text, "https://www.random.org/sequences/?min=32&max=239&col=1&format=plain&rnd=new");
                }
            }
        });

        textView = (TextView)view.findViewById(R.id.TEXT);

        return view;
    }

    public void processRandomTaskFinish(String str)
    {
        textView.setText("Your Key: " + str);
    }

    class SetUpProfileTask extends AsyncTask<String,Void, String>
    {
        String name;

        @Override
        protected String doInBackground(String... params) {

            name = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String randomNumberString = null;

            try {
                URL url = new URL(params[1]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

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

            if(randomNumberString != null) {
                String[] str = randomNumberString.split("[ \n\r]");
                char[] charArr = new char[str.length];
                for (int i = 0; i < str.length; i++) {
                    charArr[i] = (char) Integer.parseInt(str[i]);
                    if (charArr[i] == '"')
                        charArr[i] = 240;
                }
                String key = new String(charArr);
                SSMSContentProviderHelper.insertNewPerson(getActivity(), name, "", key);
                return new String(charArr);
            }
            else
            {
                return "";
            }
        }

        @Override
        public void onPostExecute(String result)
        {
            processRandomTaskFinish(result);
        }
    }
}

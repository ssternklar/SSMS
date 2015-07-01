package io.github.ssternklar.ssms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class SendMessageActivityFragment extends Fragment {

    EditText nameBox;
    EditText phoneBox;
    EditText messageBox;
    TextView previousBox;
    TextView secondPreviousBox;

    public SendMessageActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_send_message, container, false);

        nameBox = (EditText)view.findViewById(R.id.name_box);
        phoneBox = (EditText)view.findViewById(R.id.temp_phone_number);
        messageBox = (EditText)view.findViewById(R.id.write_message);
        previousBox = (TextView)view.findViewById(R.id.previous_text_message);
        secondPreviousBox = (TextView)view.findViewById(R.id.second_previous_text_message);

        Button button = (Button)view.findViewById(R.id.send_message_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = messageBox.getText().toString();
                String name = nameBox.getText().toString();
                String number = phoneBox.getText().toString(); //SSMSContentProviderHelper.getPhoneNumber(v.getContext(), name);
                String key = SSMSContentProviderHelper.getEncryptKey(getActivity(), name);

                if(number.length() > 0 && text.length() > 0 && key.length() > 0)
                {
                    messageBox.setText("");
                    sendMessage(number, SSMSEncryptHelper.Encrypt(text, key));
                }
                else
                {
                    Toast.makeText(v.getContext(), "Please enter both a valid Profile and Message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void sendMessage(String number, String text)
    {
        String messageSent = "SMS_SENT";
        String messageDelivered = "SMS_DELIVERED";
        PendingIntent sentIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(messageSent), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(getActivity(), 0, new Intent(messageDelivered), 0);

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getActivity(), "Message Sent", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Message Failed to send, re-send from your normal SMS app", Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter(messageSent));

        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getActivity(), "Message Delivered", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "Message Failed to deliver, re-send from your normal SMS app", Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter(messageDelivered));

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, text, sentIntent, deliveredIntent);
    }
}

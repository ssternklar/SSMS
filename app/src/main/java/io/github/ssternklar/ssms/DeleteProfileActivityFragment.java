package io.github.ssternklar.ssms;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A placeholder fragment containing a simple view.
 */
public class DeleteProfileActivityFragment extends Fragment {

    EditText nameBox;

    public DeleteProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete_profile, container, false);
        nameBox = (EditText)view.findViewById(R.id.name_box);
        Button b = (Button)view.findViewById(R.id.delete_this_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                .setTitle("Confirmation")
                .setMessage("Are you sure you wish to delete this profile?")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameBox.getText().toString();
                        SSMSContentProviderHelper.deleteRecord(getActivity(), name);
                        nameBox.setText("");
                    }
                }).setNegativeButton("Cancel", null).show();
            }
        });

        b = (Button)view.findViewById(R.id.delete_all_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you wish to delete all profiles?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SSMSDbHelper.dropDatabase(getActivity());
                                nameBox.setText("");
                            }
                        }).setNegativeButton("Cancel", null).show();
            }
        });

        return view;
    }
}

package io.github.ssternklar.ssms;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NameBoxFragment extends Fragment {

    public EditText box;
    public static NameBoxFragment newestInstance;
    public NameBoxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_name_box, container, false);

        newestInstance = this;

        box = (EditText)view.findViewById(R.id.name_box);

        return view;
    }

}

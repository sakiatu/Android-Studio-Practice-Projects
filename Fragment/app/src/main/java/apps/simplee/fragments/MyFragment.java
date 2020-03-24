package apps.simplee.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment {

    public static final String KEY_MY_DATA = "my_data";

    public static MyFragment getInstance(String my_data) {
        MyFragment fragment = new MyFragment();
        //create bundle to store arguments
        Bundle args = new Bundle();
       args.putString(KEY_MY_DATA,my_data);

        //set the arguments in fragment
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_my, container, false);


        //sign in button onClick action
        rootView.findViewById(R.id.btnSignInId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "sign in button have been clicked", Toast.LENGTH_SHORT).show();
            }
        });

        setText(rootView);
        return rootView;
    }

    private void setText(View rootView) {
        Bundle bundle =getArguments();
        if(bundle!=null) {
            TextView textView =rootView.findViewById(R.id.frag_text_id);
            textView.setText(bundle.getString(KEY_MY_DATA));
        }

    }

}

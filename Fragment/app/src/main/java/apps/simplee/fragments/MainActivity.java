package apps.simplee.fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        runFragTnx();
    }

    private void runFragTnx() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
       // transaction.add(R.id.frameLayoutId,new MyFragment());
        //transaction.replace(R.id.frameLayoutId,new MyFragment());
        transaction.replace(R.id.frameLayoutId,MyFragment.getInstance("Fragment"));
        transaction.commit();
    }
}

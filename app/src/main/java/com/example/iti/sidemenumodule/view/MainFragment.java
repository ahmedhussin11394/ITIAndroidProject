package com.example.iti.sidemenumodule.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.CategoryManger;
import com.example.iti.sidemenumodule.daos.EmployeeManger;
import com.example.iti.sidemenumodule.datamanger.DataManger;
import com.example.iti.sidemenumodule.helperclasses.MyData;
import com.example.iti.sidemenumodule.model.Category;
import com.example.iti.sidemenumodule.model.Message;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;
import com.norbsoft.typefacehelper.TypefaceCollection;
import com.norbsoft.typefacehelper.TypefaceHelper;

import java.util.ArrayList;

/**
 * Created by Ahmed_telnet on 5/21/2016.
 */
public class MainFragment extends Fragment implements AfterPraseResult {


    private static CustomAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<Category> data;
    static View.OnClickListener myOnClickListener;
    private static ArrayList<Integer> removedItems;
    ProgressDialog progress;
    FragmentActivity myContext;
    private static final String FRAGMENT_FLAG = "FRAGMENT_FLAG";
    static Bundle mBundle;

    public static MainFragment newInstance(int flag) {
        MainFragment mFragment = new MainFragment();
        mBundle = new Bundle();
        mBundle.putInt(FRAGMENT_FLAG, flag);
        mFragment.setArguments(mBundle);
        return mFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        data = new ArrayList<>();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        myOnClickListener = new MyOnClickListener(myContext);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(myContext);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        data = DataManger.getcategories();
        removedItems = new ArrayList<Integer>();

        progress = new ProgressDialog(myContext,R.style.MyTheme);
        progress.setCancelable(false);
        progress.show();
        CategoryManger categoryManger = CategoryManger.getInstance(myContext);
        categoryManger.getCategoriesList(this);
        adapter = new CustomAdapter(myContext, data);
        recyclerView.setAdapter(adapter);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TypefaceCollection typeface = new TypefaceCollection.Builder()
                .set(Typeface.NORMAL, Typeface.createFromAsset(myContext.getAssets(), "fonts/DroidKufi-Regular.ttf"))
                .set(Typeface.BOLD, Typeface.createFromAsset(myContext.getAssets(), "fonts/DroidKufi-Bold.ttf"))
                .create();
        TypefaceHelper.init(typeface);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        myContext = (FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void afterParesResult(Object list) {
        data=(ArrayList)list;
        adapter.getData().clear();
        adapter.getData().addAll(data);
        // fire the event
        adapter.notifyDataSetChanged();
        progress.dismiss();
    }

    @Override
    public void errorParesResult(String errorMessage) {
        AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
        alertDialog.setTitle(getString(R.string.alert));
        alertDialog.setMessage(getString(R.string.error_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            getItems(v);
        }

        private void getItems(View v) {
            int selectedItemPosition = recyclerView.getChildPosition(v);
            RecyclerView.ViewHolder viewHolder
                    = recyclerView.findViewHolderForPosition(selectedItemPosition);
            TextView textViewName
                    = (TextView) viewHolder.itemView.findViewById(R.id.textViewName);
            String selectedName = (String) textViewName.getText();
            int selectedItemId = -1;
            for (int i = 0; i < MyData.nameArray.length; i++) {
                if (selectedName.equals(MyData.nameArray[i])) {
                    selectedItemId = MyData.id_[i];
                }
            }
            moveToProductFragment(selectedItemId);

        }

        private void moveToProductFragment(int selectedItemId) {
            Fragment mFragment = null;
            FragmentManager mFragmentManager = myContext.getSupportFragmentManager();
            if (mBundle.getInt(FRAGMENT_FLAG) == 1) {
                mFragment = new requestProductFragment(selectedItemId);
            } else {
                mFragment = new PortfoliosFragment(selectedItemId);
            }
            if (mFragment != null) {
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).addToBackStack("main_fragment").commit();
            }
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        return true;
    }

}

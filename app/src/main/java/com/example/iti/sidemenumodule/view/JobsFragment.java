package com.example.iti.sidemenumodule.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iti.sidemenumodule.R;
import com.example.iti.sidemenumodule.daos.EmployeeManger;
import com.example.iti.sidemenumodule.daos.JobsManger;
import com.example.iti.sidemenumodule.helperclasses.EmployeeCustomAdapter;
import com.example.iti.sidemenumodule.helperclasses.JobsCustomAdapter;
import com.example.iti.sidemenumodule.model.Project;
import com.example.iti.sidemenumodule.network_manager.AfterPraseResult;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobsFragment extends Fragment implements AfterPraseResult {

        ListView listView;
        View rootView;
        FragmentActivity myContext;
        ArrayList<Project> data;
        ProgressDialog progress;
        JobsCustomAdapter adapter;
    boolean flag=false;
        public JobsFragment() {
            // Required empty public constructor
        }

    public JobsFragment(boolean flag) {
        this.flag=flag;
    }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            data=new ArrayList<>();
            // Inflate the layout for this fragment
            rootView = inflater.inflate(R.layout.fragment_jobs, container, false);
            listView = (ListView) rootView.findViewById(R.id.jobs_listview);
            progress = new ProgressDialog(myContext,R.style.MyTheme);
            progress.setCancelable(false);
            progress.show();
            JobsManger jobsManger=JobsManger.getInstance(myContext);
            if(flag){
                jobsManger.getWorkJobsList(this, 1);
            }else{
            jobsManger.getJobsList(this);}
            adapter = new JobsCustomAdapter(myContext, data);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // TODO Auto-generated method stub

                    String slectedItem = data.get(position).getProjectName();
                    Toast.makeText(myContext, slectedItem, Toast.LENGTH_SHORT).show();

                }
            });
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            myContext=(FragmentActivity)activity;
            super.onAttach(activity);
        }

        @Override
        public void afterParesResult(Object list) {
            data=(ArrayList)list;
            adapter.getData().clear();
            adapter.getData().addAll(data);
            Log.i("jobfragment","test");
            adapter.notifyDataSetChanged();
            progress.dismiss();
        }

        @Override
        public void errorParesResult(String errorMessage) {
            AlertDialog alertDialog = new AlertDialog.Builder(myContext).create();
            alertDialog.setTitle(getString(R.string.alert));
            alertDialog.setMessage(errorMessage);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

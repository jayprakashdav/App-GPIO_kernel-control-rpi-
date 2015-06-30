package com.example.pratik.myfail3;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditText editText5,editText6;
    public Button button2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void Connect(View view){
        mListener.onFragmentInteraction();
        //EditText editText5;
        //editText5=(EditText)view.findViewById(R.id.editText5);
        //EditText editText6 = (EditText) view.findViewById();
        //String ip2=editText5.getText().toString();
        //int port2=Integer.parseInt(editText6.getText().toString());
        //ip=ip2;
        //port=port2;
        //Thread thread1=new Thread(new ReceiverThread());
        //thread1.start();

        //Thread thread2=new Thread(new SenderThread(Synctoken));
        //thread2.start();

        //transaction.remove(addFragment);
        //transaction.commit();
        return;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank, container, false);
        container.setOnClickListener(this);
        container.setClickable(true);
        container.setFocusable(true);
        container.clearFocus();
        container.requestFocus();
        editText5=(EditText)view.findViewById(R.id.editText5);
        editText6=(EditText)view.findViewById(R.id.editText6);
        button2=(Button)view.findViewById(R.id.button2);
        editText5.setOnClickListener(this);
        editText6.setOnClickListener(this);
        button2.setOnClickListener(this);
        view.setOnClickListener(this);
       // view.clearFocus();
        //view.requestFocus();
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editText5:
                editText5.requestFocus();
                InputMethodManager imm = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.editText6:
                editText5.requestFocus();
                InputMethodManager img = (InputMethodManager)getActivity(). getSystemService(Context.INPUT_METHOD_SERVICE);
                img.showSoftInput(getView(), InputMethodManager.SHOW_IMPLICIT);
                break;
            case R.id.button2:
                mListener.onFragmentInteraction();
                break;
        }
    }



    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction();
    }

}

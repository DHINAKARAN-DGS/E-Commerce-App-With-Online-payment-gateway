package com.daatstudios.cosmic_glow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private TextView signIn;
    private EditText suemail, supass, supassc, suname;
    private Button signupBtn;
    private FrameLayout frameLayout;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;
    FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        signIn = view.findViewById(R.id.signinBtnTxt);
        suemail = view.findViewById(R.id.sign_up_user_email);
        supass = view.findViewById(R.id.sign_up_user_pass);
        supassc = view.findViewById(R.id.sign_up_user_passC);
        suname = view.findViewById(R.id.sign_up_user_name);
        signupBtn = view.findViewById(R.id.Sign_up_button);
        progressBar = view.findViewById(R.id.Sign_up_progressBar);
        frameLayout = getActivity().findViewById(R.id.REGISTER_FRAMElAYOUT);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        suemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        suname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        supass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        supassc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                chkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chEmailAndPwd();
            }
        });
    }

    private void chEmailAndPwd() {
        if (suemail.getText().toString().matches(emailPattern)) {
            if (supass.getText().toString().equals(supassc.getText().toString())) {
                progressBar.setVisibility(View.VISIBLE);
                signupBtn.setEnabled(false);
                firebaseAuth.createUserWithEmailAndPassword
                        (suemail.getText().toString(),
                                supass.getText().toString()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String url = "https://etched-stitches.000webhostapp.com/addUser.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new com.android.volley.Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            System.out.println(response);
                                            Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                            Intent intent  = new Intent(getActivity(),NewDashboardActivity.class);
                                            getContext().startActivity(intent);
                                            getActivity().finish();
                                        }

                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    System.out.println(error.toString());
                                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                            ) {
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("name", suname.getText().toString());
                                    map.put("email", suemail.getText().toString());
                                    map.put("uid",FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    return map;
                                }
                            };
                            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                            requestQueue.add(stringRequest);

                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(getActivity(), "ERROR:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                progressBar.setVisibility(View.INVISIBLE);
                supass.setError("Passwords doesn't match");
                supassc.setError("Passwords doesn't match");
            }
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            suemail.setError("Invalid email try another");
        }
    }

    private void chkInputs() {
        if (!TextUtils.isEmpty(suemail.getText())) {
            if (!TextUtils.isEmpty(suname.getText())) {
                if (!TextUtils.isEmpty(supass.getText()) && supass.length() >= 8) {
                    if (!TextUtils.isEmpty(supassc.getText()) && supass.equals(supass)) {
                        signupBtn.setEnabled(true);
                        signupBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    } else {
                        signupBtn.setEnabled(false);
                        signupBtn.setTextColor(Color.parseColor("#FFFFFF"));
                    }
                } else {
                    signupBtn.setEnabled(false);
                    signupBtn.setTextColor(Color.parseColor("#FFFFFF"));
                }
            } else {
                signupBtn.setEnabled(false);
                signupBtn.setTextColor(Color.parseColor("#FFFFFF"));
            }
        } else {
            signupBtn.setEnabled(false);
            signupBtn.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }


}
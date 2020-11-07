package com.app.gadgetbridge;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.app.gadgetbridge.adapter.AdapterPostList;
import com.app.gadgetbridge.adapter.AdapterSuggestionSearch;
import com.app.gadgetbridge.connection.API;
import com.app.gadgetbridge.connection.RestAdapter;
import com.app.gadgetbridge.connection.callbacks.CallbackListPost;
import com.app.gadgetbridge.data.Constant;
import com.app.gadgetbridge.model.Page;
import com.app.gadgetbridge.model.Post;
import com.app.gadgetbridge.utils.NetworkCheck;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySearch extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private EditText et_search;

    private RecyclerView recyclerView;
    private AdapterPostList mAdapter;

    private RecyclerView recyclerSuggestion;
    private AdapterSuggestionSearch mAdapterSuggestion;
    private LinearLayout lyt_suggestion;

    private ImageButton bt_clear;
    private ProgressBar progressBar;
    private View parent_view;
    private Call<CallbackListPost> callbackCall = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        parent_vi
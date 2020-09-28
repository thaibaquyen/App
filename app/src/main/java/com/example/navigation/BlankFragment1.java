package com.example.navigation;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;


public class BlankFragment1 extends Fragment {
    private WebView webView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_blank_fragment1,container, false);
        webView = view.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://hairgrab.luyenthib1.com/baquyen/trangchu.php");
        webView.loadUrl("http://103.130.216.98/~buoiphuc/baquyen/do_an_tn/trangchu.php");
        return  view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getuser getuser= new getuser();
        Toast.makeText(getActivity(), getuser.x.toString(),Toast.LENGTH_LONG).show();
    }
}

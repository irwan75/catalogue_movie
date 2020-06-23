package com.example.moviecatalogue.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.moviecatalogue.R;
import com.example.moviecatalogue.controller.utils.value_handle;

public class fragment_setting extends Fragment {

    value_handle chooseLanguange;

    RadioGroup rgLanguange;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rgLanguange = view.findViewById(R.id.rgBahasa);
        chooseLanguange = new value_handle();

        if (chooseLanguange.LANGUANGE.equals("en-US")){
            rgLanguange.check(R.id.lg_english);
        }else if (chooseLanguange.LANGUANGE.equals("fr-FR")){
            rgLanguange.check(R.id.lg_french);
        }else if (chooseLanguange.LANGUANGE.equals("de-DE")){
            rgLanguange.check(R.id.lg_german);
        }else if (chooseLanguange.LANGUANGE.equals("bg-BG")){
            rgLanguange.check(R.id.lg_bulgarian);
        }

        rgLanguange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lg_english:
                        chooseLanguange.LANGUANGE = "en-US";
                        break;
                    case R.id.lg_french:
                        chooseLanguange.LANGUANGE = "fr-FR";
                        break;
                    case R.id.lg_german:
                        chooseLanguange.LANGUANGE = "de-DE";
                        break;
                    case R.id.lg_bulgarian:
                        chooseLanguange.LANGUANGE = "bg-BG";
                        break;
                }
            }
        });

    }

}

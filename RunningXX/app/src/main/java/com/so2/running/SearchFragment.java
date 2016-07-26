package com.so2.running;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(getString(R.string.title_info));

        view = inflater.inflate(R.layout.fragment_search, container, false);


        /**spinner00*/
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnner);
        //建立一個ArrayAdapter物件，並放置下拉選單的內容
        ArrayAdapter adapter = new ArrayAdapter(getActivity(),R.layout.searchspinner,new String[]{"林誠億","吃","大便"});
        //設定下拉選單的樣式
        adapter.setDropDownViewResource(R.layout.searchspinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(getActivity(), "hello",Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getActivity(), "cCCC",Toast.LENGTH_SHORT).show();
            }
        });

        /**spinner01*/
        Spinner spinner1 = (Spinner) view.findViewById(R.id.spinnner1);
        //建立一個ArrayAdapter物件，並放置下拉選單的內容
        ArrayAdapter adapter1 = new ArrayAdapter(getActivity(),R.layout.searchspinner,new String[]{"紅茶","奶茶","綠茶"});
        //設定下拉選單的樣式
        adapter.setDropDownViewResource(R.layout.searchspinner);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(getActivity(), "hello",Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getActivity(), "cCCC",Toast.LENGTH_SHORT).show();
            }
        });
        /**spinner02*/
        Spinner spinner2 = (Spinner) view.findViewById(R.id.spinnner2);
        //建立一個ArrayAdapter物件，並放置下拉選單的內容
        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(),R.layout.searchspinner,new String[]{"紅茶","奶茶","綠茶"});
        //設定下拉選單的樣式
        adapter.setDropDownViewResource(R.layout.searchspinner);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(getActivity(), "hello",Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getActivity(), "cCCC",Toast.LENGTH_SHORT).show();
            }
        });
        /**spinner01*/
        Spinner spinner3 = (Spinner) view.findViewById(R.id.spinnner3);
        //建立一個ArrayAdapter物件，並放置下拉選單的內容
        ArrayAdapter adapter3 = new ArrayAdapter(getActivity(),R.layout.searchspinner,new String[]{"紅茶","奶茶","綠茶"});
        //設定下拉選單的樣式
        adapter.setDropDownViewResource(R.layout.searchspinner);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                Toast.makeText(getActivity(), "hello",Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView arg0) {
                Toast.makeText(getActivity(), "cCCC",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}

package com.example.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView result;
    private Spinner parameter_spinner,number_spinner;
    private Button search;
    private String selected_parameter;
    private Integer selected_number;
    private String[] parameter_data = {"postId","name","email","body","all"};
    private ApiClient apiClient;
    private GetApi getApi;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.result);
        parameter_spinner = findViewById(R.id.parameter);
        number_spinner = findViewById(R.id.number);
        search = findViewById(R.id.search);

        apiClient = new ApiClient();
        getApi = apiClient.getCommentApi().create(GetApi.class);

        setSpinner();
        selected_data();
        search.setOnClickListener(view -> getComment(selected_number,selected_parameter));
    }

    private void setSpinner() {

        ArrayAdapter parameter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                parameter_data
        );

        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            numbers.add(i+1);
        }
        ArrayAdapter number = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                numbers
        );

        parameter_spinner.setAdapter(parameter);
        number_spinner.setAdapter(number);
    }//Spinner的基本設定

    private void selected_data() {
        parameter_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_parameter = parameter_spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        number_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_number = Integer.parseInt(number_spinner.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }//spinner選取選項後的資料

    private void getComment(Integer selectedNumber, String selectedParameter) {
        getApi.getCommentsData(selectedNumber)
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<DataResponse>>() {
                    @Override
                    public void onNext(@NonNull List<DataResponse> dataResponses) {

                        switch (selectedParameter){
                            case "postId":
                                result.setText("postId : "+dataResponses.get(0).getPostId());
                                break;
                            case "name":
                                result.setText("name : "+dataResponses.get(0).getName());
                                break;
                            case "email":
                                result.setText("email : "+dataResponses.get(0).getEmail());
                                break;
                            case "body":
                                result.setText("body : "+dataResponses.get(0).getBody());
                                break;
                            case "all":
                                result.setText("all : "+dataResponses.get(0).toString());
                                break;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        result.setText("抓取資料失敗");
                    }

                    @Override
                    public void onComplete() {;
                    }
                });
    }//結合RxJava的Retrofit
}
package com.lancoder.ttb.imagepickerviewdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lancoder.ttb.imagepicker.listner.ImageShowPickerListener;
import com.lancoder.ttb.imagepicker.model.ImageShowPickerBean;
import com.lancoder.ttb.imagepicker.ui.ImagePickerView;
import com.lancoder.ttb.imagepicker.ui.SelectImageActivity;
import com.lancoder.ttb.imagepicker.utils.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

public class MainActivity extends AppCompatActivity implements ImageShowPickerListener {

    public static final int POST_REQUEST_CODE = 100;

    ImagePickerView mImagePickerView;

    ArrayList<String> al;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageSelector
                        .create()
                        .count(9)
                        .camera(true)
                        .multi()
                        .origenlist(al)
                        .start(MainActivity.this,POST_REQUEST_CODE);
            }
        });

        mImagePickerView = findViewById(R.id.image_show);
        mImagePickerView.setPickerListener(this);
        List<ImageBean> mList = new ArrayList<>();

        mImagePickerView.setImageLoaderInterface(new ImageShowLoader());
        mImagePickerView.setNewData(mList);
        mImagePickerView.setShowAnim(true);
        mImagePickerView.show();
    }

    @Override
    public void addOnClickListner(int remainNum) {
        /*Intent intent = new Intent(this, AlbumActivity.class);
        intent.putExtra(AlbumActivity.ALBUM_IMAGE_COUNT, 9);
        startActivityForResult(intent, POST_REQUEST_CODE);*/
        ImageSelector.create()
                .count(9)
                .camera(true)
                .multi()
                .origenlist(al)
                .start(MainActivity.this,POST_REQUEST_CODE);
    }

    @Override
    public void picOnClickListner(List<ImageShowPickerBean> list, int position, int remainNum) {

    }

    @Override
    public boolean picOnLongClickListner(List<ImageShowPickerBean> list, int position, int remainNum) {
        return false;
    }

    @Override
    public void delOnClickListner(int position, int remainNum) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == POST_REQUEST_CODE && resultCode == SelectImageActivity.SELECT_RESULT_OK) {
            al = data.getStringArrayListExtra(SelectImageActivity.EXTRA_RESULT);

            if(al != null){


                Observable.just(al)
                        .map(new io.reactivex.functions.Function<ArrayList<String>, List<File>>() {
                            @Override
                            public List<File> apply(ArrayList<String> strings) throws Exception {
                                List<File> fileList = new ArrayList<>();
                                for (String path : strings) {
                                    File file = Luban.with(MainActivity.this).load(new File(path)).get();
                                    fileList.add(file);
                                }
                                return fileList;
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new io.reactivex.functions.Consumer<List<File>>() {
                            @Override
                            public void accept(List<File> files) throws Exception {
                                onCompressSuccess(files);
                            }
                        });
            }
        }
    }

    public void onCompressSuccess(List<File> fileList) {
        List<ImageBean> list = new ArrayList<>();
        for (File file : fileList) {
            list.add(new ImageBean(file.getAbsolutePath()));
        }
        mImagePickerView.clearImage();
        mImagePickerView.addData(list);
    }
}

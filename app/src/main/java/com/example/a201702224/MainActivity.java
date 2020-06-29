package com.example.a201702224;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText mMemoEdit = null;
    TextFileManager mTextFileManager = new TextFileManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMemoEdit = (EditText) findViewById(R.id.memo_edit);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 1. 파일에 저장된 메모 텍스트 파일 불러오기
            case R.id.load_btn: {
                String memoData = mTextFileManager.load();
                mMemoEdit.setText(memoData);

                Toast.makeText(this, "불러오기 완료", Toast.LENGTH_LONG).show();
                break;
            }

            // 2. 에디트텍스트에 입력된 메모를 텍스트 파일로 저장하기
            case R.id.save_btn: {
                String memoData = mMemoEdit.getText().toString();
                mTextFileManager.save(memoData);
                mMemoEdit.setText("");

                Toast.makeText(this, "저장 완료", Toast.LENGTH_LONG).show();
                break;
            }

            // 3. 저장된 메모 파일 삭제하기
            case R.id.delete_btn: {
                mTextFileManager.delete();
                mMemoEdit.setText("");

                Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show();
            }
        }
    }
}
class TextFileManager {

    private static final String FILE_NAME = "Memo.txt";
    // 메모 내용을 저장할 파일 이름
    Context mContext = null;

    public TextFileManager(Context context) {
        mContext = context;
    }

    // 파일에 메모를 저장하는 함수
    public void save(String strData) {
        if( strData == null || strData.equals("") ) {
            return;
        }

        FileOutputStream fosMemo = null;

        try {
            // 파일에 데이터를 쓰기 위해서 output 스트림 생성
            fosMemo = mContext.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            // 파일에 메모 적기
            fosMemo.write( strData.getBytes() );
            fosMemo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 저장된 메모를 불러오는 함수
    public String load() {
        try {
            // 파일에서 데이터를 읽기 위해서 input 스트림 생성
            FileInputStream fisMemo = mContext.openFileInput(FILE_NAME);

            // 데이터를 읽어 온 뒤, String 타입 객체로 반환
            byte[] memoData = new byte[fisMemo.available()];
            while (fisMemo.read(memoData) != -1) { }

            return new String(memoData);
        } catch (IOException e) {  }

        return "";
    }

    // 저장된 메모를 삭제하는 함수
    public void delete() {
        mContext.deleteFile(FILE_NAME);
    }
}
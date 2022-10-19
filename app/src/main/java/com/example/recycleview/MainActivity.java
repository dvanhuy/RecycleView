package com.example.recycleview;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Item> arrayItem;
    ItemRecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initView();

        Button chooseimg =(Button) findViewById(R.id.additem);
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getResult.launch(intent);
            }
        });
    }

    public void initView(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclesv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        arrayItem = new ArrayList<>();
        arrayItem.add(new Item(R.drawable.natutochibi,"Uzumaki Naruto","Nhân vật chính trong bộ anime NARUTO. Lạc quan, vui vẻ và thích ăn ramen"));
        arrayItem.add(new Item(R.drawable.saitama,"Saitama","Là một siêu anh hùng và anh dễ dàng đành bại những kẻ thù của mình chỉ bằng một cú đấm"));
        arrayItem.add(new Item(R.drawable.kaitokid,"Kuroba Kaito","Tên trộm khét tiếng với biệt tài chuyên ăn trộm kim cương và đá quý với cái tên Kaito Kid"));
        arrayItem.add(new Item(R.drawable.yagami,"Light Yagami","Cậu nhặt được Death Note với khả năng cho phép người dùng nó giết bất kì ai bằng việc biết mặt và tên đối tượng"));
        arrayItem.add(new Item(R.drawable.kirito,"Kazuto Kirigaya","Kirito là một trong 1.000 người thử nghiệm beta được chọn cho phiên bản beta của Sword Art Online"));
        arrayItem.add(new Item(R.drawable.imgdoraemon,"Doraemon","Cậu là chú mèo robot của tương lai do xưởng Matsushiba sản xuất nhằm mục đích chăm sóc trẻ nhỏ."));
        arrayItem.add(new Item(R.drawable.imggoku,"Son Goku","Son Goku bị mất trí nhớ khi đến trái đất và sau đó trở thành người bảo vệ vĩ đại của Trái Đất"));
        arrayItem.add(new Item(R.drawable.imgmikasa,"Mikasa Ackerman","Cô gái gốc Ackerman này có năng lực chiến đấu can đảm và mạnh mẽ"));
        adapter = new ItemRecycleAdapter(arrayItem,getApplicationContext());
        recyclerView.setAdapter(adapter);
    }


    private ActivityResultLauncher<Intent> getResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Activity.RESULT_OK){
                        arrayItem.add(new Item((Uri) data.getData(),"Mikasa Ackerman","Cô gái gốc Ackerman này có năng lực chiến đấu can đảm và mạnh mẽ"));
                        adapter.notifyDataSetChanged();
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                    }
                }
            }
    );
}
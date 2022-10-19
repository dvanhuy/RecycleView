package com.example.btgkapptonghop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ArrayList<Item> arrayItem = new ArrayList<>();
    ItemRecycleAdapter adapter;
    Uri selectedImg=null;
    Button chooseimg;
    // TODO: Rename and change types of parameters

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (arrayItem.size() == 0)
        {
            initDATA();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclesv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                recyclerView.getContext(),
                layoutManager.getOrientation()
        );

        dividerItemDecoration.setDrawable(
                ContextCompat.getDrawable(getContext(), R.drawable.list_divider)
        );

        recyclerView.addItemDecoration(dividerItemDecoration);

//        arrayItem = new ArrayList<>(); // làm cho danh sách tạo lại
        adapter = new ItemRecycleAdapter(arrayItem,getContext());
        recyclerView.setAdapter(adapter);
        Button additem = view.findViewById(R.id.additem);
        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        return view;
    }

    public void showDialog(View view){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_additem);
        EditText username = (EditText) dialog.findViewById(R.id.editTextTextPersonName2);
        EditText description = (EditText) dialog.findViewById(R.id.editTextTextPersonName3);
        Button submit = (Button) dialog.findViewById(R.id.button2);
        chooseimg =(Button) dialog.findViewById(R.id.editTextTextPersonName);
        chooseimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getResult.launch(intent);
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedImg ==null){
                    arrayItem.add(new Item(R.drawable.quanlyuser,username.getText().toString() ,description.getText().toString()));
                }
                else {
                    arrayItem.add(new Item(selectedImg,username.getText().toString() ,description.getText().toString()));
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private ActivityResultLauncher<Intent> getResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Intent data = result.getData();
                    if (result.getResultCode() == Activity.RESULT_OK){
                        selectedImg = data.getData();
                        chooseimg.setText("Đã chọn ảnh");
                    }
                    if (result.getResultCode() == Activity.RESULT_CANCELED){
                        selectedImg = null;
                        chooseimg.setText("Chưa chọn ảnh");
                    }
                }
            }
    );

    public void initDATA(){
        arrayItem.add(new Item(R.drawable.img_natuto,"Uzumaki Naruto","Nhân vật chính trong bộ anime NARUTO. Câu chuyện xoay quanh Uzumaki Naruto, một ninja trẻ muốn tìm cách khẳng định mình để được mọi người công nhận và nuôi ước mơ trở thành Hokage - người lãnh đạo Làng Lá. Một câu bé vui vẻ , lạc quan và thích ăn ramen"));
        arrayItem.add(new Item(R.drawable.img_saitama,"Saitama","Một siêu anh hùng tên là Saitama, anh có khả năng đánh bại bất cứ đối thủ nào chỉ bằng một cú đấm. Điều này khiến anh cảm thấy hận đời không đối thủ nên luôn muốn gặp một kẻ thù nào đó xứng tầm với sức mạnh quá lớn của mình để thử thách bản thân."));
        arrayItem.add(new Item(R.drawable.img_kaitokid,"Kuroba Kaito","Tài năng chính của Kaito là việc biểu diễn ảo thuật. Anh cũng rất giỏi đột nhập và leo dây bỏ trốn, giả giọng của bất cứ ai mà không cần thiết bị hỗ trợ. Anh cực kỳ thông minh. Hiếm có ai có khả năng thoát khỏi cảnh sát một cách nhẹ nhàng và điêu luyện, một con người thông thuộc mọi loại bẫy, có khả năng mở mọi chiếc khóa trong vài giây"));
        arrayItem.add(new Item(R.drawable.img_yagami,"Light Yagami","Cậu được miêu tả là một thiên tài 17 tuổi xuất chúng, nổi tiếng và cuốn hút nhưng hay chán chường; cậu nhặt được Death Note, một cuốn sổ tay chứa năng lực siêu nhiên từ thế giới khác cho phép người dùng nó giết chết bất kì ai bằng việc biết mặt và tên đối tượng, sau khi cuốn sổ bị Tử thần Ryuk đánh rơi xuống nhân giới"));
        arrayItem.add(new Item(R.drawable.img_kirito,"Kazuto Kirigaya","Kirito là một trong 1.000 người thử nghiệm beta được chọn cho phiên bản beta của Sword Art Online, trò chơi nhập vai trực tuyến nhiều người chơi trong thực tế ảo (VRMMORPG) đầu tiên cho NerveGear và sau đó đã tham gia phiên bản chính thức của trò chơi"));
        arrayItem.add(new Item(R.drawable.img_doraemon,"Doraemon","Doraemon là một chú mèo máy được Nobi Sewashi (Nobi Nobito), cháu năm đời của Nobi Nobita, gửi từ thế kỷ 22 về quá khứ của ông mình để giúp đỡ Nobita trở nên tiến bộ và giàu có, tức là cũng sẽ cải thiện hoàn cảnh của con cháu Nobita sau này. Doraemon có một chiếc túi thần kỳ trước bụng với đủ loại bảo bối của tương lai"));
        arrayItem.add(new Item(R.drawable.img_goku,"Son Goku","Goku là một người Saiya từ hành tinh Vegeta ban đầu được gửi tới Trái Đất khi là trẻ sơ sinh với nhiệm vụ tiêu diệt hành tinh này. Tuy nhiên, một tai nạn xảy ra, Goku bị mất trí nhớ, khiến cậu trở thành một người có tâm hồn thuần khiết, và sau đó trở thành người bảo vệ vĩ đại của Trái Đất"));
        arrayItem.add(new Item(R.drawable.img_mikasa,"Mikasa Ackerman","Mặc dù chỉ muốn sống một cuộc sống yên bình với Eren, Mikasa đã chọn con đường theo cậu gia nhập quân đội, nơi mà cô trở thành người lính giỏi nhất trong khóa huấn luyện 104. Sau khi tốt nghiệp, cô gia nhập Trinh sát binh đoàn để đi theo và bảo vệ Eren."));
    }
}
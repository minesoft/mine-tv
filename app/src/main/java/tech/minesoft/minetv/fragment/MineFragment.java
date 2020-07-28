package tech.minesoft.minetv.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import tech.minesoft.minetv.R;
import tech.minesoft.minetv.bean.MineSiteInfo;
import tech.minesoft.minetv.greendao.DaoHelper;


public class MineFragment extends Fragment {

    private TextView tvUrl;
    private RadioGroup rg;

    public static Fragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);

        Button btn = view.findViewById(R.id.btn_change_url);
        btn.setOnClickListener(v -> {
            // RequestHelper.urlIndex++;
            // if (RequestHelper.urlIndex >= RequestHelper.BASE_URLs.length) {
            //     RequestHelper.urlIndex = 0;
            // }
            changeUrl();
        });

        tvUrl = view.findViewById(R.id.tv_app);

        tvUrl.setText(getActivity().getApplicationInfo().processName);

        tvUrl = view.findViewById(R.id.tv_url);
        MineSiteInfo activeSite = DaoHelper.getPrimarySite();
        tvUrl.setText(String.format("链接：%s\n编号：%s|%s", activeSite.getUrl(), activeSite.getCode(), activeSite.getName()));

        rg = (RadioGroup) view.findViewById(R.id.rg_urls);
        loadRadios();

        return view;
    }

    private void loadRadios() {
        List<MineSiteInfo> activeSites = DaoHelper.getActiveSites();
        boolean first = true;
        for (MineSiteInfo site : activeSites) {
            RadioButton radioButton = new RadioButton(getContext());
            int dimension = (int) (getResources().getDimension(R.dimen.btn_space) + 0.5f);//会自动转化为像素值
            radioButton.setPadding(dimension, 0, 0, 0);
            // radioButton.setButtonDrawable(R.drawable.selector_icon_31_32);
            radioButton.setText(site.getName());
            radioButton.setTextColor(getResources().getColor(R.color.colorWhite));
            //必须有ID，否则默认选中的选项会一直是选中状态
            radioButton.setId(site.getId().intValue());
            if (site.getPrimary() == 1) {
                //默认选中
                radioButton.setChecked(true);
            }

            //layoutParams 设置margin值
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (first) {
                layoutParams.setMargins(0, 0, 0, 0);
                first = false;
            } else {
                int i1 = (int) (getResources().getDimension(R.dimen.btn_row_margin) + 0.5f);
                layoutParams.setMargins(i1, 0, 0, 0);
            }
        }
    }

    private void changeUrl() {
        // String text = "链接：" + RequestHelper.BASE_URLs[RequestHelper.urlIndex] + "\n索引：" + RequestHelper.urlIndex;
        // tvUrl.setText(text);
    }

    private Toast toast = null;

    @SuppressLint("ShowToast")
    public void showText(CharSequence text) {
        try {
            toast.getView().isShown();
            toast.setText(text);
        } catch (Exception e) {
            toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.show();
    }

}

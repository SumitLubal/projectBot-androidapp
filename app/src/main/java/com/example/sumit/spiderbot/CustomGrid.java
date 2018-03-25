package com.example.sumit.spiderbot;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.sumit.spiderbot.Main2Activity.url;

public class CustomGrid extends BaseAdapter {
    private final OkHttpClient client ;
    private Context mContext;
    int[] ids;

    public CustomGrid(Context c, int[] ids) {
        mContext = c;
        this.ids = ids;
        client =new OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.SECONDS).build();
    }


    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        position = position * 3;
        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);
            DiscreteSeekBar seekBar0 = (DiscreteSeekBar) grid.findViewById(R.id.ds_0);
            final TextView textView0 = (TextView) grid.findViewById(R.id.tx_0);
            final int finalPosition = position;
            seekBar0.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                int pos = finalPosition;

                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    Log.d("", "Set progress of servo:" + ids[pos] + " to " + value + "%");
                    textView0.setText(ids[pos] + ":" + value);
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                    //send when we stop touching
                    textView0.setText(ids[pos] + ":" + seekBar.getProgress());
                    sendGetRequest(ids[pos], seekBar.getProgress(),textView0);
                }
            });
            textView0.setText("" + ids[position]);

            DiscreteSeekBar seekBar1 = (DiscreteSeekBar) grid.findViewById(R.id.ds_1);
            final TextView textView1 = (TextView) grid.findViewById(R.id.tx_1);
            seekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                int pos = finalPosition+1;

                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    Log.d("", "Set progress of servo:" + ids[pos] + " to " + value + "%");
                    textView1.setText(ids[pos] + ":" + value);
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                    textView1.setText(ids[pos] + ":" + seekBar.getProgress());
                    sendGetRequest(ids[pos], seekBar.getProgress(),textView1);
                }
            });
            textView1.setText("" + ids[position + 1]);

            DiscreteSeekBar seekBar2 = (DiscreteSeekBar) grid.findViewById(R.id.ds_2);
            final TextView textView2 = (TextView) grid.findViewById(R.id.tx_2);
            seekBar2.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                int pos = finalPosition+2;

                @Override
                public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                    Log.d("", "Set progress of servo:" + ids[pos] + " to " + value + "%");
                    textView2.setText(ids[pos] + ":" + value);
                }

                @Override
                public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                    textView2.setText(ids[pos] + ":" + seekBar.getProgress());
                    sendGetRequest(ids[pos], seekBar.getProgress(),textView2);
                }
            });
            textView2.setText("" + ids[position + 2]);


        } else {
            grid = (View) convertView;
        }

        return grid;
    }

    private void sendGetRequest(final int id, final int progress, final TextView tv) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://"+url+"/polls/?servo_num="+id+"&value="+progress)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        Handler handler = new Handler(mContext.getMainLooper());

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(mContext, , Toast.LENGTH_SHORT).show();
                                final Toast toast = Toast.makeText(mContext, "Missed pos:"+id, Toast.LENGTH_SHORT);
                                toast.show();

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        toast.cancel();
                                    }
                                }, 1000);
                            }
                        });

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //tv.setText(id+":"+":"+progress+"OK");
                        /*try (ResponseBody responseBody = response.body()) {
                            if (!response.isSuccessful())
                                throw new IOException("Unexpected code " + response);

                            Headers responseHeaders = response.headers();
                            for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                            }

                            System.out.println(responseBody.string());
                        }*/
                    }
                });
            }

        }).start();

    }
}
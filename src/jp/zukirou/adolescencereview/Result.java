package jp.zukirou.adolescencereview;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class Result extends Activity {

	int numQ;
	int numMiss;
	int resultScore;
	int eIndexAll;
	int hitokotoNum;
	int resultFlag = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setContentView(R.layout.result);
        
        resultFlag = 1;

		Intent intentQuestionReturn = new Intent(this, Question.class);
		intentQuestionReturn.putExtra("resultFlag", resultFlag);

        
        //Resultで表示する数値を取得
        Bundle extras = getIntent().getExtras();
        numQ = extras.getInt("numQ");
        numMiss = extras.getInt("numMiss");
        resultScore = extras.getInt("resultScore");
        eIndexAll = extras.getInt("eIndexAll");
        
        //数値を表示
		TextView num_Q = (TextView)findViewById(R.id.numQ);
		num_Q.setText(String.valueOf(numQ));

		TextView num_Miss = (TextView)findViewById(R.id.numMiss);
		num_Miss.setText(String.valueOf(numMiss));
		
		TextView rs = (TextView)findViewById(R.id.resultScore);
		rs.setText(String.valueOf(resultScore));
/*
		TextView hitokotoMes = (TextView)findViewById(R.id.hitokoto);
		//
		if(numQ <= (int)eIndexAll * 0.05){hitokotoNum = 0;}
		if(numQ > (int)eIndexAll * 0.05 && numQ <= (int)eIndexAll * 0.10){hitokotoNum = 1;}
		if(numQ > (int)eIndexAll * 0.10 && numQ <= (int)eIndexAll * 0.15){hitokotoNum = 2;}
		if(numQ > (int)eIndexAll * 0.15 && numQ <= (int)eIndexAll * 0.20){hitokotoNum = 3;}
		if(numQ > (int)eIndexAll * 0.20 && numQ <= (int)eIndexAll * 0.25){hitokotoNum = 4;}
		if(numQ > (int)eIndexAll * 0.25 && numQ <= (int)eIndexAll * 0.30){hitokotoNum = 5;}
		if(numQ > (int)eIndexAll * 0.30 && numQ <= (int)eIndexAll * 0.35){hitokotoNum = 6;}		
		if(numQ > (int)eIndexAll * 0.35 && numQ <= (int)eIndexAll * 0.40){hitokotoNum = 7;}
		if(numQ > (int)eIndexAll * 0.40 && numQ <= (int)eIndexAll * 0.45){hitokotoNum = 8;}
		if(numQ > (int)eIndexAll * 0.45 && numQ <= (int)eIndexAll * 0.50){hitokotoNum = 9;}
		if(numQ > (int)eIndexAll * 0.50 && numQ <= (int)eIndexAll * 0.55){hitokotoNum = 10;}
		if(numQ > (int)eIndexAll * 0.55 && numQ <= (int)eIndexAll * 0.60){hitokotoNum = 11;}
		if(numQ > (int)eIndexAll * 0.60 && numQ <= (int)eIndexAll * 0.65){hitokotoNum = 12;}
		if(numQ > (int)eIndexAll * 0.65 && numQ <= (int)eIndexAll * 0.70){hitokotoNum = 13;}
		if(numQ > (int)eIndexAll * 0.70 && numQ <= (int)eIndexAll * 0.75){hitokotoNum = 14;}
		if(numQ > (int)eIndexAll * 0.75 && numQ <= (int)eIndexAll * 0.80){hitokotoNum = 15;}
		if(numQ > (int)eIndexAll * 0.80 && numQ <= (int)eIndexAll * 0.85){hitokotoNum = 16;}
		if(numQ > (int)eIndexAll * 0.85 && numQ <= (int)eIndexAll * 0.99){hitokotoNum = 17;}
		if(numQ == (int)eIndexAll * 1.00){hitokotoNum = 18;}
		
		switch(hitokotoNum){
		case 0:
			hitokotoMes.setText("おや？もう終わりですか？\nどうせやるならマジメにやりましょうよ。");
			break;
		case 1:
			hitokotoMes.setText("あれ？終わり？\nまだ問題はありますよ？");
			break;
		case 2:
			hitokotoMes.setText("まだ１００問を超えたところですね。\nこの程度で満足してはこまりますよ？");
			break;
		case 3:
			hitokotoMes.setText("まだ２００問目に届いていないんですか？\n何をしているのですか？");
			break;
		case 4:
			hitokotoMes.setText("もう飽きましたか？\nそんなことだから、何をやっても長続きしないのです。");
			break;
		case 5:
			hitokotoMes.setText("こんな進み具合では\n大事なタイミングに間に合いませんよ？");
			break;
		case 6:
			hitokotoMes.setText("やっとやる気になりましたか？\nそれとも見せかけですか？");
			break;
		case 7:
			hitokotoMes.setText("そろそろ「やる気」をみせてください。\nそれとも、もう、あきらめましたか？");
			break;
		case 8:
			hitokotoMes.setText("ほんの少しだけ\n「やる気」を感じました。");
			break;
		case 9:
			hitokotoMes.setText("どんなことでもゴールテープを切らなければ\nゴールしたことになりませんよ？");
			break;
		case 10:
			hitokotoMes.setText("やっと折り返しましたね。\n雑念を払って集中しましょう。");
			break;
		case 11:
			hitokotoMes.setText("これまでにない「やる気」を感じます。\n惚れてしまうかもしれません。");
			break;
		case 12:
			hitokotoMes.setText("疲れが見えてきたかもしれませんね。\n多少の休憩は必要ですよ。");
			break;
		case 13:
			hitokotoMes.setText("休憩してばかりでは先に進みません。\nゴールした後の楽しみを糧にしましょう。");
			break;
		case 14:
			hitokotoMes.setText("つらかったら休んでもよいです。\nゆっくりでもいいから少しづつ進みましょう。");
			break;
		case 15:
			hitokotoMes.setText("あなたの信念は揺るぎません。\n周りの人が知らなくとも、自分自身が知っていればいいのです。");
			break;
		case 16:
			hitokotoMes.setText("ゴールに到達した喜びは、ゴールした人にしかわかりません。\nそして、あなたはゴールできる人です。");
			break;
		case 17:
			hitokotoMes.setText("「願い」に手が届く所に来ています。\nあとは、無理せずに背伸びしましょう。");
			break;
		case 18:
			hitokotoMes.setText("あなたは素晴らしい！！");
			break;
		default:
			break;
		}
*/
	}

    //戻るキー（Backキー）を無効にする
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                // ダイアログ表示など特定の処理を行いたい場合はここに記述
                // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返す
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }    
    
    //やめるボタンが押された時
    public void endButton_Click(View target){
    	Intent intent = new Intent();
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	//finish();
    	moveTaskToBack(true);
    }
    
    //タイトルに戻るボタンが押された時
    public void returnTitleButton_Click(View target){
    	Intent intent = new Intent(this, Start.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    }
    
    
}

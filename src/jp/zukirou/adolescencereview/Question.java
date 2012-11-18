package jp.zukirou.adolescencereview;

import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Question extends Activity{
	int eIndexAll = 717;//717 英単語の問題数　チェック用に問題数を減らしたいときは、ここの数を減らす
	int jSelectNum = 4;//日本語選択肢の数
	int[] index = new int[jSelectNum];//選択肢用の配列
	int[] notSelectIndex = new int[eIndexAll]; //すでに出題された英単語チェック用の配列
	int countQuestion = 0;//正解させた英単語の数のカウント
	int ccQ = 0;//現時点での出題数
	int eWordIndex; //問題となる英語の配列番号
	int seikai; //正解の選択肢の配列番号
	int okTimer = 2000; //正解事に、英単語と日本語と○を表示しつづける時間。1000で１秒。この時間が経過すると次の問題になる
	int ngTimer = 800; //×を表示しつづける時間。この時間経過すると×が消える。
	int cd = 1000;//カウントダウンの周期。１秒
	
	int missCount = 0;

	//スコアの加算,減算分
	int addS;
	int decS; 
	
	//カウントダウンの処理に必要なもの
	int count = 10;
	Timer CountDown = new Timer();
	CountTimer countTimer = new CountTimer();
	
	//時間系処理に必要なもの
	Timer waitTimer = new Timer();
	TimerTask timerTask;
	Handler invisibleHandler = new Handler();
	
	int resultFlag = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question);
/*		
        Bundle extras = getIntent().getExtras();
        resultFlag = extras.getInt("resultFlag");        
        if(resultFlag == 1){
        	result(countQuestion, missCount);
        }
*/
		questionE();

	}
	
	public void questionE(){ //出題　（最初、onCreatに入れていたが、繰り返し出題をしたかったので関数化して呼び出すようにした）		

		addS = 5;	//得点に加算分を初期化
		decS = 0;	//得点の減産分を初期化
		
		//制限時間を初期化
		count = 10;			
		TextView ct = (TextView)findViewById(R.id.countdown);
		int currentCount = Integer.parseInt((String) ct.getText());
		currentCount = count;
		ct.setText(String.valueOf(currentCount));
		
		//得点、制限時間の表示
		LinearLayout info = (LinearLayout)findViewById(R.id.Info);
		info.setVisibility(View.VISIBLE);
		
		Button questionWordButton = (Button)findViewById(R.id.eword);
		questionWordButton.setClickable(true);

		Random random = new Random();
		
		//問題となる英単語を取得
		do{
			eWordIndex = random.nextInt(eIndexAll);
		}while(eQuestionCheck(eWordIndex));//出題されたことがあるかをチェック
		
		//問題となる英単語を表示
		TextView questionWord = (TextView)findViewById(R.id.eword);
		questionWord.setText(ejWords.DATA[eWordIndex][0]);
		notSelectIndex[eWordIndex] = -1; //すでに出題した英単語は出題しないフラグ
		
						
		//重複しない日本語を４件取得する
		do{
			for(int i = 0; i < jSelectNum; i++){
				index[i] = random.nextInt(717);
			}
		}while(isDupulicate(index));
		
		//正解の日本語を表示する選択肢を決める
		seikai = random.nextInt(4);		
	}
	
	//英単語にタッチしたときの処理（一番上の問いかけを非表示に。選択肢表示）
	public void onClickQuestion(View target){
		
		ccQ ++;
		
		//一番上の問いかけを非表示
		TextView visibleToikake01 = (TextView)findViewById(R.id.toikake01);
		visibleToikake01.setVisibility(View.INVISIBLE);

		//日本語選択中に英単語にタッチできないようにする
		Button questionWordButton = (Button)findViewById(R.id.eword); 
		questionWordButton.setClickable(false);
		
		//日本語選択のといかけテキスト表示
		TextView visibleToikake02 = (TextView)findViewById(R.id.toikake02);
		visibleToikake02.setVisibility(View.VISIBLE);
		
		//4つのボタンのアクセス先を取得
		TextView[] arrayTextView = new TextView[jSelectNum];
		arrayTextView[0] = (TextView)findViewById(R.id.jword00);
		arrayTextView[1] = (TextView)findViewById(R.id.jword01);
		arrayTextView[2] = (TextView)findViewById(R.id.jword02);
		arrayTextView[3] = (TextView)findViewById(R.id.jword03);

		//4つのボタンに間違いの日本語選択肢を表示
		for (int i = 0; i< 4; i++){
			arrayTextView[i].setVisibility(View.VISIBLE);
			arrayTextView[i].setText(ejWords.DATA[index[i]][1]);
		}
		//4つのボタンのうち一つに正解の日本語選択肢を表示
		index[seikai] = eWordIndex;
		arrayTextView[seikai].setText(ejWords.DATA[index[seikai]][1]);
		
		//日本語選択肢のボタンにタッチできるようにする
		Button jWord00Button = (Button)findViewById(R.id.jword00);
		jWord00Button.setClickable(true);
		Button jWord01Button = (Button)findViewById(R.id.jword01);
		jWord01Button.setClickable(true);
		Button jWord02Button = (Button)findViewById(R.id.jword02);
		jWord02Button.setClickable(true);
		Button jWord03Button = (Button)findViewById(R.id.jword03);
		jWord03Button.setClickable(true);
		
		//カウントダウン開始
		countTimer = new CountTimer();
		CountDown.schedule(countTimer, 0, 1000);
		TextView ct = (TextView)findViewById(R.id.countdown);
		ct.setTextColor(Color.RED);
		ct.setVisibility(View.VISIBLE);
	}
	
	//選択肢をタッチした時の処理
	public void onClickjword00(View target){
		hantei(index[0]);                                          //正誤判定
		Button jWord00Button = (Button)findViewById(R.id.jword00);//ボタンの
		jWord00Button.setClickable(false);                        //複数タッチを禁止
	}
	public void onClickjword01(View target){
		hantei(index[1]);
		Button jWord01Button = (Button)findViewById(R.id.jword01);
		jWord01Button.setClickable(false);
	}	
	public void onClickjword02(View target){
		hantei(index[2]);
		Button jWord02Button = (Button)findViewById(R.id.jword02);
		jWord02Button.setClickable(false);
	}	
	public void onClickjword03(View target){
		hantei(index[3]);
		Button jWord03Button = (Button)findViewById(R.id.jword03);
		jWord03Button.setClickable(false);
	}
	
	//日本語選択肢をタッチした後の処理
	public void hantei(int selectID){
			
		//正解時に不正解の日本語を消すためにボタンのTextViewを取得しとく
		TextView[] jSelect = new TextView[4];
		jSelect[0] = (TextView)findViewById(R.id.jword00);
		jSelect[1] = (TextView)findViewById(R.id.jword01);
		jSelect[2] = (TextView)findViewById(R.id.jword02);
		jSelect[3] = (TextView)findViewById(R.id.jword03);
				
		//正解・不正解時のpngを設定
		FrameLayout ok = (FrameLayout)findViewById(R.id.okMark);
		FrameLayout ng = (FrameLayout)findViewById(R.id.ngMark);
		
		//正誤判定
		if (selectID == eWordIndex){	//正解時
			
			LinearLayout info = (LinearLayout)findViewById(R.id.Info);
			info.setVisibility(View.INVISIBLE);
						
			//○を表示
			ok.setVisibility(View.VISIBLE);
			
			//スコア加算
			TextView Score = (TextView)findViewById(R.id.score_value);
			int currentScore = Integer.parseInt((String) Score.getText()); 
			currentScore += addS;
			Score.setText(String.valueOf(currentScore)); 
		
			//全ての日本語選択肢と「選択肢選んで」を非表示にする
			for (int i = 0; i< 4; i++){
				jSelect[i].setVisibility(View.GONE);
			}
			TextView visibleToikake02 = (TextView)findViewById(R.id.toikake02);
			visibleToikake02.setVisibility(View.GONE);
			
			//正解選択肢を表示
			jSelect[seikai].setVisibility(View.VISIBLE);
			jSelect[seikai].setText(ejWords.DATA[index[seikai]][1]);
			
			//カウントダウンの一時停止
			countTimer.cancel();
//			countTimer = null;

//			TextView ct = (TextView)findViewById(R.id.countdown);
//			ct.setVisibility(View.INVISIBLE);

			//正解した問題の数をカウント
			countQuestion ++;
			
			//全ての問題を解答したら終了
//			if(countQuestion > eIndexAll){ コメントが「すべての問題を正解したら終了」とわけわかんないことになっていた。
			if(ccQ >= eIndexAll){
				result(ccQ, missCount);
			}else{
				//正解した後の処理。okTimer後にnextQuestionを実行
				try{
					Timer waitTimer = new Timer();
					NextQuestion nextQuestion = new NextQuestion();
					waitTimer.schedule(nextQuestion, okTimer);
				}catch (Exception e){}
			}
			
		}else if(selectID != eWordIndex){	//不正解時
			
			missCount++;
			
			int visibility;
			visibility = View.VISIBLE;
			ng.setVisibility(visibility); //×を表示
			
			//スコア減算
			TextView Score = (TextView)findViewById(R.id.score_value);
			int currentScore = Integer.parseInt((String) Score.getText());
			decS ++;
			currentScore -= decS;
			Score.setText(String.valueOf(currentScore)); 

			if (visibility == View.VISIBLE){ 
				try{
					NgInvisible ngInvisible = new NgInvisible();
					waitTimer.schedule(ngInvisible, ngTimer);//ngTimer後にngInvisibleを実行（×を一定時間で消す　）
				}catch (Exception e){}
			}
		}		
	}
	
	//不正解で×を表示したあとの処理。×を消去する
	class NgInvisible extends TimerTask{ 
		public void run(){
			invisibleHandler.post(new Runnable(){
				public void run(){
					FrameLayout ng = (FrameLayout)findViewById(R.id.ngMark);
					ng.setVisibility(View.INVISIBLE);
				}
			});
		}
	}
	
	//正解で○を表示した後の処理。○、正解選択肢を消去して、次の問題を表示する
	class NextQuestion extends TimerTask{ 
		public void run(){
			invisibleHandler.post(new Runnable(){
				public void run(){
					FrameLayout ok = (FrameLayout)findViewById(R.id.okMark);
					ok.setVisibility(View.INVISIBLE);

					TextView[] jSelect = new TextView[4];
					jSelect[0] = (TextView)findViewById(R.id.jword00);
					jSelect[1] = (TextView)findViewById(R.id.jword01);
					jSelect[2] = (TextView)findViewById(R.id.jword02);
					jSelect[3] = (TextView)findViewById(R.id.jword03);
					jSelect[seikai].setVisibility(View.INVISIBLE);
										
					TextView visibleToikake01 = (TextView)findViewById(R.id.toikake01);
					visibleToikake01.setVisibility(View.VISIBLE);
					
					LinearLayout info = (LinearLayout)findViewById(R.id.Info);
					info.setVisibility(View.VISIBLE);
					
					questionE();
				}
			});
		}
	}
	
	
	class CountTimer extends TimerTask{
		public void run(){
			invisibleHandler.post(new Runnable(){
				public void run(){
					if(count > 0){
						TextView ct = (TextView)findViewById(R.id.countdown);
						int currentCount = Integer.parseInt((String) ct.getText());
						count --;
						currentCount = count;
						ct.setText(String.valueOf(currentCount));
					}else{
						result(ccQ, missCount);
					}
				}
			});
		}
	}		

			
	//結果画面へ遷移
	public void result(int numQ, int numMiss){
		
		CountDown.cancel();

		TextView rScore = (TextView)findViewById(R.id.score_value);
		int resultScore = Integer.parseInt((String) rScore.getText()); 

		//Resultに必要な数値を渡す
		Intent intent = new Intent(this, Result.class);
		intent.putExtra("numQ", numQ);
		intent.putExtra("numMiss", numMiss);
		intent.putExtra("resultScore", resultScore);
		intent.putExtra("eIndexAll", eIndexAll);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	//英単語がすでに出題されていたかどうかをチェック
	private boolean eQuestionCheck(int eWordIndex){ 
		if(notSelectIndex[eWordIndex] < 0){
			return true;
		}else{
			return false;
		}
	}
	//選択肢の重複チェック
	private boolean isDupulicate(int[] index){
		Arrays.sort(index);
		for (int i = 0; i < (index.length -1); i++){
			if (index[i] == index[i + 1]) return true;
		}
		return false;
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
	
	
}	



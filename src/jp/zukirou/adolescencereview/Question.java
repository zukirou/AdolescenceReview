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
	int eIndexAll = 717;//717 �p�P��̖�萔�@�`�F�b�N�p�ɖ�萔�����炵�����Ƃ��́A�����̐������炷
	int jSelectNum = 4;//���{��I�����̐�
	int[] index = new int[jSelectNum];//�I�����p�̔z��
	int[] notSelectIndex = new int[eIndexAll]; //���łɏo�肳�ꂽ�p�P��`�F�b�N�p�̔z��
	int countQuestion = 0;//�����������p�P��̐��̃J�E���g
	int ccQ = 0;//�����_�ł̏o�萔
	int eWordIndex; //���ƂȂ�p��̔z��ԍ�
	int seikai; //�����̑I�����̔z��ԍ�
	int okTimer = 2000; //�������ɁA�p�P��Ɠ��{��Ɓ���\�����Â��鎞�ԁB1000�łP�b�B���̎��Ԃ��o�߂���Ǝ��̖��ɂȂ�
	int ngTimer = 800; //�~��\�����Â��鎞�ԁB���̎��Ԍo�߂���Ɓ~��������B
	int cd = 1000;//�J�E���g�_�E���̎����B�P�b
	
	int missCount = 0;

	//�X�R�A�̉��Z,���Z��
	int addS;
	int decS; 
	
	//�J�E���g�_�E���̏����ɕK�v�Ȃ���
	int count = 10;
	Timer CountDown = new Timer();
	CountTimer countTimer = new CountTimer();
	
	//���Ԍn�����ɕK�v�Ȃ���
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
	
	public void questionE(){ //�o��@�i�ŏ��AonCreat�ɓ���Ă������A�J��Ԃ��o��������������̂Ŋ֐������ČĂяo���悤�ɂ����j		

		addS = 5;	//���_�ɉ��Z����������
		decS = 0;	//���_�̌��Y����������
		
		//�������Ԃ�������
		count = 10;			
		TextView ct = (TextView)findViewById(R.id.countdown);
		int currentCount = Integer.parseInt((String) ct.getText());
		currentCount = count;
		ct.setText(String.valueOf(currentCount));
		
		//���_�A�������Ԃ̕\��
		LinearLayout info = (LinearLayout)findViewById(R.id.Info);
		info.setVisibility(View.VISIBLE);
		
		Button questionWordButton = (Button)findViewById(R.id.eword);
		questionWordButton.setClickable(true);

		Random random = new Random();
		
		//���ƂȂ�p�P����擾
		do{
			eWordIndex = random.nextInt(eIndexAll);
		}while(eQuestionCheck(eWordIndex));//�o�肳�ꂽ���Ƃ����邩���`�F�b�N
		
		//���ƂȂ�p�P���\��
		TextView questionWord = (TextView)findViewById(R.id.eword);
		questionWord.setText(ejWords.DATA[eWordIndex][0]);
		notSelectIndex[eWordIndex] = -1; //���łɏo�肵���p�P��͏o�肵�Ȃ��t���O
		
						
		//�d�����Ȃ����{����S���擾����
		do{
			for(int i = 0; i < jSelectNum; i++){
				index[i] = random.nextInt(717);
			}
		}while(isDupulicate(index));
		
		//�����̓��{���\������I���������߂�
		seikai = random.nextInt(4);		
	}
	
	//�p�P��Ƀ^�b�`�����Ƃ��̏����i��ԏ�̖₢�������\���ɁB�I�����\���j
	public void onClickQuestion(View target){
		
		ccQ ++;
		
		//��ԏ�̖₢�������\��
		TextView visibleToikake01 = (TextView)findViewById(R.id.toikake01);
		visibleToikake01.setVisibility(View.INVISIBLE);

		//���{��I�𒆂ɉp�P��Ƀ^�b�`�ł��Ȃ��悤�ɂ���
		Button questionWordButton = (Button)findViewById(R.id.eword); 
		questionWordButton.setClickable(false);
		
		//���{��I���̂Ƃ������e�L�X�g�\��
		TextView visibleToikake02 = (TextView)findViewById(R.id.toikake02);
		visibleToikake02.setVisibility(View.VISIBLE);
		
		//4�̃{�^���̃A�N�Z�X����擾
		TextView[] arrayTextView = new TextView[jSelectNum];
		arrayTextView[0] = (TextView)findViewById(R.id.jword00);
		arrayTextView[1] = (TextView)findViewById(R.id.jword01);
		arrayTextView[2] = (TextView)findViewById(R.id.jword02);
		arrayTextView[3] = (TextView)findViewById(R.id.jword03);

		//4�̃{�^���ɊԈႢ�̓��{��I������\��
		for (int i = 0; i< 4; i++){
			arrayTextView[i].setVisibility(View.VISIBLE);
			arrayTextView[i].setText(ejWords.DATA[index[i]][1]);
		}
		//4�̃{�^���̂�����ɐ����̓��{��I������\��
		index[seikai] = eWordIndex;
		arrayTextView[seikai].setText(ejWords.DATA[index[seikai]][1]);
		
		//���{��I�����̃{�^���Ƀ^�b�`�ł���悤�ɂ���
		Button jWord00Button = (Button)findViewById(R.id.jword00);
		jWord00Button.setClickable(true);
		Button jWord01Button = (Button)findViewById(R.id.jword01);
		jWord01Button.setClickable(true);
		Button jWord02Button = (Button)findViewById(R.id.jword02);
		jWord02Button.setClickable(true);
		Button jWord03Button = (Button)findViewById(R.id.jword03);
		jWord03Button.setClickable(true);
		
		//�J�E���g�_�E���J�n
		countTimer = new CountTimer();
		CountDown.schedule(countTimer, 0, 1000);
		TextView ct = (TextView)findViewById(R.id.countdown);
		ct.setTextColor(Color.RED);
		ct.setVisibility(View.VISIBLE);
	}
	
	//�I�������^�b�`�������̏���
	public void onClickjword00(View target){
		hantei(index[0]);                                          //���딻��
		Button jWord00Button = (Button)findViewById(R.id.jword00);//�{�^����
		jWord00Button.setClickable(false);                        //�����^�b�`���֎~
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
	
	//���{��I�������^�b�`������̏���
	public void hantei(int selectID){
			
		//�������ɕs�����̓��{����������߂Ƀ{�^����TextView���擾���Ƃ�
		TextView[] jSelect = new TextView[4];
		jSelect[0] = (TextView)findViewById(R.id.jword00);
		jSelect[1] = (TextView)findViewById(R.id.jword01);
		jSelect[2] = (TextView)findViewById(R.id.jword02);
		jSelect[3] = (TextView)findViewById(R.id.jword03);
				
		//�����E�s��������png��ݒ�
		FrameLayout ok = (FrameLayout)findViewById(R.id.okMark);
		FrameLayout ng = (FrameLayout)findViewById(R.id.ngMark);
		
		//���딻��
		if (selectID == eWordIndex){	//������
			
			LinearLayout info = (LinearLayout)findViewById(R.id.Info);
			info.setVisibility(View.INVISIBLE);
						
			//����\��
			ok.setVisibility(View.VISIBLE);
			
			//�X�R�A���Z
			TextView Score = (TextView)findViewById(R.id.score_value);
			int currentScore = Integer.parseInt((String) Score.getText()); 
			currentScore += addS;
			Score.setText(String.valueOf(currentScore)); 
		
			//�S�Ă̓��{��I�����Ɓu�I�����I��Łv���\���ɂ���
			for (int i = 0; i< 4; i++){
				jSelect[i].setVisibility(View.GONE);
			}
			TextView visibleToikake02 = (TextView)findViewById(R.id.toikake02);
			visibleToikake02.setVisibility(View.GONE);
			
			//����I������\��
			jSelect[seikai].setVisibility(View.VISIBLE);
			jSelect[seikai].setText(ejWords.DATA[index[seikai]][1]);
			
			//�J�E���g�_�E���̈ꎞ��~
			countTimer.cancel();
//			countTimer = null;

//			TextView ct = (TextView)findViewById(R.id.countdown);
//			ct.setVisibility(View.INVISIBLE);

			//�����������̐����J�E���g
			countQuestion ++;
			
			//�S�Ă̖����𓚂�����I��
//			if(countQuestion > eIndexAll){ �R�����g���u���ׂĂ̖��𐳉�������I���v�Ƃ킯�킩��Ȃ����ƂɂȂ��Ă����B
			if(ccQ >= eIndexAll){
				result(ccQ, missCount);
			}else{
				//����������̏����BokTimer���nextQuestion�����s
				try{
					Timer waitTimer = new Timer();
					NextQuestion nextQuestion = new NextQuestion();
					waitTimer.schedule(nextQuestion, okTimer);
				}catch (Exception e){}
			}
			
		}else if(selectID != eWordIndex){	//�s������
			
			missCount++;
			
			int visibility;
			visibility = View.VISIBLE;
			ng.setVisibility(visibility); //�~��\��
			
			//�X�R�A���Z
			TextView Score = (TextView)findViewById(R.id.score_value);
			int currentScore = Integer.parseInt((String) Score.getText());
			decS ++;
			currentScore -= decS;
			Score.setText(String.valueOf(currentScore)); 

			if (visibility == View.VISIBLE){ 
				try{
					NgInvisible ngInvisible = new NgInvisible();
					waitTimer.schedule(ngInvisible, ngTimer);//ngTimer���ngInvisible�����s�i�~����莞�Ԃŏ����@�j
				}catch (Exception e){}
			}
		}		
	}
	
	//�s�����Ł~��\���������Ƃ̏����B�~����������
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
	
	//�����Ł���\��������̏����B���A����I�������������āA���̖���\������
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

			
	//���ʉ�ʂ֑J��
	public void result(int numQ, int numMiss){
		
		CountDown.cancel();

		TextView rScore = (TextView)findViewById(R.id.score_value);
		int resultScore = Integer.parseInt((String) rScore.getText()); 

		//Result�ɕK�v�Ȑ��l��n��
		Intent intent = new Intent(this, Result.class);
		intent.putExtra("numQ", numQ);
		intent.putExtra("numMiss", numMiss);
		intent.putExtra("resultScore", resultScore);
		intent.putExtra("eIndexAll", eIndexAll);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	//�p�P�ꂪ���łɏo�肳��Ă������ǂ������`�F�b�N
	private boolean eQuestionCheck(int eWordIndex){ 
		if(notSelectIndex[eWordIndex] < 0){
			return true;
		}else{
			return false;
		}
	}
	//�I�����̏d���`�F�b�N
	private boolean isDupulicate(int[] index){
		Arrays.sort(index);
		for (int i = 0; i < (index.length -1); i++){
			if (index[i] == index[i + 1]) return true;
		}
		return false;
	}
	
    //�߂�L�[�iBack�L�[�j�𖳌��ɂ���
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
                // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ�
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }    
	
	
}	



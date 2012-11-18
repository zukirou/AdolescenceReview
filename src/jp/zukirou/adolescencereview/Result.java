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

        
        //Result�ŕ\�����鐔�l���擾
        Bundle extras = getIntent().getExtras();
        numQ = extras.getInt("numQ");
        numMiss = extras.getInt("numMiss");
        resultScore = extras.getInt("resultScore");
        eIndexAll = extras.getInt("eIndexAll");
        
        //���l��\��
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
			hitokotoMes.setText("����H�����I���ł����H\n�ǂ������Ȃ�}�W���ɂ��܂��傤��B");
			break;
		case 1:
			hitokotoMes.setText("����H�I���H\n�܂����͂���܂���H");
			break;
		case 2:
			hitokotoMes.setText("�܂��P�O�O��𒴂����Ƃ���ł��ˁB\n���̒��x�Ŗ������Ă͂��܂�܂���H");
			break;
		case 3:
			hitokotoMes.setText("�܂��Q�O�O��ڂɓ͂��Ă��Ȃ���ł����H\n�������Ă���̂ł����H");
			break;
		case 4:
			hitokotoMes.setText("�����O���܂������H\n����Ȃ��Ƃ�����A��������Ă����������Ȃ��̂ł��B");
			break;
		case 5:
			hitokotoMes.setText("����Ȑi�݋�ł�\n�厖�ȃ^�C�~���O�ɊԂɍ����܂����H");
			break;
		case 6:
			hitokotoMes.setText("����Ƃ��C�ɂȂ�܂������H\n����Ƃ����������ł����H");
			break;
		case 7:
			hitokotoMes.setText("���낻��u���C�v���݂��Ă��������B\n����Ƃ��A�����A������߂܂������H");
			break;
		case 8:
			hitokotoMes.setText("�ق�̏�������\n�u���C�v�������܂����B");
			break;
		case 9:
			hitokotoMes.setText("�ǂ�Ȃ��Ƃł��S�[���e�[�v��؂�Ȃ����\n�S�[���������ƂɂȂ�܂����H");
			break;
		case 10:
			hitokotoMes.setText("����Ɛ܂�Ԃ��܂����ˁB\n�G�O�𕥂��ďW�����܂��傤�B");
			break;
		case 11:
			hitokotoMes.setText("����܂łɂȂ��u���C�v�������܂��B\n����Ă��܂���������܂���B");
			break;
		case 12:
			hitokotoMes.setText("��ꂪ�����Ă�����������܂���ˁB\n�����̋x�e�͕K�v�ł���B");
			break;
		case 13:
			hitokotoMes.setText("�x�e���Ă΂���ł͐�ɐi�݂܂���B\n�S�[��������̊y���݂�Ƃɂ��܂��傤�B");
			break;
		case 14:
			hitokotoMes.setText("�炩������x��ł��悢�ł��B\n�������ł��������班���Âi�݂܂��傤�B");
			break;
		case 15:
			hitokotoMes.setText("���Ȃ��̐M�O�͗h�邬�܂���B\n����̐l���m��Ȃ��Ƃ��A�������g���m���Ă���΂����̂ł��B");
			break;
		case 16:
			hitokotoMes.setText("�S�[���ɓ��B������т́A�S�[�������l�ɂ����킩��܂���B\n�����āA���Ȃ��̓S�[���ł���l�ł��B");
			break;
		case 17:
			hitokotoMes.setText("�u�肢�v�Ɏ肪�͂����ɗ��Ă��܂��B\n���Ƃ́A���������ɔw�L�т��܂��傤�B");
			break;
		case 18:
			hitokotoMes.setText("���Ȃ��͑f���炵���I�I");
			break;
		default:
			break;
		}
*/
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
    
    //��߂�{�^���������ꂽ��
    public void endButton_Click(View target){
    	Intent intent = new Intent();
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	//finish();
    	moveTaskToBack(true);
    }
    
    //�^�C�g���ɖ߂�{�^���������ꂽ��
    public void returnTitleButton_Click(View target){
    	Intent intent = new Intent(this, Start.class);
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
    }
    
    
}

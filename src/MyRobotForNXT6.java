/**
 * 実機NXT用ロボットクラス
 */
public class MyRobotForNXT6 extends Robot
{
	/** leJOS での起動用 main 関数 */
	static void main(String[] args) { 
		try {
			// 時間計測
			Long time = System.currentTimeMillis();

			// ロボットオブジェクトを生成して実行
			new MyRobotForNXT6().run();

			time = (System.currentTimeMillis() - time) / 1000;
			System.out.println("Time = "+time.intValue() + "sec");

			// 7秒待ってから停止
			Thread.sleep(7000);
		}catch (InterruptedException e) {
			;
		}
	}

	/**
	 * 実行用関数
	 */
	public void run() throws InterruptedException
	{
		/* 学習した最適政策を表す配列 */
		// 最高記録 Run: 599.8cm
		int[] q = new int[8];
		q[0] = 9;
		q[1] = 3;
		q[2] = 0;
		q[3] = 1;
		q[4] = 6;
		q[5] = 0;
		q[6] = 2;
		q[7] = 2;

		while (true) {
			/* 現在の状態を観測 */
			int state = getState();

			/* 行動を選択する */
			int action = q[state];

			/* その状態における最適な行動を実行 */
			move(action);

			// delay()メソッドは、Robot.javaに応じていずれかの機能を実行する
			// 速度調整＆画面描画 (シミュレーター用)
			// ESCAPEボタン押下時に割り込みを発生させる (実機NXT用)
			delay();

			// ゴールに到達すれば終了
			if (isOnGoal())
				break;
		}
	}
	
	public int getState(){
		
		int stateNum = 0;

		// LIGHT_A : 右センサー ||| LIGHT_B : 真ん中センサー ||| LIGHT_C : 左センサー
		if(getColor(LIGHT_A) == WHITE && getColor(LIGHT_B) == WHITE && getColor(LIGHT_C) == WHITE)
			stateNum = 0;
		else if(getColor(LIGHT_A) == WHITE && getColor(LIGHT_B) == WHITE && getColor(LIGHT_C) == BLACK)
			stateNum = 1;
		else if(getColor(LIGHT_A) == WHITE && getColor(LIGHT_B) == BLACK && getColor(LIGHT_C) == WHITE)
			stateNum = 2;
		else if(getColor(LIGHT_A) == WHITE && getColor(LIGHT_B) == BLACK && getColor(LIGHT_C) == BLACK)
			stateNum = 3;
		else if(getColor(LIGHT_A) == BLACK && getColor(LIGHT_B) == WHITE && getColor(LIGHT_C) == WHITE)
			stateNum = 4;
		else if(getColor(LIGHT_A) == BLACK && getColor(LIGHT_B) == WHITE && getColor(LIGHT_C) == BLACK)
			stateNum = 5;
		else if(getColor(LIGHT_A) == BLACK && getColor(LIGHT_B) == BLACK && getColor(LIGHT_C) == WHITE)
			stateNum = 6;
		else if(getColor(LIGHT_A) == BLACK && getColor(LIGHT_B) == BLACK && getColor(LIGHT_C) == BLACK)
			stateNum = 7;

		return stateNum;
	}

	/**
	 * ロボットを移動する
	 */
	public void move(int action)
	{
		// 0:LEFT 1:RIGHT
		// 壁がないことを確認して移動する
		if(action == 0) // STRAIGHT
			goStraight(1);
		else if(action == 1) // LEFT
			turnLeft(15);
		else if(action == 3) // LEFT
			turnLeft(30);
		else if(action == 5) // LEFT
			turnLeft(45);
		else if(action == 7) // LEFT
			turnLeft(60);
		else if(action == 9) // LEFT
			turnLeft(75);
		else if(action == 11) // LEFT
			turnLeft(90);
		else if(action == 2) // RIGHT
			turnRight(15);
		else if(action == 4) // RIGHT
			turnRight(30);
		else if(action == 6) // RIGHT
			turnRight(45);
		else if(action == 8) // RIGHT
			turnRight(60);
		else if(action == 10) // RIGHT
			turnRight(75);
		else if(action == 12) // RIGHT
			turnRight(90);
		
		// ロボットの位置座標を更新
		if(action != 0)
			goStraight(1);
	}
	
	public void turnLeft(int angle){
		if(getColor(LIGHT_A) == WHITE || getColor(LIGHT_C) == BLACK)
			rotateLeft(angle);
	}
	public void turnRight(int angle){
		if(getColor(LIGHT_A) == BLACK || getColor(LIGHT_C) == WHITE)
			rotateRight(angle);
	}
	public void goStraight(int moveSpeed){
		if(getColor(LIGHT_B) == BLACK)
			forward(moveSpeed);
	}
}

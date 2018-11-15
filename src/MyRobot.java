/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
public class MyRobot extends Robot
{
	public int reward = 0; // 頑張って取得する
	/**
	 * 実行用関数
	 */
	public void run() throws InterruptedException
	{
		// ここをがんばって作る

		// step 1:	Q学習する
		// QLearningのインスタンスを作る	
		int states = 8; // 状態数
		int actions = 3;	// 行動数
		double alpha = 0.5; // 学習率
		double gamma = 0.5; // 割引率

		QLearning ql = new QLearning(states, actions, alpha, gamma);

		int trials = 500; //500 // 強化学習の試行回数 
		int steps = 500; //100 // 1試行あたりの最大ステップ数

		// 試行回数だけ繰り返し
		for(int t = 0; t < trials; t++){
			//System.out.println("This is new loop testttttttttttt: " + t);

			/* ロボットを初期位置に戻す */
			init();
			int error = 0;
			
			// ステップ数だけ繰り返し
			for(int s = 0; s < steps; s++){

				/*--------------- ε-Greedy 法により行動を選択 ---------------*/
				// 現在の状態番号を取得する
				int state = getState();

				// ランダムに行動を選択する確率
				double epsilon = 0.5;
				int action = ql.selectAction(state, epsilon);
				//int action = ql.selectAction(state);
				
//				System.out.println("Debugggggg 1");
				
				/*--------------- 選択した行動を実行 (ロボットを移動する) ---------------*/
				moveRobotCar(action);
//				System.out.println("Debugggggg 2");

				/*--------------- 新しい状態を観測＆報酬を得る ---------------*/
				//次の状態番号
				int	after = getState(); // 頑張って取得する
				//System.out.println(after);
				
				// 状態afterにおける報酬
				//int reward = 0; // 頑張って取得する

				// Goal に到達したら 100 報酬を与え、普通の通路なら -10
				if(isOnGoal())
					reward = 1000;
//				else if(after == 7)
//					reward = 50;
//				else if(after == 0)
//					reward = -50;
				else if(reward != -100 && getColor(LIGHT_B) == BLACK)
					reward = 50;
//				else if(getColor(LIGHT_B) == WHITE)
//					reward = -100;

				/*--------------- Q 値を更新 ---------------*/
				// qlインスタンスから呼び出す
				ql.update(state, action, after, reward);

				/*
				// 速度調整＆画面描画
				delay();
				//*/
//				if((state == after) && (after == 0))
//					error++;
//				
//				if(error > 5)
//					break;
				
				// ゴールに到達すれば終了
				if (isOnGoal())
					break;

			}
		}

		/* 学習終了で、最後の成果を出す */
		init(); // ロボットを初期位置に戻す
		finalRunTest(ql);

		// Done
		System.out.println("Done!!!!!!!!!!!!!!!!!");
		//System.exit(0);
	}
	
	public int getState(){
		
		int stateNum = 0;

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
	public void moveRobotCar(int action)
	{
		int rotatedAngle = 1;
		
		// 0:LEFT 1:RIGHT
		// 壁がないことを確認して移動する
		if(action == 0){ // STRAIGHT
			goStraight(1);
			System.out.println("Moved Straight!!!!!!!!!!");
		}
		else if(action == 1){ // LEFT
			do{
				//turnLeft(5);
				rotateLeft(5);
				rotatedAngle += 5;
				
				if(rotatedAngle > 160){
//					rotateRight(160);
					reward = -100;
					break;
				}
				
//				System.out.println("Debugggggg Left");
			}while(getColor(LIGHT_B) != BLACK);
			System.out.println("RotatedAngle from LefT: " + rotatedAngle);
		}
		else if(action == 2){ // RIGHT
			do{
				//turnRight(5);
				rotateRight(5);
				rotatedAngle += 5;

				if(rotatedAngle > 160){
//					rotateLeft(160);
					reward = -100;
					break;
				}
				
//				System.out.println("Debugggggg Right");
			}while(getColor(LIGHT_B) != BLACK);
			System.out.println("RotatedAngle from RIGHT: " + rotatedAngle);
		}
//		else if(action == 3) // LEFT
//			turnLeft(15);
//		else if(action == 4) // RIGHT
//			turnRight(15);
//		else if(action == 5) // LEFT
//			turnLeft(25);
//		else if(action == 6) // RIGHT
//			turnRight(25);
//		else if(action == 7) // LEFT
//			turnLeft(35);
//		else if(action == 8) // RIGHT
//			turnRight(35);
//		else if(action == 9) // LEFT
//			turnLeft(45);
//		else if(action == 10) // RIGHT
//			turnRight(45);
//		else if(action == 11) // LEFT
//			turnLeft(55);
//		else if(action == 12) // RIGHT
//			turnRight(55);
//		else if(action == 13) // LEFT
//			turnLeft(65);
//		else if(action == 14) // RIGHT
//			turnRight(65);
//		else if(action == 15) // LEFT
//			turnLeft(75);
//		else if(action == 16) // RIGHT
//			turnRight(75);
//		else if(action == 17) // LEFT
//			turnLeft(85);
//		else if(action == 18) // RIGHT
//			turnRight(85);
//		else if(action == 19) // LEFT
//			turnLeft(95);
//		else if(action == 20) // RIGHT
//			turnRight(95);
//		else if(action == 21) // LEFT
//			turnLeft(105);
//		else if(action == 22) // RIGHT
//			turnRight(105);
//		else if(action == 23) // LEFT
//			turnLeft(115);
//		else if(action == 24) // RIGHT
//			turnRight(115);
//		else if(action == 25) // LEFT
//			turnLeft(125);
//		else if(action == 26) // RIGHT
//			turnRight(125);
//		else if(action == 27) // LEFT
//			turnLeft(135);
//		else if(action == 28) // RIGHT
//			turnRight(135);
//		else if(action == 29) // LEFT
//			turnLeft(145);
//		else if(action == 30) // RIGHT
//			turnRight(145);
//		else if(action == 31) // LEFT
//			turnLeft(155);
//		else if(action == 32) // RIGHT
//			turnRight(155);
//		else if(action == 33) // LEFT
//			turnLeft(165);
//		else if(action == 34) // RIGHT
//			turnRight(165);
//		else if(action == 5) // STRAIGHT
//			goStraight(5);
		
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
	
	public void finalRunTest(QLearning ql) throws InterruptedException{

		while(true){
			// 現在の状態番号を取得する
			int state = getState();
			
			// デバッグ用
			System.out.println("A:" + getColor(LIGHT_A) + " B:" + getColor(LIGHT_B) + " C:" + getColor(LIGHT_C));

			// ランダムに行動を選択する確率
			int action = ql.selectAction(state);

			// 選択した行動を実行 (ロボットを移動する)
			moveRobotCar(action);

			// 速度調整＆画面描画
			delay();

			// ゴールに到達すれば終了
			if (isOnGoal())
				break;
		}
		
	}
	
	void trash(){

		/*
		// 右センサの色に応じて分岐
		switch (getColor(LIGHT_A)) {

		case BLACK:
			// 黒を検知 => 右回転 => 前進
			if(getColor(LIGHT_C) == WHITE)
				rotateRight(35);
			else
				rotateRight(5);
			break;

		case WHITE:
			// 白を検知 => 左回転 => 前進
			rotateLeft(10);
			break;

		}

		// 左センサの色に応じて分岐
		switch (getColor(LIGHT_C)) {

		case BLACK:
			// 黒を検知 => 左回転 => 前進
			if(getColor(LIGHT_A) == WHITE)
				rotateLeft(35);
			else
				rotateLeft(5);
			break;

		case WHITE:
			// 白を検知 => 右回転 => 前進
			rotateRight(10);
			break;

		}
		//*/
		/*
		if(getColor(LIGHT_B) == BLACK)
			forward(1);

		/*
		// 中央センサの色に応じて分岐
		switch (getColor(LIGHT_B)) {

		case BLACK:
			// 黒を検知 => 前進
			forward(1);
			break;

		case WHITE:
			// 白を検知 => 後退
			//backward(1);
			break;

		}
		//*/
	}
}

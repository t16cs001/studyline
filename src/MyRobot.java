/**
 * ロボットクラスの作成例：単純なライントレーサーロボット
 */
public class MyRobot extends Robot
{
	/**
	 * 実行用関数
	 */
	public void run() throws InterruptedException
	{
		while (true) {

			// デバッグ用
			System.out.println("A:" + getColor(LIGHT_A) + " B:" + getColor(LIGHT_B) + " C:" + getColor(LIGHT_C));

			// ここをがんばって作る

			// step 1:	Q学習する
			// QLearningのインスタンスを作る	
			int states = 8; // 状態数
			int actions = 2;	// 行動数
			double alpha = 0.5; // 学習率
			double gamma = 0.5; // 割引率

			QLearning ql = new QLearning(states, actions, alpha, gamma);

			int trials = 500; // 強化学習の試行回数 
			int steps = 100; // 1試行あたりの最大ステップ数

			// 試行回数だけ繰り返し
			for(int t = 0; t < trials; t++){
				//System.out.println("This is new loop testttttttttttt: " + t);

				/* ロボットを初期位置に戻す */

				// ステップ数だけ繰り返し
				for(int s = 0; s < steps; s++){
					
					int state;
					
					if()
						state = 0;
					else if()
						state = 1;
					else if()
						state = 2;
					else if()
						state = 3;
					else if()
						state = 4;
					else if()
						state = 5;
					else if()
						state = 6;
					else if()
						state = 7;
					
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

					/*--------------- ε-Greedy 法により行動を選択 ---------------*/
					// 現在の状態番号を取得する
					int state = 1;
					// ランダムに行動を選択する確率
					double epsilon = 0.5;
					int action = ql.selectAction(state, epsilon);
					
					/*--------------- 選択した行動を実行 (ロボットを移動する) ---------------*/
					moveRobot(action);
					
					/*--------------- 新しい状態を観測＆報酬を得る ---------------*/
					//次の状態番号
					int	after = 1; // 頑張って取得する
					//System.out.println(after);

					// 状態afterにおける報酬
					int reward = 0; // 頑張って取得する

					// Goal に到達したら 100 報酬を与え、元状態と変わらない (壁に移動する) なら -10、普通の通路なら -1
					if(x == gx && y == gy)
						reward = 100;
					else if(after == state)
						reward = -10;
					else
						reward = -1;

					/*--------------- Q 値を更新 ---------------*/
					// qlインスタンスから呼び出す
					ql.update(state, action, after, reward);

					// 速度調整＆画面描画
					delay();

					// ゴールに到達すれば終了
					if (isOnGoal())
						break;

				}
			}
			
			// Done
			System.out.println("Done!!!!!!!!!!!!!!!!!");
			System.exit(0);
		}
	}
	
	/**
	 * ロボットを移動する
	 */
	public void moveRobot(int action)
	{
		// 0:LEFT 1:RIGHT
		// 壁がないことを確認して移動する
		if(action == 0) // LEFT
			rotateLeft(10);
		else if(action == 1) // RIGHT
			rotateRight(10);

		// ロボットの位置座標を更新
		forward(1);
	}
	
}

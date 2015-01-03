package org.yyama.moneycounter.us;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/*
 * 
 */
public class MainActivity extends Activity implements OnClickListener,
		OnTouchListener {
	int[] centValue = { 1, 5, 10, 25, 50, 99999, 100, 200, 500, 1000, 2000,
			5000, 10000 };
	Map<Integer, Integer> data = new HashMap<Integer, Integer>();
	CountDialog cDialog = new CountDialog();
	Map<Integer, Integer> idMapNum = new HashMap<Integer, Integer>();
	Map<Integer, Integer> idMapSum = new HashMap<Integer, Integer>();
	Map<Integer, Integer> idMapTxt = new HashMap<Integer, Integer>();
	CountDialog countDialog;
	int[] numIds = { R.id.Num1cent, R.id.Num5cent, R.id.Num10cent,
			R.id.Num25cent, R.id.Num50cent, R.id.Num100cent, R.id.Num1dol,
			R.id.Num2dol, R.id.Num5dol, R.id.Num10dol, R.id.Num20dol,
			R.id.Num50dol, R.id.Num100dol };
	int[] sumIds = { R.id.Sum1cent, R.id.Sum5cent, R.id.Sum10cent,
			R.id.Sum25cent, R.id.Sum50cent, R.id.Sum100cent, R.id.Sum1dol,
			R.id.Sum2dol, R.id.Sum5dol, R.id.Sum10dol, R.id.Sum20dol,
			R.id.Sum50dol, R.id.Sum100dol };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		for (int i : centValue) {
			data.put(i, 0);
		}

		((Button) findViewById(R.id.btnClear)).setOnClickListener(this);

		((TextView) findViewById(R.id.Text100dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text50dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text20dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text10dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text5dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text2dol)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text1dol)).setOnClickListener(this);

		((TextView) findViewById(R.id.Text100cent)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text50cent)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text25cent)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text10cent)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text5cent)).setOnClickListener(this);
		((TextView) findViewById(R.id.Text1cent)).setOnClickListener(this);

		findViewById(R.id.img100dol).setOnTouchListener(this);
		findViewById(R.id.img50dol).setOnTouchListener(this);
		findViewById(R.id.img20dol).setOnTouchListener(this);
		findViewById(R.id.img10dol).setOnTouchListener(this);
		findViewById(R.id.img5dol).setOnTouchListener(this);
		findViewById(R.id.img2dol).setOnTouchListener(this);
		findViewById(R.id.img1dol).setOnTouchListener(this);
		findViewById(R.id.img100cent).setOnTouchListener(this);
		findViewById(R.id.img50cent).setOnTouchListener(this);
		findViewById(R.id.img25cent).setOnTouchListener(this);
		findViewById(R.id.img10cent).setOnTouchListener(this);
		findViewById(R.id.img5cent).setOnTouchListener(this);
		findViewById(R.id.img1cent).setOnTouchListener(this);

		idMapNum.put(R.id.Text1cent, R.id.Num1cent);
		idMapNum.put(R.id.Text5cent, R.id.Num5cent);
		idMapNum.put(R.id.Text10cent, R.id.Num10cent);
		idMapNum.put(R.id.Text25cent, R.id.Num25cent);
		idMapNum.put(R.id.Text50cent, R.id.Num50cent);
		idMapNum.put(R.id.Text100cent, R.id.Num100cent);
		idMapNum.put(R.id.Text1dol, R.id.Num1dol);
		idMapNum.put(R.id.Text2dol, R.id.Num2dol);
		idMapNum.put(R.id.Text5dol, R.id.Num5dol);
		idMapNum.put(R.id.Text10dol, R.id.Num10dol);
		idMapNum.put(R.id.Text20dol, R.id.Num20dol);
		idMapNum.put(R.id.Text50dol, R.id.Num50dol);
		idMapNum.put(R.id.Text100dol, R.id.Num100dol);

		idMapSum.put(R.id.Text1cent, R.id.Sum1cent);
		idMapSum.put(R.id.Text5cent, R.id.Sum5cent);
		idMapSum.put(R.id.Text10cent, R.id.Sum10cent);
		idMapSum.put(R.id.Text25cent, R.id.Sum25cent);
		idMapSum.put(R.id.Text50cent, R.id.Sum50cent);
		idMapSum.put(R.id.Text100cent, R.id.Sum100cent);
		idMapSum.put(R.id.Text1dol, R.id.Sum1dol);
		idMapSum.put(R.id.Text2dol, R.id.Sum2dol);
		idMapSum.put(R.id.Text5dol, R.id.Sum5dol);
		idMapSum.put(R.id.Text10dol, R.id.Sum10dol);
		idMapSum.put(R.id.Text20dol, R.id.Sum20dol);
		idMapSum.put(R.id.Text50dol, R.id.Sum50dol);
		idMapSum.put(R.id.Text100dol, R.id.Sum100dol);

		int idx = 0;
		idMapTxt.put(R.id.Text1cent, centValue[idx++]);
		idMapTxt.put(R.id.Text5cent, centValue[idx++]);
		idMapTxt.put(R.id.Text10cent, centValue[idx++]);
		idMapTxt.put(R.id.Text25cent, centValue[idx++]);
		idMapTxt.put(R.id.Text50cent, centValue[idx++]);
		idMapTxt.put(R.id.Text100cent, centValue[idx++]);
		idMapTxt.put(R.id.Text1dol, centValue[idx++]);
		idMapTxt.put(R.id.Text2dol, centValue[idx++]);
		idMapTxt.put(R.id.Text5dol, centValue[idx++]);
		idMapTxt.put(R.id.Text10dol, centValue[idx++]);
		idMapTxt.put(R.id.Text20dol, centValue[idx++]);
		idMapTxt.put(R.id.Text50dol, centValue[idx++]);
		idMapTxt.put(R.id.Text100dol, centValue[idx]);

		addSetting();
		add2Init();
		add2Setting();
		load(FILE_NAME);
		draw();
	}

	private boolean isStarted;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (!isStarted) {
			initVew();
			isStarted = true;
		}
	}

	private void initVew() {
		int hight = (int) ((findViewById(R.id.Text100dol).getHeight()) * 0.8);
		Log.d("yyama", "高さ" + String.valueOf(hight));
		ImageView iv;

		iv = (ImageView) findViewById(R.id.img100cent);
		int orgH = iv.getHeight();
		Log.d("yyama", "オリジナルの高さ" + orgH);
		int orgW = iv.getWidth();
		Log.d("yyama", "オリジナルの幅" + orgW);
		double raito = (double) hight / (double) orgH;
		Log.d("yyama", "比率" + raito);
		TableRow.LayoutParams prm = new TableRow.LayoutParams(
				(int) (orgW * raito), hight);
		prm.rightMargin = 0;
		prm.leftMargin = 0;
		prm.weight = 0;
		prm.gravity = Gravity.CENTER;

		// 100ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img100dol);
		iv.setLayoutParams(prm);
		Log.d("yyama", "100ドルの幅：" + iv.getWidth());

		// 50ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img50dol);
		iv.setLayoutParams(prm);
		// 20ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img20dol);
		iv.setLayoutParams(prm);
		// 10ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img10dol);
		iv.setLayoutParams(prm);
		// 5ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img5dol);
		iv.setLayoutParams(prm);
		// 2ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img2dol);
		iv.setLayoutParams(prm);
		// 1ドルイメージを表示
		iv = (ImageView) findViewById(R.id.img1dol);
		iv.setLayoutParams(prm);
		// 100セントイメージを表示
		iv = (ImageView) findViewById(R.id.img100cent);
		iv.setLayoutParams(prm);
		Log.d("yyama", "100セントの幅：" + iv.getWidth());
		// 50セントイメージを表示
		iv = (ImageView) findViewById(R.id.img50cent);
		iv.setLayoutParams(prm);
		Log.d("yyama", "50セントの幅：" + iv.getWidth());
		// 25セントイメージを表示
		iv = (ImageView) findViewById(R.id.img25cent);
		iv.setLayoutParams(prm);
		// 10セントイメージを表示
		iv = (ImageView) findViewById(R.id.img10cent);
		iv.setLayoutParams(prm);
		// 5セントイメージを表示
		iv = (ImageView) findViewById(R.id.img5cent);
		iv.setLayoutParams(prm);
		// 1セントイメージを表示
		iv = (ImageView) findViewById(R.id.img1cent);
		iv.setLayoutParams(prm);

		// iv.setScaleY(hight);
		// iv.setMaxHeight(10);
		// iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
		// iv.setImageResource(R.drawable.dol100);
	}

	private AdView adView;

	private void addSetting() {
		// adView を作成する
		adView = new AdView(this);
		adView.setAdUnitId("ca-app-pub-2505812570403600/7577517376");
		adView.setAdSize(AdSize.BANNER);

		// 属性 android:id="@+id/mainLayout" が与えられているものとして
		// LinearLayout をルックアップする
		LinearLayout layout = (LinearLayout) findViewById(R.id.adSpace);

		// adView を追加する
		layout.addView(adView);

		// 一般的なリクエストを行う
		AdRequest adRequest = new AdRequest.Builder().addTestDevice(
				"2D6B2CDFA13324C63449E43857621522").build();
		// AdRequest adRequest = new AdRequest.Builder().build();

		// 広告リクエストを行って adView を読み込む
		adView.loadAd(adRequest);

	}

	private InterstitialAd interstitial;

	// 全画面広告の方。
	private void add2Init() {
		// インタースティシャルを作成する。
		interstitial = new InterstitialAd(this);
		interstitial.setAdUnitId("ca-app-pub-2505812570403600/1530983777");

		// Set the AdListener.
		interstitial.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				add2Setting();
			}
		});
	}

	// 全画面広告の方。1回表示するたびにロードしなおす必要があるみたい。
	private void add2Setting() {
		if (!interstitial.isLoaded()) {
			Log.d("yyama", "未ロードのため、リクエストします。");
			// 広告リクエストを作成する。
			AdRequest adRequest = new AdRequest.Builder().addTestDevice(
					"2D6B2CDFA13324C63449E43857621522").build();
			// インタースティシャルの読み込みを開始する。
			interstitial.loadAd(adRequest);
		} else {
		}
	}

	private void draw() {
		int sum = 0;
		for (int i = 0; i < numIds.length; i++) {
			// Log.d("yyama", ((TextView) findViewById(numIds[i])).getText()
			// .toString());
			((TextView) findViewById(numIds[i])).setText(" "
					+ String.format("%,d", data.get(centValue[i])));
			int num = 0;
			if (centValue[i] == 99999) {
				num = 100;
			} else {
				num = centValue[i];
			}
			((TextView) findViewById(sumIds[i])).setText(String.format(
					"$ %,.2f", (double) ((double) data.get(centValue[i])
							* (double) num / 100.0d)));
			sum += data.get(centValue[i]) * num;
		}
		((TextView) findViewById(R.id.allSum)).setText(String.format("$ %,.2f",
				(double) sum / 100.0d));
	}

	@Override
	public void onClick(final View v) {
		add2Setting();
		if (v.getId() == R.id.btnClear) {
			Clear();
		} else {
			showCountDialog(v);
		}
	}

	private void save() {
		OutputStream out;
		try {
			out = openFileOutput(FILE_NAME, MODE_PRIVATE);
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,
					"UTF-8"));
			// 追記する
			for (int i = 0; i < centValue.length; i++) {
				writer.append(centValue[i] + "," + data.get(centValue[i])
						+ System.lineSeparator());
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public final static String FILE_NAME = "CountDialog.dat";

	private void load(String fileName) {
		InputStream in;
		String lineBuffer;

		try {
			in = openFileInput(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			while ((lineBuffer = reader.readLine()) != null) {
				String[] strs = lineBuffer.split(",");
				Integer kind = Integer.parseInt(strs[0]);
				data.put(kind, Integer.parseInt(strs[1]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void showCountDialog(final View v) {
		LayoutInflater li = LayoutInflater.from(this);
		final View dialog = li.inflate(R.layout.dialog, null);
		int now = getNow(v.getId());
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		((TextView) dialog.findViewById(R.id.edit))
				.setText(String.valueOf(now));
		AlertDialog.Builder builder = new Builder(this);
		builder.setView(dialog);
		builder.setTitle(((TextView) v).getText() + " ");
		builder.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface df, int which) {
				String editStr = ((EditText) dialog.findViewById(R.id.edit))
						.getText().toString();
				int num;
				// 数値判定
				try {
					num = Integer.parseInt(editStr);
				} catch (NumberFormatException e) {
					return;
				}
				Log.d("yyama2", String.valueOf(v.getId()));
				data.put(idMapTxt.get(v.getId()), num);
				save();
				draw();
			}
		});
		((Button) dialog.findViewById(R.id.btnPlus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnPlus50))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus1))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus5))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus10))
				.setOnClickListener(cDialog);
		((Button) dialog.findViewById(R.id.btnMinus50))
				.setOnClickListener(cDialog);
		builder.show();
	}

	private void Clear() {
		// 確認ダイアログの生成
		AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
		alertDlg.setTitle(getString(R.string.all_crear_confirm_title));
		alertDlg.setMessage(getString(R.string.all_crear_confirm_msg));
		alertDlg.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				for (int i : centValue) {
					data.put(i, 0);
				}
				save();
				draw();
			}
		});
		alertDlg.setNegativeButton(getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDlg.create().show();
	}

	private int getNow(int id) {
		String str = ((TextView) findViewById(idMapNum.get(id))).getText()
				.toString().replace(",", "").replace(" ", "");
		Log.d("yyama", str);
		int now = Integer.parseInt(str);
		Log.d("yyama", String.valueOf(now));
		return now;
	}

	@Override
	public void onPause() {
		adView.pause();
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		adView.resume();
	}

	@Override
	public void onDestroy() {
		adView.destroy();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public static final int REQUEST_CD = 100;

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case R.id.screenshot:
			Screenshot.saveScreen(this);
			if (interstitial.isLoaded()) {
				Log.d("yyama", "インターステシャルはロードされています。");
				interstitial.show();
			} else {
				Log.d("yyama", "インターステシャルはロードされていません。");
			}
			add2Setting();
			break;
		case R.id.file_save:
			// ファイル保存
			saveLocalFile();
			break;
		case R.id.file_open:
			// ファイル選択アクティビティを表示
			Intent intent = new Intent(this, LocalFileActivity.class);
			this.startActivityForResult(intent, REQUEST_CD);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveLocalFile() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final EditText edit = new EditText(this);
		edit.setInputType(InputType.TYPE_CLASS_TEXT);
		builder.setView(edit);
		builder.setTitle(this.getString(R.string.please_memo));

		builder.setNegativeButton(R.string.cancel,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		builder.setPositiveButton(R.string.saveFile,
				new AlertDialog.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String fileName = String.format(
								"MC_%1$tY%1$tm%1$td_%1$tH%1$tM%1$tS_%2$s.txt",
								Calendar.getInstance(), edit.getText());
						PrintWriter pw = null;
						try {
							pw = new PrintWriter(MainActivity.this
									.openFileOutput(fileName,
											Activity.MODE_PRIVATE));
							// 追記する
							for (int i : centValue) {
								pw.append(i + "," + data.get(i)
										+ System.lineSeparator());
							}
							pw.flush();
							Toast.makeText(MainActivity.this,
									R.string.has_been_saved, Toast.LENGTH_LONG)
									.show();
							if (interstitial.isLoaded()) {
								Log.d("yyama", "インターステシャルはロードされています。");
								Random rnd = new Random();
								if (rnd.nextInt(4) == 0) {
									interstitial.show();
								}
							} else {
								Log.d("yyama", "インターステシャルはロードされていません。");
							}
							add2Setting();

						} catch (Exception e) {
							Toast.makeText(MainActivity.this,
									R.string.failed_to_save, Toast.LENGTH_LONG)
									.show();
						} finally {
							if (!(pw == null)) {
								pw.close();
							}
						}
					}
				});
		final AlertDialog dialog = builder.create();
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					dialog.getButton(AlertDialog.BUTTON_POSITIVE)
							.performClick();
				}
				return true;
			}
		});
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				// ソフトキーボードを出す
				InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.showSoftInput(edit, 0);
			}
		});
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CD) {
			switch (resultCode) {
			case RESULT_OK:
				try {
					load(data.getStringExtra("file_name"));
					save();
					draw();
					Toast.makeText(
							this,
							getString(R.string.opend_file)
									+ System.lineSeparator()
									+ data.getStringExtra("file_title"),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(this, getString(R.string.file_open_error),
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_CANCELED:
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent arg1) {
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			ColorFilter cf = new LightingColorFilter(Color.rgb(157, 204, 224),
					getResources().getColor(R.color.blue));
			((ImageView) v).setColorFilter(cf);
			v.setBackgroundColor(getResources().getColor(R.color.skyblue));
			break;
		}
		case MotionEvent.ACTION_UP: {
			switch (v.getId()) {
			case R.id.img100dol: {
				findViewById(R.id.Text100dol).performClick();
				break;
			}
			case R.id.img50dol: {
				findViewById(R.id.Text50dol).performClick();
				break;
			}
			case R.id.img20dol: {
				findViewById(R.id.Text20dol).performClick();
				break;
			}
			case R.id.img10dol: {
				findViewById(R.id.Text10dol).performClick();
				break;
			}
			case R.id.img5dol: {
				findViewById(R.id.Text5dol).performClick();
				break;
			}
			case R.id.img2dol: {
				findViewById(R.id.Text2dol).performClick();
				break;
			}
			case R.id.img1dol: {
				findViewById(R.id.Text1dol).performClick();
				break;
			}
			case R.id.img100cent: {
				findViewById(R.id.Text100cent).performClick();
				break;
			}
			case R.id.img50cent: {
				findViewById(R.id.Text50cent).performClick();
				break;
			}
			case R.id.img25cent: {
				findViewById(R.id.Text25cent).performClick();
				break;
			}
			case R.id.img10cent: {
				findViewById(R.id.Text10cent).performClick();
				break;
			}
			case R.id.img5cent: {
				findViewById(R.id.Text5cent).performClick();
				break;
			}
			case R.id.img1cent: {
				findViewById(R.id.Text1cent).performClick();
				break;
			}

			}
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE: {
			((ImageView) v).setColorFilter(null);
			v.setBackgroundColor(Color.TRANSPARENT);
			break;
		}
		}
		return true;
	}
}
